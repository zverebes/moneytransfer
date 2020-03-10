package moneytransfer.convert;

import moneytransfer.convert.exchangerate.ExchangeRate;
import moneytransfer.convert.exchangerate.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MoneyConvertTest {

    @Mock
    ExchangeRateService exchangeRateService;

    @ParameterizedTest
    @CsvSource({"1, 1000, 1000", "2, 1000, 500", "0.5, 1000, 2000"})
    void testConvertStraightRate(double rate, double targetAmount, double expectedAmount) {
        ExchangeRate exchangeRate = new ExchangeRate("EUR", "USD", BigDecimal.valueOf(rate));
        MoneyConverterService service = new MoneyConverterService(exchangeRateService);
        Money target = new Money(BigDecimal.valueOf(targetAmount), "USD");
        Money money = service.convert(exchangeRate, target);

        assertThat(money.getAmount(), comparesEqualTo(BigDecimal.valueOf(expectedAmount)));
        assertEquals("EUR", money.getCurrency());
    }

    @ParameterizedTest
    @CsvSource({"1, 1000, 1000", "0.5, 1000, 500", "2, 1000, 2000"})
    void testConvertInvertedRate(double rate, double targetAmount, double expectedAmount) {
        ExchangeRate exchangeRate = new ExchangeRate("USD", "EUR", BigDecimal.valueOf(rate));
        MoneyConverterService service = new MoneyConverterService(exchangeRateService);
        Money target = new Money(BigDecimal.valueOf(targetAmount), "USD");
        Money money = service.convert(exchangeRate, target);

        assertThat(money.getAmount(), comparesEqualTo(BigDecimal.valueOf(expectedAmount)));
        assertEquals("EUR", money.getCurrency());
    }

    @Test
    void testFindExchangeRate() {
        when(exchangeRateService.findExchangeRate("EUR", "USD"))
                .thenReturn(new ExchangeRate("EUR", "USD", BigDecimal.ONE));

        MoneyConverterService service = new MoneyConverterService(exchangeRateService);
        ExchangeRate rate = service.findExchangeRate("EUR", "USD");
        assertEquals("EUR", rate.getSourceCurrency());
        assertEquals("USD", rate.getTargetCurrency());
    }
}
