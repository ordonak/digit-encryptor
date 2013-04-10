
import java.io.*;
import java.util.Scanner;
import java.math.BigInteger;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Vector;
import java.lang.String;
import java.util.HashMap;

//WORKING ON: FINISH UP THE DO-WHILE LOOP
//PROBLEMS: THERE'S A PROBLEM WITH THE TRY-CATCH BLOCK IF
//THERE'S A INPUT MISMATCH EXCEPTION. FIGURE THIS OUT!


public class Driver {

	public static void main(String[] args) {
		//hashmap for storing user-password combinations
		HashMap<String, String> authMap = new HashMap<String, String>();
		authMap.put("MySuperPassword", "super");
		RSA test = new RSA();

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwyyz0123456789;:,.";
		String tempData;
		int fChoice = 0;
		int sChoice = 0;
		String fileName = "users.txt";
		String command;
		String curUser = "";
		LoginResult userInfo = new LoginResult();
		int numUsers = 0;
		int tempNum;
		String tempPass = "";
		boolean done = false;
		Scanner sysIn = new Scanner(System.in);
		File inFile = new File(fileName);
		Random rand  = new Random();
		Vector<String> userList = new Vector<String>();
		Vector<String> pwList = new Vector<String>();

		BigInteger[] k = new BigInteger[2];

		String ttest = "MySuperPassword";
		System.out.println(ttest);
		BigInteger bigInt = new BigInteger(ttest.getBytes());
		System.out.println(bigInt);
		//String test = 
		//String textBack = new String(bigInt.toByteArray());
		//System.out.println(textBack);

		//userList being created from 
		try {
			Scanner fileScan = new Scanner(inFile);
			while(fileScan.hasNext())
			{
				tempData = fileScan.nextLine();
				userList.add(tempData);
			}
			fileScan.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("User Files could not be found. Super-Access allowed only.");

		}




		do{

			if(userInfo.logIn && !userInfo.sup)
			{


			}
			else if(userInfo.logIn && userInfo.sup)
			{

				System.out.println("1 - Add User");
				

			}
			else
			{
				System.out.println("1 - Login");
				System.out.println("2 - Exit");
				System.out.print("Enter your choice and press return: ");
				try{
					fChoice=sysIn.nextInt();
				}
				catch(InputMismatchException e){
					System.out.println("Please enter a valid number. Try again.\n");
				}
				switch(fChoice)
				{
				case 1: 
					if(userInfo.name.equals("super")){
						userInfo.logIn = true;
						userInfo.sup = true;
					}
					else{
						userInfo = login(userList, sysIn);
						System.out.println(userInfo.name);
					}
					break;
				case 2: 
					done = true;
					System.out.println("Now exiting...");
					break;
				default: 
					System.out.println("Please enter a valid number. Try again.\n");

				}
			}
		}
		while(!done);

		sysIn.close();
		try {
			outputUsers(userList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Output problem(user.txt)! Now exiting!");
		}

	}

	public static void outputUsers(Vector<String> userList) throws IOException
	{
		File userFile = new File("users.txt");

		if(!userFile.exists()){
			userFile.createNewFile();

		}

		else{
			userFile.delete();
			userFile = new File("users.txt");
			userFile.createNewFile();
		}

		FileWriter fstream = new FileWriter("users.txt");
		PrintWriter pw = new PrintWriter(fstream);



		for(int i = 0; i < userList.size(); i++)
		{
			pw.println(userList.elementAt(i));
			System.out.println(userList.elementAt(i));
		}
		pw.close();
	}

	public static String passGen(String alpha){
		String tempPass = "";
		int tempNum;
		int passSize = 8;
		Random rand = new Random();

		for(int j = 0; j < passSize; j++)
		{

			tempNum = rand.nextInt(alpha.length());
			tempPass += alpha.charAt(tempNum);
		}
		System.out.println(tempPass);
		return tempPass;
	}

	public static LoginResult login(Vector<String> loadU, Scanner in)
	{
		String loginAttempt;
		String temp;
		char userInput = 'y';
		boolean found = false;
		boolean again = true;
		LoginResult userInfo = new LoginResult();
		do
		{
			System.out.print("Please enter your credentials(login): ");

			loginAttempt = in.next();

			//std::transform(loginAttempt.begin(), loginAttempt.end(), loginAttempt.begin(), ::tolower);

			if(loginAttempt.equals("super")){
				System.out.println("Welcome super. All restrictions removed.");
				userInfo.sup = true;
				userInfo.name = loginAttempt;
				userInfo.logIn = true;
				return userInfo;
			}
			for(int i = 0; i < loadU.size(); i++)
			{
				if(loadU.elementAt(i) == loginAttempt)
				{
					userInfo.name = loginAttempt;
					userInfo.logIn = true;
					return userInfo;
				}
			}
			System.out.print("Username not recognized. Try again? ");
			temp = in.next();
			userInput = temp.charAt(0);

			if(userInput == 'n' || userInput == 'N')
			{
				System.out.println("Returning to main menu.");
				return userInfo;
			}
			else if (userInput == 'y' || userInput == 'Y'){
				System.out.println("Retrying login.");
			}
			else{
				System.out.println("Input not recognized. Resetting login.");
				userInput = 'Y';
			}
		}
		while(userInput == 'y' || userInput == 'Y');

		return userInfo;
	}

}
//
