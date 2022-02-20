package com.martinlacorrona.ryve.api.exception;

public enum ErrorMessage {
    MISSING_PARAMETER("Falta el parámetro {0}."),
    PARAMETER_MIN_MAX_CHARACTERS("El parámetro {0} tiene que tener entre {1} y {2} carácteres."),
    PARAMETER_MIN_MAX_QUANTITY("El parámetro {0} tiene que estar entre {1} y {2}."),
    ALREADY_REGISTER_USER_WITH_MAIL("Ya hay alguien registrado con ese correo."),
    CHANGE_YOUR_PROFILE("Solo puedes cambiar tu perfil con tu correo."),

    YOU_ALREADY_HAVE_THIS_FAVOURITE("Ya tienes esa estación agregada como favorita."),
    STATION_SERVICE_DOESNT_EXIST("Esa estación de servicio no existe."),
    FUEL_TYPE_DOESNT_EXIST("No existe ese tipo de combustible."),
    YOU_DONT_HAVE_THIS_FAVOURITE("No tienes esa estación de servicio como favorita."),

    THIS_IS_NOT_A_MAIL("Eso no es un correo electrónico."),

    PASSWORD_NOT_POLICY("La contraseña debe tener entre 8 y 24 caracteres, una letra y un número."),

    CANNOT_DELETE_ALERT_THAT_DOESNT_EXIST("No puedes borrar una alerta que no existe."),
    CANNOT_DELETE_ALERT_THAT_ISNT_YOURTS("No puedes borrar una alerta que no es tuya."),
    MIN_AND_MAX_PERIOD_IN_DAYS("Solo puedes poner periodos de entre 1 y 30 días."),
    CANNOT_CREATE_SIMILAR_ALERT("Ya tienes una alerta creada con la misma estación y días."),

    NOT_RESULT_FOUND("No se han encontrado resultados para esa consulta."),
    NOT_ROUTE_FOUND("No se han encontrado una ruta para ese origen/destino."),
    DIRECTIONS_API_FAILED("El servicio de generar las rutas ha fallado, intentalo de nuevo mas tarde."),

    EXAMPLE("Example nothing.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
