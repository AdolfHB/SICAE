// Se utiliza para verificar y decodificar tokens JWT
const jwt = require("jsonwebtoken");

// Middleware para validar el token JWT
const validateToken = (req, res, next) => {
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

const validateAdmin = (req, res, next) => {
    if (!req.user) {
        return res.status(401).json({
            error: true,
            message: "Usuario no autenticado",
        });
    }
    
    if (req.user.rol !== "administrador") {
        return res.status(403).json({
            error: true,
            message: "Acceso denegado. Se requieren permisos de administrador",
        });
    }
    
    next();
};
//Exporta el middelware para usarlo en rutas protegidas
module.exports =  { validateToken, validateAdmin };