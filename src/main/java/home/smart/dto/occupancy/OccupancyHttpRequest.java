package home.smart.dto.occupancy;

import home.smart.domain.model.room.UnoccupiedRoom;
import home.smart.domain.model.value.Bid;
import home.smart.domain.model.value.Price;
import home.smart.domain.model.value.RoomCategory;
import home.smart.domain.request.OccupancyRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record OccupancyHttpRequest(
        int premiumRooms,
        int economyRooms,
        List<String> guestsBids
) {
    public OccupancyRequest intoDomainRequest() {
        var economyRooms = Stream.generate(() -> new UnoccupiedRoom(RoomCategory.ECONOMY))
                .limit(economyRooms())
                .toList();

        var premiumRooms = Stream.generate(() -> new UnoccupiedRoom(RoomCategory.PREMIUM))
                .limit(premiumRooms())
                .toList();

        var unoccupiedRooms = new ArrayList<>(economyRooms);
        unoccupiedRooms.addAll(premiumRooms);

        var guestsBids = guestsBids().stream()
                .map(bid -> Bid.of(Price.of(new BigDecimal(bid))))
                .toList();

        return new OccupancyRequest(unoccupiedRooms, guestsBids);
    }
}
