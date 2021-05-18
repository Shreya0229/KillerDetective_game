package AP_Asign3_mafia;

public class Mafia extends Player {

	
	public Mafia(int ID) {
		super(ID);
		this.setStatus("Mafia");
		this.setHP(2500);
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
