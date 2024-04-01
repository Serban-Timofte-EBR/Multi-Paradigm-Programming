package ro.ase.acs.main;

import ro.ase.acs.classes.HandballMatch;
import ro.ase.acs.classes.Utils;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		//Playground
		//here you can test your code manually
		List<HandballMatch> list1 = new ArrayList<>();
		List<HandballMatch> list2 = new ArrayList<>();

		HandballMatch match1 = new HandballMatch();
		HandballMatch m1 = new HandballMatch();
		m1.setHomeTeam("RM VALCEA");
		m1.setAwayTeam("BUCURESTI");
		m1.setGoalsHomeTeam(25);
		m1.setGoalsAwayTeam(28);
		list1.add(m1);
		HandballMatch m2 = new HandballMatch();
		m2.setHomeTeam("BAIA MARE");
		m2.setAwayTeam("BUZAU");
		m2.setGoalsHomeTeam(33);
		m2.setGoalsAwayTeam(32);
		list1.add(m2);
		HandballMatch m3 = new HandballMatch();
		m3.setHomeTeam("BRAILA");
		m3.setAwayTeam("ZALAU");
		m3.setGoalsHomeTeam(27);
		m3.setGoalsAwayTeam(27);
		list1.add(m3);
		HandballMatch m4 = new HandballMatch();
		m4.setHomeTeam("RAPID");
		m4.setAwayTeam("BUCURESTI");
		m4.setGoalsHomeTeam(21);
		m4.setGoalsAwayTeam(21);
		list2.add(m4);
		HandballMatch m5 = new HandballMatch();
		m5.setHomeTeam("CRAIOVA");
		m5.setAwayTeam("CLUJ");
		m5.setGoalsHomeTeam(25);
		m5.setGoalsAwayTeam(20);
		list2.add(m5);
		HandballMatch m6 = new HandballMatch();
		m6.setHomeTeam("CLUJ");
		m6.setAwayTeam("RAPID");
		m6.setGoalsHomeTeam(21);
		m6.setGoalsAwayTeam(22);
		list2.add(m6);
		HandballMatch m7 = new HandballMatch();
		m7.setHomeTeam("CRAIOVA");
		m7.setAwayTeam("BUCURESTI");
		m7.setGoalsHomeTeam(21);
		m7.setGoalsAwayTeam(21);
		list2.add(m7);
		HandballMatch m8 = new HandballMatch();
		m8.setHomeTeam("CRAIOVA");
		m8.setAwayTeam("RAPID");
		m8.setGoalsHomeTeam(20);
		m8.setGoalsAwayTeam(20);
		list2.add(m8);
		HandballMatch m9 = new HandballMatch();
		m9.setHomeTeam("BUCURESTI");
		m9.setAwayTeam("CLUJ");
		m9.setGoalsHomeTeam(26);
		m9.setGoalsAwayTeam(21);
		list2.add(m9);

		//Requirement 1
		Utils.matchDayReport(list1, "matches.txt");

		//Requirement 2
		List<HandballMatch> matchesReadFromCSV = new ArrayList<>();
		matchesReadFromCSV = Utils.readFromCSV("matches.csv");
		for(HandballMatch match : matchesReadFromCSV) {
			System.out.print(match.getHomeTeam() + ", " + match.getAwayTeam() + ", " +
					match.getGoalsHomeTeam() + ", " + match.getGoalsAwayTeam() + System.lineSeparator());
		}
	}

}
