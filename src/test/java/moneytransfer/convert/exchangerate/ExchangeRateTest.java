package moneytransfer.convert.exchangerate;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExchangeRateTest {

    @Test
    void testExchangeRateFlip() {
        ExchangeRate rate = new ExchangeRate("USD", "EUR", BigDecimal.TEN);
        ExchangeRate flipedRate = rate.invert();
        assertEquals("EUR", flipedRate.getSourceCurrency());
        assertEquals("USD", flipedRate.getTargetCurrency());
        assertThat(BigDecimal.ONE.divide(BigDecimal.TEN), comparesEqualTo(flipedRate.getRate()));
    }
}
