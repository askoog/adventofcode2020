package se.askware.aoc2020.dec11;

import java.io.IOException;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		Seating seating = new Seating(input);
		System.out.println(seating);
		int numIterations=0;
		while (seating.iterate1()) {
			numIterations++;
		}
		System.out.println("Finished in " + numIterations);
		System.out.println(seating);
		System.out.println(seating.getNumOccupiedSeats());
	}

	@Override
	public void solvePartTwo(List<String> readLines) {
		Seating seating = new Seating(input);
		System.out.println(seating);
		int numIterations=0;
		while (seating.iterate2()) {
			numIterations++;
		}
		System.out.println("Finished in " + numIterations);
		System.out.println(seating);
		System.out.println(seating.getNumOccupiedSeats());
	}

	private static class Seating {

		private char[][] seats;

		public Seating(List<String> lines) {
			seats = new char[lines.size()][];
			int index = -1;
			for (String line : lines) {
				seats[++index] = line.toCharArray();
			}
		}

		public int getNumOccupiedSeats() {
			int occupied = 0;
			for (int i = 0; i < seats.length; i++) {
				for (int j = 0; j < seats[0].length; j++) {
					occupied += seats[i][j] == '#' ? 1:0;
				}
			}
			return occupied;
		}

		@Override
		public String toString() {
			char[][] matrix = seats;
			return toString(matrix);
		}

		private String toString(char[][] matrix) {
			StringBuilder sb = new StringBuilder();
			for (char[] cs : matrix) {
				sb.append(new String(cs)).append("\n");
			}
			return sb.toString();
		}

		private boolean isOccupiesSeat(int i, int j) {
			return (i >= 0 && i < seats.length && j >= 0 && j < seats[i].length) ? seats[i][j] == '#' : false;
		}

		public int numOccupiedAdjacentSeats(int y, int x) {
			int result = 0;
			result += isOccupiesSeat(y - 1, x - 1) ? 1 : 0;
			result += isOccupiesSeat(y - 1, x) ? 1 : 0;
			result += isOccupiesSeat(y - 1, x + 1) ? 1 : 0;
			result += isOccupiesSeat(y, x - 1) ? 1 : 0;
			result += isOccupiesSeat(y, x + 1) ? 1 : 0;
			result += isOccupiesSeat(y + 1, x - 1) ? 1 : 0;
			result += isOccupiesSeat(y + 1, x) ? 1 : 0;
			result += isOccupiesSeat(y + 1, x + 1) ? 1 : 0;

			return result;
		}
		
		public int numOccupiedAdjacentSeats2(int y, int x) {
			int resulte = 0;
			resulte += getFirstSeat(y,x, y2 -> y2-1, x2 -> x2-1) == '#' ? 1 : 0;
			resulte += getFirstSeat(y,x, y2 -> y2-1, x2 -> x2) == '#' ? 1 : 0;
			resulte += getFirstSeat(y,x, y2 -> y2-1, x2 -> x2+1) == '#' ? 1 : 0;
			resulte += getFirstSeat(y,x, y2 -> y2, x2 -> x2-1) == '#' ? 1 : 0;
			resulte += getFirstSeat(y,x, y2 -> y2, x2 -> x2+1) == '#' ? 1 : 0;
			resulte += getFirstSeat(y,x, y2 -> y2+1, x2 -> x2-1) == '#' ? 1 : 0;
			resulte += getFirstSeat(y,x, y2 -> y2+1, x2 -> x2) == '#' ? 1 : 0;
			resulte += getFirstSeat(y,x, y2 -> y2+1, x2 -> x2+1) == '#' ? 1 : 0;

			return resulte;
		}

		private boolean isSeat(int i, int j) {
			return (i >= 0 && i < seats.length && j >= 0 && j < seats[i].length);
		}

		public char getFirstSeat(int i, int j, Function<Integer,Integer> changeI, Function<Integer,Integer> changeJ) {
			Integer i2 = changeI.apply(i);
			Integer j2 = changeJ.apply(j);
			if (isSeat(i2, j2)) {
				char seat = seats[i2][j2];
				if (seat == '.') {
					return getFirstSeat(i2, j2, changeI, changeJ);
				} else {
					return seat;
				}
			} 
			return '.';			
		}

		public boolean iterate1() {
			BiFunction<Integer, Integer, Integer> getSeatFunction = this::numOccupiedAdjacentSeats;
			int occupiedSeatsLimit = 4;
			return iterate(getSeatFunction, occupiedSeatsLimit);
		}

		public boolean iterate2() {
			BiFunction<Integer, Integer, Integer> getSeatFunction = this::numOccupiedAdjacentSeats2;
			int occupiedSeatsLimit = 5;
			return iterate(getSeatFunction, occupiedSeatsLimit);
		}

		public boolean iterate(BiFunction<Integer, Integer, Integer> getSeatFunction, int occupiedSeatsLimit) {
			boolean changed = false;
			char[][] next = new char[seats.length][seats[0].length];
			for (int i = 0; i < seats.length; i++) {
				for (int j = 0; j < seats[0].length; j++) {		
					if (seats[i][j] == 'L' && getSeatFunction.apply(i, j) == 0) {
						next[i][j] = '#';
						changed = true;
					} else if (seats[i][j] == '#' && getSeatFunction.apply(i, j) >= occupiedSeatsLimit ) {
						next[i][j] = 'L';
						changed = true;
					} else {
						next[i][j] = seats[i][j];
					}
				}
			}
			seats = next;
			return changed;
		}
	}
}
