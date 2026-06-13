// Guarda la estructura de los mensajes
class ResponseUser {
    static success(data, message = "Operación exitosa") {
        return {
            error: false,
            message: message,
            data: data
        };
    }
    
    static fail(message) {
        return {
            error: true,
            message: message,
            data: null
        };
    }
}
module.exports = ResponseUser;