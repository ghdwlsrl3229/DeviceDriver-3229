import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DeviceDriverTest {

    @Mock
    FlashMemoryDevice hardware;

    @Spy
    @InjectMocks // hardware mock을 driver에 주입
    DeviceDriver driver;

    @BeforeEach
    void setUp() {
        // delay 함수를 stubbing해서 read의 200ms 딜레이를 제거
        doNothing().when(driver).delay200ms();
    }

    @Test
    void ReadFromHardwareSuccess() {
        when(hardware.read(0xFF))
                .thenReturn((byte)0)
                .thenReturn((byte)0)
                .thenReturn((byte)0)
                .thenReturn((byte)0)
                .thenReturn((byte)0);

        try {
            byte data = driver.read(0xFF);
            assertEquals(0, data);
        }
        catch (ReadFailException e){
            fail();
        }
    }

    @Test
    void ReadFromHardwareFail() {
        when(hardware.read(0xFF))
                .thenReturn((byte)0)
                .thenReturn((byte)0)
                .thenReturn((byte)2)
                .thenReturn((byte)0)
                .thenReturn((byte)0);

        assertThrows(ReadFailException.class, () -> {
            driver.read(0xFF);
        });
    }

    @Test
    void WriteToHardwareSuccess() {
        when(hardware.read(0xFF))
                .thenReturn((byte)0xFF)
                .thenReturn((byte)0xFF)
                .thenReturn((byte)0xFF)
                .thenReturn((byte)0xFF)
                .thenReturn((byte)0xFF);

        try {
            driver.write(0xFF, (byte)0);
            verify(hardware, times(1)).write(0xFF, (byte)0);
        }
        catch (WriteFailException e) {
            fail();
        }
    }

    @Test
    void WriteToHardwareFailByNonEmptyData() {
        when(hardware.read(0xFF))
                .thenReturn((byte)0)
                .thenReturn((byte)0)
                .thenReturn((byte)0)
                .thenReturn((byte)0)
                .thenReturn((byte)0);

        assertThrows(WriteFailException.class, () -> {
            driver.write(0xFF, (byte)0);
        });
    }

    @Test
    void WriteToHardwareFailByReadFail() {
        when(hardware.read(0xFF))
                .thenReturn((byte)0xFF)
                .thenReturn((byte)0xFF)
                .thenReturn((byte)2)
                .thenReturn((byte)0xFF)
                .thenReturn((byte)0xFF);

        assertThrows(WriteFailException.class, () -> {
            driver.write(0xFF, (byte)0);
        });
    }
}