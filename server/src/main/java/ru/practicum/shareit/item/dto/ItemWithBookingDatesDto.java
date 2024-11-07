package ru.practicum.shareit.item.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ItemWithBookingDatesDto {

    private  Long id;
    private String name;
    private String description;
    private Boolean available;
    private UserDto owner;
    private ItemRequest request;
    private LocalDateTime lastBookingStart;
    private LocalDateTime lastBookingEnd;
    private LocalDateTime nextBookingStart;
    private LocalDateTime nextBookingEnd;
    List<Comment> comments;
}
