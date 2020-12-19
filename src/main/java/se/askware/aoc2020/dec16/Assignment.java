package se.askware.aoc2020.dec16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		Data data = parseInput(input);
		int sum = 0;
		for (Ticket ticket : data.tickets) {
			if (!ticket.validate(data.fields)) {
				sum += ticket.invalidFieldValue;
			}
		}
		System.out.println("Sum of invalid: " + sum);

	}

	@Override
	public void solvePartTwo(List<String> input) {
		Data data = parseInput(input);
		Set<Field> fields = data.fields;
		List<Ticket> validTickets = data.tickets.stream().filter(t -> t.validate(data.fields)).collect(Collectors.toList());

		int numFields = validTickets.get(0).fields.length;
		Field[] fieldPerIndex = new Field[numFields];
		boolean running = true;
		while(running) {
			for (int i = 0; i < numFields; i++) {
				if (fieldPerIndex[i] == null) {
					Set<Field> validFieldsForIndex = new HashSet<>();
					final int index = i;
					for (Field field : fields) {
						if (validTickets.stream().allMatch(ticket -> field.valid.contains(ticket.fields[index]))){
							validFieldsForIndex.add(field);
						}
					}
					if (validFieldsForIndex.size() == 1) {
						Field found = validFieldsForIndex.iterator().next();
						fields.remove(found);
						fieldPerIndex[i] = found;
					}
				}
			}
			running = false;
			for (int i = 0; i < fieldPerIndex.length; i++) {
				if (fieldPerIndex[i] == null) {
					running = true;
				}
			}
		}
		long product = 1; 
		for (int i = 0; i < fieldPerIndex.length; i++) {
			System.out.println(i + " " + fieldPerIndex[i].name);
			if (fieldPerIndex[i].name.startsWith("departure")) {
				product *= data.myTicket.fields[i];
			}
		}
		System.out.println("product: " + product);
	}

	
	private Data parseInput(List<String> input) {
		int mode = 0;
		Set<Field> fields = new HashSet<>();
		Ticket myTicket = null;
		List<Ticket> tickets = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			String string = input.get(i);
			if (string.isEmpty()) {
				mode++;
				i += 2;
				string = input.get(i);
			}
			if (mode == 0) {
				Field f = Field.parse(string);
				fields.add(f);
			}
			if (mode == 1) {
				Ticket t = new Ticket(string);
				myTicket = t;
			}
			if (mode == 2) {
				Ticket t = new Ticket(string);
				tickets.add(t);
			}
		}

		Data data = new Data(myTicket, fields, tickets);
		return data;
	}

	
	private static class Data {
		Ticket myTicket;
		Set<Field> fields;
		List<Ticket> tickets;
		public Data(Ticket myTicket, Set<Field> fields, List<Ticket> tickets) {
			super();
			this.myTicket = myTicket;
			this.fields = fields;
			this.tickets = tickets;
		}
		
		
	}

	private static class Ticket {
		private final int[] fields;
		private int invalidFieldValue;
		private int invalidField;

		public Ticket(String line) {
			fields = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
		}

		@Override
		public String toString() {
			return "Ticket [fields=" + Arrays.toString(fields) + "]";
		}

		public boolean validate(Set<Field> fieldsToCheck) {
			for (int i = 0; i < fields.length; i++) {
				int f = fields[i];
				if (fieldsToCheck.stream().noneMatch(field -> field.valid.contains(f))) {
					invalidFieldValue = f;
					invalidField = i;
					return false;
				}
			}
			return true;
		}

	}

	private static class Field {

		String name;
		Set<Integer> valid;

		public Field(String name, Set<Integer> valid) {
			super();
			this.name = name;
			this.valid = valid;
		}

		static Pattern p = Pattern.compile("([a-z ]+): ([0-9]+)-([0-9]+) or ([0-9]+)-([0-9]+)");

		private static Field parse(String line) {
			Matcher matcher = p.matcher(line);
			matcher.find();
			String fieldName = matcher.group(1);
			int range1 = Integer.parseInt(matcher.group(2));
			int range2 = Integer.parseInt(matcher.group(3));
			int range3 = Integer.parseInt(matcher.group(4));
			int range4 = Integer.parseInt(matcher.group(5));

			Set<Integer> vals = IntStream
					.concat(IntStream.range(range1, range2 + 1), IntStream.range(range3, range4 + 1)).boxed()
					.collect(Collectors.toSet());
			return new Field(fieldName, vals);
		}

		@Override
		public String toString() {
			return "Field [name=" + name + ", valid=" + valid + "]";
		}

	}

}
