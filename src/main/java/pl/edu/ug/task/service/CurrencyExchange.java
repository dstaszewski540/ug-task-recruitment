package pl.edu.ug.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import pl.edu.ug.task.model.Exchange;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CurrencyExchange {

    private static final String EXCHANGE_URL = "https://api.nbp.pl/api/exchangerates/rates/C";

    /**
     * Exchange USD to PLN for a given date.
     * We do pull bid rate (Table C) for a specific date.
     * We do not use an average exchange rate (Table A and/or B).
     *
     * @param usd  USD amount
     * @param date exchange date
     * @return PLN amount
     */
    public double exchangeTo(double usd, LocalDate date) {
        return Math.round(Math.ceil(usd * getRate("USD", date).getRates()[0].getBid() * 100.0)) / 100.0;
    }

    /**
     * Pull exchange rate data from API
     *
     * @param code 3 letter currency code
     * @param date exchange date
     * @return exchange rate data
     */
    public Exchange getRate(String code, LocalDate date) {
        try {
            var res = RestClient.create(EXCHANGE_URL).get().uri("/%s/%s".formatted(code, formatDate(date)))
                    .retrieve().toEntity(Exchange.class); // respond data as the Exchange object
            return res.getBody();// convert raw data to the Exchange object if there is no issue
        } catch (HttpClientErrorException e) { // pull exception if error occurred
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) { // result 404 when the current date is not having any data
                return getRate(code, date.minusDays(1)); // call day before until data is available
            } else {
                throw e; // otherwise throw exception with the original error message and status code
            }
        }
    }

    /**
     * Format date to ISO format for API request
     *
     * @param date date to format
     * @return RAW date in ISO format
     */
    private String formatDate(LocalDate date) {
        return DateTimeFormatter.ISO_DATE.format(date); // format date to ISO format for API request
    }
}
