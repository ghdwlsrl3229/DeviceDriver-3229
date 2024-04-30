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
        byte[] readResult = new byte[5];
        for (int i = 0; i < READ_CHECK_COUNT; i++) {
            readResult[i] = hardware.read(address);
            if (i < READ_CHECK_COUNT - 1) {
                delay200ms();
            }
        }
        for (int i = 0; i < READ_CHECK_COUNT - 1; i++) {
            if (readResult[i] != readResult[i + 1]) {
                throw new ReadFailException();
            }
        }
        return readResult[0];
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