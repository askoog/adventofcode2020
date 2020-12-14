package se.askware.aoc2020.dec12;

import java.io.IOException;
import java.util.List;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	enum Direction {
		NORTH, EAST, SOUTH, WEST;
	
		Direction turnRight(int degrees) {
			Direction next = Direction.values()[(this.ordinal() + 1) % Direction.values().length];
			if (degrees > 90) {
				return next.turnRight(degrees-90);
			} else {
				return next;
			}
		}

		Direction turnLeft(int degrees) {
			Direction next = Direction.values()[(this.ordinal() - 1 + Direction.values().length) % Direction.values().length];
			if (degrees > 90) {
				return next.turnLeft(degrees-90);
			} else {
				return next;
			}
		}
}

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {

		//testDirections();
		int x = 0;
		int y = 0;
		Direction dir = Direction.EAST;
		for (String string : input) {
			char action = string.charAt(0);
			int num = Integer.parseInt(string.substring(1));

			if (action == 'F') {
				if (dir == Direction.NORTH) {
					y+= num;
				}
				if (dir == Direction.EAST) {
					x+= num;
				}
				if (dir == Direction.SOUTH) {
					y-= num;
				}
				if (dir == Direction.WEST) {
					x-= num;
				}
			}
			if (action == 'N') {
				y+=num;
			}
			if (action == 'S') {
				y-=num;
			}
			if (action == 'E') {
				x+=num;
			}
			if (action == 'W') {
				x-=num;
			}
			if (action == 'L') {
				dir = dir.turnLeft(num);
			}
			if (action == 'R') {
				dir = dir.turnRight(num);
			}
			System.out.println(string + " -> " + x + "," + y);

		}

		System.out.println(x + "," + y + " = " + (Math.abs(x)+ Math.abs(y)));
		
	}

	private void testDirections() {
		for(Direction dir : Direction.values()) {
			System.out.println(dir + " R90 = " + dir.turnRight(90));
			System.out.println(dir + " R180 = " + dir.turnRight(180));
			System.out.println(dir + " R270 = " + dir.turnRight(270));
		}

		for(Direction dir : Direction.values()) {
			System.out.println(dir + " L90 = " + dir.turnLeft(90));
			System.out.println(dir + " L180 = " + dir.turnLeft(180));
			System.out.println(dir + " L270 = " + dir.turnLeft(270));
		}
	}

	@Override
	public void solvePartTwo(List<String> input) {
		int x = 0;
		int y = 0;
		int waypointX = 10;
		int waypointY = 1;
		Direction dir = Direction.EAST;
		for (String string : input) {
			char action = string.charAt(0);
			int num = Integer.parseInt(string.substring(1));

			if (action == 'F') {
				x = x + (waypointX * num);
				y = y + (waypointY * num);
				
			}
			if (action == 'N') {
				waypointY+=num;
			}
			if (action == 'S') {
				waypointY-=num;
			}
			if (action == 'E') {
				waypointX+=num;
			}
			if (action == 'W') {
				waypointX-=num;
			}
			if (action == 'L') {
				while(num>0) {
					int tmpy = waypointX;
					waypointX = -waypointY;
					waypointY = tmpy;
					num-=90;
				}
			}
			if (action == 'R') {
				while(num>0) {
					int tmpy = -waypointX;
					waypointX = waypointY;
					waypointY = tmpy;
					num-=90;
				}
			}
			System.out.println(string + " -> " + x + "," + y + " " + waypointX + "," + waypointY);

		}

		System.out.println(x + "," + y + " = " + (Math.abs(x)+ Math.abs(y)));
	}

}
