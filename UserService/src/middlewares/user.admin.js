// Middleware para validar si el usuario es administrador
const validateAdmin = (req, res, next) => {
    if (req.user.idRol !== 1) {
        return res.status(403).json({
            error: true,
            message: "Acceso denegado. Se requieren permisos de administrador",
        });
    }
    next();
};

module.exports =  validateAdmin;