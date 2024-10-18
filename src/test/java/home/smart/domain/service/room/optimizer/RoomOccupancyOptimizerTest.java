package home.smart.domain.service.room.optimizer;

import home.smart.domain.model.room.OccupiedRoom;
import home.smart.domain.model.room.UnoccupiedRoom;
import home.smart.domain.model.value.Bid;
import home.smart.domain.model.value.Price;
import home.smart.domain.model.value.RoomCategory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static home.smart.domain.model.value.RoomCategory.ECONOMY;
import static home.smart.domain.model.value.RoomCategory.PREMIUM;
import static org.assertj.core.api.Assertions.assertThat;

class RoomOccupancyOptimizerTest {

    @Test
    public void shouldAllocateGuestIntoEconomyRoom() {
        //given
        var sysUnderTest = new RoomBookingAllocator(Price.of(100));

        //when
        var rooms = sysUnderTest.allocate(
                List.of(room(ECONOMY)),
                List.of(bid(50))
        );

        //then
        assertThat(rooms).containsExactly(room(ECONOMY, 50));
    }

    private static Bid bid(long value) {
        return Bid.of(Price.of(value));
    }

    private static UnoccupiedRoom room(RoomCategory category) {
        return new UnoccupiedRoom(category);
    }

    private static OccupiedRoom room(RoomCategory category, long value) {
        return new OccupiedRoom(Price.of(value), category);
    }

    @Test
    public void shouldUpgradeGuestWithInsufficientFundsIntoPremiumRoom() {
        //given
        var sysUnderTest = new RoomBookingAllocator(Price.of(100));

        //when
        var rooms = sysUnderTest.allocate(
                List.of(room(PREMIUM)),
                List.of(bid(50))
        );

        //then
        assertThat(rooms).containsExactly(room(PREMIUM, 50));
    }

    @Test
    public void shouldNotUpgradeGuestWithInsufficientFundsIntoPremiumRoom() {
        //given
        var sysUnderTest = new RoomBookingAllocator(Price.of(100));

        //when
        var rooms = sysUnderTest.allocate(
                List.of(room(PREMIUM), room(ECONOMY), room(PREMIUM)),
                List.of(bid(50))
        );

        //then
        assertThat(rooms).containsExactly(room(ECONOMY, 50));
    }

    @Test
    public void shouldAllocatePremiumGuestIntoPremiumRoom() {
        //given
        var sysUnderTest = new RoomBookingAllocator(Price.of(100));

        //when
        var rooms = sysUnderTest.allocate(
                List.of(room(PREMIUM)),
                List.of(bid(100))
        );

        //then
        assertThat(rooms).containsExactly(room(PREMIUM, 100));
    }

    @Test
    public void shouldAllocatePremiumGuestIntoPremiumRoomDespiteCheaperAlternatives() {
        //given
        var sysUnderTest = new RoomBookingAllocator(Price.of(100));

        //when
        var rooms = sysUnderTest.allocate(
                List.of(room(ECONOMY), room(PREMIUM), room(ECONOMY)),
                List.of(bid(100))
        );

        //then
        assertThat(rooms).containsExactly(room(PREMIUM, 100));
    }

    @Test
    public void shouldRejectPremiumGuestDueToLackOfPremiumRooms() {
        //given
        var sysUnderTest = new RoomBookingAllocator(Price.of(100));

        //when
        var rooms = sysUnderTest.allocate(
                List.of(room(ECONOMY)),
                List.of(bid(100))
        );

        //then
        assertThat(rooms).isEmpty();
    }

    @Test
    public void shouldPrioritizePremiumGuests() {
        //given
        var sysUnderTest = new RoomBookingAllocator(Price.of(100));

        //when
        var rooms = sysUnderTest.allocate(
                List.of(room(ECONOMY), room(PREMIUM)),
                List.of(bid(50), bid(75), bid(100))
        );

        //then
        assertThat(rooms).containsExactly(room(ECONOMY, 50), room(PREMIUM, 100));
    }
}