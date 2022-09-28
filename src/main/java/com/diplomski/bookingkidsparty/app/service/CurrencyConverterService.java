package com.diplomski.bookingkidsparty.app.service;

import java.io.IOException;

public interface CurrencyConverterService {

    double convert(String from, String to, int amount) throws IOException;
}
