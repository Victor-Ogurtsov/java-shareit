package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
@ToString
public class RequestBookingDto {
    @NotNull
    private LocalDateTime start;
    @NotNull
    private LocalDateTime end;
    @NotNull
    Long itemId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RequestBookingDto that)) return false;
        return Objects.equals(getStart(), that.getStart()) && Objects.equals(getEnd(), that.getEnd()) && Objects.equals(getItemId(), that.getItemId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStart(), getEnd(), getItemId());
    }
}
