package ru.practicum.shareit.booking;

import java.util.Comparator;

public class BookingStartComparator implements Comparator<Booking> {

    @Override
    public int compare(Booking o1, Booking o2) {
        return o1.getStart().compareTo(o2.getStart());
    }
}