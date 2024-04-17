package ro.ase.acs.classes;

public class HandballMatch {
	private String homeTeam;
	private String awayTeam;
	private int goalsHomeTeam;
	private int goalsAwayTeam;
	
	public String getHomeTeam() {
		return homeTeam;
	}
	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}
	public String getAwayTeam() {
		return awayTeam;
	}
	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}
	public int getGoalsHomeTeam() {
		return goalsHomeTeam;
	}
	public void setGoalsHomeTeam(int goalsHomeTeam) {
		this.goalsHomeTeam = goalsHomeTeam;
	}
	public int getGoalsAwayTeam() {
		return goalsAwayTeam;
	}
	public void setGoalsAwayTeam(int goalsAwayTeam) {
		this.goalsAwayTeam = goalsAwayTeam;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((awayTeam == null) ? 0 : awayTeam.hashCode());
		result = prime * result + goalsAwayTeam;
		result = prime * result + goalsHomeTeam;
		result = prime * result + ((homeTeam == null) ? 0 : homeTeam.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HandballMatch other = (HandballMatch) obj;
		if (awayTeam == null) {
			if (other.awayTeam != null)
				return false;
		} else if (!awayTeam.equals(other.awayTeam))
			return false;
		if (goalsAwayTeam != other.goalsAwayTeam)
			return false;
		if (goalsHomeTeam != other.goalsHomeTeam)
			return false;
		if (homeTeam == null) {
			if (other.homeTeam != null)
				return false;
		} else if (!homeTeam.equals(other.homeTeam))
			return false;
		return true;
	}
}
