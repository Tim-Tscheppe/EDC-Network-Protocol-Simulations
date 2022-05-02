/*
 * EECE 4690 Project
 * This program demonstrates how the checksum protocol works on a high level
 * Tim Tscheppe
 */

package ChecksumSimulation;

import java.util.Scanner;
import java.util.Random;

public class Checksum {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int input1;
		int input2;

		System.out.println("Enter first number (0 - 15) :");
		input1 = sc.nextInt();
		System.out.println("Enter second number (0 - 15) :");
		input2 = sc.nextInt();
		sc.close();

		String binary1 = DecToBinary(input1);
		String binary2 = DecToBinary(input2);

		System.out.println("Number 1 in binary: " + binary1);
		System.out.println("Number 2 in binary: " + binary2);

		String key = BinaryAdd(binary1, binary2);

		System.out.println("Key is " + key);
		
		String receivedMessage1 = SingleBitError(binary1);
		String receivedMessage2 = SingleBitError(binary2);
		
		System.out.println("Received message 1: " + receivedMessage1);
		System.out.println("Received message 2: " + receivedMessage2);

		String receivedKey = BinaryAdd(receivedMessage1, receivedMessage2);
		
		System.out.println("Key calculated from received messages is: " + receivedKey);
		
		if(receivedKey.equals(key)) {
			System.out.println("Keys match - NO ERROR DETECTED");
		}
		else {
			System.out.println("Keys don't match - ERROR Detected");
		}
	}

	static String DecToBinary(int s) {
		String result;
		switch (s) {
		case 0:
			result = "0000";
			break;
		case 1:
			result = "0001";
			break;
		case 2:
			result = "0010";
			break;
		case 3:
			result = "0011";
			break;
		case 4:
			result = "0100";
			break;
		case 5:
			result = "0101";
			break;
		case 6:
			result = "0110";
			break;
		case 7:
			result = "0111";
			break;
		case 8:
			result = "1000";
			break;
		case 9:
			result = "1001";
			break;
		case 10:
			result = "1010";
			break;
		case 11:
			result = "1011";
			break;
		case 12:
			result = "1100";
			break;
		case 13:
			result = "1101";
			break;
		case 14:
			result = "1110";
			break;
		case 15:
			result = "1111";
			break;
		default:
			result = "9999";
			break;
		}
		return result;
	}

	public static String BinaryAdd(String s1, String s2) {

		int b1 = Integer.parseInt(s1, 2);

		int b2 = Integer.parseInt(s2, 2);

		int sum = b1 + b2;

		return Integer.toBinaryString(sum);
	}

	public static String SingleBitError(String s) {

		Random rnd = new Random();
		char zero = '0';
		char one = '1';
		String output;

		int changedAddress = rnd.nextInt(4);

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

		output = s;

		return output;

	}

}
