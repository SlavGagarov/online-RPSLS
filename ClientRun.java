
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientRun extends Application{
	
	private Stage stage;
	
	 NetworkConn  conn;// = createClient();
	 TextArea messages = new TextArea("MESSAGES FROM SERVER:\n");
	 Button start=new Button("Start Game!");
	 Text fill;
	 int portAns;
	 String ipAns;
	 
	 int score1=0;
	 int score2=0;
	 String win;
	 String playmove1;
	 String playmove2;
	 
	 static int readytoPlay=0;
	 
	 Button play1=new Button();
	 Button play2=new Button();
	 Button play3=new Button();
 	 Button play4=new Button();
	 Button play5=new Button();
	  
	 Button otherConn=new Button("Click to Start");
	 Button playAgain=new Button("Play Again");
	 Button quit=new Button("Quit");
	 public Scene createRPS()
	 {
		 Text NULLt=new Text("");
		 Image pic = new Image("rock.jpg");
			ImageView v = new ImageView(pic);
			v.setFitHeight(100);
			v.setFitWidth(100);
			//v.setPreserveRatio(true);
			play1.setGraphic(v);
		    
		    Image pic2 = new Image("paper.jpg");
			ImageView v2 = new ImageView(pic2);
			v2.setFitHeight(100);
			v2.setFitWidth(100);
			play2.setGraphic(v2);
		    
		    Image pic3 = new Image("scissors.jpg");
			ImageView v3 = new ImageView(pic3);
			v3.setFitHeight(100);
			v3.setFitWidth(100);
			play3.setGraphic(v3);
		    
		    Image pic4 = new Image("lizard.png");
			ImageView v4 = new ImageView(pic4);
			v4.setFitHeight(100);
			v4.setFitWidth(100);
			play4.setGraphic(v4);
		    
		    Image pic5 = new Image("spock.jpg");
			ImageView v5 = new ImageView(pic5);
			v5.setFitHeight(100);
			v5.setFitWidth(100);
			play5.setGraphic(v5);
			
			HBox plays = new HBox(10, play1, play2,play3,play4,play5);
			plays.setAlignment(Pos.CENTER);
			BorderPane pane = new BorderPane();
			pane.setCenter(plays);
			Text pick=new Text("Choose a play!");
			VBox toptext = new VBox(50,NULLt,pick);
			toptext.setAlignment(Pos.CENTER);
			pane.setTop(toptext);
			
			Scene endR=new Scene(pane,800,600);
			return endR;
	 }
	 
	 public Parent firstContent()
	 {
		 
			messages.setPrefHeight(550);
			TextField inputPort = new TextField();
			TextField inputIP = new TextField();
			String message = "Enter IP and press Enter";
			String message2 = "Enter Port then press Enter then click Start Game";
			Text messageText=new Text(message);
			Text messageText2=new Text(message2);
			fill=new Text("						");
			Text fill2=new Text("						");;
			
			inputIP.setOnAction(event -> {
				String answerPort=inputIP.getText();
				ipAns=answerPort;
				inputIP.clear();
			});
			
			
			inputPort.setOnAction(event -> {
				String answerPort=inputPort.getText();
				portAns=Integer.parseInt(answerPort);
				inputPort.clear();
				conn=createClient(ipAns,portAns);
				try
					{conn.startConn();}
				catch(Exception e)
					{System.out.println("cant start connection: "+ e);}
			});
			
			
			
			VBox root = new VBox(20,messageText,inputIP,messageText2,inputPort,start);
			VBox fillbox=new VBox(100,fill,root);
			
			HBox ans=new HBox(0,fill2,fillbox);
			ans.setPrefSize(600, 600);
			return ans;
			
	 }
	 
	 
	private Parent createContent() {
		messages.setPrefHeight(550);
		TextField input = new TextField();
		
		input.setOnAction(event -> {
			String message = "Client: ";
			message += input.getText();
			input.clear();
			
			messages.appendText(message + "\n");
			try {
				conn.send(message);
			}
			catch(Exception e) {
				
			}
			
		});
		
		VBox root = new VBox(20, messages, input);
		root.setPrefSize(600, 600);
		
		return root;
	}
	
	public Scene waitingRoom() {
		messages.setMaxHeight(600);
		messages.setMaxWidth(250);
		Text waitngfor=new Text("Waiting for another player to connect.");
		Text NULLt=new Text("");
		BorderPane pane = new BorderPane();
		
		if(readytoPlay==1)
		{
			VBox toptext = new VBox(50,NULLt,waitngfor);
			toptext.setAlignment(Pos.CENTER);
			pane.setTop(toptext);
			Scene endR=new Scene(pane,800,600);
			return endR;
		}
		if(readytoPlay==2)
		{
			return createRPS();
		}
		else
		{
			return createRPS();
		}
		
	}
	
	public Scene afterRound()
	{

		String collection="Player1 Score = "+score1+"\nPlayer2 Score = "+score2+
				"\nPlayer1 Played "+playmove1+"\nPlayer2 Played : "+playmove2+"\nWinner: "+win;
		Text results=new Text(collection);
		BorderPane pane = new BorderPane();
		VBox toptext = new VBox(50,results,playAgain,quit);
		toptext.setAlignment(Pos.CENTER);
		pane.setTop(toptext);
		pane.setRight(messages);
		Scene endR=new Scene(pane,800,600);
		return endR;
		
	}
	/*
	private Scene otherConnected() {
		BorderPane pane = new BorderPane();
		Text readytext=new Text("Another player has connected.");
		Text NULLt=new Text("");
		VBox toptext = new VBox(50,NULLt,readytext);
		toptext.setAlignment(Pos.CENTER);
		pane.setTop(toptext);
		
		Scene endR=new Scene(pane,800,600);
		return endR;
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		stage = primaryStage;
		stage.setScene(new Scene(firstContent()));
		//primaryStage.setScene(createRPS());
		
		start.setOnAction(event->{
			try
			{
				stage.setScene(waitingRoom());
			}
			catch(Exception e)
			{
				System.out.println("unable to start: "+e);
			}
		});
		
		play1.setOnAction(event->{
			try {
				conn.send("Rock");
			}
			catch(Exception e)
			{
				System.out.println("Button Error:"+e);
			}
		});
		
		play2.setOnAction(event->{
			try {
				conn.send("Paper");
			}
			catch(Exception e)
			{
				System.out.println("Button Error:"+e);
			}
		});
		
		play3.setOnAction(event->{
			try {
				conn.send("Scissors");
			}
			catch(Exception e)
			{
				System.out.println("Button Error:"+e);
			}
		});
		
		play4.setOnAction(event->{
			try {
				conn.send("Lizard");
			}
			catch(Exception e)
			{
				System.out.println("Button Error:"+e);
			}
		});
		
		play5.setOnAction(event->{
			try {
				conn.send("Spock");
			}
			catch(Exception e)
			{
				System.out.println("Button Error:"+e);
			}
		});
		
		playAgain.setOnAction(event->{
			try
			{
				stage.setScene(waitingRoom());
			}
			catch(Exception e)
			{
				System.out.println("unable to start: "+e);
			}
		});
		
		quit.setOnAction(event->{
			Platform.exit();
		});
		primaryStage.show();
		
	}
	/*
	@Override
	public void init() throws Exception{
		conn.startConn();
	}
	*/
	@Override
	public void stop() throws Exception{
		conn.closeConn();
	}
	
	private RPSLSclient createClient(String ip, int port) {
		return new RPSLSclient(ip,port, data -> {
			Platform.runLater(()->{
				messages.appendText(data.toString() + "\n");
				
				String incoming=data.toString();
				this.evalStr(incoming);

				
			});
		});
	}
	
	public void evalStr(String s)
	{
		if(s.equals("wait"))
		{
			readytoPlay=1;
			try {
				conn.send("CONN");
			}
			catch(Exception e){
				System.out.println("Error sending msg to server:"+e);
			}
		}
		if(s.equals("go"))
		{
			readytoPlay=2;
			try {
				conn.send("CONN");
			}
			catch(Exception e){
				System.out.println("Error sending msg to server:"+e);
			}
			stage.setScene(createRPS());
		}
		
		if(s.equals("winner:1"))
		{
			win="Player 1 won!";
		}
		if(s.equals("winner:2"))
		{
			win="Player 2 won!";
		}
		if(s.equals("winner:0"))
		{
			win="TIE!";
		}
		
		if(s.startsWith("1points:"))
		{
			score1++;
		}
		if(s.startsWith("2points:"))
		{
			score2++;
		}
		if(s.startsWith("1play:"))
		{
			String ans=s.replace("1play:","");
			playmove1=ans;
		}
		if(s.startsWith("2play:"))
		{
			String ans=s.replace("2play:","");
			playmove2=ans;
		}
		if(s.equals("bothPlayed"))
		{
			stage.setScene(afterRound());
		}
		
	}

}
