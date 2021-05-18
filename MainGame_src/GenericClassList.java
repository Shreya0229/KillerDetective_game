package AP_Asign3_mafia;
import java.util.*;

public class GenericClassList <T>{

	private ArrayList<T> _list;
	public GenericClassList() {
		_list= new ArrayList<T>();
	}

	public T get(int i) {
		return _list.get(i);
	}
	public void add(T obj) {
		_list.add(obj);
	}
	
	public void displayList() {
		for(int i=0; i<_list.size(); i++) {
			Player _player= (Player)_list.get(i);
			
			if(i==_list.size()-1)
			System.out.print("Player"+_player.getID()+" ");
			
			else {
				if(_list.size()==2)
				System.out.print("Player"+_player.getID()+" and ");
				else System.out.print("Player"+_player.getID()+", ");
			}
		}
	}
	
	public int getsize() {
		return _list.size();
	}
}
