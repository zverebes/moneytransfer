package moneytransfer.exchangerate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import moneytransfer.exchangerate.api.ExchangeRateResponse;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

//@SpringBootTest
@RestClientTest(ExchangeRateService.class)
public class ExchangeRateServiceTest {

    @Autowired
    private ExchangeRateService service;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void init() throws JsonProcessingException {
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse();
        exchangeRateResponse.setBase("HUF");
        Map<String, Double> target = new HashMap<>();
        target.put("USD", 0.00349);
        exchangeRateResponse.setRates(target);
        String mockResponse = mapper.writeValueAsString(exchangeRateResponse);

        this.server.expect(requestTo("https://api.exchangeratesapi.io/latest?base=HUF&symbols=USD"))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

    }

    @Test
    void testAPIResponse() {
        ExchangeRate rate = service.findExchangeRate("HUF", "USD");
        assertEquals("HUF", rate.getSourceCurrency());
        assertEquals("USD", rate.getTargetCurrency());
        MatcherAssert.assertThat(BigDecimal.valueOf(0.00349), Matchers.comparesEqualTo(rate.getRate()));
    }
}
