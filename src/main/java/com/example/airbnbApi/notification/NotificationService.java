package com.example.airbnbApi.notification;

import com.example.airbnbApi.notification.dto.ResponseNotificationDTO;
import com.example.airbnbApi.user.Account;
import com.example.airbnbApi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;


    public List<ResponseNotificationDTO> getNotificationByAccountId(Integer accountId) {
        return notificationRepository.findNotificationByAccountId(accountId);
    }

    public long getNotificationCountByAccountId(Integer accountId) {
        Account account = userRepository.findOnlyId(accountId);
        return notificationRepository.countByAccount(account);
    }

    public void deleteNotification(Integer account_id, Integer notification_id) {
        Account account = userRepository.findOnlyId(account_id);
        notificationRepository.deleteNotificationByIdAndAccount(notification_id,account);

    }
}
