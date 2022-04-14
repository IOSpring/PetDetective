package com.iospring.pets.petsfinder.commond.apns.service;


import com.iospring.pets.petsfinder.commond.apns.entity.CustomNotification;
import com.iospring.pets.petsfinder.detectBoard.dto.DetectBoardDTO;
import com.turo.pushy.apns.ApnsClient;
import com.turo.pushy.apns.ApnsClientBuilder;
import com.turo.pushy.apns.PushNotificationResponse;
import com.turo.pushy.apns.auth.ApnsSigningKey;
import com.turo.pushy.apns.util.ApnsPayloadBuilder;
import com.turo.pushy.apns.util.SimpleApnsPushNotification;
import com.turo.pushy.apns.util.TokenUtil;
import com.turo.pushy.apns.util.concurrent.PushNotificationFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

@Configuration
public class
ApnsService {

    @Value("${apns.team_id}")
    private  String teamId;

    @Value("${apns.key_id}")
    private String keyId;

    @Value("${apns.topic}")
    private String topic;

    @Value("${apns.device_token}")
    public String deviceToken;


    @Bean
    public  ApnsClient getClient() {
        ApnsClient client = null;

        try {
            client = new ApnsClientBuilder().setApnsServer(ApnsClientBuilder.DEVELOPMENT_APNS_HOST).setSigningKey(
                    ApnsSigningKey.loadFromInputStream(new FileInputStream("src/main/resources/AuthKey_K482HA2L4W.p8"), teamId, keyId)
            ).build();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return client;
    }

    public String pushCustomNotification(CustomNotification customNotification) {
        try {
            ApnsClient client = this.getClient();

            ApnsPayloadBuilder payloadBuilder = new ApnsPayloadBuilder();

            customNotification.setDeviceToken(deviceToken);
            payloadBuilder.setAlertTitle(customNotification.getAlertTitle());
            payloadBuilder.setAlertBody(customNotification.getAlertBody());


            payloadBuilder.setTargetContentId(customNotification.getAlertId());

            payloadBuilder.setSound("default");


            String payload = payloadBuilder.buildWithDefaultMaximumLength();


            String token = TokenUtil.sanitizeTokenString(customNotification.getDeviceToken());
            SimpleApnsPushNotification pushNotification = new SimpleApnsPushNotification(token, topic, payload);
            PushNotificationFuture<SimpleApnsPushNotification, PushNotificationResponse<SimpleApnsPushNotification>> simpleApnsPushNotificationPushNotificationResponsePushNotificationFuture = client.sendNotification(pushNotification);

            PushNotificationResponse<SimpleApnsPushNotification> response = simpleApnsPushNotificationPushNotificationResponsePushNotificationFuture.get();
            if (!response.isAccepted()) {
                System.out.println("response.getRejectionReason() = " + response.getRejectionReason());
            }
            else{
                System.out.println("=================================================================");
                System.out.println("response = " + response);
                System.out.println("response.getPushNotification().getPayload() = " + response.getPushNotification().getPayload());
                System.out.println("=================================================================");
            }

            return response.getPushNotification().getPayload();
        }  catch (ExecutionException e) {
            e.printStackTrace();

            return e.getLocalizedMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();

            return e.getLocalizedMessage();
        }
    }

}
