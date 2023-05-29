package com.example.airbnbApi.notification;

import com.example.airbnbApi.user.Account;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Integer id;

    private String  title;

    private String link;

    private String message;

    private boolean checked;

    private String sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private LocalDateTime createdLocalDateTime;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

}
