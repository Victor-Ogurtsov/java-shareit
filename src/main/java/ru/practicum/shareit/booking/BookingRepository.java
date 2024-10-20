package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(
                    "select b " +
                    "from Booking as b " +
                    "JOIN FETCH b.item as i " +
                    "JOIN FETCH i.owner as ou " +
                    "JOIN FETCH b.booker as bu " +
                    "where b.id = ?1"
    )
    Optional<Booking> findBookingById(Long id);


    @Query(
                    "select b " +
                    "from Booking as b " +
                    "JOIN FETCH b.item as i " +
                    "JOIN FETCH i.owner as ou " +
                    "JOIN FETCH b.booker as bu " +
                    "where bu.id = ?1 and b.status in ?2 " +
                    "order by b.start desc"
    )
    List<Booking> getBookingListByBookerIdAndState(Long userId, List<BookingStatus> statusList);

    @Query(
                    "select b " +
                    "from Booking as b " +
                    "JOIN FETCH b.item as i " +
                    "JOIN FETCH i.owner as ou " +
                    "JOIN FETCH b.booker as bu " +
                    "where ou.id = ?1 and b.status in ?2 " +
                    "order by b.start desc"
    )
    List<Booking> getBookingListByOwnerIdAndState(Long userId, List<BookingStatus> statusList);

    @Query(
                    "select b " +
                    "from Booking as b " +
                    "JOIN FETCH b.item as i " +
                    "JOIN FETCH i.owner as ou " +
                    "JOIN FETCH b.booker as bu " +
                    "where i.id in ?1  " +
                    "order by b.start desc"
    )
    List<Booking> getBookingListByItemIdList(List<Long> itemId);

    @Query(
                    "select b " +
                    "from Booking as b " +
                    "JOIN FETCH b.item as i " +
                    "JOIN FETCH i.owner as ou " +
                    "JOIN FETCH b.booker as bu " +
                    "where bu.id = ?1 and i.id = ?2 and b.status in ?3 "
    )
    List<Booking> getBookingListByBookerIdAndItemIdAndState(Long userId, Long itemId, List<BookingStatus> statusList);

    @Query(
                    "select b " +
                    "from Booking as b " +
                    "JOIN FETCH b.item as i " +
                    "JOIN FETCH i.owner as ou " +
                    "JOIN FETCH b.booker as bu " +
                    "where i.id = ?1"
    )
    List<Booking> getBookingListByItemId(Long id);
}
