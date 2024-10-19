package home.smart.dto.occupancy;

import home.smart.domain.model.room.OccupiedRoom;
import home.smart.domain.model.value.Price;
import home.smart.domain.model.value.RoomCategory;
import home.smart.domain.response.OccupancyResponse;

import static java.util.stream.Collectors.*;

public record OccupancyHttpResponse(
    long usagePremium,
    String revenuePremium,
    long usageEconomy,
    String revenueEconomy
) {
    public static OccupancyHttpResponse fromDomainResponse(OccupancyResponse occupancyResponse) {
        var statisticsByCategory = occupancyResponse.occupiedRooms()
            .stream()
            .collect(
                groupingBy(
                    OccupiedRoom::roomCategory,
                    teeing(
                        counting(),
                        reducing(Price.ZERO, OccupiedRoom::price, Price::add),
                        OccupancySummaryStatistics::new
                    )
                )
            );

        var premiumRoomsOccupancyStatistics = statisticsByCategory.getOrDefault(RoomCategory.PREMIUM, OccupancySummaryStatistics.EMPTY);
        var economyRoomsOccupancyStatistics = statisticsByCategory.getOrDefault(RoomCategory.ECONOMY, OccupancySummaryStatistics.EMPTY);

        return new OccupancyHttpResponse(
            premiumRoomsOccupancyStatistics.count(),
            premiumRoomsOccupancyStatistics.revenue().value().toString(),
            economyRoomsOccupancyStatistics.count(),
            economyRoomsOccupancyStatistics.revenue().value().toString()
        );
    }

    private record OccupancySummaryStatistics(long count, Price revenue) {
        public static OccupancySummaryStatistics EMPTY = new OccupancySummaryStatistics(0, Price.ZERO);
    }
}
