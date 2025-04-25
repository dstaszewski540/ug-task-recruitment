package pl.edu.ug.task.model;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "invoice")
@JsonRootName("invoice")
@JacksonXmlRootElement(localName = "computer")
public class Item {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double usd;
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private double pln;
}
