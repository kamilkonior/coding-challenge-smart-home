package home.smart.dto.occupancy;

public record OccupancyHttpResponse(
        int usagePremium,
        String revenuePremium,
        int usageEconomy,
        String revenueEconomy
) {
}
