package com.example.airbnbApi.notification;

import com.example.airbnbApi.notification.dto.ResponseNotificationDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{account_id}")
    public ResponseEntity<?> getNotificationByAccountId(@PathVariable("account_id")Integer accountId){
        List<ResponseNotificationDTO> result = notificationService.getNotificationByAccountId(accountId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/count/{account_id}")
    public ResponseEntity<?> getNotificationCountByAccountId(@PathVariable("account_id")Integer accountId){
        long count =  notificationService.getNotificationCountByAccountId(accountId);
        return ResponseEntity.ok().body(count);
    }

    @DeleteMapping("/{account_id}/{notification_id}")
    public ResponseEntity<?> deleteNotification(@PathVariable("account_id")Integer accountId,
                                                @PathVariable("notification_id")Integer notification_id){
         notificationService.deleteNotification(accountId,notification_id);
        return ResponseEntity.ok().body("success");
    }


}
