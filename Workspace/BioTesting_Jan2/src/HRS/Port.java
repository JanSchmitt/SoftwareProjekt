package HRS;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.ini4j.Wini;

import application.Initialization;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import com.fazecast.jSerialComm.SerialPort;

public class Port {

	static boolean portOn = false;
	public int curr;
	int sum = 0, erg;
	String puls;
	int rp, time = 0;
	int hr;
	boolean ruhig = false;
	boolean stress = false;
	int standardPort = 0;
	SerialPort port2;
	Initialization init = new Initialization();

	int chosenPort;
	Scanner data;

	public int getHR(int p) {
		SerialPort port;
		SerialPort ports[] = SerialPort.getCommPorts();
		port = ports[p - 1];
		port.openPort();
		data = new Scanner(port.getInputStream());
		port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		if (data.hasNextLine() == true) {
			curr = data.nextInt();
			System.out.println(curr);
		}
		return curr;
	}

	public int selectPort() {
		SerialPort ports[] = SerialPort.getCommPorts();
		System.out.println("Select a Port");
		int i = 0;
		/*
		 * for(i = 0; i<ports.length; i++) { System.out.println(i++ + "." +
		 * sp.getSystemPortName()); }
		 */
		for (SerialPort port : ports) {
			i = i + 1;
			System.out.println(i + "." + port.getSystemPortName());
		}
		Scanner s = new Scanner(System.in);
		chosenPort = s.nextInt();
		s.close();
		return chosenPort;
		/*
		 * if(f==0) { Scanner s = new Scanner(System.in); chosenPort = s.nextInt(); sp =
		 * ports[chosenPort-1]; s.close(); } else { sp = ports[f-1]; }
		 * 
		 * if(sp.openPort()) { System.out.println("Sucessfully opened Port"); } else {
		 * System.out.println("Unable to open port"); }
		 * sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		 */
	}
	
	public SerialPort usePort(int p) {
		SerialPort port;
		System.out.println(p);
		SerialPort ports[] = SerialPort.getCommPorts();
		port = ports[p - 1];
		port.openPort();
		if(port.isOpen()) { 
			System.out.println("Sucessfully opened Port"); 
		} else {
			System.out.println("Unable to open port"); }
		return port;
	}
	
	public int getHeartR(SerialPort sp) {
		Scanner data = new Scanner(sp.getInputStream());
		sp.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		if (data.hasNextLine() == true) {
			curr = data.nextInt();
			System.out.println(curr);
		}
		data.close();
		return curr;
	}
	
	public void close(SerialPort sp) {
		sp.closePort();
	}

	public int getChosenPort() {
		return chosenPort;
	}
	
	public int getHeartRate() {
		puls = init.getRP();
		rp = Integer.parseInt(puls);
		return rp;
	}
	
	/*
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
		time = 0;

		while (data.hasNextLine()) {
			curr = data.nextInt();
			if (curr >= sum + 20) {
				ruhig = false;
				stress = true;
			} else if (curr <= sum - 10) {
				ruhig = true;
				stress = false;
			}
			// System.out.println(curr);
			sum = sum + curr;
			if (r > 1) {
				r = r - 1;
			} else if (r == 1) {
				break;
			} else if (r == 0) {

			}

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

	

	public int getResult() {
		return erg;
	}
*/
}
