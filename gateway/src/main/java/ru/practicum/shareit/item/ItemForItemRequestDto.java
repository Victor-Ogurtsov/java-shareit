package ru.practicum.shareit.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemForItemRequestDto {

    private Long id;
    private String name;
    private Long ownerId;
}