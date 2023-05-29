package com.example.airbnbApi.notification;

import com.example.airbnbApi.user.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer>, NotificationRepositoryExtension {


    Long countByAccount(Account account);


    void deleteNotificationByIdAndAccount(Integer id, Account account);


}
