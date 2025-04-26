package pl.edu.ug.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.ug.task.model.Rate;
import pl.edu.ug.task.service.CurrencyExchange;

import java.time.LocalDate;

/**
 * Controller for pages
 *
 * @author Damian Staszewski
 */
@RestController
@RequiredArgsConstructor
public class ExchangeRateController {

    /**
     * Service for currency exchange data
     */
    private final CurrencyExchange exchange;

    /**
     * Currency exchange data for a given date and currency code
     *
     * @param code currency code
     * @param date exchange date
     * @return exchange rate
     */
    @CrossOrigin
    @GetMapping(value = "/api/exchange/{code}/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Rate exchange(@PathVariable String code, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return exchange.getRate(code, date).getRates()[0];
    }
}
