package ru.practicum.shareit.booking.dto;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResponseBookingDtoMapper {

    ResponseBookingDto toResponseBookingDto(Booking booking);

    UserDto toUserDto(User user);

    ItemDto toItemDto(Item item);

    List<ResponseBookingDto> toResponseBookingDtoList(List<Booking> bookingList);
}
