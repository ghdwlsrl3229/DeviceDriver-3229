import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    Application app;

    @Mock
    DeviceDriver deviceDriver;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        app = new Application(deviceDriver);
    }

    @Test
    void ReadAndPrintSuccess() {
        try {
            when(deviceDriver.read(0)).thenReturn((byte)0);
            when(deviceDriver.read(1)).thenReturn((byte)1);
            when(deviceDriver.read(2)).thenReturn((byte)2);
            when(deviceDriver.read(3)).thenReturn((byte)3);
            app.ReadAndPrint(0, 3);
            assertEquals("0 1 2 3 ", outContent.toString());
        } catch(ReadFailException e) {
            fail();
        }
    }

    @Test
    void ReadAndPrintFail() {
        try {
            when(deviceDriver.read(0)).thenReturn((byte)0);
            when(deviceDriver.read(1)).thenReturn((byte)1);
            when(deviceDriver.read(2)).thenThrow(ReadFailException.class);
            app.ReadAndPrint(0, 3);
            fail();
        } catch(ReadFailException e) {

        }
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }
}