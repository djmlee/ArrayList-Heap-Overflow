import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

/*
	//Dinsdale Lee CISC3325

	Theoretical memory allocation
	10000 innerArray objs
	10000 * 100,000,000 = 1,000,000,000,000 elms
	An Integer obj in Java occupies 16 bytes
	16,000,000,000,000 bytes = 16,000,000 megabytes = 16,000 gigabytes
	
	386,482,500 bytes were used before the exception was generated.
	Approximately 
	Heap overflows is an exploitable vulnerability as it can allow for execution of arbitrary code
*/

public class LargeArrays {
	public static int SIZE = 10000; //Global variable for sizing
	public static long count = 0;

	static public class subArray {
		//	subArray = ArrayList of SIZE random Integers upon Object initialization
		// 1 subArrayObject = 10,000 Integers
		
		public ArrayList <Integer> arrInt;
		
		public subArray(){
			arrInt = new ArrayList<Integer>();
			Random rand = new Random();
			
			for(int i = 0; i < SIZE; i++)
				arrInt.add(rand.nextInt());
			
			try{
				//Determine how much bytes one arrInt object uses
				//Determine the total amount of bytes currently being used 
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    ObjectOutputStream out = new ObjectOutputStream(baos);
			    out.writeObject(arrInt);
			    out.close();
			    
			    System.out.println(arrInt.getClass().getSimpleName() +
			        " used " + baos.toByteArray().length + " bytes");
			    count += baos.toByteArray().length;
			    System.out.println("Totalling: " + count + " bytes" );
			    
			} catch (IOException e){
				System.out.println("IO Exception in byte display");
			}//catch
			
		}//constructor
	}//end subArray
	
	static class innerArray {
		//	innerArray = ArrayList of SIZE subArrays
		//	Array of SIZE, inside of each has SIZE random Ints
		// 10,000 * 10,000 elements per innerArray Object
		
		public ArrayList <subArray> foo;
		public subArray bar;
		
		public innerArray(){
			foo = new ArrayList<subArray>();
			for(int i = 0; i < SIZE; i++){
				foo.add(bar = new subArray());
			}
		}
	}//innerArray
	

	public static void main(String[] args){
		
		ArrayList <innerArray> bigList = new ArrayList<innerArray>();
		innerArray baz;
		
		for(int i = 0; i < SIZE; i++){
			bigList.add(baz = new innerArray());
		}

		for(int i = 0; i < SIZE; i++){
				System.out.println(i + ":" + bigList.get(i).foo.get(i).arrInt.get(i));
				System.out.println("Still Alive");
		}
		
	}
}
