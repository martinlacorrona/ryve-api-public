package com.martinlacorrona.ryve.api.service;

public interface FirebaseCloudMessagingService {
    /**
     * Mandar notificacion.
     *
     * @param title            titulo de la notificacion
     * @param body             cuerpo de la notificacion
     * @param stationServiceId id de la estacion de la que trata
     * @param tokenId          IDs
     */
    void sendNotification(String title, String body, Long stationServiceId, String tokenId);

    /**
     * Guarda token por el usuario.
     *
     * @param tokenId
     */
    void saveToken(String tokenId);

    /**
     * Borrar token de la BBDD por el tokenID teniendo en cuenta el usuario
     *
     * @param tokenId
     */
    void deleteToken(String tokenId);
}
