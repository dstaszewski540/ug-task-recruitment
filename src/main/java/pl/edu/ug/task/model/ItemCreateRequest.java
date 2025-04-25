package pl.edu.ug.task.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

/**
 * Item create request data
 *
 * @author Damian Staszewski
 */
@Data
@Validated
public class ItemCreateRequest {
    /**
     * Item name
     */
    @NotNull
    private String name;
    /**
     * Item price in USD
     */
    @NotNull
    @Positive
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double price;
    /**
     * Item accounting date
     */
    @NotNull
    @PastOrPresent
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
}
