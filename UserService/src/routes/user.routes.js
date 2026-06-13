// Importa Express para usar su sistema de rutas
const express = require("express");

// Crea un "router" (manejador de rutas independiente)
const router = express.Router();

// Importa el controlador donde están las funciones que manejan la lógica
const controller = require("../controllers/user.controller");
const { validateToken, validateAdmin } = require("../middlewares/user.middleware");

// Registrar usuario (solo admin)
router.post("/register", validateToken, validateAdmin, controller.register);

// Editar usuario (mismo usuario o admin)
router.put("/edit/:id", validateToken, controller.update);

// Ver perfil (mismo usuario o admin)
router.get("/profile-id/:id", validateToken, controller.getProfile);
router.get("/profile-clave/:claveUsuario", validateToken, controller.getProfileByClave);

// Cambiar estatus (solo admin)
router.patch("/edit/:id/status", validateToken, validateAdmin, controller.changeStatus);

module.exports = router;