// Guarda la estructura de los mensajes
class ResponseLogin {
    static success (user,token){
        return{
            error : false,
            message: "Bienvenido al proyecto SICAE",
            user: user,
            token: token,
        };
    }
    static fail (message){
        return{
            error : true,
            msj: message,
            user: null,
            token: null,
        };
    }
}
module.exports = ResponseLogin;