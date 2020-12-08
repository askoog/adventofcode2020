package se.askware.aoc2020.dec08;

import java.io.IOException;
import java.util.List;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		Solver solver = new Solver(input, -1);
		solver.iterate();
		System.out.println(solver.acc);
	}

	private static class Solver {
		int index = 0;
		long acc = 0;
		boolean[] visited;
		List<String> lines;
		private final int override;

		public Solver(List<String> lines, int override) {
			super();
			this.lines = lines;
			this.override = override;
			visited = new boolean[lines.size()];
		}

		public boolean iterate() {
			while (index < lines.size() && !visited[index]) {
				visited[index] = true;
				String[] line = lines.get(index).split(" ");

				String instruction = line[0].trim();
				int jump = Integer.parseInt(line[1].substring(1));
				if (line[1].startsWith("-")) {
					jump = -jump;
				}
				if (override == index) {
					if (instruction.equals("jmp")) {
						instruction = "nop";
					} else if (instruction.equals("nop")) {
						instruction = "jmp";
					}
				}

				//System.out.println(index + " " + instruction + " " + jump);
				if (instruction.equals("nop")) {
					index++;
				} else if (instruction.equals("acc")) {
					acc += jump;
					index++;
				} else if (instruction.equals("jmp")) {
					index += jump;
				}
			}
			if (index >= lines.size()) {
				System.out.println("terminated");
				return true;
			}
			return false;
		}

	}

	@Override
	public void solvePartTwo(List<String> input) {
		for (int i = 0; i < input.size(); i++) {

			Solver solver = new Solver(input, i);
			if (solver.iterate()) {
				System.out.println(solver.acc);
			}
		}
	}
}
