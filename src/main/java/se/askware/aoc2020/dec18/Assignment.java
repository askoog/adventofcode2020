package se.askware.aoc2020.dec18;

import java.io.IOException;
import java.util.List;
import java.util.Stack;
import java.util.function.BiFunction;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		long result = 0;
		for (String string : input) {
			result += evaluate(string);
		}
		System.out.println(result);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		long result = 0;
		for (String string : input) {
			result += evaluate2(string);
		}
		System.out.println(result);
	}

	private long evaluateString(String result) {
		//System.out.println("Evaluate " + result);
		while (result.indexOf("+") > 0) {
			String[] tokens = result.split(" ");
			result = "";
			for (int i = 0; i < tokens.length; i++) {
				if (i < tokens.length -1 && tokens[i + 1].equals("+")) {
					result += (Integer.parseInt(tokens[i]) + Integer.parseInt(tokens[i + 2])) + " ";
					i+=2;
				} else {
					result += tokens[i] + " ";
				}
			}
			result = result.trim();
			//System.out.println(result);
		}
		String[] tokens = result.split(" ");
		long resultVal = 1;
		for (int i = 0; i < tokens.length; i++) {
			if (!tokens[i].equals("*")) {				
				resultVal *= Long.parseLong(tokens[i]);
			}
		}
		//System.out.println("=" + resultVal);
		return resultVal;
	}

	private long evaluate(String input) {

		BiFunction<Long, Long, Long> add = (l1, l2) -> l1 + l2;
		BiFunction<Long, Long, Long> multiply = (l1, l2) -> l1 * l2;

		StackElement e = new StackElement(0, add);
		Stack<StackElement> stack = new Stack<>();
		for (char c : input.toCharArray()) {
			if (c == '+') {
				e.func = add;
			} else if (c == '*') {
				e.func = multiply;
			} else if (c == '(') {				
				stack.push(e);
				e = new StackElement(0, add);
			} else if (c == ')') {
				long val2 = e.value;
				e = stack.pop();
				e.apply(val2);
			} else if (Character.isDigit(c)) {
				e.apply(Character.getNumericValue(c));
			}
		}
		return e.value;
	}

	private long evaluate2(String input) {

		BiFunction<Long, Long, Long> add = (l1, l2) -> l1 + l2;
		BiFunction<Long, Long, Long> multiply = (l1, l2) -> l1 * l2;

		Stack<String> stack = new Stack<>();
		String current = "";
		for (char c : input.toCharArray()) {
			if (c == '(') {
				//System.out.println("push " + current);
				stack.push(current);
				current = "";
			} else if (c == ')') {
				long evaluate = evaluateString(current);
				//System.out.println("pop " + current + " = " + evaluate);
				current = stack.pop() +  evaluate;
				//System.out.println(current);
			} else  {
				current = current += c;
			}
		}
		return evaluateString(current);
	}

	private static class StackElement {
		long value;
		BiFunction<Long, Long, Long> func;

		public StackElement(long val, BiFunction<Long, Long, Long> func) {
			super();
			this.value = val;
			this.func = func;
		}

		public void apply(long newVal) {
			value = func.apply(value, newVal);
		}

	}

	
}
