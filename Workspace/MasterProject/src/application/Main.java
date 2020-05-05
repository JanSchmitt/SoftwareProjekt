public class Main{
	static OpSettings opsettings;

	public void start(){
		try {
			opsettings.launch();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
