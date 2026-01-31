//Zachary Hull
//Assignment 8: interfaces
//COP 3330 0002
//11/16/2022

public class Basketball implements Sports{
	
	private int score;
	private int id;
	
	public Basketball(int score, int id) {
		this.score = score;
		this.id = id;
	}

	@Override
	public int compareTo(Sports o) {
		// TODO Auto-generated method stub
		return o.getScore() - this.score;
	}

	@Override
	public void scoreLarge() {
		this.score += 3;
		
	}

	@Override
	public void scoreMed() {
		this.score += 2;
		
	}

	@Override
	public void scoreSmall() {
		this.score += 1;
		
	}

	@Override
	public int getScore() {
		return this.score;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return this.id;
	}


}
