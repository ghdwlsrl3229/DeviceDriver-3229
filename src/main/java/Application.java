import java.util.ArrayList;

public class Application {
    private DeviceDriver deviceDriver;

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

    public void WriteAll(byte value) {

    }
}
