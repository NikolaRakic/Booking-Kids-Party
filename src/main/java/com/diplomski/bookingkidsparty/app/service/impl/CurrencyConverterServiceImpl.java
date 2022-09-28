package com.diplomski.bookingkidsparty.app.service.impl;

import com.diplomski.bookingkidsparty.app.dto.response.CurrencyConverterResponseDTO;
import com.diplomski.bookingkidsparty.app.service.CurrencyConverterService;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Collections;

@Service
public class CurrencyConverterServiceImpl implements CurrencyConverterService {

    @Override
    public double convert(String from, String to, int amount) throws IOException {

        String uri = "https://api.exchangerate.host/convert?from="+from+"&to="+to+"&amount="+amount;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(uri)
                .build();

        try{
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();
            Gson g = new Gson();
            CurrencyConverterResponseDTO responseJson = g.fromJson(responseString, CurrencyConverterResponseDTO.class);
           return responseJson.getResult();
        } catch (IOException ex) {
            System.out.println(ex);
            throw new IOException();
        }
    }
}
