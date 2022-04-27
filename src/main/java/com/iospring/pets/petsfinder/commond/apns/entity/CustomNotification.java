package com.iospring.pets.petsfinder.commond.apns.entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Data
public class CustomNotification {
    public static void main(String[] args) {

        CustomNotification customNotification = new CustomNotification();

        customNotification.createNotificationData("AA", "BB", "CCC");

        String s = customNotification.notificationDataObjToJson();
        System.out.println("s = " + s);
    }
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
        System.out.println("notificationData = " + notificationData);
    }

    public String notificationDataObjToJson()  {
        ObjectMapper objectMapper = new ObjectMapper();

        String notificationDataJson = null;
        try {
            notificationDataJson = objectMapper.writeValueAsString(this.notificationData);
        } catch (JsonProcessingException e) {

            System.out.println("e = " + e.getLocalizedMessage());
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
