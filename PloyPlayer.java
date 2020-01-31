package de.tuberlin.sese.swtpp.gameserver.model.ploy;

public class PloyPlayer {

	private String name;

	private String type;

	private int score;

	public PloyPlayer(String name, String type) {
		this.name = name;
		this.type = type;
		this.score = 0;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
