import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;

public class Port {
		int curr;
		int sum = 0;
		boolean ruhig = false;
		boolean stress = false;
		
	public void rec(int c) {
		
		
		int r = c;
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
			curr = data.nextInt();
			if(curr >= sum +20) {
				ruhig = false;
				stress = true;
			}else if(curr <= sum -10) {
				ruhig = true;
				stress = false;
			}
			System.out.println(curr);
			sum = sum + curr;
			if(r > 1) {
				r = r-1;
			}else if(r == 1) {
				break;
			}else if(r == 0) {
				
			}
		}
		if(c != 0) {
			System.out.println("Mittlere Herzrate = " + sum/c);
		}
	}
	
	
	
	public void start() {
		int time = 0;
		rec(time);
	}
	
	public void test(){
		int f = 10;
		rec(f);
	}
}
