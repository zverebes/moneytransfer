package moneytransfer.exchangerate;

import java.math.BigDecimal;

public class ExchangeRate {

    private final String sourceCurrency;
    private final String targetCurrency;
    private final BigDecimal rate;

    public ExchangeRate(String sourceCurrency, String targetCurrency, BigDecimal rate) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }


    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public ExchangeRate invert() {
        return new ExchangeRate(targetCurrency, sourceCurrency, BigDecimal.ONE.divide(rate));
    }
}
