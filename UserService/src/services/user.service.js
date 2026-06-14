const repo = require("../repositories/user.repository");
const ResponseUser = require("../models/responses/response.user");
const { hashPassword, comparePassword } = require("../utils/bcrypt");
const generateClaveUsuario = require("../utils/generarClave");


const validateEmail = (email) => {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
};

const validateRequiredFields = (data, required) => {
    const missing = required.filter(field => !data[field] || data[field].toString().trim() === '');
    return missing;
};

exports.testApi = async () => {
    return "The test was succesful... (Authentication Microservices)"
};

// Registrar usuario 
exports.register = async (userData, authenticatedUser) => {
    try {
        // Validar que quien crea es ADMIN
        if (!authenticatedUser || authenticatedUser.rol !== "administrador") {
            return ResponseUser.fail("Solo administradores pueden registrar usuarios");
        }

        const required = ['nombre', 'apellidoPaterno', 'username', 'password', 'email', 
            'idRol', 'idTipoUsuario', 'idProgramaEducativo'];
        const missing = validateRequiredFields(userData, required);
        if (missing.length > 0) {
            return ResponseUser.fail(`Datos faltantes: ${missing.join(', ')}`);
        }

        if (!validateEmail(userData.email)) {
            return ResponseUser.fail("Formato de correo inválido");
        }

        if (userData.nombre.length < 2 || userData.nombre.length > 50) {
            return ResponseUser.fail("El nombre debe tener entre 2 y 50 caracteres");
        }
        if (userData.username.length < 3 || userData.username.length > 30) {
            return ResponseUser.fail("El nombre de usuario debe tener entre 3 y 30 caracteres");
        }
        if (userData.password.length < 6) {
            return ResponseUser.fail("La contraseña debe tener al menos 6 caracteres");
        }
        

        const usernameExists = await repo.findUser({ username: userData.username });
        if (usernameExists) {
            return ResponseUser.fail("El username ya está registrado");
        }

        const emailExists = await repo.findUser({ email: userData.email });
        if (emailExists) {
            return ResponseUser.fail("El correo ya está registrado");
        }

        const existingClaves = await repo.getAllClavesUsuario();
        const claveUsuario = await generateClaveUsuario(userData.nombre, userData.apellidoPaterno, existingClaves);

        const hashedPassword = await hashPassword(userData.password);

        const newUser = {
            ...userData,
            claveUsuario: claveUsuario,
            password: hashedPassword
        };

        const result = await repo.create(newUser);

        if (result && result.idUsuario) {
            const createdUser = await repo.findUser({ idUsuario: result.idUsuario });
            return ResponseUser.success(createdUser, "Usuario registrado exitosamente");
        }
        return ResponseUser.fail("Error al registrar usuario");

    } catch (error) {
        console.error("Error en register:", error);
        return ResponseUser.fail("Error interno al registrar usuario");
    }
};

// Editar usuario 
exports.update = async (idUsuario, userData, authenticatedUser) => {
    try {
        // Verificar que el usuario existe
        const existingUser = await repo.findUser({ idUsuario });
        if (!existingUser) {
            return ResponseUser.fail("Usuario no encontrado");
        }

        if (parseInt(authenticatedUser.idUsuario) !== parseInt(idUsuario) && 
        authenticatedUser.rol !== "administrador") {
            return ResponseUser.fail("No tienes permiso para editar este usuario");
        }

        const required = ['nombre', 'apellidoPaterno', 'email', 'idRol', 'idTipoUsuario', 'idProgramaEducativo'];
        const missing = validateRequiredFields(userData, required);
        if (missing.length > 0) {
            return ResponseUser.fail(`Datos faltantes: ${missing.join(', ')}`);
        }

        if (!validateEmail(userData.email)) {
            return ResponseUser.fail("Formato de correo inválido");
        }

        const emailExists = await repo.findUser({ email: userData.email, excludeId: idUsuario });
        if (emailExists) {
            return ResponseUser.fail("El correo ya está registrado por otro usuario");
        }

        const result = await repo.update(idUsuario, userData);

        if (result) {
            const updatedUser = await repo.findUser({ idUsuario });
            return ResponseUser.success(updatedUser, "Usuario actualizado exitosamente");
        }
        return ResponseUser.fail("Error al actualizar usuario");

    } catch (error) {
        console.error("Error en update:", error);
        return ResponseUser.fail("Error interno al actualizar usuario");
    }
};

// Ver perfil por idUsuario
exports.getProfile = async (idUsuario, authenticatedUser) => {
    try {
        if (parseInt(authenticatedUser.idUsuario) !== parseInt(idUsuario) && authenticatedUser.rol !== "administrador") {
            return ResponseUser.fail("No tienes permiso para ver este perfil");
        }

         const user = await repo.findUser({ idUsuario });
        if (!user) return ResponseUser.fail("Usuario no encontrado");

        return ResponseUser.success(user, "Perfil obtenido exitosamente");

    } catch (error) {
        console.error("Error en getProfile:", error);
        return ResponseUser.fail("Error interno al obtener perfil");
    }
};

//  Ver perfil por claveUsuario
exports.getProfileByClave = async (claveUsuario) => {
    try {
        if (!claveUsuario) {
            return ResponseUser.fail("La clave de usuario es requerida");
        }

        const user = await repo.findUser({ claveUsuario });
        if (!user) {
            return ResponseUser.fail("Usuario no encontrado con esa clave");
        }

        // Verificar que el usuario esté activo
        if (user.estatus !== '1') {
            return ResponseUser.fail("El usuario está inactivo");
        }

        return ResponseUser.success({
            idUsuario: user.idUsuario,
            claveUsuario: user.claveUsuario,
            nombreCompleto: `${user.nombre} ${user.apellidoPaterno}`,
            estatus: user.estatus,
            idRol: user.idRol,
            rol: user.rol
        }, "Usuario encontrado");

    } catch (error) {
        console.error("Error en getProfileByClave:", error);
        return ResponseUser.fail("Error interno al buscar usuario por clave");
    }
};

// Cambiar estatus 
exports.changeStatus = async (idUsuario, authenticatedUser) => {
    try {
        if (authenticatedUser.rol !== "administrador") {
            return ResponseUser.fail("Solo los administradores pueden cambiar el estatus");
        }

        const user = await repo.findUser({ idUsuario });
        if (!user) return ResponseUser.fail("Usuario no encontrado");

        if (parseInt(idUsuario) === authenticatedUser.idUsuario) {
            return ResponseUser.fail("No puedes cambiar tu propio estatus");
        }

        const newStatus = user.estatus === '1' ? '0' : '1';
        const result = await repo.changeStatus(idUsuario, newStatus);

        if (result) {
            const statusText = newStatus === '1' ? "activado" : "desactivado";
            return ResponseUser.success({ idUsuario, estatus: newStatus }, `Usuario ${statusText} exitosamente`);
        }
        return ResponseUser.fail("Error al cambiar estatus");

    } catch (error) {
        console.error("Error en changeStatus:", error);
        return ResponseUser.fail("Error interno al cambiar estatus");
    }
};

