import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {
    //test{Method}_Should{Do}_When{Condition}

    @Test
    void testConstructor_ShouldThrowIllegalArgumentException_WhenArgIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void testConstructor_ShouldWriteMessage_WhenArgIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void testConstructor_ShouldThrowIllegalArgumentException_WhenArgIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.emptyList()));
    }

    @Test
    void testConstructor_ShouldReturnMessage_WhenArgIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new Hippodrome(Collections.emptyList()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void testGetHorses_ShouldReturnSameHorsesInSameOrder() {

        List<Horse> expectedHorses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            expectedHorses.add(new Horse("Horse_" + i, i * 1.5, i * 2.0));
        }
        Hippodrome hippodrome = new Hippodrome(expectedHorses);
        List<Horse> actualHorses = hippodrome.getHorses();

        assertIterableEquals(expectedHorses, actualHorses);
    }

    @Test
    void testMove_ShouldCallMoveOnAllHorses() {

        List<Horse> mockHorses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mockHorses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(mockHorses);
        hippodrome.move();

        mockHorses.forEach(horse -> verify(horse).move());
    }

    @Test
    void testGetWinner_ShouldReturnHorseWithMaxDistance() {
        Horse horse1 = mock(Horse.class);
        Horse horse2 = mock(Horse.class);
        Horse horse3 = mock(Horse.class);

        when(horse1.getDistance()).thenReturn(2.5);
        when(horse2.getDistance()).thenReturn(7.8);
        when(horse3.getDistance()).thenReturn(4.2);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        assertSame(horse2, hippodrome.getWinner());
    }

}