package se.askware.aoc2020.dec19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		List<String> lines = new ArrayList<>();
		Map<Integer, String> rules = new HashMap<>();
		input.forEach(line -> {
			if (line.contains(":")) {
				String[] tokens = line.split(":");
				int index = Integer.parseInt(tokens[0]);
				rules.put(index, tokens[1].trim());
			} else {
				if (!line.trim().isEmpty()) {
					lines.add(line);
				}
			}
		});
		
		int numMatched = 0;
		for (String line : lines) {
			Set<String> matches = matches(0, rules, line);
			if (matches.contains(line)) {
				numMatched++;
			}
		}
		System.out.println(numMatched);
		
	}

	public Set<String> matches(int index, Map<Integer, String> rules, String input){
		if (input.isEmpty()) {
			return new HashSet<>();
		}
		String string = rules.get(index);
		
		if (string.contains("\"")) {			
			if (input.charAt(0) == string.charAt(1)) {
				Set<String> result = new HashSet<>();
				result.add(input.substring(0,1));
				return result;
			}
		} else {
			String[] tokens;
			if (string.contains("|")) {
				tokens = string.split("\\|");
			} else {
				tokens = new String[] {string};
			}
			Set<String> result = new HashSet<>();
			for (String or : tokens) {
				String[] split = or.trim().split(" ");
				Set<String> match1 = matches(Integer.parseInt(split[0]), rules, input);
				if (match1.size() > 0 &&  split.length > 1) {
					for (String m : match1) {
						String substring = input.substring(m.length());
						Set<String> match2 = matches(Integer.parseInt(split[1]), rules, substring);
						for (String string2 : match2) {
							result.add(m + string2);					
						}
					}
				} else {
					result.addAll(match1);
				}
			}
			return result;
		}
				
		return new HashSet<>();
	}
	
	@Override
	public void solvePartTwo(List<String> input) {
		List<String> lines = new ArrayList<>();
		Map<Integer, String> rules = new HashMap<>();
		input.forEach(line -> {
			if (line.contains(":")) {
				String[] tokens = line.split(":");
				int index = Integer.parseInt(tokens[0]);
				rules.put(index, tokens[1].trim());
			} else {
				if (!line.trim().isEmpty()) {
					lines.add(line);
				}
			}
		});
		
		rules.put(8, "42 | 42 8");
		rules.put(11, "42 31 | 42 200"); // hack since the solution only handles length 2...
		rules.put(200, "11 31");
				
		int numMatched = 0;
		for (String line : lines) {
			Set<String> matches = matches(0, rules, line);
			if (matches.contains(line)) {
				numMatched++;
			}
		}
		System.out.println(numMatched);
		
	}

}
