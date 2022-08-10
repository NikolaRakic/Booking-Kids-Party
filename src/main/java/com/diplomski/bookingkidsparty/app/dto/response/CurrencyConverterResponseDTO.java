package com.diplomski.bookingkidsparty.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class CurrencyConverterResponseDTO {

    private String date;
    private double result;
    private boolean success;
}
