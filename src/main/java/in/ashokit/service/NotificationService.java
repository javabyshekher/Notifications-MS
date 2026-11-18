package in.ashokit.service;

import in.ashokit.entity.OrderEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.repository.OrderRepository;
import in.ashokit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class NotificationService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UserRepository userRepo;

    public void sendDeliveredNotification(Integer orderId, String otp) {
        OrderEntity orderEntity = orderRepo.findById(orderId).get();
        if(otp.equals(orderEntity.getDeliveryOtp())) {
            orderEntity.setOrderStatus("DELIVERED");
            orderRepo.save(orderEntity);

            String email = orderEntity.getUser().getEmail();
            String subject = "Your Order Delivered";
            String body = "Thank you for shopping with us... Have a great day";
            emailService.sendEmail(email, subject, body);

        }
        else
            throw new RuntimeException("OTP mismatch");
    }

    public void sendDeliveryUpdates() {
        List<OrderEntity> orders = orderRepo.findByDeliveryDate(LocalDate.now());
        orders.forEach(order-> {
            String deliveryOtp = generatedOtp(5);
            order.setDeliveryOtp(deliveryOtp);
            orderRepo.save(order);
            String email = order.getUser().getEmail();
            String subject = "Your Order is Out For Delivery";
            String body = "Please share delivery OTP " + deliveryOtp;
            emailService.sendEmail(email, subject, body);
        });
    }

    public void sendOrderConfirmation(Integer orderId) {
        OrderEntity orderEntity = orderRepo.findById(orderId).get();
        String email = orderEntity.getUser().getEmail();
        String subject = "Your order confirmed";
        String body = "Thank you for shopping with us!";
        emailService.sendEmail(email, subject, body);
    }

    public void sendOffersNotification() {
        List<UserEntity> users = userRepo.findAll();
        users.forEach(user -> {
            String subject = "Big Billion Day Starts From Tomorrow - OurMart";
            String body = "50% Offer Sale on all products..";
            emailService.sendEmail(user.getEmail(), subject, body);
        });
    }

    public void cancelledOrderNotification(Integer orderId) {

        OrderEntity orderEntity = orderRepo.findById(orderId).orElseThrow();

        String email = orderEntity.getUser().getEmail();

        String subject = "Your Order Cancelled";
        String body = "Your refund amount will be processed in 4 working days..!!";

        emailService.sendEmail(email, subject, body);

    }


    private String generatedOtp(int length) {
        String chars = "0123456789";
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<length; i++) {
            int randomIndex = random.nextInt(chars.length());
            char ch = chars.charAt(randomIndex);
            builder.append(ch);
        }
        return builder.toString();
    }
}
