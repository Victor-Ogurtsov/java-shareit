package ru.practicum.shareit.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {

    @Query(
                    "select ir " +
                    "from ItemRequest as ir " +
                    "JOIN FETCH ir.requestor as u " +
                    "where ir.id = ?1"
    )
    Optional<ItemRequest> findItemRequestById(Long id);

    @Query(
                    "select ir " +
                    "from ItemRequest as ir " +
                    "JOIN FETCH ir.requestor as u " +
                    "where u.id = ?1"
    )
    List<ItemRequest> findAllItemRequestByUserId(Long id);

    @Query(
                    "select ir " +
                    "from ItemRequest as ir " +
                    "JOIN FETCH ir.requestor as u " +
                    "order by ir.created desc"
    )
    List<ItemRequest> findAllSortedItemRequest();
}
