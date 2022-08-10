package com.diplomski.bookingkidsparty.app.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AccessException extends SecurityException {
    private String messsage;
}
