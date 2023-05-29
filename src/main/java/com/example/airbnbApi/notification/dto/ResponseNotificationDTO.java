package com.example.airbnbApi.notification.dto;

import com.example.airbnbApi.notification.NotificationType;
import com.example.airbnbApi.user.Account;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ResponseNotificationDTO {

    private Integer id;

    private String  title;

    private String link;

    private String message;

    private boolean checked;

    private String sendUser;

    private LocalDateTime createdLocalDateTime;

    private NotificationType notificationType;

    @QueryProjection
    public ResponseNotificationDTO(Integer id, String title, String link,
                                   String message, boolean checked, String sendUser,
                                   LocalDateTime createdLocalDateTime, NotificationType notificationType) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.message = message;
        this.checked = checked;
        this.sendUser = sendUser;
        this.createdLocalDateTime = createdLocalDateTime;
        this.notificationType = notificationType;
    }
}
