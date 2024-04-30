import java.util.ArrayList;

public class Application {
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
        for (int addr = 0; addr <= 4; addr++) {
            deviceDriver.write(addr, value);
        }
    }
}
