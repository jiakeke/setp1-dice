import org.mockito.MockedConstruction;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DiceTest {

    Dice dice = new Dice();

    @org.junit.jupiter.api.Test
    void roll() {
        dice.roll();
        assertTrue(dice.getDie1() >= 1 && dice.getDie1() <= 6);
        assertTrue(dice.getDie2() >= 1 && dice.getDie2() <= 6);
    }

    @org.junit.jupiter.api.Test
    void getDie1() {
        assertEquals(0, dice.getDie1());
        dice.roll();
        assertTrue(dice.getDie1() >= 1 && dice.getDie1() <= 6);
    }

    @org.junit.jupiter.api.Test
    void getDie2() {
        assertEquals(-1, dice.getDie2());
        dice.roll();
        assertTrue(dice.getDie2() >= 1 && dice.getDie2() <= 6);
    }

    @org.junit.jupiter.api.Test
    void isDouble() {
        Dice mockDice= mock(Dice.class);
        when(mockDice.getDie1()).thenReturn(3);
        when(mockDice.getDie2()).thenReturn(3);
        when(mockDice.isDouble()).thenCallRealMethod();
        assertTrue(mockDice.isDouble());

        when(mockDice.getDie1()).thenReturn(3);
        when(mockDice.getDie2()).thenReturn(6);
        when(mockDice.isDouble()).thenCallRealMethod();
        assertFalse(mockDice.isDouble());
    }

    @org.junit.jupiter.api.Test
    void setCounter() {
        assertEquals(0, dice.getCounter());
        dice.setCounter();
        assertEquals(1, dice.getCounter());
    }

    @org.junit.jupiter.api.Test
    void testPlayDice() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        // Run the main method
        // Use a spy to call the real main method but mock the Dice instance
        try (MockedConstruction<Dice> mocked = mockConstruction(Dice.class, (mock, context) -> {
            when(mock.getDie1()).thenReturn(2, 2, 4, 3);
            when(mock.getDie2()).thenReturn(5, 5, 5, 3);
            when(mock.isDouble()).thenReturn(false, false, false, true);
            when(mock.getCounter()).thenCallRealMethod();
            doCallRealMethod().when(mock).setCounter();
        })) {
            Dice.main(new String[]{});
        }

        // Restore the original System.out
        System.setOut(originalOut);

        // Verify the output
        String output = outContent.toString();
        System.out.println(output);
        assertTrue(output.contains("Rolling the dice..."));
        assertTrue(output.contains("Try again."));
        assertTrue(output.contains("You rolled a double!"));
        assertTrue(output.contains("After 3 try both reach the same value!"));
    }
}