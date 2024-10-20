package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.comment.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ItemWithBookingDtoMapper {

    @Mapping(target = "id", source = "item.id")
    ItemWithBookingDto toItemWithBookingDto(Item item, Booking lastBooking, Booking nextBooking, List<Comment> comments);
}
