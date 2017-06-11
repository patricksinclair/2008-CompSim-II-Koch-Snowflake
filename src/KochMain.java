import java.io.IOException;


public class KochMain {
	public static void main(String args[])throws IOException{
		
		KochFlake kf = KochFlake.askValues();
		kf.printValues();
		kf.saveValues();
	}
}
