package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(
                    "select i " +
                    "from Item as i " +
                    "JOIN FETCH i.owner as u " +
                    "where u.id = ?1"
    )
    List<Item> findAllItemsByUserId(Long userId);

    @Query(
                    "select i " +
                    "from Item as i " +
                    "JOIN FETCH i.owner as u " +
                    "where i.id = ?1"
    )
    Optional<Item> findItemById(Long id);

    @Query(
                    "select i from Item i " +
                    "JOIN FETCH i.owner as u " +
                    "where i.available = true and (upper(i.name) like upper(concat('%', ?1, '%')) " +
                    "or upper(i.description) like upper(concat('%', ?1, '%')))"
    )
    List<Item> getItemListBySearch(String searchQuery);
}
