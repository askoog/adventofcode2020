package se.askware.aoc2020.dec13;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import se.askware.aoc2020.common.AocBase;

public class Assignment extends AocBase {

	public static void main(String[] args) throws IOException {
		new Assignment().run();
	}

	@Override
	public void solvePartOne(List<String> input) {
		long wanted = Long.parseLong(input.get(0));
		int[] buses = Arrays.stream(input.get(1).split(",")).filter(p -> !p.equals("x")).mapToInt(Integer::parseInt)
				.toArray();

		int min = Integer.MAX_VALUE;
		int busNumber = 0;
		for (int i = 0; i < buses.length; i++) {
			if (wanted % buses[i] == 0) {
				System.out.println(buses[i]);
			}
			int times = (int) (wanted / buses[i]) + 1;
			int firstDeparture = times * buses[i];
			System.out.println(wanted + " " + firstDeparture);
			if (firstDeparture < min) {
				busNumber = buses[i];
				min = firstDeparture;
			}
		}
		System.out.println(min);
		System.out.println((min - wanted) * busNumber);
	}

	@Override
	public void solvePartTwo(List<String> input) {
		for (int i = 1; i < input.size(); i++) {
			String line = input.get(i);
			solveForLine(line);
		}
	}

	private void solveForLine(String line) {
		long[] buses = Arrays.stream(line.split(",")).map(p -> p.equals("x") ? "1" : p)
				.mapToLong(Long::parseLong).toArray();
		AtomicInteger counter = new AtomicInteger(0);
		
		// List of buses [departure][index]
		List<long[]> collect = Arrays.stream(buses).mapToObj(l -> new long[] { l, counter.getAndIncrement() })
				.filter(l -> l[0] != 1).sorted((l1, l2) -> Long.compare(l2[0], l1[0])).collect(toList());
		for (long[] ms : collect) {
			System.out.println(Arrays.toString(ms));
		}
		
		long[] firstBus = collect.stream().filter(l -> l[1] == 0).findFirst().orElse(new long[] {0L,0L});

		long reduce = collect.stream().mapToLong(l -> l[0]).reduce((l1,l2) -> l1*l2).orElse(0) / firstBus[0];
		System.out.println(reduce / firstBus[0]);
		
		long accumulateValue = 1;
		for (int i = 0; i < collect.size(); i++) {
			if (collect.get(i)[1] == firstBus[0]) {
				accumulateValue = collect.get(i)[0] ;
			}
		}
		System.out.println(accumulateValue);
		
		for (long m = 0; m < Long.MAX_VALUE; m+=accumulateValue) {
			long offset = firstBus[0] * (m-1);
			if (m % 1_000_000 == 0) {
				System.out.println(m + " "+ offset);
			}
			boolean abort = false;
			for (int i = 0; i <collect.size()  && !abort; i++) {
				long[] bus = collect.get(i);
				if ((offset + bus[1]) % bus[0] != 0) {
					abort = true;
				}
			}
			if (!abort) {
				System.out.println(firstBus[0]*(m-1));
				return;
			}
			
		}		 
	}

}
