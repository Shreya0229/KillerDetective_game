package AP_Asign3_mafia;

public class Commoner extends Player{

	public Commoner(int ID) {
		super(ID);
		this.setStatus( "Commoner");
		this.setHP( 1000);
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
