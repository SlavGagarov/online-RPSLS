
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ServerRun extends Application{
	
	private boolean isServer = true;
	 private Stage stage;
	 NetworkConn  conn;
	 TextArea messages = new TextArea();
	 Button start=new Button("Start Server!");
	 Text fill;
	 GameState game=new GameState();
	 Button quit=new Button("Turn Server Off");
	 String win;
	 int p1play=0;
	 int p2play=0;
	 public Parent firstContent()
	 {
		 
			messages.setPrefHeight(550);
			TextField input = new TextField();
			String message = "Enter Port then press Enter then press Start Server";
			Text messageText=new Text(message);
			fill=new Text("						");
			Text fill2=new Text("						");;
			input.setOnAction(event -> {
				String answerPort=input.getText();
				int portAns=Integer.parseInt(answerPort);
				conn=createServer(portAns);
				input.clear();
			});
			
			VBox root = new VBox(20,messageText,input,start);
			VBox fillbox=new VBox(100,fill,root);
			
			HBox ans=new HBox(0,fill2,fillbox);
			ans.setPrefSize(600, 600);
			return ans;
			
	 }
	 
	public Scene mainScene()
	{
		BorderPane pane = new BorderPane();
		int ppl = conn.clients.size();
		String connected="Numper of Users Connected = "+ppl;
		String collection="Player1 Score = "+game.p1.points+"\nPlayer2 Score = "+game.p2.points+
				"\nPlayer1 Played "+game.p1.getPlay()+"\nPlayer2 Played : "+game.p2.getPlay()+"\nWinner: "+win;
		
		Text results=new Text(collection);
		Text conn=new Text(connected);
		VBox toptext = new VBox(100,conn,results,quit);
		toptext.setAlignment(Pos.CENTER);
		pane.setTop(toptext);
		
		Scene endR=new Scene(pane,800,600);
		return endR;
	}
	private Parent startingContent() {
		
		messages.setPrefHeight(550);
		TextField input = new TextField();
		
		input.setOnAction(event -> {
			String message = "Server: ";
			message += input.getText();
			input.clear();
			
			messages.appendText(message + "\n");
			try {
				conn.send(message);
			}
			catch(Exception e) {
				
			}
			
		});
		
		VBox root = new VBox(20, messages, input,quit);
		root.setPrefSize(600, 600);
		
		return root;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setScene(new Scene((firstContent())));
		
		start.setOnAction(event->{
			try
			{
				startConn();
				stage.setScene(mainScene());
			}
			catch(Exception e)
			{
				System.out.println("unable to start: "+e);
			}
		});
		quit.setOnAction(event->{
			try {
				conn.closeConn();
			}
			catch(Exception e)
			{
				System.out.println("Error Closing Server: "+e);
			}
			Platform.exit();
		});
		
		
		primaryStage.show();
	}
	
	/*
	@Override
	public void init() throws Exception{
		conn.startConn();
	}*/
	
	public void startConn() throws Exception{
		conn.startConn();
	}
	
	@Override
	public void stop() throws Exception{
		conn.closeConn();
	}
	
	private RPSLSserver createServer(int portGiven) {
		return new RPSLSserver(portGiven, data-> {
			Platform.runLater(()->{
				messages.appendText(data.toString() + "\n");
				
				String incoming=data.toString();
				this.evalString(incoming);
				//System.out.println("incoming="+incoming);

			});
		});
	}
	
	public void evalString(String s)
	{
		if(s.equals("1 Rock"))
		{
			game.p1.play=1;
			game.p1.hasPlayed=true;
		}
		if(s.equals("1 Paper"))
		{
			game.p1.play=2;
			game.p1.hasPlayed=true;
		}
		if(s.equals("1 Scissors"))
		{
			game.p1.play=3;
			game.p1.hasPlayed=true;
		}
		if(s.equals("1 Lizard"))
		{
			game.p1.play=4;
			game.p1.hasPlayed=true;
		}
		if(s.equals("1 Spock"))
		{
			game.p1.play=5;
			game.p1.hasPlayed=true;
		}
		
		if(s.equals("2 Rock"))
		{
			game.p2.play=1;
			game.p2.hasPlayed=true;
		}
		if(s.equals("2 Paper"))
		{
			game.p2.play=2;
			game.p2.hasPlayed=true;
		}
		if(s.equals("2 Scissors"))
		{
			game.p2.play=3;
			game.p2.hasPlayed=true;
		}
		if(s.equals("2 Lizard"))
		{
			game.p2.play=4;
			game.p2.hasPlayed=true;
		}
		if(s.equals("2 Spock"))
		{
			game.p2.play=5;
			game.p2.hasPlayed=true;
		}
		if(s.equals("1 CONN"))
		{
			stage.setScene(mainScene());
		}
		if(game.p1.hasPlayed && game.p2.hasPlayed)
		{
			
			this.evalPlays();
		}
	}
	
	public int evalPlays()
	{
		int winner=0;
		int pl1=game.p1.play;
		int pl2=game.p2.play;
		if(pl1 == pl2)
		{
			winner=0;
		}
		else if(pl1==1)
		{
			switch(pl2) {
			case 1:	winner=0;
					break;
			case 2:	winner=2;
					break;
			case 3:	winner=1;
					break;
			case 4:	winner=1;
					break;
			case 5:	winner=2;
					break;
			default: winner=0;
					break;
			}
		}
		
		else if(pl1==2)
		{
			switch(pl2) {
			case 1:	winner=1;
					break;
			case 2:	winner=0;
					break;
			case 3:	winner=2;
					break;
			case 4:	winner=2;
					break;
			case 5:	winner=1;
					break;
			default: winner=0;
					break;
			}
		}
		
		else if(pl1==3)
		{
			switch(pl2) {
			case 1:	winner=2;
					break;
			case 2:	winner=1;
					break;
			case 3:	winner=0;
					break;
			case 4:	winner=1;
					break;
			case 5:	winner=2;
					break;
			default: winner=0;
					break;
			}
		}
		
		else if(pl1==4)
		{
			switch(pl2) {
			case 1:	winner=2;
					break;
			case 2:	winner=1;
					break;
			case 3:	winner=2;
					break;
			case 4:	winner=0;
					break;
			case 5:	winner=1;
					break;
			default: winner=0;
					break;
			}
		}
		
		else if(pl1==5)
		{
			switch(pl2) {
			case 1:	winner=1;
					break;
			case 2:	winner=2;
					break;
			case 3:	winner=1;
					break;
			case 4:	winner=2;
					break;
			case 5:	winner=0;
					break;
			default: winner=0;
					break;
			}
		}
		game.p1.hasPlayed=false;
		game.p2.hasPlayed=false;
		if(winner==1)
		{
			win="Player1";
			game.p1.points+=1;
			String scoremessage="1points:"+game.p1.points;
			String play1message="1play:"+game.p1.getPlay();
			String play2message="2play:"+game.p2.getPlay();
			try {
				conn.send(scoremessage);
				conn.send(play1message);
				conn.send(play2message);
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		if(winner==2)
		{
			win="Player2";
			game.p2.points+=1;
			String scoremessage="2points:"+game.p2.points;
			String play1message="1play:"+game.p1.getPlay();
			String play2message="2play:"+game.p2.getPlay();
			try {
				conn.send(scoremessage);
				conn.send(play1message);
				conn.send(play2message);
			}
			catch(Exception e) {
				System.out.println(e);
			
			}
		}
		if(winner==0)
		{
			win="TIE";
			String play1message="1play:"+game.p1.getPlay();
			String play2message="2play:"+game.p2.getPlay();
			try {
				conn.send(play1message);
				conn.send(play2message);
			}
			catch(Exception e) {
				System.out.println(e);
			
			}
		}
		String message="winner:"+winner;
		try {
			conn.send(message);
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		try {
			String sendthis="bothPlayed";
			conn.send(sendthis);

		}
		catch(Exception e) {
			System.out.println(e);
		
		}
		stage.setScene(mainScene());
		return winner;
	}

}
