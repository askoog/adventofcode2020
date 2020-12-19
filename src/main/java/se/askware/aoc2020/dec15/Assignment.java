package se.askware.aoc2020.dec15;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		for (String line : input) {
			solveForLine1(line, 2020);
		}
	}

	private void solveForLine1(String line, int numTurns) {
		int[] array = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
		System.out.println(line);

		Map<Integer, List<Integer>> seen = new HashMap<>();
		int lastSaid = -1;
		int turn = 1;
		for (int initial : array) {
			seen.computeIfAbsent(initial, i -> new ArrayList<>()).add(turn);
			lastSaid = initial;
			turn++;
		}
		while (turn <= numTurns) {
			List<Integer> previousTurns = seen.computeIfAbsent(lastSaid, i -> new ArrayList<>());
			if (previousTurns.size() > 1) {
				int diff = previousTurns.get(previousTurns.size() - 1) - previousTurns.get(previousTurns.size() - 2);
				lastSaid = diff;
				previousTurns.remove(0);
			} else {
				lastSaid = 0;
			}
			seen.computeIfAbsent(lastSaid, i -> new ArrayList<>()).add(turn);
			turn++;
			if (turn % 1_000_000 == 0) {
				System.out.print(".");
			}
		}
		System.out.println(lastSaid);

	}

	@Override
	public void solvePartTwo(List<String> input) {
		for (String line : input) {
			solveForLine1(line, 30_000_000);
		}
	}

}
