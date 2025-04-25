package pl.edu.ug.task.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.ug.task.model.Item;

import java.time.LocalDate;


/**
 * Data access interface for {@link Item} entity
 *
 * @author Damian Staszewski
 */
public interface Items extends JpaRepository<Item, Long> {
    /**
     * Search by name contains any name part ignores cases
     * QUERY: {@code SELECT * FROM Item * WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%'))}
     *
     * @param name query
     * @param p    pagination
     * @return items
     */
    Page<Item> findByNameIsContainingIgnoreCase(String name, Pageable p);

    /**
     * Search by accounting date
     * QUERY: {@code SELECT * FROM Item WHERE date is between :dateAfter and :dateBefore}
     *
     * @param dateBefore start accounting date
     * @param dateAfter  end accounting date
     * @param p                 pagination
     * @return items
     */
    Page<Item> findByDateIsBetween(LocalDate dateAfter, LocalDate dateBefore, Pageable p);

    /**
     * Search by name and account date
     * QUERY: {@code SELECT * FROM Item WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name, '%')) AND date is between :dateAfter and :dateBefore}
     *
     * @param name              query
     * @param dateBefore start accounting date
     * @param dateAfter  end accounting date
     * @param p                 pagination
     * @return requested items
     */
    Page<Item> findByNameIsContainingIgnoreCaseAndDateIsBetween(String name, LocalDate dateAfter, LocalDate dateBefore, Pageable p);
}
