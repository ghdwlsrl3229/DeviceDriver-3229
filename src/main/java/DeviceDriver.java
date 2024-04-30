import static java.lang.Thread.sleep;

/**
 * This class is used by the operating system to interact with the hardware 'FlashMemoryDevice'.
 */
class ReadFailException extends Exception {}
class WriteFailException extends Exception {}

public class DeviceDriver {
    private FlashMemoryDevice hardware;

    public DeviceDriver(FlashMemoryDevice hardware) {
        this.hardware = hardware;
    }

    public byte read(long address) throws ReadFailException{
        byte[] readResult = new byte[5];
        for (int i = 0; i < 5; i++) {
            readResult[i] = hardware.read(address);
            if (i < 4) {
                delay200ms();
            }
        }
        for (int i = 0; i < 4; i++) {
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
            if (read(address) == (byte)0xFF) {
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