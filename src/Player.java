	public class Player {
		int points;
		int play; //0=none 1=rock 2=paper 3=scissors 4=lizard 5=spock
		boolean connected;
		boolean hasPlayed;
		
		public Player()
		{
			points=0;
			play=0;
			connected=false;
			hasPlayed=false;
		}
		
		public void play(int decision)
		{
			this.play=decision;
		}
		
		public String getPlay()
		{
			String ans;
			switch(play) {
				case 1:	ans="Rock";
						break;
				case 2:	ans="Paper";
						break;
				case 3:	ans="Scissors";
						break;
				case 4:	ans="Lizard";
						break;
				case 5:	ans="Spock";
						break;
				default: ans="None";
						break;
			}
			return ans;
		}
	}