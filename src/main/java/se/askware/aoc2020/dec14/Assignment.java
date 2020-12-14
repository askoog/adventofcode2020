package se.askware.aoc2020.dec14;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		String mask = "";
		Map<Integer, char[]> values = new HashMap<>();
		for (String line : input) {
			String[] split = line.split(" = ");
			if (split[0].trim().equals("mask")) {
				mask = split[1];
			} else {
				int register = Integer.parseInt(split[0].replace("mem[", "").replace("]", ""));
				int value = Integer.parseInt(split[1].trim());
		
				String binary = Integer.toBinaryString(value);
				String padded = String.format("%36s", binary).replace(' ', '0');
				char[] chars = padded.toCharArray();
				
				for (int i = 0; i < mask.length(); i++) {
					if (mask.charAt(i) != 'X') {
						chars[i] = mask.charAt(i);
					} else {
					}
				}
				values.put(register, chars);
			}
		}
		long sum = 0;
		for (char[] chars : values.values()) {
			long val = Long.parseLong(new String(chars), 2);
			sum += val;
		}
		System.out.println(sum);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		String mask = "";
		Map<Long, Long> values = new HashMap<>();
		for (String line : input) {
			String[] split = line.split(" = ");
			if (split[0].trim().equals("mask")) {
				mask = split[1];
			} else {
				int register = Integer.parseInt(split[0].replace("mem[", "").replace("]", ""));
				long value = Long.parseLong(split[1].trim());
				String binary = Integer.toBinaryString(register);
				String padded = String.format("%36s", binary).replace(' ', '0');
								
				char[] c = padded.toCharArray();
				for (int i = 0; i < mask.length(); i++) {
					if (mask.charAt(i) == 'X') {
						c[i]= 'X';
					} else if (mask.charAt(i) == '1') {
						c[i]= '1';
					}
				}
				Queue<String> todo = new ArrayDeque<>();
				todo.add(new String(c));
				
				List<String> done = new ArrayList<>();
				while(!todo.isEmpty()) {
					String poll = todo.poll();
					int indexOf = poll.indexOf('X');
					
					if (indexOf >= 0) {
						char[] charArray = poll.toCharArray();
						charArray[indexOf] = '0';
						todo.add(new String(charArray));
						charArray[indexOf] = '1';
						todo.add(new String(charArray));
					} else {
						done.add(poll);
					}
					
				}
				for (String string : done) {
					long register2 = Long.parseLong(string, 2);
					values.put(register2, value);
				}
			}
		}
		
		System.out.println(values.values().stream().mapToLong(i -> i).sum());	
		
	}

}
