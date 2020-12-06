package se.askware.aoc2020.dec04;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.io.IOUtils;

public class Dec04 {

	public static void main(String[] args) throws IOException {

		List<String> lines = IOUtils.readLines(Dec04.class.getResourceAsStream("inputs.txt"),
				StandardCharsets.UTF_8);

		validate1(lines);
		validate2(lines);
	}

	private static void validate1(List<String> lines) {
		List<String> current = new ArrayList<>();
		int numValid = 0;
		for (String line : lines) {
			if (line.trim().isEmpty()) {
				numValid += validateEntry1(current) ? 1 : 0;
				current = new ArrayList<>();
			}
			String[] split = line.split(" ");
			current.addAll(Arrays.asList(split));
		}
		if (!current.isEmpty()) {
			numValid += validateEntry1(current) ? 1 : 0;
		}
		System.out.println(numValid);
	}
	
	private static void validate2(List<String> lines) {
		List<String> current = new ArrayList<>();
		int numValid = 0;
		for (String line : lines) {
			if (line.trim().isEmpty()) {
				numValid += validateEntry2(current) ? 1 : 0;
				current = new ArrayList<>();
			}
			String[] split = line.split(" ");
			current.addAll(Arrays.asList(split));
		}
		if (!current.isEmpty()) {
			numValid += validateEntry1(current) ? 1 : 0;
		}
		System.out.println(numValid);
	}


	private static boolean validateEntry1(List<String> lines) {
		List<String> requiredFields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
		List<Predicate<String>> validators = Arrays.asList(
				s -> s.startsWith("byr"),
				s -> s.startsWith("iyr"),
				s -> s.startsWith("eyr"),
				s -> s.startsWith("hgt"),
				s -> s.startsWith("hcl"),
				s -> s.startsWith("ecl"),
				s -> s.startsWith("pid"));

		List<String> optionalFields = Arrays.asList("cid");

		boolean required = validators.stream().allMatch(validator -> lines.stream().anyMatch(s -> validator.test(s)));
		long numOPtional = optionalFields.stream().filter(field -> lines.stream().anyMatch(s -> s.startsWith(field)))
				.count();

		return required;

	}

	private static boolean validateEntry2(List<String> lines) {
		List<Predicate<String>> validators = Arrays.asList(
				s -> {
					if (s.startsWith("byr")) {
						int year = Integer.parseInt(s.trim().split(":")[1]);
						return (year >= 1920 && year <= 2002);
					}
					return false;
				},
				s -> {
					if (s.startsWith("iyr")) {
						int year = Integer.parseInt(s.trim().split(":")[1]);
						return (year >= 2010 && year <= 2020);
					}
					return false;
				},
				s -> {
					if (s.startsWith("eyr")) {
						int year = Integer.parseInt(s.trim().split(":")[1]);
						return (year >= 2020 && year <= 2030);
					}
					return false;
				},
				s -> {
					if (s.startsWith("hgt")) {
						String data = s.trim().split(":")[1];
						if (data.endsWith("cm")) {
							int year = Integer.parseInt(data.replace("cm", ""));
							return (year >= 150 && year <= 193);
						} if (data.endsWith("in")) {
							int year = Integer.parseInt(data.replace("in", ""));
							return (year >= 59 && year <= 76);							
						}
					}
					return false;
				},
				s -> {
					if (s.startsWith("hcl")) {
						String data = s.trim().split(":")[1];
						if (data.startsWith("#")) {
							boolean valid = data.substring(1).chars().allMatch(c -> Character.isDigit(c) || (c >= 'a' && c<= 'f'));
							System.out.println(data + " " + valid);
							return valid;
						}
					}
					return false;
				},
				s -> {
					if (s.startsWith("ecl")) {
						String[] valid = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
						String data = s.trim().split(":")[1];
						return Arrays.stream(valid).anyMatch(v -> data.equals(v));
					}
					return false;
				},
				s -> {
					if (s.startsWith("pid")) {
						String data = s.trim().split(":")[1];
						return data.length() == 9 &&  data.chars().allMatch(c -> Character.isDigit(c));
					}
					return false;
				});

		boolean required = validators.stream().allMatch(validator -> lines.stream().anyMatch(s -> validator.test(s)));
		return required;

	}

}
