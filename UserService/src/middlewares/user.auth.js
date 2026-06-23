// Se utiliza para verificar y decodificar tokens JWT
const jwt = require("jsonwebtoken");
// Para poder validar el estado
const repo = require("../repositories/user.repository");

// Middleware para validar el token JWT
const validateToken = async (req, res, next) => {
    // Obtiene el encabezado Autorization de la petición
    const authHeader = req.headers.authorization;

    // Verifica si el token fue enviado
    if (!authHeader){
        //Retornar error 401 (No autorizado)
        return res.status(401).json({
            error: true,
            message: "No autorizado - token requerído",
        });
    }

    //Extrae el token del formato
    // "Bearer token aquí"
    const token = authHeader.split(" ")[1];

    try{
        //Verifica si el token es válido usando la clave secreta
        const decoded = jwt.verify(token, process.env.JWT_SECRET);

        //Guarda la información decodificada del usuario dentro del objeto request
        req.user = decoded;

        //Verifica que el usuario está activo (para cuando se desactive el solo)
        const user = await repo.findUser({ idUsuario: decoded.idUsuario });

        if (user.estatus !== '1') {
            return res.status(401).json({
                error: true,
                message: "Tu cuenta está desactivada. Contacta a un administrador.",
            });
        }

        // Continua con el siguiente middleware o controlador
        next();
    } catch (error){
        //Si el token es inválido o expiró, retorna error 401
        return res.status(401).json({
            error: true,
            message: "Token inválido",
        });
    }
};

//Exporta el middelware para usarlo en rutas protegidas
module.exports =  validateToken;