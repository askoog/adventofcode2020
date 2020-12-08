package se.askware.aoc2020.common;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;

public abstract class AocBase {

	protected List<String> input;
	protected List<String> example;
	 
	public AocBase() {
		try {
			input = IOUtils.readLines(getClass().getResourceAsStream("inputs.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
		try {
			example = IOUtils.readLines(getClass().getResourceAsStream("example.txt"), StandardCharsets.UTF_8);
		} catch (Exception e) {
		}
	}

	public void run() {
		if (example != null) {
			System.out.println("** example **");
			solvePartOne(example);
		}
		System.out.println("** part one **");
		solvePartOne(input);
		System.out.println("** part two **");
		solvePartTwo(input);
	}
	
	public abstract void solvePartOne(List<String> input);
	public abstract void solvePartTwo(List<String> input);

}
