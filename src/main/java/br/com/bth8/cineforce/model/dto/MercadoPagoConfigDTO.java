package br.com.bth8.cineforce.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MercadoPagoConfigDTO {

    private String action;
    private String api_version;
    private MercadoPagoData data;
    private String date_created;
    private Long id;
    private boolean live_mode;
    private String type;
    private UUID user;

    @Data
    @NoArgsConstructor
    private class MercadoPagoData {
        private String id;

        public String getId() {
            return id;
        }
    }
}
