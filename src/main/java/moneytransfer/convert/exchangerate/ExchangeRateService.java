package moneytransfer.convert.exchangerate;

import moneytransfer.convert.exchangerate.api.ExchangeRateResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class ExchangeRateService {

    private static final String BASE = "https://api.exchangeratesapi.io/latest";

    private RestTemplate restTemplate;

    ExchangeRateService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public ExchangeRate findExchangeRate(String sourceCurrency, String targetCurrency) {
        String url = String.format("%s?base=%s&symbols=%s", BASE, sourceCurrency, targetCurrency);

        ExchangeRateResponse response = restTemplate.getForObject(url, ExchangeRateResponse.class);
        if (response == null) {
            return null;
        }
        double value = response.getRates().getOrDefault(targetCurrency, 0d);
        return new ExchangeRate(sourceCurrency, targetCurrency, BigDecimal.valueOf(value));
    }
}
