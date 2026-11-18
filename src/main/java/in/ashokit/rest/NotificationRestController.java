package in.ashokit.rest;

import in.ashokit.dto.ApiResponse;
import in.ashokit.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notification")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/order-confirmation/{orderId}")
    public ResponseEntity<ApiResponse<String>> sendOrderConfirmation(@PathVariable Integer orderId) {
        notificationService.sendOrderConfirmation(orderId);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setMessage("Order Confirmation email sent successfully");
        response.setData("");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/cancel-order-notification/{orderId}")
    public ResponseEntity<ApiResponse<String>> cancelledOrderConfirmation(@PathVariable Integer orderId) {
        notificationService.cancelledOrderNotification(orderId);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setMessage("Cancelled Order email sent successfully");
        response.setData("");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/daily-delivery-updates")
    public ResponseEntity<ApiResponse<String>> sendDailyDeliveryUpdates() {

        notificationService.sendDeliveryUpdates();

        ApiResponse<String> response = new ApiResponse<>();

        response.setMessage("Notification sent successfully");
        response.setStatusCode(200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/offers-notification")
    public ResponseEntity<ApiResponse<String>> sendOfferNotification() {

        notificationService.sendOffersNotification();

        ApiResponse<String> response = new ApiResponse<>();

        response.setMessage("Notification sent successfully");
        response.setStatusCode(200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
