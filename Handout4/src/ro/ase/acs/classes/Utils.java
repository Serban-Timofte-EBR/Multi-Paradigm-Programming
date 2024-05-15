package ro.ase.acs.classes;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Utils {
	public static DataSeriesOperation anonymousOperation = new DataSeriesOperation() {
		@Override
		public Double doOperation(Integer[] values) {
			double prod = 1;
			for (Integer val : values) {
				prod *= val;
			}
			return prod;
		}
	};
	
	public static DataSeriesOperation lambdaOperation = values -> {
		int counter = 0;
		int sum = 0;
		for(int val : values) {
			sum += val;
			counter ++;
		}
		return sum / (double) counter;
	};
	
	public static Integer[] input = new Integer[] { 2, 5, 7 };
	
	public static Double result = null;
	
	public static Runnable runnable = null;
	
	public static Callable<Double> callable = null;
	
	public static Stream<String> getCardsBySuit(List<String> cards, char suit) {
		return cards.stream().filter(card -> card.charAt(1) == suit);
	}
	
	public static Stream<String> getCardsByRank(List<String> cards, char rank) {
		return cards.stream().filter(card -> card.charAt(0) == rank).sorted().distinct();
	}
	
	public static Stream<String> getCardsLowerThan(List<String> cards, char rank) {
		String ranks = "23456789TJQKA";
		return cards.stream().filter(card -> ranks.indexOf(card.charAt(0)) < ranks.indexOf(rank));
	}

	public static Stream<String> getCardsLowerThanSorted(List<String> cards, char rank) {
		String ranks = "23456789TJQKA";
		String suits = "SDHC";

		return cards.stream()
				.filter(card -> ranks.indexOf(card.charAt(0)) < ranks.indexOf(rank))
				.sorted((c1, c2) -> {
					int valueComp = Integer.compare(ranks.indexOf(c1.charAt(0)), ranks.indexOf(c2.charAt(0)));
					if(valueComp != 0) {
						return valueComp;
					} else {
						return Integer.compare(suits.indexOf(c1.charAt(1)), suits.indexOf(c2.charAt(1)));
					}
				});
	}
	
	public static String printDeckRanks(Stream<String> cards) {
		String ranks = "23456789TJQKA";

		return cards.map(card -> ranks.indexOf(card.charAt(0)) + 1)
				.sorted()
				.map(String::valueOf)
				.collect(Collectors.joining(", "));
	}

	static {
		runnable = new Runnable() {
			@Override
			public void run() {
				result = anonymousOperation.doOperation(input);
			}
		};

		callable = new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				return lambdaOperation.doOperation(input);
			}
		};
	}


}
