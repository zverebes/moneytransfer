package moneytransfer.exchangerate.api;

import java.util.List;
import java.util.Map;

public class ExchangeRateResponse {

    private String base;

    private Map<String, Double> rates;

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
}
