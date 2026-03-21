import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import bai03.*;

public class UserProcessorTest {
    private UserProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new UserProcessor();
    }

    @Test
    void shouldReturnSameEmail_whenEmailIsValid() {
        String result = processor.processEmail("user@gmail.com");
        assertEquals("user@gmail.com", result);
    }

    @Test
    void shouldThrowException_whenEmailMissingAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail("usergmail.com");
        });
    }

    @Test
    void shouldThrowException_whenEmailMissingDomain() {
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail("user@");
        });
    }

    @Test
    void shouldConvertEmailToLowercase() {
        String result = processor.processEmail("Example@Gmail.com");
        assertEquals("example@gmail.com", result);
    }
}
