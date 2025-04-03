package nameThatTune.model;

import java.util.List;

public class Player {
	int score = 0;
	int choice = -1;
	String playerName;
	int position;
	boolean hasBuzzed;
	String selectedCategory;
	
	public int getChoice() {
		return choice;
	}

	public synchronized void setChoice(int choice) {
		this.choice = choice;
	}

	public boolean getHasBuzzed() {
		return hasBuzzed;
	}

	public void setHasBuzzed(boolean hasBuzzed) {
		this.hasBuzzed = hasBuzzed;
	}

	
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

	public synchronized String getSelectedCategory() {
		return selectedCategory;
	}

	public synchronized void setSelectedCategory(String selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
}
