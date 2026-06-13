const generateClaveUsuario = async (nombre, apellidoPaterno, existingKeys = []) => {
    // Tomar primeras 3 letras del nombre
    let parte1 = nombre.substring(0, 3).toUpperCase();
    // Tomar primeras 3 letras del apellido
    let parte2 = apellidoPaterno.substring(0, 3).toUpperCase();
    
    // Si son más cortos, completar con X
    if (parte1.length < 3) parte1 = parte1.padEnd(3, 'X');
    if (parte2.length < 3) parte2 = parte2.padEnd(3, 'X');
    
    let numero = Math.floor(Math.random() * 900) + 100;
    let clave = `${parte1}${parte2}-${numero}`;
    
    // Verificar unicidad
    let contador = 0;
    while (existingKeys.includes(clave) && contador < 10) {
        numero = Math.floor(Math.random() * 900) + 100;
        clave = `${parte1}${parte2}-${numero}`;
        contador++;
    }
    
    return clave;
};

module.exports = generateClaveUsuario;