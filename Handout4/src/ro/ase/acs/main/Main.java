package ro.ase.acs.main;

import ro.ase.acs.classes.DataSeriesOperation;
import ro.ase.acs.classes.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Main {

	public static void main(String[] args) {
		//Playground
		//here you can test your code manually
		List<String> cards = Arrays.asList("3S", "3H", "3C", "7S", "3S");
		Stream<String> cards_getCardsByRank = Utils.getCardsByRank(cards, '3');
		cards_getCardsByRank.forEach(System.out::println);
		System.out.println();
		List<String> list = Arrays.asList("3S", "4H", "AC", "KS", "JS", "QD");
		List<String> result1 = Utils.getCardsLowerThanSorted(list, 'Q').collect(Collectors.toList());
		result1.forEach(System.out::println);
	}

}