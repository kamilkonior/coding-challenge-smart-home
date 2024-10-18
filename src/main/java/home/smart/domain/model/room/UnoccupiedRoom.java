package home.smart.domain.model.room;

import home.smart.domain.model.value.RoomCategory;
import home.smart.domain.model.value.Price;

public record UnoccupiedRoom(Price price, RoomCategory category) {
}
