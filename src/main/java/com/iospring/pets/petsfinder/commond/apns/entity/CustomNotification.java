package com.iospring.pets.petsfinder.commond.apns.entity;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class CustomNotification {
    public String alertBody;
    public String alertTitle;
    public String alertId;
    public String deviceToken;
}
