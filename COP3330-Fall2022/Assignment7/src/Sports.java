//Zachary Hull
//Assignment 8: interfaces
//COP 3330 0002
//11/16/2022

public interface Sports extends Comparable<Sports> {
	public void scoreLarge();
	public void scoreMed();
	public void scoreSmall();
	public int getScore();
	public int getId();
}
