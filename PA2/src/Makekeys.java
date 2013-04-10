import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Makekeys {

	private static final Random rand  = new Random();

	public static void main(String[] args) {

		BigInteger p, q, n;
		BigInteger separator = new BigInteger("100");
		Scanner in = new Scanner(System.in);

		System.out.println("What bit-size do you want your key to be?"); 
		String fileName = in.next();
		System.out.println("Bit-size: " + fileName);
		//in.close();
		int bitSize = Integer.parseInt(fileName);
		BigInteger largePrime = new BigInteger(bitSize, rand);

		p = generate_prime(largePrime);
		largePrime = largePrime.add(separator);
		q = generate_prime(largePrime);
		n = p;
		n.multiply(q);

		BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		boolean relativelyPrime = false;
		BigInteger e = new BigInteger("0");
		int randGen = 0;

		//Solve for a relative prime to the phi function of the number n
		while (!relativelyPrime){
			randGen = (int)(Math.random() * ( 20 ) + 1);
			e = (BigInteger.valueOf(randGen));
			relativelyPrime = (gcd(phi, e).equals(BigInteger.ONE)) && !(e.equals(BigInteger.ONE)) ? true : false;
		}

		//Calculate the inverse of this relative prime
		BigInteger[] extended = ExtendedEuclid(phi, e);
		BigInteger d = extended[2];

		//Make sure that our number is positive to make it easier
		if (d.compareTo(BigInteger.ZERO) < 0)
			d = d.add(phi);

		try{
			String publicFile = "bob.public";
			String privateFile = "bob.private";
			String publicInfo = "("+ n + "," + e + ")";
			String privateInfo = "(" + n + "," + d + ")";
			PrintWriter publicFile1 = new PrintWriter(publicFile);
			PrintWriter privateFile1 = new PrintWriter(privateFile);

			//Write out the public and private info to two
			//different files
			publicFile1.println(publicInfo);
			publicFile1.close();
			privateFile1.println(privateInfo);
			privateFile1.close();
			in.close();
			System.out.println("Keys Created.");
		}catch(IOException h){
			System.out.println("Please try again with correct input file name");
		}
	}

	//Method that returns the greatest common divisor of two numbers
	public static BigInteger gcd(BigInteger a, BigInteger b) {

		if(b.equals(BigInteger.ZERO))
			return a;

		else {

			return gcd(b, a.mod(b));
		}
	}



	//Method that returns the inverse of a number mod
	//using the Extended Euclidean Algorithm
	public static BigInteger[] ExtendedEuclid(BigInteger a, BigInteger b){	     
		BigInteger[] ans = new BigInteger[3];
		BigInteger q;

		if (b.equals(BigInteger.ZERO))  {
			ans[0] = a;
			ans[1] = BigInteger.ONE;
			ans[2] = BigInteger.ZERO;
		}
		else {
			q = a.divide(b);
			ans = ExtendedEuclid (b,  a.mod(b));
			BigInteger temp = ans[1].subtract(ans[2].multiply(q));
			ans[1] = ans[2];
			ans[2] = temp;
		}

		return ans;
	}

	//Method that performs iterations of Miller-rabin
	private static boolean rabinIter(BigInteger temp, BigInteger checkNum) {
		BigInteger numMinus = checkNum.subtract(BigInteger.ONE);
		BigInteger tempTwo = numMinus;

		int num = tempTwo.getLowestSetBit();
		tempTwo = tempTwo.shiftRight(num);

		BigInteger power = temp.modPow(tempTwo, checkNum);
		if (power.equals(BigInteger.ONE)) 
			return true;

		for (int i = 0; i < num-1; i++) {
			if (power.equals(numMinus))
				return true;
			power = power.multiply(power).mod(checkNum);
		}
		if (power.equals(numMinus)) 
			return true;

		return false;
	}

	//Method that tests whether or not a certain BigInteger is prime.
	public static boolean is_prime(BigInteger checkNum) {

		for (int n = 0; n < 20; n++) {
			BigInteger temp;

			do {
				temp = new BigInteger(checkNum.bitLength(), rand);
			} while (temp.equals(BigInteger.ZERO));

			if (!rabinIter(temp, checkNum)) {
				return false;
			}
		}
		return true;
	}

	//
	public static BigInteger generate_prime(BigInteger num){
		boolean test = false;

		while(!test)
		{
			num = num.add(BigInteger.ONE);

			if(is_prime(num))
			{
				test = true;
			}
		}

		return num;

	}

}
