// Importa el frameowrk de express para crear el servidor
const express = require("express");

// Importar las rutas
const authRoutes = require("./routes/user.routes");

// Crea la instalacia de la aplicación
const app = express();

// Middleware que permite al servidor entender datos en formato JSON
app.use(express.json());

app.use("/sicae/user/api",authRoutes);


// Exporta la aplicación para poderla usar en otro archivo (Por ejemplo, donde se inicia el servidor)
module.exports = app;