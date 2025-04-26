package pl.edu.ug.task.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.edu.ug.task.data.Items;
import pl.edu.ug.task.model.Item;
import pl.edu.ug.task.model.ItemCreateRequest;
import pl.edu.ug.task.service.CurrencyExchange;

import java.net.URI;
import java.time.LocalDate;

/**
 * REST controller for {@link Item} entity
 *
 * @author Damian Staszewski
 */
@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/items")
public class RestItemController {
    private final Items items;
    private final CurrencyExchange exchange;

    /**
     * @param query     name query case-insensitive
     * @param startDate accounting date started - format YYYY-MM-DD
     * @param endDate   accounting date ended - format YYYY-MM-DD
     * @param limit     limit of items for the page
     * @param page      current page
     * @param sort      sorting by name or accounting date. or booth
     * @return items in JSON format
     */
    @GetMapping
    @CrossOrigin
    public ResponseEntity<Page<Item>> fetch(@RequestParam(value = "query", required = false) String query,
                                            @RequestParam(value = "start_date", required = false)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                            @RequestParam(value = "end_date", required = false)
                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                            @RequestParam(value = "limit", defaultValue = "20")
                                            @Min(5) @Max(100) int limit,
                                            @RequestParam(value = "page", defaultValue = "1")
                                            @Min(1) int page,
                                            @RequestParam(value = "sort_by", defaultValue = "name") String sort,
                                            @RequestParam(value = "sort_direction", defaultValue = "ASC")
                                            Sort.Direction direction
    ) {
        try {
            Sort s = Sort.by(direction, sort);
            Pageable p = PageRequest.of(page - 1, limit, s);
            Page<Item> items;
            if (query != null) {
                if (startDate != null || endDate != null) {
                    items = this.items.findByNameIsContainingIgnoreCaseAndDateIsBetween(query, startDate, endDate, p);
                } else {
                    items = this.items.findByNameIsContainingIgnoreCase(query, p);
                }
            } else {
                if (startDate != null || endDate != null) {
                    items = this.items.findByDateIsBetween(startDate, endDate, p);
                } else {
                    items = this.items.findAll(p);
                }
            }
            return ResponseEntity.ok(items);
        } catch (Exception e) {
            log.error("Error occurred while fetching items", e);
            return handleError(e);
        }
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<Item> create(@Valid @RequestBody ItemCreateRequest body) {
        try {
            Item i = new Item();
            i.setName(body.getName());
            i.setUsd(body.getPrice());
            i.setDate(body.getDate());
            i.setPln(exchange.exchangeTo(i.getUsd(), i.getDate()));
            i = items.saveAndFlush(i);
            return ResponseEntity.created(URI.create("/api/items/%d".formatted(i.getId()))).body(i);
        } catch (Exception e) {
            log.error("Error occurred while creating item", e);
            return handleError(e);
        }
    }

    @CrossOrigin
    @GetMapping(value = "/{id}")
    public ResponseEntity<Item> get(@PathVariable("id") long id) {
        try {
            return items.findById(id).map(ResponseEntity::ok)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item " + id + " not found"));
        } catch (Exception e) {
            log.error("Error occurred while fetching item", e);
            return handleError(e);
        }
    }

    @CrossOrigin
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Item> update(@PathVariable("id") long id, @RequestBody ItemCreateRequest body) {
        try {
            Item i = items.getReferenceById(id);
            if (body.getPrice() != i.getUsd() && body.getPrice() > 0.0) {
                i.setUsd(body.getPrice());
                i.setPln(exchange.exchangeTo(i.getUsd(), i.getDate()));
            }
            if (body.getName() != null && !body.getName().isEmpty() && !body.getName().equalsIgnoreCase(i.getName())) {
                i.setName(body.getName());
            }
            if (body.getDate() != null) {
                i.setDate(body.getDate());
            }
            return ResponseEntity.ok(items.saveAndFlush(i));
        } catch (Exception e) {
            log.error("Error occurred while updating item", e);
            return handleError(e);
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        try {
            items.deleteById(id);
            items.flush();
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error occurred while deleting item", e);
            return handleError(e);
        }
    }

    private <T> ResponseEntity<T> handleError(Exception e) {
        if (e instanceof ResponseStatusException) {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(((ResponseStatusException) e).getStatusCode(), ((ResponseStatusException) e).getReason())).build();
        }
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage())).build();
    }
}
