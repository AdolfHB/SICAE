const service = require("../services/user.service");

exports.register = async (req, res) => {
    try {
        const result = await service.register(req.body, req.user);
        res.status(result.error ? 400 : 201).json(result);
    } catch (err) {      
        res.status(500).json({ error: true, message: "Error al ejecutar el registro" });
        console.error(err.message);
    }
};

exports.update = async (req, res) => {
    try {
        const { id } = req.params;
        const result = await service.update(id, req.body, req.user);
        res.status(result.error ? 400 : 200).json(result);
    } catch (err) {
        res.status(500).json({ error: true, message: "Error al ejecutar la edición" });
        console.error(err.message);
    }
};

exports.getProfile = async (req, res) => {
    try {
        const { id } = req.params;
        const result = await service.getProfile(id, req.user);
        res.status(result.error ? 404 : 200).json(result);
    } catch (err) {
        res.status(500).json({ error: true, message: "Error al obtener el perfil" });
        console.error(err.message);
    }
};

exports.getProfileByClave = async (req, res) => {
    try {
        const { claveUsuario } = req.params;
        const result = await service.getProfileByClave(claveUsuario);
        res.status(result.error ? 404 : 200).json(result);
    } catch (err) {
        res.status(500).json({ error: true, message: "Error al obtener el perfil" });
        console.error(err.message);
    }
};

exports.changeStatus = async (req, res) => {
    try {
        const { id } = req.params;
        const { estatus } = req.body; 
        const result = await service.changeStatus(id, estatus, req.user);
        res.status(result.error ? 400 : 200).json(result);
    } catch (err) {
       res.status(500).json({ error: true, message: "Error al cambiar el estatus" });
        console.error(err.message);
    }
};