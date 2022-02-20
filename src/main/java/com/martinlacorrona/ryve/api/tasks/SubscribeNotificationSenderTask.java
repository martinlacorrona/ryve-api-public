package com.martinlacorrona.ryve.api.tasks;

public interface SubscribeNotificationSenderTask {
    void sendAndUpdateAllPendingNotifications();
}
