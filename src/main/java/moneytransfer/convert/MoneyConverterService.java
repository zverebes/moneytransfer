package moneytransfer.convert;

import moneytransfer.exchangerate.ExchangeRate;
import moneytransfer.exchangerate.ExchangeRateService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Service
public class MoneyConverterService {

    private final ExchangeRateService exchangeRateService;

    public MoneyConverterService(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    ExchangeRate findExchangeRate(String sourceCurrency, String targetCurrency) {
        return exchangeRateService.findExchangeRate(sourceCurrency, targetCurrency);
    }

    public Money convert(@NotNull ExchangeRate rate, @NotNull Money target) {
        BigDecimal targetAmount = target.getAmount();
        BigDecimal actualRate;
        String targetCurrency;
        if (!target.getCurrency().equalsIgnoreCase(rate.getTargetCurrency())) {
            rate = rate.invert();
        }

        actualRate = rate.getRate();
        targetCurrency = rate.getSourceCurrency();
        BigDecimal amount = targetAmount.divide(actualRate);
        return new Money(amount, targetCurrency);
    }
}
