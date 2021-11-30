package se.askware.aoc2020.dec23;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}
	public void solvePartOneTakeOne(List<String> input) {
		List<Integer> cups = new LinkedList<>(
				input.get(0).chars().map(Character::getNumericValue).boxed().collect(toList()));
		int numcups = cups.size();

		int pos = 0;
		int numMoves = 100;
		for (int move = 1; move <= numMoves; move++) {
			System.out.println("-- move " + move + " --");

			System.out.println(cups);
			int cupAtPos = cups.get(pos);
			System.out.println(cupAtPos);
			List<Integer> subList = new ArrayList<>();
			int p = pos + 1;
			for (int i = 0; i < 3; i++) {
				p = p%cups.size();
				subList.add(cups.remove(p));
			}
			System.out.println("pickup " + subList);
			int wantedCup = cupAtPos - 1;
			int max = cups.stream().mapToInt(Integer::intValue).max().orElse(0);
			int min = cups.stream().mapToInt(Integer::intValue).min().orElse(0);
			boolean changed = false;
			do {
				changed = false;
				if (wantedCup < min) {
					wantedCup = max;
					changed = true;
				}
				if (subList.contains(wantedCup)) {
					wantedCup--;
					changed = true;
				}
			}while(changed);
			
			System.out.println("wanted " + wantedCup);
			for (int i = 0; i < cups.size(); i++) {
				if (cups.get(i) == wantedCup) {
					System.out.println("destination " + (i + 1));
					for (int j = 0; j < subList.size(); j++) {
						cups.add((i + 1 +j) % numcups, subList.get(j));						
					}					
					break;
				}
			}
			System.out.println(cups);
			pos = (cups.lastIndexOf(cupAtPos) + 1) % cups.size();
		}

		StringBuilder sb = new StringBuilder();
		int start = cups.indexOf(1);
		for (int i = 1; i < numcups; i++) {
			sb.append(cups.get((start + i) % numcups));
		}
		System.out.println(sb);
	}

	@Override
	public void solvePartOne(List<String> input) {
		int totalNumCups = 10;
		int numMoves = 100;
		Map<Node, Node> nodes = solveForCups(input, totalNumCups, numMoves);	
		
		StringBuilder sb = new StringBuilder();
		Node node = nodes.get(new Node(1));
		for (int i = 1; i < 10; i++) {
			node = node.next;
			if (node.value == 0) {
				node = node.next;
			}
			sb.append(node.value);
		}
		System.out.println(sb);

	}

	private Map<Node, Node> solveForCups(List<String> input, int totalNumCups, int numMoves) {
		Map<Node, Node> nodes = new HashMap<>();
		
		List<Integer> cups = new LinkedList<>(
				input.get(0).chars().map(Character::getNumericValue).boxed().collect(toList()));
		Node ZERO = new Node(0);
		ZERO.next = ZERO;
		ZERO.previous = ZERO;
		Node tmp = ZERO;
		for (Integer value : cups) {
			Node n = new Node(value);
			tmp.insertAfter(n);
			tmp = n;
			nodes.put(n, n);
		}
		for (int i = cups.size() + 1; i <= totalNumCups; i++) {
			Node n = new Node(i);
			tmp.insertAfter(n);
			tmp = n;
			nodes.put(n, n);
		}
		Node current = ZERO.next;
		List<Node> removed;
 		for (int move = 1; move <= numMoves; move++) {
//			System.out.println("-- move " + move + " --");
//			printNodes(ZERO, current);
			
			Node n1 = current.next;
			removed = new ArrayList<>();
			for (int i = 0; i < 3; i++) {
				if (n1 == ZERO) {
					n1 = n1.next;
				}
				Node n2 = n1.next;
				n1.remove();
				removed.add(n1);
				n1 = n2;
			}
//			System.out.println(removed);
			Node wanted = current;
			do {
				int wantedValue = wanted.value - 1;
				if (wantedValue == 0) {
					wantedValue = totalNumCups;
				}
				wanted = new Node(wantedValue);
			} while(removed.contains(wanted));
			
			Node node = nodes.get(wanted);
//			System.out.println(node.value);

			for (Node r : removed) {
				if (node == ZERO) {
					node = node.next;
				}
				node.insertAfter(r);
				node = r;
			}
			current = current.next;
			if (current == ZERO) {
				current = current.next;
			}
		}

 		return nodes;
	}
	private void printNodes(Node zero, Node current) {
		Node n = zero.next;
		while(n != zero) {
			if (n == current) {
				System.out.print("(" + n.value + ") ");
			} else {
				System.out.print(n.value + " ");
			}
			n = n.next;
		}
		System.out.println();
	}

	@Override
	public void solvePartTwo(List<String> input) {
		Map<Node, Node> nodes = solveForCups(input, 1_000_000, 10_000_000);
		
		Node node = nodes.get(new Node(1));
		System.out.println((long)node.next.value * node.next.next.value);
	}

	private static class Node {
		int value;
		Node next;
		Node previous;
		
		public Node(int value) {
			this.value = value;
		}

		public void insertAfter(Node n) {
			n.previous = this;
			n.next = next;
			next.previous = n;
			next = n;
		}
		
		public void remove() {
			previous.next = next;
			next.previous = previous;
			next = null;
			previous = null;
		}

		@Override
		public int hashCode() {
			return value;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Node other = (Node) obj;
			if (value != other.value) {
				return false;
			}
			return true;
		}
		
		@Override
		public String toString() {
			return "" + value;
		}
		
		
	}
	
}
