const service = require("../services/auth.service");

exports.test = async (req, res) => {
    const nombre = req.query;
    console.log(nombre);
    try {
        const result = await service.testApi();
        res.status(200).json({ message: result });
    } catch (err) {
        res.status(500).json({error: "Error al ejecutar el test" })
        console.log(err.message); 
    }
};

exports.login = async (req, res) => {
    try {
        const result = await service.login(req.body);
        res.status(200).json({ result });
    } catch (err) {
        res.status(500).json({error: "Error al ejecutar servicio" })
        console.log(err.message);
    }
}