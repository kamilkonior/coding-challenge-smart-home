package home.smart.domain.request;

import home.smart.domain.model.room.UnoccupiedRoom;
import home.smart.domain.model.value.Bid;

import java.util.List;

public record OccupancyRequest(List<UnoccupiedRoom> unoccupiedRooms, List<Bid> guestsBids) {
}
