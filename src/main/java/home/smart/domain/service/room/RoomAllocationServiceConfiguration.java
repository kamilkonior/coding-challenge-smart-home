package home.smart.domain.service.room;

import home.smart.domain.model.value.Price;
import home.smart.domain.service.room.allocation.RoomBookingAllocator;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@Configurable
public class RoomAllocationServiceConfiguration {
    @Bean
    public RoomBookingAllocator roomOccupancyOptimizer() {
        return new RoomBookingAllocator(Price.of(BigDecimal.valueOf(100)));
    }
}
