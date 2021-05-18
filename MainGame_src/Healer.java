package AP_Asign3_mafia;

public class Healer extends Player{


	public Healer(int ID) {
		super(ID);
		this.setStatus( "Healer");
		this.setHP( 800);
	}

	@Override
	public int getID() {
		return super.getID();
	}
	
	@Override
	public boolean equals(Object o1) {
		if(getClass()==o1.getClass())
			return true;
		return false;
	}
}
