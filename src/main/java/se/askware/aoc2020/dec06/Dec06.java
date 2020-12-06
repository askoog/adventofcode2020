package se.askware.aoc2020.dec06;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Dec06 {

	public static void main(String[] args) throws IOException {

		List<String> readLines = IOUtils.readLines(Dec06.class.getResourceAsStream("inputs.txt"),
				StandardCharsets.UTF_8);

		part1(readLines);

		part2(readLines);
	}

	private static void part2(List<String> readLines) {
		int sum = 0;
		final List<String> current = new ArrayList<>();
		for (String string : readLines) {
			if (string.trim().isEmpty()) {
				int[] all = current.stream().flatMapToInt(String::chars).distinct().toArray();
				long count = Arrays.stream(all).filter(c -> current.stream().allMatch(s -> s.indexOf(c) >= 0)).count();
				sum += count;
				current.clear();
			} else {
				current.add(string);
			}
		}
		int[] all = current.stream().flatMapToInt(String::chars).distinct().toArray();
		long count = Arrays.stream(all).filter(c -> current.stream().allMatch(s -> s.indexOf(c) >= 0)).count();
		sum += count;
		System.out.println(sum);
	}

	private static void part1(List<String> readLines) {
		int sum = 0;
		String current = "";
		for (String string : readLines) {
			if (string.trim().isEmpty()) {
				sum += current.chars().distinct().count();
				current = "";
			} else {
				current = current + string;
			}
		}
		sum += current.chars().distinct().count();
		System.out.println(sum);
	}
}
