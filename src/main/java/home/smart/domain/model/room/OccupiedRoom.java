package home.smart.domain.model.room;

import home.smart.domain.model.value.Price;
import home.smart.domain.model.value.RoomCategory;

public record OccupiedRoom(Price price, RoomCategory roomCategory) {
}
