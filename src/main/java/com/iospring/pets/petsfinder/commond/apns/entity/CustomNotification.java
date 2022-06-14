package com.iospring.pets.petsfinder.commond.apns.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class CustomNotification {

    private String alertBody;
    private String alertTitle;
    private String deviceToken;
    private String imageUrl;
    private NotificationData notificationData;

    public void createNotificationData(String mode, String type, String boardId) {
        NotificationData notificationData = new NotificationData();
        notificationData.setMode(mode);
        notificationData.setType(type);
        notificationData.setBoardId(boardId);

        this.notificationData = notificationData;
    }

    public String notificationDataObjToJson()  {
        ObjectMapper objectMapper = new ObjectMapper();

        String notificationDataJson = null;
        try {
            notificationDataJson = objectMapper.writeValueAsString(this.notificationData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return notificationDataJson;
    }
}

@Data
class NotificationData {
    private String mode;
    private String type;
    private String boardId;
}
