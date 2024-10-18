package ru.practicum.shareit.item.comment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.model.Item;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@ToString
public class CommentDto {
    private Long id;
    private String text;
    private Item item;
    private String authorName;
    private LocalDateTime created = LocalDateTime.now();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentDto that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getText(), that.getText()) && Objects.equals(getItem(), that.getItem()) && Objects.equals(getAuthorName(), that.getAuthorName()) && Objects.equals(getCreated(), that.getCreated());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText(), getItem(), getAuthorName(), getCreated());
    }
}
