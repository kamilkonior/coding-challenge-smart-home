package home.smart.domain.service.room.allocation;

import home.smart.domain.model.value.RoomCategory;
import home.smart.domain.model.room.UnoccupiedRoom;

import java.util.*;

import static java.util.Collections.emptyIterator;
import static java.util.stream.Collectors.*;

class RoomsByCategory {
    private final Map<RoomCategory, Iterator<UnoccupiedRoom>> roomsByCategory;

    public RoomsByCategory(Collection<UnoccupiedRoom> unoccupiedRooms) {
        this.roomsByCategory = unoccupiedRooms.stream()
                .collect(groupingBy(
                        UnoccupiedRoom::category,
                        collectingAndThen(toList(), List::iterator))
                );
    }

    public Optional<UnoccupiedRoom> tryNext(RoomCategory category) {
        var iter = roomsByCategory.getOrDefault(category, emptyIterator());
        return iter.hasNext() ? Optional.of(iter.next()) : Optional.empty();
    }
}
