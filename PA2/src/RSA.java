
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.math.BigInteger;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class RSA {

	private static final Random rand  = new Random();

	public static void runRoutine() {
		Scanner sysIn = new Scanner(System.in);
		makeKeys(sysIn);
		int choice = -1;
		boolean runner = false;

		try{
			//Scanner sysIn = new Scanner(System.in);
			String f = "";
			String enOrDe = "";
			

			//choice = Integer.parseInt(in.next());
			while(!runner){
				System.out.println("Do you wish to encrypt(0) or decrypt(1) the requested file?"); 
				choice=sysIn.nextInt();
				if (choice == 0){
					f="key.public";
					enOrDe = "encrypt.txt";
					runner = true;
				}
				else if(choice == 1){
					f="key.private";
					enOrDe = "decrypt.txt";
					runner = true;
				}
				else{
					System.out.println("Error. Choice is not valid, please try again."); 
					runner = false;
				}
			}
			//scanner system
			//Read the given file
			File inFile = new File(f);
			Scanner input = new Scanner(inFile);
			String temporary="";

			temporary=input.nextLine();
			System.out.println(temporary);
			//Get the numbers in the given file
			String[] input2 = temporary.substring(1, temporary.length()-1).split(",");
			BigInteger[] key = new BigInteger[input2.length];
			for (int i = 0; i < key.length; i++)
				key[i] = new BigInteger(input2[i]);

			String lineIn = "";
			String line;

			//Read from stdin the message or the encrypted message
			System.out.println("What is the file name?"); 
			String filename = sysIn.next();


			File inFile2 = new File(filename);
			Scanner filecontent = new Scanner(inFile2);
			try{
				while (filecontent.hasNext()){
					line = filecontent.nextLine();
					lineIn += line + "\n";
				}
				filecontent.close();
			} 
			catch (Exception e) {
				System.out.println("Does not work");
			}


			String[] message = lineIn.trim().split("\n");
			BigInteger[] messages = new BigInteger[message.length];
			for (int i = 0; i < messages.length; i++)
				messages[i] = new BigInteger(message[i]);
			//outputs the plaintext/ciphertext
			String result = encryptOrDecrypt(messages, key);
			PrintWriter encryptType = new PrintWriter(enOrDe);
			encryptType.println(result);
			encryptType.close();
			System.out.println("Output is: ");
			System.out.println(result);
			sysIn.close();
			input.close();
		}
		catch (IOException e) {
			System.out.println("Does not work");
		}


	}

	//Method that encrypts the given numbers
	//and returns an encrypted string
	public static String encryptOrDecrypt(BigInteger[] m, BigInteger[] k){
		BigInteger[] messages = m;
		String code = "";

		for (int i = 0; i < messages.length; i++){
			code += (BigInteger) power(messages[i], k[1], k[0]) + "\n";
		}

		return code;
	}

	//Method that decrypts the given numbers
	//and returns a decrypted string
	public static String decrypt(BigInteger[] m, BigInteger[] k){
		BigInteger[] messages = m;
		String decoded = "";	

		for (int i = 0; i < messages.length; i++){
			decoded += (BigInteger) power(messages[i], k[1], k[0]) + "\n";
		}

		return decoded;
	}

	//Method that returns the power of a number raised to another
	//mod a third number
	public static BigInteger power(BigInteger x, BigInteger y, BigInteger n){
		BigInteger temp;
		if (y.equals(BigInteger.ZERO))
			return BigInteger.ONE;
		BigInteger tempor;
		tempor = y.divide(BigInteger.valueOf(2));
		temp = (power(x, tempor, n).mod(n));
		//System.out.println("the temp is:" + temp);
		if (y.mod(BigInteger.valueOf(2))== BigInteger.ZERO)
			return (temp.multiply(temp)).mod(n);
		else{
			if (y.compareTo(BigInteger.ZERO) > 0)
				return ((((x.multiply(temp)).multiply(temp))).mod(n));
			else return (((temp.multiply(temp)).divide(x))).mod(n);
		}
	}

	public static void makeKeys(Scanner in) {

		BigInteger p, q, n;
		BigInteger separator = new BigInteger("100");
		//Scanner in = new Scanner(System.in);

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
			String publicFile = "key.public";
			String privateFile = "key.private";
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
			//in.close();
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
	 
	public String numToChar(int number) {
		  char[] alphaList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		  String converted = "";
		  while(true) {
		    converted = alphaList[number % 26] + converted;
		    if(number < 26) {
		      break;
		    }
		    number /= 26;
		  }
		  return converted;
		}
	
	public int strToNum(String str) {
		  char[] alphaList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		  Map<Character, Integer> mapp = new HashMap<Character, Integer>();
		  int j = 1;
		  for(char c: alphaList) {
		    mapp.put(c, j++);
		  }
		  int converted = 0;
		  int mul = 1;
		  for(char c: new StringBuffer(str).reverse().toString().toCharArray()) {
		    converted += mapp.get(c) * mul;
		    mul *= alphaList.length;
		  }
		  return converted;
		}


}
