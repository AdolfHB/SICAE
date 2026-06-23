const repo = require("../repositories/auth.repository");
const ResponseLogin = require("../models/responses/response.login");
const generateToken = require("../config/jwt");
const comparePassword = require("../utils/bcrypt");

exports.testApi = async () => {
    return "The test was succesful... (Authentication Microservices)"
};

exports.login = async (user) => {
    if (!user.username || !user.password){
        return  ResponseLogin.fail("Los datos del usuario son requeridos");
    }

    const data = await repo.login(user);
    
    if(!data){
        return  ResponseLogin.fail("El usuario no existe, verifíque las credenciales");
    }
    
    if (data.estatus !== '1') {
        return ResponseLogin.fail("El usuario está inactivo, contacte con un administrador");
    }
    const validPassword = await comparePassword(user.password, data.password);
    if (!validPassword) {
        return ResponseLogin.fail("Contraseña Incorrecta")
    }

    const token = generateToken(data)
    return ResponseLogin.success({
        idUsuario: data.idUsuario,
        idRol: data.idRol,
        rol: data.rol,
        usuario: data.username,  
        nombreCompleto: `${data.nombre} ${data.apellidoPaterno} ${data.apellidoMaterno || ''}`.trim(),      
        idTipoUsuario: data.idTipoUsuario,
        tipoUsuario: data.tipoUsuario
    }, token);
};

