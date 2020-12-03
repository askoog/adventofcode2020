package se.askware.aoc2020.dec02;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Dec02 {

	public static void main(String[] args) throws IOException {
		List<String> readLines = IOUtils.readLines(Dec02.class.getResourceAsStream("inputs.txt"),
				StandardCharsets.UTF_8);
	
		System.out.println(readLines.stream().filter(Dec02::validate1).count());
		System.out.println(readLines.stream().filter(Dec02::validate2).count());
	}

	private static boolean validate1(String string) {
		String[] split = string.split(" ");
		int[] range = Arrays.stream(split[0].split("-")).mapToInt(Integer::parseInt).toArray();
		char wanted = split[1].charAt(0);
		long count = split[2].chars().filter(c -> c == wanted).count();
		return count >= range[0] && count <= range[1];
	}
	
	
	private static boolean validate2(String string) {
		String[] split = string.split(" ");
		int[] range = Arrays.stream(split[0].split("-")).mapToInt(Integer::parseInt).toArray();
		char wanted = split[1].charAt(0);
		if (split[2].charAt(range[0] - 1) == wanted) {
			return split[2].charAt(range[1] - 1) != wanted;
		}
		if (split[2].charAt(range[1] - 1) == wanted) {
			return split[2].charAt(range[0] - 1) != wanted;
		}
		return false;
	}
	
}
