package AP_Asign3_mafia;

public class Detective extends Player{


	public Detective(int ID) {
		super(ID);
		this.setStatus( "Detective");
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
