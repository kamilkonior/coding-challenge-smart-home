package home.smart.domain.service.room;

import home.smart.domain.request.OccupancyRequest;
import home.smart.domain.response.OccupancyResponse;
import home.smart.domain.service.room.optimizer.RoomBookingAllocator;
import org.springframework.stereotype.Service;

@Service
public class RoomAllocationService {
    private final RoomBookingAllocator roomBookingAllocator;

    public RoomAllocationService(RoomBookingAllocator roomBookingAllocator) {
        this.roomBookingAllocator = roomBookingAllocator;
    }

    public OccupancyResponse allocateRoomsForGuests(OccupancyRequest occupancyRequest) {
        var occupiedRooms = roomBookingAllocator.allocate(occupancyRequest.unoccupiedRooms(), occupancyRequest.guestsBids());
        return new OccupancyResponse(occupiedRooms);
    }
}
