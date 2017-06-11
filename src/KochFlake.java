import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class KochFlake {

	private double l;
	private int n;

	public KochFlake(double l, int n){
		this.l = l;
		this.n = n;
	}


	public double getL(){
		return l;
	}
	public void setL(double l){
		this.l = l;
	}
	public int getN(){
		return n;
	}
	public void setN(int n){
		this.n = n;
	}
	

	public double area0(){
		return getL()*getL()*(Math.sqrt(3.0)/4.0);
	}

	public double perim0(){
		return 3.0*getL();
	}


	public double areaN(){

		double runningtotal = area0();

		for(int i = 1; i <= getN(); i++){
			runningtotal = runningtotal + area0()*(Math.pow(4.0, i-1)/(3.0*Math.pow(9.0, i-1)));
		}
		return runningtotal;
	}

	public double perimN(){
		return perim0()*(Math.pow(4.0, getN())/Math.pow(3.0, getN()));
	}

	
	public double areaInf(){
		return area0()*(8.0/5.0);
	}


	
	
	public void printValues(){
		String str1 = String.format("The original area is %.2f and the original perimeter is %.2f", area0(), perim0());
		String str2 = String.format("After %d iterations,  the area is %.2f and the "
				+ "perimeter is %.2f", getN(), areaN(), perimN());

		System.out.println(str1);
		System.out.println(str2);
		int nAcc = KochFlake.nAccurate(new KochFlake(getL(), getN()));
		System.out.println("The number of iterations required to reach this accuracy is: "+nAcc);
	}



	public void saveValues()throws IOException{
		String filename = KochFlake.askFilename();
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(filename)));

		int nItr = getN();
		int interval = getN()/10;
		String str = null;

		for(int i = 0; i <= nItr; i++){


			if(i%interval == 0 || i == nItr){
				setN(i);
				str = String.format("At %d iterations, the area is %.4f "
						+ "and the perimeter is %.2f.\n", i, areaN(), perimN());
				out.write(str);
			}
		}
		out.close();
		System.out.println("The results have been saved to "+filename);		
	}



	public static int nAccurate(KochFlake kf){
		KochFlake kf2 = new KochFlake(kf.getL(), kf.getN());
		double accuracy = askAccuracy();
		double tolerance = kf.areaInf()*(accuracy/100.0);
		int nItr = 0;
		
		whileloop:
		while(true){
			
			kf2.setN(nItr);
			
			if(kf2.areaN() >= kf2.areaInf()-tolerance && kf2.areaN() <= kf2.areaInf()+tolerance) break whileloop;
			
			nItr++;
		}
		//kf.setNAcc(nItr);
		return nItr;
	}

	public static KochFlake askValues(){
		Scanner keyboard = new Scanner(System.in);
		double l = 0;
		int n = 0;

		while(true){
			try{
				System.out.println("Enter the length of the sides: ");
				l = keyboard.nextDouble();
				if(l >= 1 && l <= 10) break;
				System.out.println("The length must be between 1 & 10.");
			}catch(InputMismatchException e){
				System.out.println("Please enter a number.");
				keyboard.nextLine();
			}
		}

		while(true){
			try{
				System.out.println("Enter the number of iterations: ");
				n = keyboard.nextInt();
				if(n >= 30) break;
				System.out.println("Value must be at least 30.");
			}catch(InputMismatchException e){
				System.out.println("Please enter an integer.");
				keyboard.nextLine();
			}
		}

		return new KochFlake(l, n);
	}


	
	public static String askFilename(){
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Please enter the name of the file to save results to.");
		String filename = keyboard.next().trim()+".txt";
		return filename;
	}
	
	
	
	public static double askAccuracy(){
		Scanner keyboard = new Scanner(System.in);
		double accuracy = 0;
		
		while(true){
			try{
				System.out.println("Enter a value for the accuracy% of the infinite area.");
				accuracy = keyboard.nextDouble();
				if(accuracy <= 0.1 && accuracy >= 0.001) break;
				System.out.println("The accuray must be between 0.1% & 0.001%");
			}catch(InputMismatchException e){
				System.out.println("Please enter a number.");
				keyboard.nextLine();
			}
		}
		return accuracy;
	}

	
}