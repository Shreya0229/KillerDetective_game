package AP_Asign3_mafia;

import java.util.Comparator;

public abstract class Player implements Comparable<Player>{
	
	private int ID;
	private String status;
	private boolean alive;
	private float HP;
	private int voteReceived;
	public Player(int ID) {
		this.ID= ID;
		this.alive= true;
		this.voteReceived=0;
	}
	
	
	public int getID() {
		return this.ID;
	}
	
	@Override
	public abstract boolean equals(Object o1);
	
	public float getHP() {
		return HP;
	}
	
	public void setHP(float n) {
		this.HP= n;
	}
	
	public void decreaseHP(float x) {
		this.HP-=x;
	}
	
	public void increaseHP(float x) {
		this.HP+=x;
	}
	
	
	@Override
	public int compareTo(Player p2) {
		if(voteReceived >p2.voteReceived) return -1;
		else if(voteReceived< p2.voteReceived) return 1;
		else return 0;
	}
	
	public boolean getAlive() {
		return this.alive;
	}
	
	public void addVoteByOne() {
		this.voteReceived++;
	}
	
	public void setVotingZero() {
		this.voteReceived=0;
	}
	
	public void setStatus(String stat) {
		this.status=stat;
	}
}
