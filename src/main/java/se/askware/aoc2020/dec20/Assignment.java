package se.askware.aoc2020.dec20;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	Grid solutionPartOne;
	
	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		List<Tile> tiles = parseTiles(input);
		System.out.println(tiles.size());
		int size = (int) Math.sqrt(tiles.size());
		System.out.println(size);
		Grid grid = new Grid(size);
		
//		tiles.get(0).getAllVariants().stream().forEach(System.out::println);
		
		tiles.stream().filter(t -> t.id == 1951).findAny().get().getAllVariants().forEach(System.out::println);
		
		Queue<Grid> solveQueue = new ArrayDeque<>();
		solveQueue.add(grid);
		while(!solveQueue.isEmpty()) {
			Grid g = solveQueue.poll();
			int[] freePos = g.getFreePos();
			Tile t = g.getTile(0, 0);
			if (freePos == null) {
				g.print();
				long result = g.getTile(0, 0).id * g.getTile(size - 1, 0).id * g.getTile(0, size -1).id * g.getTile(size - 1, size -1).id;
				System.out.println(result);
				solutionPartOne = g;
				return;
			}
			for (Tile tile : tiles) {
				if (!g.isUsed(tile.id)) {
					for (Tile tile2 : tile.getAllVariants()) { // todo cache
						if (g.fits(freePos[0], freePos[1], tile2)) {
							Grid g2 = new Grid(g);
							g2.setTile(freePos[0], freePos[1], tile2);
							solveQueue.add(g2);
						}
					}					
				}
			}
		
		}
		
		
		System.out.println("Huh? No match");
	}

	private List<Tile> parseTiles(List<String> input) {
		List<Tile> tiles = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			int id = Integer.parseInt(input.get(i).replace("Tile ", "").replace(":", ""));
			Tile t = new Tile(id);
			for (int j = 0; j < 10; j++) {
				t.setLine(j, input.get(i+1+j));
			}
			i+=11;
			tiles.add(t);
		}
		return tiles;
	}

	@Override
	public void solvePartTwo(List<String> input) {
		Tile t = new Tile(0, 12*8);
		int line = 0;
		for (int i = 0; i < 12; i++) {
			for (int k = 1; k < 9; k++) {
				StringBuilder sb =new StringBuilder();
				for (int j = 0; j < 12; j++) {
					sb.append(solutionPartOne.tiles[j][i].getLine(k).substring(1,9));
				}
				System.out.println(sb.toString());
				t.setLine(line, sb.toString());
				line++;
			}
		}
		String monster = "                  # \n"
				       + "#    ##    ##    ###\n"
			       	   + " #  #  #  #  #  #   ";
		char[][] monsterChar = new char[3][];
		String[] split = monster.split("\n");
		for (int i = 0; i < split.length; i++) {
			monsterChar[i] = split[i].toCharArray();
		}

		Tile solution = null;
		for(Tile t2 : t.getAllVariants()) {
			for (int i = 0; i < t.chars.length - 2; i++) {
				for (int j = 0; j < t.chars.length - monsterChar[0].length; j++) {
					boolean found = true;
					int match = 0;
					for (int m = 0; m < monsterChar[0].length; m++) {
						if ((monsterChar[0][m] == ' ' || (monsterChar[0][m] == '#' && t2.chars[i][j+m] == '#')) 
							&& (monsterChar[1][m] == ' ' || (monsterChar[1][m] == '#' && t2.chars[i+1][j+m] == '#'))
							&& (monsterChar[2][m] == ' ' || (monsterChar[2][m] == '#' && t2.chars[i+2][j+m] == '#'))) {
							match++;
						} else {
							found = false;
							break;
						}
					}
					if (found) {
						solution = t2;
						System.out.println("found " + j);

						System.out.println(new String(t2.chars[i]));
						System.out.println(new String(t2.chars[i+1]));
						System.out.println(new String(t2.chars[i+2]));
						for (int m = 0; m < monsterChar[0].length; m++) {
							if (monsterChar[0][m] == '#') {
								t2.chars[i][j+m] = 'O';
							}
							if (monsterChar[1][m] == '#') {
								t2.chars[i+1][j+m] = 'O';
							}
							if (monsterChar[2][m] == '#') {
								t2.chars[i+2][j+m] = 'O';
							}
						}
						System.out.println(new String(t2.chars[i]));
						System.out.println(new String(t2.chars[i+1]));
						System.out.println(new String(t2.chars[i+2]));
					}
				}
			}
		}
		System.out.println(solution.toString());
		
		int sum = 0;
		for(char c : solution.toString().toCharArray()) {
			if (c == '#') {
				sum++;
			}
		}
		System.out.println(sum);
		System.out.println("done");
	}

	private static class Grid {
		public int size = 3;
		private final Tile[][] tiles;
		private final Grid parent;
		
		public Grid(int size) {
			this.parent = null;
			this.tiles = new Tile[size][size];
			this.size = size;
		}
		
		public int[] getFreePos() {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (tiles[i][j] == null) {
						return new int[] {i,j}; 
					}
				}
			}
			return null;
		}
		
		public Grid(Grid parent) {
			this.parent = parent;
			this.size = parent.size;
			this.tiles = new Tile[parent.size][parent.size];
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					tiles[i][j] = parent.tiles[i][j];
				}
			}
			if (size == 0) {
				System.out.println("??");
			}
		}

		public boolean isUsed(long tileId) {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (tiles[i][j] != null && tiles[i][j].id  == tileId) {
						return true;
					}
				}
			}
			return false;
		}

		public boolean fits(int x, int y, Tile t) {
			Tile left = getTile(x - 1, y);
			Tile above = getTile(x, y -1);
			
			boolean matchesLeft = left == null || matches(left.getRightColumn(), t.getLeftColumn());
			boolean matchesAbove = above == null || matches(above.getBottomLine(), t.getTopLine());
			
			return matchesLeft && matchesAbove;
		}

		public boolean matches(String s1, String s2) {
			for (int i = 0; i < s1.length(); i++) {
//				System.out.println(s1.charAt(i) +"="+ s2.charAt(i));
				if (s1.charAt(i) != s2.charAt(i)) {
					return false;
				}
			}
			return true;
		}
		
		public Tile getTile(int x, int y) {
			if (x < 0 || y < 0 || x > size || y > size) {
				return null;
			}
			return tiles[x][y];
		}
		
		public void setTile(int x, int y, Tile t) {
			tiles[x][y] = t;
		}
		
		public void print() {
			for (int i = 0; i < tiles.length; i++) {
				for (int line = 0; line < 10; line++) {
					for (int j = 0; j < tiles.length; j++) {
						System.out.print(tiles[j][i] != null ? tiles[j][i].getLine(line) : "0000000000");
						System.out.print(" ");
					}
					System.out.println("");
				}
				System.out.println("");
			}
		}

	}
	
	private static class Tile {
		long id;
		boolean rotated;
		boolean flipped;
		
		char[][] chars = new char[10][10];

		public Tile(int id) {
			this.id = id;
		}
		
		public Tile(int id, int size) {
			this.id = id;
			chars = new char[size][size];
		}

		public Tile(long id, boolean rotated, boolean flipped, char[][] chars) {
			super();
			this.id = id;
			this.rotated = rotated;
			this.flipped = flipped;
			this.chars = chars;
		}

		public String getLine(int line) {
			return new String(chars[line]);
		}
		
		public String getTopLine() {
			return getLine(0);
		}
		public String getBottomLine() {
			return getLine(chars.length -1);
		}

		public String getLeftColumn() {
			return getColumn(0);
		}
		public String getRightColumn() {
			return getColumn(chars.length -1);
		}

		public String getColumn(int column) {
			char[] result = new char[chars.length];
			for (int i = 0; i < chars.length; i++) {
				result[i] = chars[i][column];
			}
			return new String(result);
		}
		
		public void setLine(int line, String chars) {
			this.chars[line] = chars.toCharArray();
		}
		
		public List<Tile> getAllVariants(){
			
			List<Tile> allVariants = new ArrayList<>();
			Tile t = this;
			for (int i = 0; i < 4; i++) {
				allVariants.add(t);
				allVariants.add(t.flip());
				t = t.rotate();
			}
			
			return allVariants;
		}
		
		public Tile flip() {
			return new Tile(id, rotated, true, flip(chars));
		}
		
		public Tile rotate() {
			return new Tile(id, true, flipped, rotate(chars));
		}

		public char[][] flip(char[][] input){
			int size = input.length;
			char[][] result = new char[input.length][input[0].length];
			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < result.length; j++) {
					result[i][size - j - 1] = input[i][j];
				}
			}
			return result;
		}
		public char[][] rotate(char[][] input){
			int size = input.length;
			char[][] result = new char[size][size];
		    // Consider all squares one by one 
		    for (int x = 0; x < size / 2; x++) { 
		        // Consider elements in group 
		        // of 4 in current square 
		        for (int y = x; y < size - x - 1; y++) { 
		            // Store current cell in 
		            // temp variable 
		            char temp = input[x][y]; 
		  
		            // Move values from right to top 
		            result[x][y] = input[y][size - 1 - x]; 
		  
		            // Move values from bottom to right 
		            result[y][size - 1 - x] 
		                = input[size - 1 - x][size - 1 - y]; 
		  
		            // Move values from left to bottom 
		            result[size - 1 - x][size - 1 - y] 
		                = input[size - 1 - y][x]; 
		  
		            // Assign temp to left 
		            result[size - 1 - y][x] = temp; 
		        } 
		    } 
		    return result;
		} 
		  
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < chars[0].length; j++) {
				sb.append(getLine(j) + "\n");
			}
			return sb.toString();
		}
	}
}
