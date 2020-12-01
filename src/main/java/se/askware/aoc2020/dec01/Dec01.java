package se.askware.aoc2020.dec01;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Dec01 {

	public static void main(String[] args) throws IOException {

		List<String> readLines = IOUtils.readLines(Dec01.class.getResourceAsStream("inputs.txt"),
				StandardCharsets.UTF_8);
		int[] numbers = readLines.stream().mapToInt(Integer::parseInt).toArray();
		partOne(numbers);

		partTwo(numbers);

	}

	private static void partTwo(int[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers.length; j++) {
				for (int k = 0; k < numbers.length; k++) {
					if (i != j && i != k && j != k) {
						if (numbers[i] + numbers[j] + numbers[k] == 2020) {
							System.out.println(numbers[i] * numbers[j] * numbers[k]);
							return;
						}
					}
				}
			}
		}
	}

	private static void partOne(int[] numbers) {
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers.length; j++) {
				if (i != j) {
					if (numbers[i] + numbers[j] == 2020) {
						System.out.println(numbers[i] * numbers[j]);
						return;
					}
				}
			}
		}
	}

}
