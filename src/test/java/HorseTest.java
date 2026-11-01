import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class HorseTest {
    //test{Method}_Should{Do}_When{Condition}

    @Test
    void testConstructor_ShouldThrowIllegalArgumentException_ThenFirstArgIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 2.5));
    }

    @Test
    void testConstructor_ShouldReturnMessage_ThenFirstArgIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 2.5, 10.0);
        });
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n", "\t \n"})
    void testConstructor_ShouldThrowIllegalArgumentException_ThenFirstArgIsSpace(String param) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(param, 2.5, 10.0);
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n", "\t \n"})
    void testConstructor_ShouldReturnMessage_ThenFirstArgIsSpace(String par) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(par, 2.5, 10.0);
        });
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void testConstructor_ShouldThrowIllegalArgumentException_ThenSecondArgIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Butsefal", -1.0, 10.0);
        });
    }

    @Test
    void testConstructor_ShouldReturnMessage_ThenSecondArgIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Butsefal", -1.0, 10.0);
        });
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void testConstructor_ShouldThrowIllegalArgumentException_ThenThirdArgIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Butsefal", 2.5, -10.0);
        });
    }

    @Test
    void testConstructor_ShouldReturnMessage_ThenThirdArgIsNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Butsefal", 2.5, -10.0);
        });
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void testGetName_ShouldReturnString() throws NoSuchFieldException, IllegalAccessException {
        //Reflection!!!
        String expectedName = "Butsefal";
        Horse horse = new Horse(expectedName, 2.5);
        Field field = Horse.class.getDeclaredField("name");
        field.setAccessible(true);
        String nameFromField = (String) field.get(horse);

        assertEquals(expectedName, nameFromField);
    }

    @Test
    void testGetSpeed_ShouldReturnDouble() throws NoSuchFieldException, IllegalAccessException {
        double expectedSpeed = 2.5;
        Horse horse = new Horse("Butsefal", expectedSpeed);
        Field field = Horse.class.getDeclaredField("speed");
        field.setAccessible(true);
        double speedFromField = (double) field.get(horse);

        assertEquals(expectedSpeed, speedFromField);
    }

    @Test
    void testGetDistance_ShouldReturnDouble_WhenTreeArg() throws NoSuchFieldException, IllegalAccessException {
        double expectedDistance = 10;
        Horse horse = new Horse("Butsefal", 2.5, expectedDistance);
        Field field = Horse.class.getDeclaredField("distance");
        field.setAccessible(true);
        double distanceFromField = (double) field.get(horse);

        assertEquals(expectedDistance, distanceFromField);
    }

    @Test
    void testGetDistance_ShouldReturnDouble_WhenTwoArg() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Butsefal", 2.5);
        double expectedDistance = 0;
        Field field = Horse.class.getDeclaredField("distance");
        field.setAccessible(true);
        double distanceFromField = (double) field.get(horse);

        assertEquals(expectedDistance, distanceFromField);
    }

    @Test
    void testMove_ShouldCallGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Butsefal", 10);
            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(ArgumentMatchers.anyDouble(), ArgumentMatchers.anyDouble()));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 1.5, 3.4})
    void testMove_ShouldCheckDistanceValue(double fakeValue) {
        Horse horse = new Horse("Butsefal", 10.0, 1.0);
        double expectedDistance = horse.getDistance() + horse.getSpeed() * fakeValue;
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(fakeValue);
            horse.move();
            double actualDistance = horse.getDistance();
            assertEquals(expectedDistance, actualDistance);
        }
    }

}
