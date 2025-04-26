package pl.edu.ug.task;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.ug.task.model.Exchange;
import pl.edu.ug.task.service.CurrencyExchange;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class CurrencyExchangeTest {
    @Autowired
    private CurrencyExchange exchange;

    @Test
    void liveExchangeFromUSDtoPLN() {
        // given
        double usd = 599.00;
        LocalDate date = LocalDate.parse("2025-01-10");
        // when
        double pln = exchange.exchangeTo(usd, date);
        // then
        assertEquals(2453.93, pln);
    }

    @Test
    void liveExchangeFromUSDtoPLNInHolidays() {
        // given
        double usd = 499.00;
        LocalDate date = LocalDate.parse("2025-01-01");
        // when
        double pln = exchange.exchangeTo(usd, date);
        // then
        assertEquals(2028.09, pln);
    }

    @Test
    void liveExchangeRate() {
        // given
        double usd = 599.00;
        LocalDate date = LocalDate.parse("2025-01-10");
        // when
        Exchange x = exchange.getRate("USD", date);
        // then
        assertEquals(4.0967, x.getRates()[0].getBid());
        assertEquals(4.1795, x.getRates()[0].getAsk());
    }

    @Test
    void liveExchangeRateInHolidays() {
        // given
        double usd = 599.00;
        LocalDate date = LocalDate.parse("2024-12-25");
        // when
        Exchange x = exchange.getRate("USD", date);
        // then
        assertNotEquals(date, x.getRates()[0].getEffectiveDate());
        assertEquals(4.0665, x.getRates()[0].getBid());
        assertEquals(4.1487, x.getRates()[0].getAsk());
    }
}