package CRCSimulation;

/*
 * EECE 4690 Project
 * This program simulates the CRC network protocol based on a user input.
 * Tim Tscheppe, David Titzer
 */

import java.util.Scanner;
import java.util.Random;

public class CRCSimulationDemo {

	public static void main(String[] args) {
		
		// Prompt user for 8 "Data" Bits
		Scanner sc = new Scanner(System.in);
		String data;
		System.out.println("Enter 8-bit data section: ");
		data = sc.next();
		
		// Declare our generator "G" which will be 17 Bits long
		final String GENERATOR = "01010101010101010";
		
		// Generate R to create our full message
		String R = GenerateR(data);
		
		// Create the full message "DR" to be sent
		String fullmessage = data.concat(R);
		
		// Print info to console
		System.out.println("Generator (known to sender/receiver) is: " + GENERATOR);
		System.out.println("Full message transmitted from sender is: " + fullmessage);
		
		// Generate an error by randomly scrambling the full message
		String receivedMessage = Error(fullmessage);
		
		// Print info to console
		System.out.println("After transmission, received message is: " + receivedMessage);
		
		// Determine if there is an error detected
		boolean detection = Remainder(receivedMessage);
		
		// Print if the error was detected via CRC
		if (detection == true) {
			System.out.println("Error was detected");
		}
		else {
			System.out.println("Error was NOT detected");
		}
		
		// Clean up resources
		sc.close();
		
	}
	
	public static String Error(String s) {

		// Initialize variables
		Random rnd = new Random();
		char zero = '0';
		char one = '1';
		int numErrors;
		String output;

		// Declare the amount of times we will swap a value (Can be tweaked)
		numErrors = rnd.nextInt(24);
		System.out.println("There were a total of " + numErrors + " Errors");

		for (int i = 0; i < numErrors; i++) {
			
			// Determine what index is going to be changed
			int changedAddress = rnd.nextInt(24);
			
			// Swaps value of zero and one by converting to a char array
			if (s.charAt(changedAddress) == '0') {
				char[] chars = s.toCharArray();
				chars[changedAddress] = one;
				s = String.valueOf(chars);
			}
			else {
				char[] chars = s.toCharArray();
				chars[changedAddress] = zero;
				s = String.valueOf(chars);
			}
		}
		
		// Return erroneous String
		output = s;
		return output;

	}
	
	public static String GenerateR(String dataSection) {
		
		// Left shifts D by R bits
		String zeros = "0000000000000000";
		String leftshift = dataSection.concat(zeros);
		
		// Copy G value to method
		final String GENERATOR = "01010101010101010";
		
		// Convert binary strings to decimal equivalent
		int decimalData = Integer.parseInt(leftshift,2);
		int decimalGenerator = Integer.parseInt(GENERATOR,2);
		
		// Calculate remainder in decimal
		int decimalRemainder = decimalData % decimalGenerator;
		
		// Return the remainder as a binary string
		String remainder = Integer.toBinaryString(decimalRemainder);
		System.out.println("The remainder generated is: " + remainder);
		return remainder;
		
	}
	
	public static boolean Remainder(String message) {
		
		// Initialize variables
		boolean bool;
		final String GENERATOR = "01010101010101010";
		
		// Convert binary strings to decimal equivalent
		int decimalMessage = Integer.parseInt(message,2);
		int decimalGenerator = Integer.parseInt(GENERATOR,2);
		
		// Calculate received DR % G
		int decimalRemainder = decimalMessage % decimalGenerator;
		System.out.println("Remainder calculated upon receiving data is " + Integer.toBinaryString(decimalRemainder));
		
		// If remainder is not zero, return "true" aka error detected
		if (decimalRemainder != 0) {
			bool = true;
		}
		else {
			bool = false;
		}
		
		return bool;
		
	}

}
