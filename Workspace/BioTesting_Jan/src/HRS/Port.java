package HRS;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.ini4j.Wini;

import application.Initialization;
import com.fazecast.jSerialComm.SerialPort;

public class Port {

	static boolean portOn = false;
	int curr;
	int sum = 0, erg;
	String puls;
	int rp;
	boolean ruhig = false;
	boolean stress = false;
	int standardPort = 0;
	SerialPort port2;
	Initialization init = new Initialization();

	public void rec(int c) {
		portOn = true;
		int r = c;
		if (c != 0) {
			SerialPort ports[] = SerialPort.getCommPorts();
			System.out.println("Select a port");
			int i = 1;
			for (SerialPort port : ports) {
				System.out.println(i++ + ". " + port.getSystemPortName());
			}

			Scanner s = new Scanner(System.in);
			int chosenPort = s.nextInt();
			standardPort = chosenPort - 1;
			s.close();
			SerialPort port;
			port = ports[standardPort];
			if (port.openPort()) {
				System.out.println("Sucessfully opened Port");
			} else {
				System.out.println("Unable to open port");
			}

			port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
			port2 = port;
		}
		
		Scanner data = new Scanner(port2.getInputStream());
		while (data.hasNextLine()) {
			curr = data.nextInt();
			if (curr >= sum + 20) {
				ruhig = false;
				stress = true;
			} else if (curr <= sum - 10) {
				ruhig = true;
				stress = false;
			}
			//System.out.println(curr);
			sum = sum + curr;
			if (r > 1) {
				r = r - 1;
			} else if (r == 1) {
				break;
			} /*
				 * else if (r == 0) {
				 * 
				 * }
				 */
		}
		if (c != 0) {
			System.out.println("Mittlere Herzrate = " + sum / c);
			erg = sum / c;
		}
	}

	public void start() {
		int time = 0;
		standardPort = 0;
		rec(time);
	}

	public void test() {
		int f = 10;
		rec(f);
	}

	public int getHeartRate() {
		if (portOn == true) {
			return curr;
		} else {
			puls = init.getRP();
			rp = Integer.parseInt(puls);
			return rp;
		}
	}

	public int getResult() {
		return erg;
	}

}
