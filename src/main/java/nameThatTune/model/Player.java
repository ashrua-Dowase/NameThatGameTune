package nameThatTune.model;

public class Player {
	int score = 0;
	String playerName;
	int position;
	boolean hasBuzzed;
	
	public int getPosition() {
		return position;
	}
	
	public synchronized boolean gethasBuzzed() {
		return this.hasBuzzed;
	}
	public synchronized void sethasBuzzed(boolean hasbuzzed) {
		this.hasBuzzed = hasbuzzed;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Player(String name,int pos) {
		this.playerName = name;
		this.position = pos;
		this.hasBuzzed = false;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public Integer getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	public void addScore(int score) {
		this.score+=score;
	}
}
