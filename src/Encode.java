
import java.io.*;
import java.util.Scanner;
import java.math.BigInteger;

public class Encode {

	public static void main(String[] args) {

		int choice = -1;
		boolean runner = false;

		try{
			Scanner in = new Scanner(System.in);
			String f = "";
			String enOrDe = "";

			
			//choice = Integer.parseInt(in.next());
			while(!runner){
				System.out.println("Do you wish to encrypt(0) or decrypt(1) the requested file?"); 
				choice=in.nextInt();
				if (choice == 0){
					f="bob.public";
					enOrDe = "encrypt.txt";
					runner = true;
				}
				else if(choice == 1){
					f="bob.private";
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
			String filename = in.next();


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
			in.close();
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

}
