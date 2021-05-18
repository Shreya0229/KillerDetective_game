package AP_Asign3_mafia;
import java.util.*;

public class Mainn {


	static int round_num=1;
	static int player_ID=0;
	static int player_num=0;
	
	static HashMap<Integer, Player> player_list= new HashMap<>();
	static int player_num_exludingOut= player_num;
	static int mafia_num=0;
	static int detective_num=0;
	static int healer_num=0;
	static int commoner_num=0;
	
	static GenericClassList<Mafia> mafia_list= new GenericClassList<>();
	static GenericClassList<Detective> detective_list= new GenericClassList<>();
	static GenericClassList<Healer> healer_list= new GenericClassList<>();
	static GenericClassList<Commoner> commoner_list= new GenericClassList<>();
	
	
	public static void main(String[] args) {
		Scanner sc= new Scanner(System.in);
		
		
		System.out.println("Welcome to Mafia");
		while(player_num<6) {
		
		boolean done= false;
		while(done==false) {
		System.out.println("Enter Number of players: ");
		try {
			Scanner scc= new Scanner(System.in);
			player_num= scc.nextInt();
			done =true;
			}
		catch(InputMismatchException e) {
			System.out.println("Wrong input format.");
			}
		}
		
		player_num_exludingOut= player_num;
		
		if(player_num<6) 
			System.out.println("Players should be atleast six!");
		}
		
		
		
		System.out.println("Choose a Character: ");
		System.out.println("1) Mafia\n2) Detective \n3) Healer\n4) Commoner\n5) Assign Randomly");
		
		int chose= sc.nextInt();
		if(chose==1) {			//Mafia
			player_list.put(1,new Mafia(1));
			mafia_list.add(new Mafia(1));
			mafia_num++;
		}
		else if(chose==2) {		//detective
			player_list.put(1,new Detective(1));
			detective_list.add(new Detective(1));
			detective_num++;
		}
		else if(chose==3) {		//healer
			player_list.put(1,new Healer(1));
			healer_list.add(new Healer(1));
			healer_num++;
		}
		else if(chose==4) {		//commoner
			player_list.put(1,new Commoner(1));
			commoner_list.add(new Commoner(1));
			commoner_num++;
		}
		else {					//randomly
			RandomNumToPlayer(player_num);
			
		}
		System.out.println("You are Player"+player_list.get(1).getID());
		System.out.println("You are a "+ player_list.get(1).getClass().getSimpleName());
		
		
		
		assignPlayers(player_num);
		
		
		//other detectives are: [player 6]
		int flag2=0;
		System.out.print("Other "+ player_list.get(1).getClass().getSimpleName() +" are: ");
		for(int i=2; i<=player_num; i++) {
			if(player_list.get(1).getClass()==player_list.get(i).getClass()) {
				System.out.print(" Player"+player_list.get(i).getID());
				flag2=1;
			}
		}if(flag2==0)System.out.print(" []");
		System.out.println();
		
		int mafia_target;
		int detective_target;
		int healer_target;
		
		while(check_round()) {
			System.out.println("Round "+round_num+":");
			
			
			displayRemainingPlayers();
			
			//if user is Mafia
			if(player_list.containsKey(1) &&player_list.get(1).equals(new Mafia(0))) {
				mafia_target= TargetByUserPlayer(sc);
			}
			else mafia_target=TargetByPlayer(new Mafia(0));
			
			
			//if user is detective
			if(player_list.containsKey(1) &&player_list.get(1).equals(new Detective(0))) 
				detective_target= TargetByUserPlayer(sc);
			else {
				detective_target=TargetByPlayer(new Detective(0));
			}if(detective_target==1);
			
			
			//if user is healer
			if(player_list.containsKey(1) && player_list.get(1).equals(new Healer(0)))
				healer_target= TargetByUserPlayer(sc);
			else healer_target = TargetByPlayer(new Healer(0));
			
			
			//if user is commoner
			
			
			System.out.println("--End of Actions--");
			
			
			//player 4 has died
			float mafiaHPSum= 0;
			for(Player _player: player_list.values()) {
				if(_player.equals(new Mafia(0)))
					mafiaHPSum+=_player.getHP();
			}
			
			
			//is healer present
			boolean healerExists=false;
			for(Player _player: player_list.values()) {
			if(_player.equals(new Healer(0))){ 
				healerExists=true;
				break;
				}
			}
			
			
			int dieOrNot=setMafiaHPOut(player_list.get(mafia_target), mafiaHPSum);
			if(dieOrNot==-1 || (healer_target==mafia_target && healerExists))
				System.out.println("No one died.");
			else {
				System.out.println("Player "+dieOrNot+ " died.");
				
				
				//check if Mafia detective or who was player that died
				updatePlayerNumAlive(mafia_target);
				player_list.remove(mafia_target);
				player_num_exludingOut-=1;
			}
			
			//healer has increased HP
			
			if(healerExists) 
				player_list.get(healer_target).increaseHP(500);
				
			
			
			
			
			
			//Voting process
			ArrayList<Player> voting_list= new ArrayList<>();
			for(Player _player: player_list.values())
				voting_list.add(_player);
			
			
			int flag=1;
			while(flag==1) {
			for(Player _player :player_list.values()) {
				if(_player.getID()==1) {
					System.out.println("Select a person to vote out: ");
					int v= sc.nextInt();
					
					while(player_list.containsKey(v)==false) {
						System.out.println("Select alive player only!");
						System.out.println("Select a person to vote out: ");
						v=sc.nextInt();
					}
					player_list.get(v).addVoteByOne();
					continue;
				}
				int a= GeneratePlayerVoteOut();
				player_list.get(a).addVoteByOne();
			}
			
			
			Collections.sort(voting_list);
			if(voting_list.get(0).compareTo(voting_list.get(1))!=0) {
				flag=0;
				updatePlayerNumAlive(voting_list.get(0).getID());
				player_list.remove(voting_list.get(0).getID());
				player_num_exludingOut-=1;
				}
			else {
				//clear list and vote=0
				if(player_list.containsKey(1))
				System.out.println("It was a tie. Voting again.");
				voting_list.clear();
				for(Player _player: player_list.values()) {
					_player.setVotingZero();
					voting_list.add(_player);
				}
			}
			
			}
			
			System.out.println("Player"+voting_list.get(0).getID()+" has been voted out.");
			
			
			//clear vote in list for next round
			voting_list.clear();
			for(Player _player: player_list.values()) {
				_player.setVotingZero();
			}
			
			
			
			System.out.println("--End of round "+ round_num+++"--");
			System.out.println();
			
			
			
		}
		
		
		
		
		System.out.println("Game Over.");
		
		if(mafia_num== (player_num_exludingOut-mafia_num))
			System.out.println("The Mafia have won.");
		else System.out.println("The Mafia have lost.");
		
		
		mafia_list.displayList();
		if(mafia_list.getsize()!=1)
		System.out.print("were Mafias.\n");
		else System.out.print("was Mafia.\n");
		
		detective_list.displayList();
		if(detective_list.getsize()!=1)
		System.out.print("were Detectives.\n");
		else System.out.print("was Detective.\n");
		
		healer_list.displayList();
		if(healer_list.getsize()!=1)
		System.out.print("were Healers.\n");
		else System.out.print("was Healer.\n");
		
		commoner_list.displayList();
		if(commoner_list.getsize()!=1)
		System.out.print("were Commoners.\n");
		else System.out.print("was Commoner.\n");
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	public static boolean check_round() {
		if(mafia_num== (player_num_exludingOut-mafia_num) || mafia_num==0)
		return false;
		
		else return true;
	}
	
	public static void assignPlayers(int n) {
		
		int n_mafia=n/5;
		if(mafia_num==1)
			n_mafia--;
		while(n_mafia-->0) {
			int id= playerIDgenerate();
			Mafia ma = new Mafia(id);
			player_list.put(id, ma);
			mafia_list.add(ma);
			mafia_num++;
		}
		
		int n_detective=n/5;
		if(detective_num==1)
			n_detective--;
		while(n_detective-->0) {
		
			int id= playerIDgenerate();
			Detective de= new Detective(id);
			player_list.put(id, de);
			detective_list.add(de);
			detective_num++;
		}
		
		
		int n_healer=n/10;
		if(healer_num==1)
			n_healer--;
		while(n_healer-->0 || healer_num<1) {
			int id= playerIDgenerate();
			Healer he =new Healer(id);
			player_list.put(id, he);
			healer_list.add(he);
			healer_num++;
		}
		
		int n_commoner=player_num-(mafia_num+detective_num+healer_num);
		if(commoner_num==1)
			n_commoner--;
		while(n_commoner-->0) {
			int id= playerIDgenerate();
			Commoner co= new Commoner(id);
			player_list.put(id, co);
			commoner_list.add(co);
			commoner_num++;
		}
		
	}
	
	
	public static int playerIDgenerate() {
		Random rand= new Random();
		int a =rand.nextInt(player_num)+1;
		while(player_list.containsKey(a)) {
		
		a =rand.nextInt(player_num)+1;
		}
		return a;
	}
	
	
	public static void RandomNumToPlayer(int n) {
		int a= (int)(Math.random()*(4))+1;
		switch(a) {
		case 1: {player_list.put(1,new Mafia(1));
		mafia_list.add(new Mafia(1));
		mafia_num++; 
		break;}
		case 2: {player_list.put(1,new Detective(1));
		detective_list.add(new Detective(1));
		detective_num++;
		break;}
		case 3: {player_list.put(1,new Commoner(1));
		commoner_list.add(new Commoner(1));
		commoner_num++; 
		break;}
		case 4: {player_list.put(1,new Healer(1));
		healer_list.add(new Healer(1));
		healer_num++; 
		break;}
		
		}
	}
	
	
	public static void displayRemainingPlayers() {
		int x=0;
		System.out.print(player_num_exludingOut+" players are remaining: ");
		for(Map.Entry<Integer, Player> entry: player_list.entrySet()) {
			++x;
			if(player_list.size()!=x)
			System.out.print("Player"+entry.getValue().getID()+", ");
			else System.out.print("Player"+entry.getValue().getID()+" are alive.\n");
		}
	}
	
	
	public static int playerVoteGenerate() {
		while(true) {
		int a =(int)(Math.random()*player_num+1);
		
		if(player_list.containsKey(a)==true)
			return a;
		}
	}
	
	public static int TargetByPlayer(Player _playertype) {
		while(true) {
			int a = playerVoteGenerate();
			
			if(player_list.get(a).equals(_playertype)==false) {
				
				if(_playertype.getClass().getSimpleName().equals("Mafia")) {
				System.out.println("Mafias have chosen their target.");
				return a;
				}
				
				else if(_playertype.getClass().getSimpleName().equals("Detective")) {
					System.out.println("Detectives have chosen a player to test.");
					return a;
					}
				else if(_playertype.getClass().getSimpleName().equals("Healer")) {
					System.out.println("Healers have chosen someone to heal.");
					return a;
					}
			}
		}
	}
	
	public static int TargetByUserPlayer(Scanner sc) {
		int target=0;
		while(true) {
			
				if(player_list.get(1).equals(new Mafia(0))) {
					System.out.println("Choose a target: ");
						target= sc.nextInt();
					
					while(player_list.containsKey(target)==false || player_list.get(target).equals(player_list.get(1))) {
					if(player_list.containsKey(target)==false) {
						System.out.println("Choose from existing players only.\nChoose a target: ");
						target= sc.nextInt();
						}
					else if(player_list.get(target).equals(player_list.get(1))) {
						System.out.println("You can not target Mafia. Choose a target: ");
						target= sc.nextInt();
						}
					}
				}
				
				else if(player_list.get(1).equals(new Detective(0))) {
					System.out.println("Choose a player to test: ");
						target= sc.nextInt();
					
					while(player_list.containsKey(target)==false || player_list.get(target).equals(player_list.get(1))) {
					if(player_list.containsKey(target)==false) {
						System.out.println("Choose from existing players only.\nChoose a player to test: ");
						target= sc.nextInt();
						}
					else if(player_list.get(target).equals(player_list.get(1))) {
						System.out.println("You cannot test a detective. Choose a player to test: ");
						target= sc.nextInt();
						}
					}
					if(player_list.get(target).equals(new Mafia(0)))
						System.out.println("Player"+ target+" is a Mafia.");
					else System.out.println("Player"+ target+" is not a Mafia.");
				}
				else if(player_list.get(1).equals(new Healer(0))) {
					System.out.println("Choose a player to heal: ");
						target= sc.nextInt();
					
					
					while(player_list.containsKey(target)==false) {
						System.out.println("Choose from existing players only.\nChoose someone to heal: ");
						target= sc.nextInt();
						}
				}
				
				
				return target;
		}
	}
	
	
	
	public static int setMafiaHPOut(Player _targetPlayer, float mafiaHPSum) {
		
		float aliveMafia=0;
		for(Player _player: player_list.values()) {
			if(_player.equals(new Mafia(0)) && _player.getAlive())
				aliveMafia+=1;
		}
		
		
		float targetHPToBeCleared= _targetPlayer.getHP();
		float HPPortionForEachMafia= targetHPToBeCleared/aliveMafia;
		
		
		//if Mafia can kill	
		if(mafiaHPSum>= _targetPlayer.getHP()) {
			mafiaHPSum= _targetPlayer.getHP();
			
			while(mafiaHPSum>0) {
			for(Player _player: player_list.values()) {
				if(mafiaHPSum<=0) break;
				
				if(_player.equals(new Mafia(0)) && _player.getHP()>0) {
					//_players are Mafia
					float presentMafiaHP= _player.getHP();
					if(presentMafiaHP >= HPPortionForEachMafia) {
						//very loyal
						_player.decreaseHP(HPPortionForEachMafia);
						mafiaHPSum-=HPPortionForEachMafia;
					}
					else {	//poor Mafia
						
						if(presentMafiaHP <=mafiaHPSum) {
						_player.decreaseHP(_player.getHP());
						mafiaHPSum-=_player.getHP();
						}
						else {_player.decreaseHP(mafiaHPSum);
						mafiaHPSum-=mafiaHPSum;
						     }
						}
					
					}
				}
			}
			
			_targetPlayer.decreaseHP(_targetPlayer.getHP());
			return _targetPlayer.getID();
			}
		
		
		
		
		else {
			_targetPlayer.decreaseHP(mafiaHPSum);
			
			targetHPToBeCleared= mafiaHPSum;
			HPPortionForEachMafia= targetHPToBeCleared/aliveMafia;
			
			
			while(mafiaHPSum>0) {
				for(Player _player: player_list.values()) {
					if(mafiaHPSum<=0) break;
					
					if(_player.equals(new Mafia(0)) && _player.getHP()>0) {
						//_players are Mafia
						float presentMafiaHP= _player.getHP();
						if(presentMafiaHP >= HPPortionForEachMafia) {
							//very loyal
							_player.decreaseHP(HPPortionForEachMafia);
							mafiaHPSum-=HPPortionForEachMafia;
						}
						else {	//poor Mafia
							
							if(presentMafiaHP <=mafiaHPSum) {
								_player.decreaseHP(_player.getHP());
								mafiaHPSum-=_player.getHP();
								}
								else {_player.decreaseHP(mafiaHPSum);
								mafiaHPSum-=mafiaHPSum;
								}
								}
						
						}
					}
				}
			
			return -1;
		}
	}
	
	public static int GeneratePlayerVoteOut() {
		Random rand2= new Random();
		int a = rand2.nextInt(player_num)+1;
		while(player_list.containsKey(a)==false) {
			a= rand2.nextInt(player_num)+1;
		}
		return a;
	}
	
	
	public static void updatePlayerNumAlive(int mafia_target) {
		if(player_list.get(mafia_target).equals(new Mafia(0)))
			mafia_num-=1;
		else if(player_list.get(mafia_target).equals(new Detective(0)))
			detective_num-=1;
		else if(player_list.get(mafia_target).equals(new Healer(0)))
			healer_num-=1;
		else commoner_num-=1;
	}
	
	
	
	

}
