// Carga las variables de entorno desde el archivo .env
require("dotenv").config();

// Importamos la aplicación de express
const app = require("./app");

const PORT = process.env.PORT_SERVER || 3000;

app.listen(process.env.PORT_SERVER,(err)=>{
    if (err) console(err); // Para depuración
    // Mensaje de la consola cuando el servidor está en ejecución
    console.log(`El servidor UserService está ejecutándose en el puerto ${PORT}`);
})