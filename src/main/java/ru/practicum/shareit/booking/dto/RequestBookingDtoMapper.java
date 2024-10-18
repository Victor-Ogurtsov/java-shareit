package ru.practicum.shareit.booking.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.model.Item;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RequestBookingDtoMapper {

    @Named("itemFromLongUserId")
    default Item itemFromLongUserId(Long itemId) {
        Item item = new Item();
        item.setId(itemId);
        return item;
    }

    @Mapping(target = "item", source = "itemId",
            qualifiedByName = "itemFromLongUserId")
    Booking fromRequestBookingDto(RequestBookingDto requestBookingDto);
}