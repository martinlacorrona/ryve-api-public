package com.martinlacorrona.ryve.api.rest;

import com.martinlacorrona.ryve.api.rest.model.firebasecloudmessaging.FirebaseCloudMessagingResponse;
import com.martinlacorrona.ryve.api.rest.model.firebasecloudmessaging.NotificationBodyRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FirebaseCloudMessagingAPI {
    String BASE_URL = "https://fcm.googleapis.com/fcm/";
    String FIREBASE_KEY = "USER_YOUR_FIREBASE_KEY";
    /**
     * Petición get al servicio
     *
     * @return Objeto ResponseApi con la lista de estaciones de servicio
     */
    @Headers({
            "Content-Type: application/json",
            "Authorization: key=" + FIREBASE_KEY
    })
    @POST("send")
    Call<FirebaseCloudMessagingResponse> sendNotification(@Body NotificationBodyRequest notificationBodyRequest);
}
