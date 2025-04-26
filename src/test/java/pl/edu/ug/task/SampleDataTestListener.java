package pl.edu.ug.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.ApplicationListener;
import pl.edu.ug.task.data.Items;
import pl.edu.ug.task.model.Item;
import pl.edu.ug.task.service.CurrencyExchange;

import java.time.LocalDate;

@Slf4j
@TestComponent
public class SampleDataTestListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Items items = event.getApplicationContext().getBean(Items.class);
        CurrencyExchange exchange = event.getApplicationContext().getBean(CurrencyExchange.class);
        log.info("Injecting sample data into database...");
        items.save(first(exchange));
        items.save(second(exchange));
        items.save(third(exchange));
    }

    private Item first(CurrencyExchange exchange) {
        Item item = new Item();
        item.setName("ACER Aspire");
        item.setDate(LocalDate.parse("2025-01-03"));
        item.setUsd(345);
        item.setPln(exchange.exchangeTo(345, LocalDate.parse("2025-01-03")));
        return item;
    }

    private Item second(CurrencyExchange exchange) {
        Item item = new Item();
        item.setName("DELL Latitude");
        item.setDate(LocalDate.parse("2025-01-10"));
        item.setUsd(543);
        item.setPln(exchange.exchangeTo(543, LocalDate.parse("2025-01-10")));
        return item;
    }

    private Item third(CurrencyExchange exchange) {
        Item item = new Item();
        item.setName("HP Victus");
        item.setDate(LocalDate.parse("2025-01-19"));
        item.setUsd(346);
        item.setPln(exchange.exchangeTo(346, LocalDate.parse("2025-01-19")));
        return item;
    }
}
