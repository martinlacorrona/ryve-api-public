package com.martinlacorrona.ryve.api.service.impl;

import com.martinlacorrona.ryve.api.dao.NotificationTokenDao;
import com.martinlacorrona.ryve.api.entities.NotificationTokenEntity;
import com.martinlacorrona.ryve.api.entities.UserEntity;
import com.martinlacorrona.ryve.api.rest.FirebaseCloudMessagingAPI;
import com.martinlacorrona.ryve.api.rest.RetrofitClientFirebase;
import com.martinlacorrona.ryve.api.rest.model.firebasecloudmessaging.FirebaseCloudMessagingResponse;
import com.martinlacorrona.ryve.api.rest.model.firebasecloudmessaging.NotificationBodyRequest;
import com.martinlacorrona.ryve.api.rest.model.firebasecloudmessaging.NotificationBodyRequestData;
import com.martinlacorrona.ryve.api.security.DataUserSecurity;
import com.martinlacorrona.ryve.api.service.FirebaseCloudMessagingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Collections;
import java.util.Date;

@Service
public class FirebaseCloudMessagingServiceImpl implements FirebaseCloudMessagingService {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseCloudMessagingServiceImpl.class);

    private final FirebaseCloudMessagingAPI clienteReqResAPI = RetrofitClientFirebase.getClient(FirebaseCloudMessagingAPI.BASE_URL)
            .create(FirebaseCloudMessagingAPI.class);

    @Autowired
    private NotificationTokenDao notificationTokenDao;

    @Autowired
    private DataUserSecurity dataUserSecurity;

    @Override
    public void saveToken(String tokenFirebase) {
        UserEntity userEntity = dataUserSecurity.getUserEntity();
        NotificationTokenEntity notificationTokenEntity =
                notificationTokenDao.findNotificationTokenEntityByToken(tokenFirebase);
        if(notificationTokenEntity == null) {
            notificationTokenDao.save(NotificationTokenEntity.builder()
                    .user(userEntity)
                    .token(tokenFirebase)
                    .lastuse(new Date())
                    .build());
        } else {
            notificationTokenEntity.setLastuse(new Date());
            notificationTokenEntity.setUser(userEntity);
            notificationTokenDao.save(notificationTokenEntity);
        }
    }
    
    @Override
    public void deleteToken(String tokenId) {
        UserEntity userEntity = dataUserSecurity.getUserEntity();
        NotificationTokenEntity entity = notificationTokenDao.findNotificationTokenEntityByToken(tokenId);
        if(entity != null) {
            //Comprobamos que la ID del usuario sea la de la entidad para solo borrar las que sean suyas
            if(entity.getUser().getId() == userEntity.getId()) {
                notificationTokenDao.deleteById(entity.getId());
            }
        }
    }

    @Override
    public void sendNotification(String title, String body, Long stationServiceId ,String tokenId) {
        NotificationBodyRequest notificationBodyRequest = NotificationBodyRequest.builder()
                .registrationIds(Collections.singletonList(tokenId))
                .data(NotificationBodyRequestData.builder()
                        .title(title)
                        .body(body)
                        .stationServiceId(stationServiceId.toString())
                        .build())
                .build();

        Call<FirebaseCloudMessagingResponse> call = clienteReqResAPI.sendNotification(notificationBodyRequest);
        logger.info("[FirebaseCloudMessagingService] Enviando notificacion {}.", notificationBodyRequest);
        call.enqueue(new Callback<FirebaseCloudMessagingResponse>() {
            @Override
            public void onResponse(Call<FirebaseCloudMessagingResponse> call, Response<FirebaseCloudMessagingResponse> response) {
                if (response.code() == 200) {
                    logger.info("[FirebaseCloudMessagingService] Notificacion enviada con exito.");
                } else {
                    logger.info("[FirebaseCloudMessagingService] Recibido de la API de firebase una respuesta con codigo {}.", response.code());
                }
            }

            @Override
            public void onFailure(Call<FirebaseCloudMessagingResponse> call, Throwable throwable) {
                logger.info("[FirebaseCloudMessagingService] La API no ha dado respuesta. {}", throwable.getMessage());
            }
        });
    }
}
