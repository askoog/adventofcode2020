package se.askware.aoc2020.dec07;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	Map<String, Bag> bags = new HashMap<>();

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {

		for (String line : input) {
			String delimiter = " bags contain";
			String bagColour = line.substring(0, line.indexOf(delimiter));
			String rest = line.substring(line.indexOf(delimiter) + delimiter.length()).trim();
			Bag baggins = new Bag(bagColour);
			if (!rest.startsWith("no other")) {

				String[] bagLines = rest.split(", ");

				for (String bag : bagLines) {
					String bagLine = bag.substring(0, bag.indexOf(" bag"));

					int num = Integer.parseInt(bagLine.substring(0, bagLine.indexOf(" ")));
					String c = bagLine.substring(bagLine.indexOf(" ")).trim();
					System.out.println(num + " -> " + c);

					Bag cMap = bags.computeIfAbsent(c, c2 -> new Bag(c2));
					baggins.add(cMap, num);

				}
			}
			bags.put(baggins.colour, baggins);
		}
		System.out.println(bags);

		String wanted = "shiny gold";
		System.out.println(getBagsContaining(wanted).size());

	}

	private static class Bag {
		private final String colour;
		private final Map<Bag, Integer> contains = new HashMap<>();

		public Bag(String colour) {
			super();
			this.colour = colour;
		}

		public void add(Bag bag, int num) {
			contains.put(bag, num);
		}

		@Override
		public String toString() {
			return "Bag [colour=" + colour + ", contains=" + contains + "]";
		}

		public String getColour() {
			return colour;
		}

		public Map<Bag, Integer> getContains() {
			return contains;
		}

	}

	public Collection<Bag> getBagsContaining(String wanted) {
		Collection<Bag> matches = new HashSet<>();
		for (Bag b : bags.values()) {
			if (b.getContains().keySet().stream().anyMatch(b2 -> b2.getColour().equals(wanted))) {
				matches.add(b);
			}
		}
		for (Bag bag : new HashSet<>(matches)) {
			matches.addAll(getBagsContaining(bag.colour));
		}
		
		System.out.println(matches);
		
		return matches;

	}

	@Override
	public void solvePartTwo(List<String> input) {
		int containCount = getContainCount("shiny gold");
		System.out.println(containCount);
	}

	private int getContainCount(String wanted) {
		Bag bag = bags.get(wanted);
		int count = 0;
		for (Entry<Bag, Integer> e: bag.getContains().entrySet()) {
			count += e.getValue() * (1 +(getContainCount(e.getKey().getColour())));
		}
		return count;
		
	}
}
