package com.example.airbnbApi.notification;

import com.example.airbnbApi.notification.dto.ResponseNotificationDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface NotificationRepositoryExtension {

    List<ResponseNotificationDTO> findNotificationByAccountId(Integer accountId);
}
