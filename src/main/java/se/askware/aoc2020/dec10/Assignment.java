package se.askware.aoc2020.dec10;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		long max = input.stream().mapToLong(Long::parseLong).max().orElse(0) + 3;
		long[] values = Stream.concat(Stream.of("0", "" + max), input.stream()).mapToLong(Long::parseLong).sorted()
				.limit(Integer.MAX_VALUE).toArray();

		long[] diffs = new long[4];
		for (int i = 1; i < values.length; i++) {
			int diff = (int)(values[i] - values[i-1]);
			diffs[diff]++;
		}
		System.out.println(diffs[1] * diffs[3]);
	}

	@Override
	public void solvePartTwo(List<String> readLines) {
		long max = input.stream().mapToLong(Long::parseLong).max().orElse(0) + 3;

		long[] values = Stream.concat(Stream.of("0", "" + max), input.stream()).mapToLong(Long::parseLong).sorted()
				.limit(Integer.MAX_VALUE).toArray();

		long[] paths = new long[values.length];

		paths[0] = 1;
		for (int i = 0; i < values.length; i++) {
			for (int j = i - 3; j < i; j++) {
				if (j >= 0 && values[i] - values[j] <= 3) {
					paths[i] += paths[j];
				}
			}
		}
		System.out.println(paths[values.length - 1]);
	}
}
