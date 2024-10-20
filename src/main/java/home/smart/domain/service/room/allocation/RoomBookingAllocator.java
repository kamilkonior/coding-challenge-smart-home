package home.smart.domain.service.room.allocation;

import home.smart.domain.model.room.OccupiedRoom;
import home.smart.domain.model.room.UnoccupiedRoom;
import home.smart.domain.model.value.Bid;
import home.smart.domain.model.value.Price;
import home.smart.domain.model.value.RoomCategory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class RoomBookingAllocator {
    private final Price premiumRoomPriceThreshold;

    public RoomBookingAllocator(Price premiumRoomPriceThreshold) {
        this.premiumRoomPriceThreshold = premiumRoomPriceThreshold;
    }

    public List<OccupiedRoom> allocate(
        Collection<UnoccupiedRoom> unoccupiedRooms,
        Collection<Bid> guestsBids
    ) {
        var roomsByCategory = new RoomsByCategory(unoccupiedRooms);
        var guestsBidsDescending = guestsBids.stream()
            .sorted(Comparator.reverseOrder())
            .toList();

        var occupiedRooms = new ArrayList<OccupiedRoom>(guestsBids.size());
        for (var bid : guestsBidsDescending) {
            if (isEconomyClassBid(bid)) {
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

    private boolean isEconomyClassBid(Bid bid) {
        return bid.value().compareTo(premiumRoomPriceThreshold) < 0;
    }
}
