package se.askware.aoc2020.dec24;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		Grid grid = new Grid(100);
		for (String line : input) {
			List<Direction> parseLine = parseLine(line);
			grid.navigate(parseLine);			
		}
		System.out.println(grid.numBlackTiles());

	}

	@Override
	public void solvePartTwo(List<String> input) {
		Grid grid = new Grid(200);
		for (String line : input) {
			List<Direction> parseLine = parseLine(line);
			grid.navigate(parseLine);			
		}
		for (int i = 0; i < 100; i++) {
			grid = grid.flipAdjacent();
		}
		System.out.println(grid.numBlackTiles());
	}
	
	
	public List<Direction> parseLine(String line){
		List<Direction> result = new ArrayList<>();
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == 'e') {
				result.add(Direction.EAST);
			} else if (line.charAt(i) == 'w') {
				result.add(Direction.WEST);
			} else if (line.charAt(i) == 'n') {
				if (line.charAt(++i) == 'e') {
					result.add(Direction.NE);
				} else {
					result.add(Direction.NW);
				}
			} else if (line.charAt(i) == 's') {
				if (line.charAt(++i) == 'e') {
					result.add(Direction.SE);
				} else {
					result.add(Direction.SW);
				}
			} else {
				throw new RuntimeException();
			}
		}
		return result;
	}

	public static class Grid {
		int size = 100;
		Color[][] grid = new Color[size][size];
		int x = size / 2;
		int y = size / 2;

		public Grid(int size) {
			this.size = size;
			grid = new Color[size][size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					grid [i][j] = Color.WHITE;
				}
			}
			x = size / 2;
			y = size / 2;
			
		}
		
		public Grid flipAdjacent() {
			Grid g = new Grid(size);
			for (int y0 = 0; y0 < size; y0++) {
				for (int x0 = 0; x0 < size; x0++) {
					int numNeighboringBlackTiles = numNeighboringBlackTiles(y0, x0);
					if (grid[y0][x0] == Color.BLACK && (numNeighboringBlackTiles == 0 || numNeighboringBlackTiles > 2)) {
						g.grid[y0][x0] = Color.WHITE;
					} else if (grid[y0][x0] == Color.WHITE && numNeighboringBlackTiles == 2) {
						g.grid[y0][x0] = Color.BLACK;
					} else {
						g.grid[y0][x0] = grid[y0][x0];
					}
				}
			}
			return g;
		}
		
		public int numNeighboringBlackTiles(int y1, int x1) {
			int result = 0;
			int xold = x;
			int yold = y;
			for(Direction d : Direction.values()) {
				x = x1;
				y = y1;
				navigate(d);
				if (x >= 0 && x < size && y >= 0 && y <size) {
					if(grid[y][x] == Color.BLACK) {
						result++;
					}
				}			
			}
			x = xold;
			y = yold;
			return result;			
		}
		
		public int numBlackTiles() {
			int result = 0;
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (grid[i][j] == Color.BLACK) {
						result++;
					}
				}
			}
			return result;			
		}

		public void navigate(List<Direction> parseLine) {
			x = size/2;
			y = size/2;
			for (Direction direction : parseLine) {
				navigate(direction);
			}
			flip();
		}
		public void navigatePrint(List<Direction> parseLine) {
			x = size/2;
			y = size/2;
			for (Direction direction : parseLine) {
				navigatePrint(direction);
			}
			flip();
			System.out.println(x + " " + y + " " + grid[y][x]);
		}

		private void flip() {
			grid[y][x] = grid[y][x].flip();
		}

		public void navigatePrint(Direction dir) {
			System.out.print(x + " " + y + " " + dir);
			navigate(dir);
			System.out.println(" -> " + x + " " + y);

		}
		public void navigate(Direction dir) {
			boolean isOddRow = y % 2 == 1;
			boolean isEvenRow = y % 2 == 0;
			switch (dir) {
				case EAST:
					x++;
					return;
				case WEST:
					x--;
					return;
				case NW:
					if (isOddRow) {
						x--;
					}
					y--;
					return;
				case NE:
					if (isEvenRow) {
						x++;
					}
					y--;
					return;
				case SW:
					if (isOddRow) {
						x--;
					}
					y++;
					return;
				case SE:
					if (isEvenRow) {
						x++;
					}
					y++;
					return;
			}
		}
	}

	public enum Color {
		WHITE, BLACK;

		public Color flip() {
			return this == BLACK ? WHITE : BLACK;
		}
	}

	public enum Direction {
		EAST, SE, SW, WEST, NW, NE
	}

}
