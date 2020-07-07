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
	
	// Variablen
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
	

	// functoin selectPort() is used to define the USB to which the sensor is plugged into
	// should be called at least once to make sure the right port is selected
	// after that the selected port will be used throughout the whole application usage
	// otherwise default value will be used
	public int selectPort() {
		SerialPort ports[] = SerialPort.getCommPorts();
		System.out.println("Select a Port");
		int i = 0;
		
		for (SerialPort port : ports) {
			i = i + 1;
			System.out.println(i + "." + port.getSystemPortName());
		}
		Scanner s = new Scanner(System.in);
		chosenPort = s.nextInt();
		s.close();
		return chosenPort;
	}

	// functon usePort() sets up  and opens up the communication to the Port previously selected
	// returns opened Port so it can be used to communicate with sensor
	public SerialPort usePort(int p) {
		SerialPort port;
		System.out.println(p);
		SerialPort ports[] = SerialPort.getCommPorts();
		port = ports[p - 1];
		port.openPort();
		if (port.isOpen()) {
			System.out.println("Sucessfully opened Port");
		} else {
			System.out.println("Unable to open port");
		}
		return port;
	}

	// returns Heart rate; p has default value or selected value after calling selectPort()
	// Serial port communication requires jSerialComm jar
	public int getHeartR(SerialPort sp) {
		data = new Scanner(sp.getInputStream());
		sp.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 0, 0);
		if(data.hasNextInt()) {
			curr = data.nextInt();
		}
		data.close();
		return curr;
	}

	// closes Serial Port communication
	public void close(SerialPort sp) {
		sp.closePort();
	}

	// function returns port currently chosen 
	public int getChosenPort() {
		return chosenPort;
	}

	// function returns a default value for Heart Rate in case no sensor is used
	// is needed so application runs in either case
	public int getHeartRate() {
		puls = init.getRP();
		rp = Integer.parseInt(puls);
		return rp;
	}
}
