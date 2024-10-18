package ru.practicum.shareit.item.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(
                    "select c " +
                    "from Comment as c " +
                    "JOIN FETCH c.item as i " +
                    "JOIN FETCH i.owner as ou " +
                    "JOIN FETCH c.author as au " +
                    "where i.id in ?1 "
    )
    List<Comment> getCommentListByItemIdList(List<Long> itemIdList);

    @Query(
            "select c " +
                    "from Comment as c " +
                    "JOIN FETCH c.item as i " +
                    "JOIN FETCH i.owner as ou " +
                    "JOIN FETCH c.author as au " +
                    "where i.id = ?1 "
    )
    List<Comment> getCommentListByItemId(Long itemId);
}
