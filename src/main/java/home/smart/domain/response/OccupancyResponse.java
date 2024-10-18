package home.smart.domain.response;

import home.smart.domain.model.room.OccupiedRoom;

import java.util.List;

public record OccupancyResponse(List<OccupiedRoom> occupiedRooms) {
}
