const { Pool } = require("pg"); // Si da error, encierra en corchetes el Pool

// La función requiere un objeto de configuración
const pool = new Pool({
    user: process.env.DB_USER,
    host: process.env.DB_HOST,
    database: process.env.DB_NAME,
    password: process.env.DB_PASSWORD,
    port: process.env.DB_PORT,
    max: 20,
});

module.exports = pool;