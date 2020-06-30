import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;

public class Port {
		int curr, chosenPort;
		int sum = 0;
		SerialPort port;
		
	public void rec(SerialPort p) {
		Scanner data = new Scanner(p.getInputStream());
		curr = data.nextInt();
		System.out.println(curr);
	}
	
	
	public void selectPort(int f) {
		SerialPort ports[] = SerialPort.getCommPorts();
		System.out.println("Select a port");
		int i = 1;
		for(SerialPort port : ports) {
			System.out.println(i++ + ". " + port.getSystemPortName());
		}
		Scanner s = new Scanner(System.in);
		if(f == 0) {
			chosenPort = s.nextInt();
			port = ports[chosenPort -1];
		}else {
			port = ports[f-1];
		}
		
		if(port.openPort()){
			System.out.println("Sucessfully opened Port");
		}else {
			System.out.println("Unable to open port");
		}
		port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
		s.close();
	}
	
	public int getchosenPort() {
		return chosenPort;
	}
	
	public void start(int f) {
		rec(port);
	}
	
}
