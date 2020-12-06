package se.askware.aoc2020.dec05;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Dec05 {

	public static void main(String[] args) throws IOException {

		List<String> readLines = IOUtils.readLines(Dec05.class.getResourceAsStream("inputs.txt"),
				StandardCharsets.UTF_8);
		int max = readLines.stream().map(Dec05::parseRowAndSeat).mapToInt(i -> i[0]*8 + i[1]).max().orElse(0);
		System.out.println(max);

		boolean[][] seating = new boolean[128][8];
		

		int[] seats = readLines.stream().map(Dec05::parseRowAndSeat).mapToInt(i -> i[0]*8 + i[1]).sorted().toArray();
		
		for (int i = 1; i < seats.length - 1; i++) {
			if (seats[i] +2 == seats[i+1]) {
				System.out.println(seats[i] + 1);
				
			}
		}
	}

	private static int[] parseRowAndSeat(String line) {
		int rows = 128;
		int offset = 0;
		String rowString = line.substring(0,7);
//		System.out.println(rowString);
		for (int i = 0; i < rowString.length(); i++) {
			if (rowString.charAt(i) == 'B') {
				offset += rows/2;
			}
			rows /= 2;
		}
//		System.out.println(offset);
		
		String seatString = line.substring(7);
		int seats = 8;
		int seatoffset = 0;
		for (int i = 0; i < seatString.length(); i++) {
			if (seatString.charAt(i) == 'R') {
				seatoffset += seats/2;
			}
			seats /= 2;
		}
	//	System.out.println(offset + " " + seatoffset);
		return new int[] {offset, seatoffset};
	}
}
