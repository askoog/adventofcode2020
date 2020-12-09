package se.askware.aoc2020.dec09;

import java.io.IOException;
import java.util.List;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		int preamble = 25;
		long[][] lastSums = new long[preamble][preamble+1];
		for (int i = 0; i < input.size(); i++) {
			long current = Long.parseLong(input.get(i));
			if (i >= preamble) {
				boolean found = false;
				for (int j = 0; j < preamble; j++) {
					for (int k = 0; k < preamble; k++) {
						if (lastSums[j][k+1] == current) {
							found = true;
							break;
						}
					}
				}
				if (!found ) {
					System.out.println("not found: " + current);
					for (int j = 0; j < lastSums.length; j++) {
						for (int k = 0; k < lastSums[j].length; k++) {
							System.out.print(String.format("%-3d ", lastSums[j][k]));
						}
						System.out.println();
					}
				}
			}
			lastSums[i%preamble][0] = current;
			for (int j = 0; j < preamble; j++) {
				lastSums[i%preamble][j+1] = lastSums[j][0] + current; 
			}
		}
	}

	@Override
	public void solvePartTwo(List<String> readLines) {
		
		long[] array = readLines.stream().mapToLong(Long::parseLong).toArray();
		int wanted = 20874512;
		for (int i = 0; i < array.length; i++) {
			long sum = 0;
			for (int j = i; j < array.length; j++) {
				sum += array[j];
				if (sum == wanted) {
					System.out.println(array[i] + " + " + array[j]  + " = " + (array[i]+array[j]));
					return;
				} else if (sum > wanted ) {
					break;
				}
			}
		}
	}

}
