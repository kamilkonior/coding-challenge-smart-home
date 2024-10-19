package home.smart.dto.occupancy;

import home.smart.domain.model.room.UnoccupiedRoom;
import home.smart.domain.model.value.Bid;
import home.smart.domain.model.value.Price;
import home.smart.domain.model.value.RoomCategory;
import home.smart.domain.request.OccupancyRequest;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record OccupancyHttpRequest(
    @Min(value = 0, message = "Premium rooms count must be zero or positive")
    int premiumRooms,
    @Min(value = 0, message = "Economy rooms count must be zero or positive")
    int economyRooms,
    @NotNull(message = "List of potential guests must be provided")
    List<Double> potentialGuests
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

        var guestsBids = potentialGuests().stream()
            .map(bid -> Bid.of(Price.of(new BigDecimal(bid))))
            .toList();

        return new OccupancyRequest(unoccupiedRooms, guestsBids);
    }
}
