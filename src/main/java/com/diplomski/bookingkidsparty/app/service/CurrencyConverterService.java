package com.diplomski.bookingkidsparty.app.service;

import java.io.IOException;

public interface CurrencyConverterService {

    double convert(int amount, String from, String to) throws IOException;
}
