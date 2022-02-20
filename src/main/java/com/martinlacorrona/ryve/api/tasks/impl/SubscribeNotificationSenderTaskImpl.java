package com.martinlacorrona.ryve.api.tasks.impl;

import com.martinlacorrona.ryve.api.dao.HistoryStationServiceDao;
import com.martinlacorrona.ryve.api.dao.NotificationTokenDao;
import com.martinlacorrona.ryve.api.dao.SubscribeNotificationDao;
import com.martinlacorrona.ryve.api.entities.HistoryStationServiceEntity;
import com.martinlacorrona.ryve.api.entities.NotificationTokenEntity;
import com.martinlacorrona.ryve.api.entities.SubscribeNotificationEntity;
import com.martinlacorrona.ryve.api.service.FirebaseCloudMessagingService;
import com.martinlacorrona.ryve.api.tasks.SubscribeNotificationSenderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@EnableAsync
@Component
public class SubscribeNotificationSenderTaskImpl implements SubscribeNotificationSenderTask {

    private static final Logger logger = LoggerFactory.getLogger(SubscribeNotificationSenderTaskImpl.class);

    @Autowired
    private FirebaseCloudMessagingService firebaseCloudMessagingService;

    @Autowired
    private NotificationTokenDao notificationTokenDao;

    @Autowired
    private SubscribeNotificationDao subscribeNotificationDao;

    @Autowired
    private HistoryStationServiceDao historyStationServiceDao;

    /**
     * Envia las notificaciones que tocan y actualizan su nextDate todos los dias a las 09:00 de la mañana
     */
    @Override
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendAndUpdateAllPendingNotifications() {
        logger.debug("[TASK] [SubscribeNotificationSenderTask] Ejecutando el task de enviar notificaciones y actualizarlas.");

        Date today = new Date();
        subscribeNotificationDao.findAllByNextNotifyLessThanEqual(today).stream().parallel().forEach(
                this::sendAndUpdateNotification
        );

        logger.debug("[TASK] [SubscribeNotificationSenderTask] Task finalizada.");
    }

    private void sendAndUpdateNotification(SubscribeNotificationEntity subscribeNotificationEntity) {
        List<NotificationTokenEntity> notificationTokensByUser = notificationTokenDao
                .findAllByUserId(subscribeNotificationEntity.getUser().getId());
        if (subscribeNotificationEntity.isStatus()) //solo enviar si esta activada
            sendNotification(subscribeNotificationEntity, notificationTokensByUser);
        updateNotification(subscribeNotificationEntity);
    }

    /**
     * Enviamos via firebase las notificiones del precio de hoy
     *
     * @param subscribeNotificationEntity la entidad de la cuale stamos suscritos
     * @param notificationsTokenByUser    los tokens del usuario al cual se los hay que mandar
     */
    private void sendNotification(SubscribeNotificationEntity subscribeNotificationEntity, List<NotificationTokenEntity> notificationsTokenByUser) {
        notificationsTokenByUser.stream().parallel().forEach(
                token -> {
                    HistoryStationServiceEntity historyStationToday = historyStationServiceDao.findAllByFuelTypeIdAndStationServiceIdAndDate(
                            subscribeNotificationEntity.getFuelType().getId(),
                            subscribeNotificationEntity.getStationService().getId(),
                            new Date());
                    String title = subscribeNotificationEntity.getStationService().getName();
                    String body;
                    if (historyStationToday != null)
                        body = "El precio de la estación hoy es de " +
                                String.format("%.2f", historyStationToday.getPrice()) + "€ de " +
                                subscribeNotificationEntity.getFuelType().getName() + ".";
                    else
                        body = "Hoy no hay precio para esta estación de servicio.";
                    firebaseCloudMessagingService.sendNotification(title, body, subscribeNotificationEntity.getStationService().getId(), token.getToken());
                }
        );
    }

    /**
     * Actualizamos las fechas de las notificaciones con menor fecha a hoy y le ponemos de fecha hoy + los dias definidos
     *
     * @param subscribeNotificationEntity la entidad de la cuale stamos suscritos
     */
    private void updateNotification(SubscribeNotificationEntity subscribeNotificationEntity) {
        Date today = new Date();
        subscribeNotificationEntity.setLastNotified(today);

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, subscribeNotificationEntity.getPeriodInDays());
        subscribeNotificationEntity.setNextNotify(cal.getTime());

        subscribeNotificationDao.save(subscribeNotificationEntity);
    }
}
