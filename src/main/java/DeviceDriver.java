import static java.lang.Thread.sleep;

/**
 * This class is used by the operating system to interact with the hardware 'FlashMemoryDevice'.
 */

public class DeviceDriver {
    public static final byte EMPTY_DATA = (byte) 0xFF;
    public static final int READ_CHECK_COUNT = 5;
    final private FlashMemoryDevice hardware;

    public DeviceDriver(FlashMemoryDevice hardware) {
        this.hardware = hardware;
    }

    public byte read(long address) throws ReadFailException{
        byte firstReadData = hardware.read(address);
        for (int i = 1; i < READ_CHECK_COUNT; i++) {
            delay200ms();
            if (firstReadData != hardware.read(address)) {
                throw new ReadFailException();
            }
        }
        return firstReadData;
    }

    public void delay200ms() {
        try {
            sleep(200);
        } catch (InterruptedException e) {
        }
    }

    public void write(long address, byte data) throws WriteFailException{
        try {
            if (read(address) == EMPTY_DATA) {
                hardware.write(address, data);
            }
            else {
                throw new WriteFailException();
            }
        }
        catch (ReadFailException e) {
            throw new WriteFailException();
        }
    }
}