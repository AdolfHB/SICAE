// Importa Express para usar su sistema de rutas
const express = require("express");

// Crea un "router" (manejador de rutas independiente)
const router = express.Router();

// Importa el controlador donde están las funciones que manejan la lógica
const controller = require("../controllers/auth.controller");


router.get("/test", controller.test);
router.post("/login", controller.login);

// Exportar el router para usarlo en la app principal
module.exports = router;