import java.util.ArrayList;

public class Application {
    public static final int WRITE_START_ADDR = 0;
    public static final int WRITE_END_ADDR = 4;
    final private DeviceDriver deviceDriver;

    public Application(DeviceDriver deviceDriver) {
        this.deviceDriver = deviceDriver;
    }

    public void ReadAndPrint(long startAddr, long endAddr) throws ReadFailException {
        ArrayList<Byte> result = new ArrayList<>();
        for (long addr = startAddr; addr <= endAddr; addr++) {
            result.add(deviceDriver.read(addr));
        }
        for (byte data : result) {
            System.out.print(data + " ");
        }
    }

    public void WriteAll(byte value) throws WriteFailException{
        for (int addr = WRITE_START_ADDR; addr <= WRITE_END_ADDR; addr++) {
            deviceDriver.write(addr, value);
        }
    }
}
