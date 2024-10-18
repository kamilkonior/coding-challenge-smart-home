package home.smart.domain.service.room.optimizer;

import home.smart.domain.model.room.OccupiedRoom;
import home.smart.domain.model.room.UnoccupiedRoom;
import home.smart.domain.model.value.Bid;
import home.smart.domain.model.value.Price;
import home.smart.domain.model.value.RoomCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoomOccupancyOptimizer {
    private final Price premiumRoomPriceThreshold;

    public RoomOccupancyOptimizer(Price premiumRoomPriceThreshold) {
        this.premiumRoomPriceThreshold = premiumRoomPriceThreshold;
    }

    public List<OccupiedRoom> optimize(
            Collection<UnoccupiedRoom> unoccupiedRooms,
            Collection<Bid> guestsBids
    ) {
        var roomsByCategory = new RoomsByCategory(unoccupiedRooms);
        var guestsBidsAscending = guestsBids.stream()
                .sorted()
                .toList();

        var occupiedRooms = new ArrayList<OccupiedRoom>(guestsBids.size());
        for (var bid : guestsBidsAscending) {
            if (bid.value().compareTo(premiumRoomPriceThreshold) < 0) {
                roomsByCategory.tryNext(RoomCategory.ECONOMY)
                        .or(() -> roomsByCategory.tryNext(RoomCategory.PREMIUM))
                        .ifPresent(room -> occupiedRooms.add(new OccupiedRoom(bid.value(), room.category())));
            } else {
                roomsByCategory.tryNext(RoomCategory.PREMIUM)
                        .ifPresent(room -> occupiedRooms.add(new OccupiedRoom(bid.value(), room.category())));
            }
        }

        return occupiedRooms;
    }
}
