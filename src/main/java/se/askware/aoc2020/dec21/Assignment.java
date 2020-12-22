package se.askware.aoc2020.dec21;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {

		Map<String, Set<String>> maybeAllergens = new HashMap<>();
		Map<String, List<Set<String>>> maybeIngedients = new HashMap<>();
		Map<String, AtomicInteger> occurences = new HashMap<>();
		Set<String> allAllergens = new HashSet<>();

		for (String line : input) {
			String[] lineAllergens = line.substring(line.indexOf("(") + 10).replace(")", "").trim().split(",");
			for (String allergen : lineAllergens) {
				allAllergens.add(allergen.trim());
			}
		}

		for (String line : input) {
			String[] ingredients = line.substring(0, line.indexOf("(")).trim().split(" ");
			String[] itemAllergens = line.substring(line.indexOf("(") + 10).replace(")", "").trim().split(",");
			Set<String> maybeSet = new HashSet<>();
			for (String allergen : itemAllergens) {
				maybeSet.add(allergen.trim());
			}
			for (int i = 0; i < ingredients.length; i++) {
				occurences.computeIfAbsent(ingredients[i], k -> new AtomicInteger()).incrementAndGet();
				maybeAllergens.computeIfAbsent(ingredients[i], k -> new HashSet<>()).addAll(maybeSet);
			}
			for (String string : maybeSet) {
				maybeIngedients.computeIfAbsent(string, k -> new ArrayList<>())
						.add(new HashSet<>(Arrays.asList(ingredients)));
			}
		}

		Map<String, Set<String>> candidates = new HashMap<>();
		for (Entry<String, List<Set<String>>> e : maybeIngedients.entrySet()) {
			Set<String> all = e.getValue().get(0);
			for (Set<String> s : e.getValue()) {
				all.retainAll(s);
			}
			System.out.println(e.getKey() + " : " + all);
			candidates.put(e.getKey(), all);
		}
		boolean done = false;
		while (!done) {
			done = true;
			for (Entry<String, Set<String>> e : candidates.entrySet()) {
				if (e.getValue().size() == 1) {
					for (Entry<String, Set<String>> e2 : candidates.entrySet()) {
						if (!e.getKey().equals(e2.getKey())) {
							if (e2.getValue().removeAll(e.getValue())) {
								done = false;
							}
						}
					}
				}
			}
		}
		List<String> definitiveAllergens = candidates.values().stream().flatMap(Set::stream).sorted().collect(toList());

		System.out.println(occurences);
		System.out.println(candidates);
		System.out.println(maybeAllergens);
		System.out.println(definitiveAllergens);
		System.out.println(
				occurences.entrySet().stream().filter(e -> !definitiveAllergens.contains(e.getKey())).collect(toSet()));

		int sum = occurences.entrySet().stream().filter(e -> !definitiveAllergens.contains(e.getKey()))
				.mapToInt(e -> e.getValue().get()).sum();
		System.out.println(sum);
		
		List<String> collect = candidates.entrySet().stream().sorted(Comparator.comparing(Entry::getKey)).flatMap(e -> e.getValue().stream()).collect(toList());
		
		System.out.println(collect.stream().collect(joining(",")));
	}

	@Override
	public void solvePartTwo(List<String> input) {
	}

}
