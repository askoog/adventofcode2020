package se.askware.aoc2020.dec03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Dec03 {

	public static void main(String[] args) throws IOException {

		List<String> lines = IOUtils.readLines(Dec03.class.getResourceAsStream("inputs.txt"),
				StandardCharsets.UTF_8);
		traverse(lines, 3, 1);

		traverse2(lines);
	}
	
	private static void traverse2(List<String> lines) {
		int reduce = Arrays.stream(new int[][] { { 1, 1 }, { 3, 1 }, { 5, 1 }, { 7, 1 }, { 1, 2 } })
				.mapToInt(a -> traverse(lines, a[0], a[1]))
				.reduce((i1, i2) -> i1 * i2).orElse(0);
		System.out.println(reduce);
	}

	private static int traverse(List<String> lines, int colIncrement, int rowIncrement) {
		int col = 0;
		int treeCount = 0;
		for (int row = 0; row < lines.size(); row+= rowIncrement ) {
			if (lines.get(row).charAt(col) == '#') {
				treeCount++;
			}
			col = (col + colIncrement) % lines.get(0).length();
		}
		System.out.println(treeCount);
		return treeCount;
	}


}
