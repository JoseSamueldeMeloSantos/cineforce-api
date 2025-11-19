package br.com.bth8.cineforce.infra.Client;

import br.com.bth8.cineforce.exception.PaymentGatewayException;
import br.com.bth8.cineforce.model.dto.request.CreateReferenceRequestDTO;
import br.com.bth8.cineforce.model.dto.response.CreatePreferenceResponseDTO;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Component
public class MercadoPagoClient {

    @Value("${mercado-pago.token}")
    private String token;
    @Value("${mercado-pago.notification-url}")
    private String notificationUrl;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(token);
        log.info("Starting mercado pago...");
    }

    public CreatePreferenceResponseDTO createPreference(CreateReferenceRequestDTO request, String orderNumber) {

        try {
            PreferenceClient client = new PreferenceClient();

            //criando os itens
            List<PreferenceItemRequest> items = request.items().stream()
                    .map(itemDTO -> PreferenceItemRequest.builder()
                            .id(request.userId().toString())
                            .title(itemDTO.title())
                            .quantity(itemDTO.quantity())
                            .unitPrice(itemDTO.unitPrice())
                            .build()
                    ).collect(Collectors.toList());

            //criando o pagador
            PreferencePayerRequest payer = PreferencePayerRequest.builder()
                    .name(request.payer().name())
                    .email(request.payer().email())
                    .build();

            //Criando as urls de resposta
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success(request.backUrls().succes())
                    .failure(request.backUrls().failure())
                    .pending(request.backUrls().pending())
                    .build();

            //passando o user id para o metada para atualizar o banco
            Map<String, Object> metadata = new HashMap<>();
            metadata.put("userId", request.userId());

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .payer(payer)
                    .backUrls(backUrls)
                    .autoReturn("approved")//automaticamente retorna após um pagamento aprovado
                    .notificationUrl(notificationUrl)
                    .externalReference(orderNumber)//id do pedido
                    .metadata(metadata)
                    .build();

            Preference preference = client.create(preferenceRequest);

            return new CreatePreferenceResponseDTO(
                    preference.getId(),
                    preference.getInitPoint()
            );

        }catch (MPApiException e) {
            log.error("Mercado Pago API error creating preference for orderNumber {}: Status Code: {}, Response: {}",
                    orderNumber, e.getStatusCode(), e.getApiResponse().getContent(), e);
            throw new PaymentGatewayException("Mercado Pago API error: " + e.getApiResponse().getContent(), e);
        } catch (MPException e) {
            log.error("Mercado Pago SDK error creating preference for orderNumber {}: {}", orderNumber, e.getMessage(), e);
            throw new PaymentGatewayException("Mercado Pago SDK error: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Unexpected error creating Mercado Pago preference for orderNumber {}: {}", orderNumber, e.getMessage(), e);
            throw new PaymentGatewayException("Unexpected error creating preference.", e);
        }
    }
}
