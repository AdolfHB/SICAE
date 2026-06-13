const jwt = require("jsonwebtoken");

const generateToken = (user) => {
    const payload = {
        idUsuario: user.idUsuario,
        username: user.username,
        idRol: user.idRol,
        rol: user.rol,
    };
    
    return jwt.sign(
        payload, //Datos incluidos en el token
        process.env.JWT_SECRET, // Generación de clave secreta
        {expiresIn: "48h"}, // Tiempo de expiración
        //{algorithm:"HS512"} // Si no se lo ponemos, usara por defecto #HS256
    );
};

module.exports = generateToken;