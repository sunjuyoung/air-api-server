package com.example.airbnbApi.notification;

import com.example.airbnbApi.listing.QListing;
import com.example.airbnbApi.notification.dto.QResponseNotificationDTO;
import com.example.airbnbApi.notification.dto.ResponseNotificationDTO;
import com.example.airbnbApi.user.QAccount;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.example.airbnbApi.notification.QNotification.*;
import static com.example.airbnbApi.user.QAccount.*;

public class NotificationRepositoryExtensionImpl extends QuerydslRepositorySupport implements NotificationRepositoryExtension {
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.

     */
    public NotificationRepositoryExtensionImpl() {
        super(Notification.class);
    }


    @Override
    public List<ResponseNotificationDTO> findNotificationByAccountId(Integer accountId) {
        List<ResponseNotificationDTO> result = from(notification)
                .innerJoin(account).on(notification.account.eq(account))
                .select(new QResponseNotificationDTO(
                        notification.id,
                        notification.title,
                        notification.link,
                        notification.message,
                        notification.checked,
                        notification.sendUser,
                        notification.createdLocalDateTime,
                        notification.notificationType
                ))
                .where(notification.account.id.eq(accountId)).fetch();

        return result;
    }
}
