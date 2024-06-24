package com.backend.app.persistence.entities;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaypalEntity {
    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
}
