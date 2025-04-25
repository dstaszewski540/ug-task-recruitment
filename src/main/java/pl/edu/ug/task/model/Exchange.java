package pl.edu.ug.task.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

/**
 * Exchange rate data
 *
 * @author Damian Staszewski
 */
@Data
@JsonRootName("exchange")
public class Exchange {
    /**
     * Table name
     */
    private String table;
    /**
     * Currency name
     */
    private String currency;
    /**
     * Currency code
     */
    private String code;
    /**
     * Exchange rates
     */
    private Rate[] rates;
}
