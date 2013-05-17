import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.core.IChimpDevice;

public class MonkeyTest {
	public static void main(String[] args) {
		// sdk/platform-tools has to be in PATH env variable in order to find adb
		IChimpDevice device = new AdbBackend().waitForConnection();
		
		// Print Device Name
		System.out.println(device.getProperty("build.model"));
		
		// Take a snapshot and save to out.png
		device.takeSnapshot().writeToFile("out.png", null);
		
		device.dispose();
	}
}
