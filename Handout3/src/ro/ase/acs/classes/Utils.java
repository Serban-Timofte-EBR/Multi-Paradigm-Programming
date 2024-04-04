package ro.ase.acs.classes;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
	
	public static void matchDayReport(List<HandballMatch> matches, String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);

			for(HandballMatch match : matches) {
				bw.write(match.getHomeTeam());
				bw.write(" " + match.getGoalsHomeTeam() + " - ");
				bw.write(match.getGoalsAwayTeam() + " ");
				bw.write(match.getAwayTeam());
				bw.write(System.lineSeparator());
			}
			bw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static List<HandballMatch> readFromCSV(String filename) {
		List<HandballMatch> matches = new ArrayList<>();
		if(filename.length() > 3) {
			try(FileInputStream fos = new FileInputStream(filename);
				InputStreamReader isr = new InputStreamReader(fos);
				BufferedReader br = new BufferedReader(isr)
					) {
				String line = br.readLine();
				while ((line = br.readLine()) != null) {
					String[] values = line.split(",");
					HandballMatch matchRead = new HandballMatch();
					matchRead.setHomeTeam(values[0].trim());
					matchRead.setAwayTeam(values[1].trim());
					matchRead.setGoalsHomeTeam(Integer.parseInt(values[2].trim()));
					matchRead.setGoalsAwayTeam(Integer.parseInt(values[3].trim()));
					matches.add(matchRead);
				}
				return matches;
			} catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
		else {
			return matches;
		}
	}
	
	public static int secretInfo(String filename) {
		try(FileInputStream fis = new FileInputStream(filename);
			DataInputStream dis = new DataInputStream(fis)
		) {
			long skiped = dis.skip(12);
			return dis.readInt();
		} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static void serialize(List<HandballMatch> matches, String filename) {
		try(FileOutputStream fos = new FileOutputStream(filename);
			DataOutputStream dos = new DataOutputStream(fos) ) {
			dos.writeInt(matches.size());
			for(HandballMatch match : matches) {
				dos.writeUTF(match.getHomeTeam());
				dos.writeInt(match.getGoalsHomeTeam());
				dos.writeInt(match.getGoalsAwayTeam());
				dos.writeUTF(match.getAwayTeam());
			}
		} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static List<HandballMatch> deserialize(String filename) {
		List<HandballMatch> matches = new ArrayList<>();
		try(FileInputStream fis = new FileInputStream(filename);
			DataInputStream dis = new DataInputStream(fis)) {
			int nr_matches = dis.readInt();
			for(int i=0; i< nr_matches; i++) {
				HandballMatch match = new HandballMatch();
				match.setHomeTeam(dis.readUTF());
				match.setGoalsHomeTeam(dis.readInt());
				match.setGoalsAwayTeam(dis.readInt());
				match.setAwayTeam(dis.readUTF());
				matches.add(match);
			}
		} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		return matches;
    }
	
	public static void writeHeader(String filename) {
		try(FileOutputStream fos = new FileOutputStream(filename);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw) ) {
			bw.write("NO, TEAM, PTS, GF, GA, GD");
			bw.write(System.lineSeparator());
		} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static void writePoints(String filename, List<HandballMatch> matches) {
		Map<String, Integer> centralizator_puncte = new HashMap<String, Integer>();

		for(HandballMatch match : matches) {
			centralizator_puncte.putIfAbsent(match.getHomeTeam(), 0);
			centralizator_puncte.putIfAbsent(match.getAwayTeam(), 0);

			//gazdele castiga
			if(match.getGoalsHomeTeam() > match.getGoalsAwayTeam()) {
				centralizator_puncte.put(match.getHomeTeam(), centralizator_puncte.get(match.getHomeTeam()) + 3);
			} else if (match.getGoalsHomeTeam() == match.getGoalsAwayTeam()) {
				// egal
				centralizator_puncte.put(match.getHomeTeam(), centralizator_puncte.get(match.getHomeTeam()) + 1);
				centralizator_puncte.put(match.getAwayTeam(), centralizator_puncte.get(match.getAwayTeam()) + 1);
			}
			else {
				//gazdele pierd
				centralizator_puncte.put(match.getAwayTeam(), centralizator_puncte.get(match.getAwayTeam()) + 3);
			}
		}

		List<Map.Entry<String, Integer>> sortedEntries = centralizator_puncte.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.collect(Collectors.toList());

		try(FileOutputStream fos = new FileOutputStream(filename, true);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw)
		) {
			int position = 1;
			for(Map.Entry<String, Integer> team : centralizator_puncte.entrySet()) {
				bw.write(position + ", " + team.getKey() + ", " + team.getValue() + System.lineSeparator());
				position++;
			}
		} catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

	public static void writePointsAndGoals(String filename, List<HandballMatch> matches) {
		Map<String, int[]> teamStats = new HashMap<>();

		for (HandballMatch match : matches) {
			teamStats.putIfAbsent(match.getHomeTeam(), new int[4]); // [Puncte, GF, GA, GD]
			teamStats.putIfAbsent(match.getAwayTeam(), new int[4]);

			int[] homeStats = teamStats.get(match.getHomeTeam());
			int[] awayStats = teamStats.get(match.getAwayTeam());

			homeStats[1] += match.getGoalsHomeTeam();
			homeStats[2] += match.getGoalsAwayTeam();
			awayStats[1] += match.getGoalsAwayTeam();
			awayStats[2] += match.getGoalsHomeTeam();

			if (match.getGoalsHomeTeam() > match.getGoalsAwayTeam()) {
				homeStats[0] += 3;
			} else if (match.getGoalsHomeTeam() < match.getGoalsAwayTeam()) {
				awayStats[0] += 3;
			} else {
				homeStats[0] += 1;
				awayStats[0] += 1;
			}

			homeStats[3] = homeStats[1] - homeStats[2];
			awayStats[3] = awayStats[1] - awayStats[2];
		}

		List<Map.Entry<String, int[]>> sortedTeams = new ArrayList<>(teamStats.entrySet());
		sortedTeams.sort((e1, e2) -> {
			int comparePoints = Integer.compare(e2.getValue()[0], e1.getValue()[0]);
			if (comparePoints == 0) {
				return Integer.compare(e2.getValue()[3], e1.getValue()[3]);
			}
			return comparePoints;
		});


		try (FileWriter writer = new FileWriter(filename)) {
			writer.write("TEAM, PTS, GF, GA, GD\n");
			for (Map.Entry<String, int[]> entry : sortedTeams) {
				int[] stats = entry.getValue();
				writer.write(String.format("%s, %d, %d, %d, %d\n", entry.getKey(), stats[0], stats[1], stats[2], stats[3]));
			}
		} catch (IOException e) {
			throw new RuntimeException("Eroare la scrierea în fișier: " + e.getMessage(), e);
		}
	}

	public static void leagueTable(String filename, List<HandballMatch> matches) {
		Map<String, int[]> teamStats = new HashMap<>();

		for (HandballMatch match : matches) {
			teamStats.putIfAbsent(match.getHomeTeam(), new int[4]); // [Puncte, GF, GA, GD]
			teamStats.putIfAbsent(match.getAwayTeam(), new int[4]);

			int[] homeStats = teamStats.get(match.getHomeTeam());
			int[] awayStats = teamStats.get(match.getAwayTeam());

			homeStats[1] += match.getGoalsHomeTeam();
			homeStats[2] += match.getGoalsAwayTeam();
			awayStats[1] += match.getGoalsAwayTeam();
			awayStats[2] += match.getGoalsHomeTeam();

			if (match.getGoalsHomeTeam() > match.getGoalsAwayTeam()) {
				homeStats[0] += 3;
			} else if (match.getGoalsHomeTeam() < match.getGoalsAwayTeam()) {
				awayStats[0] += 3;
			} else {
				homeStats[0] += 1;
				awayStats[0] += 1;
			}

			homeStats[3] = homeStats[1] - homeStats[2];
			awayStats[3] = awayStats[1] - awayStats[2];
		}

		List<Map.Entry<String, int[]>> sortedTeams = new ArrayList<>(teamStats.entrySet());
		sortedTeams.sort((e1, e2) -> {
			int comparePoints = Integer.compare(e2.getValue()[0], e1.getValue()[0]);
			if (comparePoints == 0) {
				return Integer.compare(e2.getValue()[3], e1.getValue()[3]);
			}

			return comparePoints;
		});


		try (FileWriter writer = new FileWriter(filename)) {
			writer.write("TEAM, PTS, GF, GA, GD\n");
			for (Map.Entry<String, int[]> entry : sortedTeams) {
				int[] stats = entry.getValue();
				writer.write(String.format("%s, %d, %d, %d, %d\n", entry.getKey(), stats[0], stats[1], stats[2], stats[3]));
			}
		} catch (IOException e) {
			throw new RuntimeException("Eroare la scrierea în fișier: " + e.getMessage(), e);
		}
	}
	
	public static void specialLeagueTable(String filename, List<HandballMatch> matches) {
		
	}
}
