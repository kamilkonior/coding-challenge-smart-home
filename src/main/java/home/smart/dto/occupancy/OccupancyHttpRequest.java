package home.smart.dto.occupancy;

import java.util.List;

public record OccupancyHttpRequest(
        int premiumRooms,
        int economyRooms,
        List<Double> guestsBids
) {
}
