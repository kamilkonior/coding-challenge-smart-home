package home.smart.controller;

import home.smart.domain.service.room.RoomAllocationService;
import home.smart.dto.occupancy.OccupancyHttpRequest;
import home.smart.dto.occupancy.OccupancyHttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OccupancyController {
    private final RoomAllocationService roomAllocationService;

    public OccupancyController(RoomAllocationService roomAllocationService) {
        this.roomAllocationService = roomAllocationService;
    }

    @PostMapping("/occupancy")
    public ResponseEntity<OccupancyHttpResponse> allocateRooms(@RequestBody OccupancyHttpRequest occupancyHttpRequest) {
        var occupancyRequest = occupancyHttpRequest.intoDomainRequest();
        var occupancyResponse = roomAllocationService.allocateRoomsForGuests(occupancyRequest);
        return ResponseEntity.ok(OccupancyHttpResponse.fromDomainResponse(occupancyResponse));
    }
}
