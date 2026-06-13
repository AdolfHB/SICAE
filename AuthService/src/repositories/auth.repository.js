const db = require("../config/db")

exports.login = async (userData) =>{
    const query = `
    SELECT "idUsuario", "nombre", "apellidoPaterno", "apellidoMaterno", 
    "claveUsuario", "email", "telefono", "username", "password", "estatus", 
    "idRol", "rol", "idTipoUsuario", "tipoUsuario", "programaEducativo"
    FROM "usuarioFullInfo"
    WHERE username = $1 
    `;
    params = [userData.username];
    const result = await db.query(query,params);
    return result.rows[0];
};
