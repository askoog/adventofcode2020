package se.askware.aoc2020.dec17;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		List<Cell3D> active = new ArrayList<>();
		int x = 500, y = 500, z = 500;
		for (String line : input) {

			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '#') {
					active.add(new Cell3D(x + i, y, z));
				}
			}
			y++;
		}
		int maxCycles = 6;
		for (int i = 0; i < maxCycles; i++) {
			System.out.println("Cycle " + i);
			Grid g = new Grid();
			for (Cell3D cell : active) {
				List<Integer> neighbours = cell.getNeighbours();
				//System.out.println(neighbours);
				neighbours.stream().forEach(n -> g.touch(n));
			}
			//System.out.println(g);
			active = g.getActiveCells(active);
			System.out.println("num active: " +  active.size());
		}
	}

	@Override
	public void solvePartTwo(List<String> input) {
		List<Cell4D> active = new ArrayList<>();
		int x = 500, y = 500, z = 500, w = 500;
		for (String line : input) {
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == '#') {
					active.add(new Cell4D(x + i, y, z, w));
				}
			}
			y++;
		}
		int maxCycles = 6;
		for (int i = 1; i <= maxCycles; i++) {
			System.out.println("Cycle " + i);
			Grid4D g = new Grid4D();
			for (Cell4D cell : active) {
				List<Integer> neighbours = cell.getNeighbours();
				//System.out.println(neighbours);
				neighbours.stream().forEach(n -> g.touch(n));
			}
			//System.out.println(g);
			active = g.getActiveCells(active);
			System.out.println("num active: " +  active.size());
		}
	}

	private static class Grid {

		Predicate<Cell3D> activePredicate = c -> c.neighborCount == 2 || c.neighborCount == 3;

		Map<Integer, Cell3D> active = new HashMap<>();

		public Grid() {

		}

		public void touch(Integer xyz) {
			active.computeIfAbsent(xyz, p -> new Cell3D(p, 0)).increaseNeighbour();
		}

		public List<Cell3D> getActiveCells(List<Cell3D> lastActive) {
			Set<Integer> activeIds = lastActive.stream().map(c -> c.xyz).collect(toSet());
			return active.values().stream().filter(c -> {
				if (activeIds.contains(c.xyz)) {
					return c.neighborCount == 2 || c.neighborCount == 3; 
				} else {
					return c.neighborCount == 3;
				}
			}).collect(toList());
			
		}

		@Override
		public String toString() {
			return "Grid [active=" + active + "]";
		}

	}

	private static class Cell3D {

		static int SIZE = 1000;

		int xyz;
		int neighborCount;

		public Cell3D(int x, int y, int z) {
			xyz = toPos(x, y, z);
			neighborCount = 0;
		}

		public Cell3D(int xyz, int neighborCount) {
			super();
			this.xyz = xyz;
			this.neighborCount = neighborCount;
		}

		public void increaseNeighbour() {
			neighborCount++;
		}

		public List<Integer> getNeighbours() {
			int xy = xyz / SIZE;
			int z0 = xyz % SIZE;
			int y0 = xy % SIZE;
			int x0 = xy / SIZE;

			List<Integer> result = new ArrayList<>();
			for (int x = x0 - 1; x <= x0 + 1; x++) {
				for (int y = y0 - 1; y <= y0 + 1; y++) {
					for (int z = z0 - 1; z <= z0 + 1; z++) {
						if (!(x == x0 && y == y0 && z == z0)) {
							int neighbor = toPos(x, y, z);
							//System.out.println(neighbor);
							result.add(neighbor);
						}
					}
				}
			}
			return result;
		}

		private int toPos(int x, int y, int z) {
			return x * SIZE * SIZE + y * SIZE + z;
		}

		@Override
		public String toString() {
			return "Cell [xyz=" + xyz + ", neighborCount=" + neighborCount + "]";
		}

	}
	
	private static class Grid4D {

		Predicate<Cell4D> activePredicate = c -> c.neighborCount == 2 || c.neighborCount == 3;

		Map<Integer, Cell4D> active = new HashMap<>();

		public Grid4D() {

		}

		public void touch(Integer xyz) {
			active.computeIfAbsent(xyz, p -> new Cell4D(p, 0)).increaseNeighbour();
		}

		public List<Cell4D> getActiveCells(List<Cell4D> lastActive) {
			Set<Integer> activeIds = lastActive.stream().map(c -> c.xyzw).collect(toSet());
			return active.values().stream().filter(c -> {
				if (activeIds.contains(c.xyzw)) {
					return c.neighborCount == 2 || c.neighborCount == 3; 
				} else {
					return c.neighborCount == 3;
				}
			}).collect(toList());
			
		}

		@Override
		public String toString() {
			return "Grid [active=" + active + "]";
		}

	}

	
	private static class Cell4D {

		static int SIZE = 1000;

		int xyzw;
		int neighborCount;

		public Cell4D(int x, int y, int z, int w) {
			xyzw = toPos(x, y, z, w);
			neighborCount = 0;
		}

		public Cell4D(int xyz, int neighborCount) {
			super();
			this.xyzw = xyz;
			this.neighborCount = neighborCount;
		}

		public void increaseNeighbour() {
			neighborCount++;
		}

		public List<Integer> getNeighbours() {
			int xyz0 = xyzw / SIZE;
			int w0 = xyzw % SIZE;
			int xy0 = xyz0 / SIZE;
			int z0 = xyz0 % SIZE;
			int y0 = xy0 % SIZE;
			int x0 = xy0 / SIZE;

			List<Integer> result = new ArrayList<>();
			for (int x = x0 - 1; x <= x0 + 1; x++) {
				for (int y = y0 - 1; y <= y0 + 1; y++) {
					for (int z = z0 - 1; z <= z0 + 1; z++) {
						for (int w = w0 - 1; w <= w0 + 1; w++) {
							if (!(x == x0 && y == y0 && z == z0 && w == w0)) {
								int neighbor = toPos(x, y, z, w);
								//System.out.println(neighbor);
								result.add(neighbor);
							}
						}
					}
				}
			}
			return result;
		}

		private int toPos(int x, int y, int z, int w) {
			return x * SIZE * SIZE * SIZE + y * SIZE * SIZE + z* SIZE  + w;
		}

		@Override
		public String toString() {
			return "Cell [xyz=" + xyzw + ", neighborCount=" + neighborCount + "]";
		}

	}

}
