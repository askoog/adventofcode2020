package se.askware.aoc2020.dec22;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		Queue<Integer> p1 = new ArrayDeque<>();
		Queue<Integer> p2 = new ArrayDeque<>();
		parseInput(input, p1, p2);
		System.out.println(p1);
		System.out.println(p2);
	
		while(!p1.isEmpty() && !p2.isEmpty()) {
			Integer c1 = p1.poll();
			Integer c2 = p2.poll();
			if (c1 > c2) {
				p1.add(c1);
				p1.add(c2);
			} else {
				p2.add(c2);
				p2.add(c1);
			}			
//			System.out.println(p1);
//			System.out.println(p2);
		}
		System.out.println(p1);
		System.out.println(p2);
		Integer[] winner = p1.isEmpty() ? p2.toArray(new Integer[p2.size()]) : p1.toArray(new Integer[p1.size()]);
		int sum = 0;
		for (int j = 0; j < winner.length; j++) {
			sum += winner[j]*(winner.length - j);
		}
		System.out.println(sum);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		Queue<Integer> p1 = new ArrayDeque<>();
		Queue<Integer> p2 = new ArrayDeque<>();
		parseInput(input, p1, p2);
		System.out.println(p1);
		System.out.println(p2);
	
		new Game(p1,p2).play();
		Integer[] winner = p1.isEmpty() ? p2.toArray(new Integer[p2.size()]) : p1.toArray(new Integer[p1.size()]);
		int sum = 0;
		for (int j = 0; j < winner.length; j++) {
			sum += winner[j]*(winner.length - j);
		}
		System.out.println(sum);
	}

	private void parseInput(List<String> input, Queue<Integer> p1, Queue<Integer> p2) {
		int i = 1;
		for (;  !input.get(i).trim().isEmpty(); i++) {
			p1.add(Integer.parseInt(input.get(i)));
		}
		for (i+=2;  i <input.size(); i++) {
			p2.add(Integer.parseInt(input.get(i)));
		}
	}

	
	private class Game {
		Queue<Integer> p1;
		Queue<Integer> p2;
		Set<List<Integer>> seen = new HashSet<>();
		
		public Game(Queue<Integer> p1, Queue<Integer> p2) {
			super();
			this.p1 = p1;
			this.p2 = p2;
		}
		
		public int play() {
			while(!p1.isEmpty() && !p2.isEmpty()) {
				if (!seen.add(new ArrayList<>(p1))) {
					return 1;				
				}
				
				Integer c1 = p1.poll();
				Integer c2 = p2.poll();
				if (p1.size() >= c1 && p2.size() >= c2) {
					
					Game recursiveGame = new Game(new ArrayDeque<>(p1.stream().limit(c1).collect(toList())),
							new ArrayDeque<>(p2.stream().limit(c2).collect(toList())));
					if (recursiveGame.play() == 1) {
						p1.add(c1);
						p1.add(c2);
					} else {
						p2.add(c2);
						p2.add(c1);
					}					
				} else {
					
					if (c1 > c2) {
						p1.add(c1);
						p1.add(c2);
					} else {
						p2.add(c2);
						p2.add(c1);
					}			
				}
			}
			return p1.isEmpty() ? 2 : 1;
		}
		
	}
}
