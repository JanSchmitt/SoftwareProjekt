import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class Port {

	public static void main(String[] args) {
		SerialPort ports[] = SerialPort.getCommPorts();
		System.out.println("Select a port");
		int i = 1;
		for(SerialPort port : ports) {
			System.out.println(i++ + ". " + port.getSystemPortName());
		}
		Scanner s = new Scanner(System.in);
		int chosenPort = s.nextInt();
		
		SerialPort port = ports[chosenPort -1];
		if(port.openPort()){
			System.out.println("Sucessfully opened Port");
		}else {
			System.out.println("Unable to open port");
		}
		port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		s.close();
		Scanner data = new Scanner(port.getInputStream());
		while(data.hasNextLine()) {
			System.out.println(data.nextLine());
		}
	}

}
