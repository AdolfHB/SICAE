const db = require("../config/db")

// Buscar usuarios dinámicamente
exports.findUser = async (filters) => {
    let query = `SELECT * FROM "usuarioFullInfo"`;
    const conditions = [];
    const params = [];

    // Búsqueda por username
    if (filters.username) {
        conditions.push(`username = $${params.length + 1}`);
        params.push(filters.username);
    }

    // Búsqueda por email
    if (filters.email) {
        conditions.push(`email = $${params.length + 1}`);
        params.push(filters.email);
    }

    // Búsqueda por ID
    if (filters.idUsuario) {
        conditions.push(`"idUsuario" = $${params.length + 1}`);
        params.push(filters.idUsuario);
    }

    // Búsqueda por clave de usuario
    if (filters.claveUsuario) {
        conditions.push(`"claveUsuario" = $${params.length + 1}`);
        params.push(filters.claveUsuario);
    }

    // Excluir un ID específico
    if (filters.excludeId) {
        conditions.push(`"idUsuario" != $${params.length + 1}`);
        params.push(filters.excludeId);
    }

    // Agregar WHERE si hay condiciones
    if (conditions.length > 0) {
        query += ` WHERE ` + conditions.join(" AND ");
    }

    // Limitar a un solo resultado
    query += ` LIMIT 1`;

    const result = await db.query(query, params);
    return result.rows[0];
};

// Verificar si username existe
exports.usernameExists = async (username, excludeId = null) => {
    let query = `SELECT "idUsuario" FROM usuario WHERE username = $1`;
    let params = [username];
    if (excludeId) {
        query += ` AND "idUsuario" != $2`;
        params.push(excludeId);
    }
    const result = await db.query(query, params);
    return result.rows.length > 0;
};

exports.emailExists = async (email, excludeId = null) => {
    const filters = { email };
    if (excludeId) {
        filters.excludeId = excludeId;
    }
    const user = await exports.findUser(filters);
    return !!user;
};

// Obtener todas las claves de usuario existentes
exports.getAllClavesUsuario = async () => {
    const query = `SELECT "claveUsuario" FROM usuario WHERE "claveUsuario" IS NOT NULL`;
    const result = await db.query(query);
    return result.rows.map(row => row.claveUsuario);
};

// Registrar nuevo usuario
exports.create = async (userData) => {
    const query = `
        INSERT INTO usuario (
            nombre, "apellidoPaterno", "apellidoMaterno", "claveUsuario",
            email, telefono, username, password, estatus,
            "idRol", "idTipoUsuario", "idProgramaEducativo", "tiempoCreacion"
        ) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, '1', $9, $10, $11, NOW())
        RETURNING "idUsuario"
    `;
    const params = [
        userData.nombre,
        userData.apellidoPaterno,
        userData.apellidoMaterno || null,
        userData.claveUsuario,
        userData.email,
        userData.telefono || null,
        userData.username,
        userData.password,
        userData.idRol,
        userData.idTipoUsuario,
        userData.idProgramaEducativo
    ];
    const result = await db.query(query, params);
    return result.rows[0];
};

// Actualizar usuario
exports.update = async (idUsuario, userData) => {
    const query = `
        UPDATE usuario 
        SET nombre = $1, "apellidoPaterno" = $2, "apellidoMaterno" = $3,
            email = $4, telefono = $5,
            "idRol" = $6, "idTipoUsuario" = $7, "idProgramaEducativo" = $8,
            "tiempoActualizacion" = NOW()
        WHERE "idUsuario" = $9
        RETURNING "idUsuario"
    `;
    const params = [
        userData.nombre,
        userData.apellidoPaterno,
        userData.apellidoMaterno || null,
        userData.email,
        userData.telefono || null,
        userData.idRol,
        userData.idTipoUsuario,
        userData.idProgramaEducativo,
        idUsuario
    ];
    const result = await db.query(query, params);
    return result.rows[0];
};

// Cambiar estatus del usuario
exports.changeStatus = async (idUsuario, estatus) => {
    const query = `
        UPDATE usuario 
        SET estatus = $1, "tiempoActualizacion" = NOW()
        WHERE "idUsuario" = $2
        RETURNING "idUsuario"
    `;
    const result = await db.query(query, [estatus, idUsuario]);
    return result.rows[0];
};


