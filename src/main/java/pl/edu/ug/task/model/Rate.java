package pl.edu.ug.task.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.time.LocalDate;

/**
 * Exchange rate data
 *
 * @author Damian Staszewski
 */
@Data
@JsonRootName("rate")
public class Rate {
    /**
     * Table identifier
     */
    private String no;
    /**
     * Effective local date
     */
    private LocalDate effectiveDate;
    /**
     * Bid rate
     */
    private double bid;
    /**
     * Ask rate
     */
    private double ask;
}
