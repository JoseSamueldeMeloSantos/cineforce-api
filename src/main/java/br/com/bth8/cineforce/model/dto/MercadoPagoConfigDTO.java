package br.com.bth8.cineforce.model.dto;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.util.UUID;

public class MercadoPagoConfigDTO {

    private String action;
    private String api_version;
    private LocalDate data;
    private String date_created;
    private Long id;
    private boolean live_mode;
    private String type;
    private UUID user;
}
