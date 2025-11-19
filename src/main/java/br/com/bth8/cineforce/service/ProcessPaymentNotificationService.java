package br.com.bth8.cineforce.service;

import br.com.bth8.cineforce.infra.Client.MercadoPagoClient;
import br.com.bth8.cineforce.exception.EnitityNotFoundException;
import br.com.bth8.cineforce.exception.PaymentGatewayException;
import br.com.bth8.cineforce.model.dto.request.ProcessPaymentNotificationRequestDTO;
import br.com.bth8.cineforce.model.dto.response.ProccessPaymentNotificationResponseDTO;
import br.com.bth8.cineforce.model.entity.User;
import br.com.bth8.cineforce.repository.CartRepository;
import br.com.bth8.cineforce.repository.UserRepository;
import com.mercadopago.client.payment.PaymentClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class ProcessPaymentNotificationService {

    private final MercadoPagoClient mercadoPagoClient;
    @Autowired
    private CartRepository repository;
    @Autowired
    private UserRepository userRepository;


    public ProccessPaymentNotificationResponseDTO processPaymentNotification(
            ProcessPaymentNotificationRequestDTO request
    ) {
        try {
            PaymentClient client = new PaymentClient();
            com.mercadopago.resources.payment.Payment mpPayment = client.get(Long.parseLong(request.resourceId()));

            if (mpPayment == null) {
                log.warn("Payment not found in Mercado Pago");
                throw new PaymentGatewayException("Payment not found");
            }

            String status = mpPayment.getStatus().toLowerCase();

            if (status.equalsIgnoreCase("approved")) {

                String userIdString = (String) mpPayment.getMetadata().get("userId");
                UUID userId = UUID.fromString(userIdString);

                var cart = repository.findById(userId).orElseThrow(() -> new EnitityNotFoundException("Cart not found"));
                User user = cart.getUser();

                cart.getItems().forEach(
                        i -> user.getPurchasedMovies().add(i.getMovie())
                );

                cart.setTotalPrice(0.0);
                cart.getItems().clear();

                userRepository.save(user);
                repository.save(cart);

                return new ProccessPaymentNotificationResponseDTO(true, "SUCCESS");
            } else {
                log.warn("Payment not approved. Status");
                log.error("Unexpected error processing notification for payment");
                return new ProccessPaymentNotificationResponseDTO(false, "PROCESSING_ERROR");
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
