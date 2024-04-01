package ro.ase.acs.classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
			DataInputStream dis = new DataInputStream(fis);
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
		
	}
	
	public static List<HandballMatch> deserialize(String filename) {
		return null;
	}
	
	public static void writeHeader(String filename) {
		
	}
	
	public static void writePoints(String filename, List<HandballMatch> matches) {
		
	}
	
	public static void writePointsAndGoals(String filename, List<HandballMatch> matches) {
		
	}
	
	public static void leagueTable(String filename, List<HandballMatch> matches) {
		
	}
	
	public static void specialLeagueTable(String filename, List<HandballMatch> matches) {
		
	}
}
