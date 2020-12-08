package se.askware.aoc2020.dec06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.askware.aoc2020.common.AocBase;

public class Dec06 extends AocBase {

	public static void main(String[] args) throws IOException {
		new Dec06().run();
	}

	@Override
	public void solvePartTwo(List<String> readLines) {
		int sum = 0;
		final List<String> current = new ArrayList<>();
		for (String string : input) {
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

	@Override
	public void solvePartOne(List<String> input) {
		int sum = 0;
		String current = "";
		for (String string : input) {
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
