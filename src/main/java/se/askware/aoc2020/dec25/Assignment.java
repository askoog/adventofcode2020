package se.askware.aoc2020.dec25;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	/*
	 * The handshake used by the card and the door involves an operation that transforms a subject number.

	 *  To transform a subject number, start with the value 1. 
	 *  Then, a number of times called the loop size, perform the following steps:

		Set the value to itself multiplied by the subject number.
		Set the value to the remainder after dividing the value by 20201227.
		The card always uses a specific, secret loop size when it transforms a subject number. The door always uses a different, secret loop size.

The cryptographic handshake works like this:

The card transforms the subject number of 7 according to the card's secret loop size. The result is called the card's public key.
The door transforms the subject number of 7 according to the door's secret loop size. The result is called the door's public key.
The card and door use the wireless RFID signal to transmit the two public keys (your puzzle input) to the other device. Now, the card has the door's public key, and the door has the card's public key. Because you can eavesdrop on the signal, you have both public keys, but neither device's loop size.
The card transforms the subject number of the door's public key according to the card's loop size. The result is the encryption key.
The door transforms the subject number of the card's public key according to the door's loop size. The result is the same encryption key as the card calculated.
If you can use the two public keys to determine each device's loop size, you will have enough information to calculate the secret encryption key that the card and door use to communicate; this would let you send the unlock command directly to the door!

For example, suppose you know that the card's public key is 5764801. With a little trial and error, you can work out that the card's loop size must be 8, because transforming the initial subject number of 7 with a loop size of 8 produces 5764801.

Then, suppose you know that the door's public key is 17807724. By the same process, you can determine that the door's loop size is 11, because transforming the initial subject number of 7 with a loop size of 11 produces 17807724.

At this point, you can use either device's loop size with the other device's public key to calculate the encryption key. Transforming the subject number of 17807724 (the door's public key) with a loop size of 8 (the card's loop size) produces the encryption key, 14897079. (Transforming the subject number of 5764801 (the card's public key) with a loop size of 11 (the door's loop size) produces the same encryption key: 14897079.)

What encryption key is the handshake trying to establish?
	 */
	@Override
	public void solvePartOne(List<String> input) {
		
		int[] vals = input.stream().mapToInt(Integer::parseInt).toArray();
		
		int[] loopsizes = Arrays.stream(vals).map(this::findLoopSize).toArray();
		
		System.out.println(Arrays.toString(loopsizes));
		
		long transform1 = transform(vals[0], loopsizes[1]);
		long transform2 = transform(vals[1], loopsizes[0]);
		
		System.out.println(transform1 + " " + transform2);
	}

	private int findLoopSize(int sought) {
		long subjectNumber = 7;
		long value = 1;
		for (int i = 1; i < 100_000_0000; i++) {
			value = (value * subjectNumber) % 20201227L;
			if (value == sought) {
				return i;
			}
		}
		return -1;
	}

	private long transform(long subjectNumber, int loopSize) {
		long value = 1;
		for (int i = 0; i < loopSize ; i++) {
			value = (value * subjectNumber) % 20201227L;
		}
		return value;
	}

	@Override
	public void solvePartTwo(List<String> input) {
	}

}
