package nameThatTune.screen;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import nameThatTune.model.GamePads;
import nameThatTune.model.Group;
import nameThatTune.model.Player;

public class Question {
	static Boolean shown = false;
	public static void displayQuestionBoard(Stage stage,Scene scene, StackPane sp, Group players,int pick,Map<String, Map<String,String>> songsGame,Player currentPlayerObj,Map<String,Integer> used) throws Exception {
		Set<String> songList = songsGame.keySet();
		Player[] player = new Player[4];
		player[0] = players.getFirst();
		player[1] = players.getSecond();
		player[2] = players.getThird();
		player[3] = players.getFourth();
		Queue<String> songStack = new PriorityQueue<String>();
		String[] catString = new String[4];
		int i = 0;
		for (String cat:songList) {
			catString[i] = cat;
			i++;
		}
		String thisCategoryString = catString[pick];
		Map<String,String> catSong  = songsGame.get(catString[pick]);
		Set<String> songs = catSong.keySet();
		Iterator<String> songIter = songs.iterator();
		while(songIter.hasNext()) {
			String key = songIter.next();
			songStack.add(key);
		}
		String thisSong = songStack.remove();
		Map<String,String> mapCatSongs = songsGame.get(thisCategoryString);
		String songPlaying = mapCatSongs.remove(thisSong);
		songsGame.replace(thisCategoryString,mapCatSongs);
		
		MediaPlayer songToPlay  = getSong(thisCategoryString,thisSong);
		//Draw Board
		sp.getChildren().clear();
		questionNumber(sp,stage,thisSong,1);
		questionNumber(sp,stage,thisCategoryString,0);
		Label[] scores = new Label[4];
		ImageView[] selected = new ImageView[4];
		for(i=0;i<4;i++) {
			scores[i] = drawScores(stage,scene,player[i],sp,i);
			selected[i] = drawSelected(sp,i);
		}
		Label answer = new Label(songPlaying);
		answer.setStyle("-fx-font: normal 32px 'Arial' ");
		answer.setTextFill(Color.WHITE);
		answer.setOpacity(0);
		sp.getChildren().add(answer);
		GamePads buzzers = null;
		buzzers = new GamePads("game",players,songToPlay,selected);
		buzzers.start();
		///////////////////////////
		keyBoardControlListener(stage,scene,sp,buzzers,players,answer,songsGame,used,currentPlayerObj,scores,selected,songToPlay);
	}
	
	public static Label drawScores(Stage stage,Scene scene,Player player,StackPane sp,int i) {
		Double stageW = stage.getWidth();
		Label labelPlayer = new Label(player.getPlayerName());
		Label playerScore = new Label("\n$"+player.getScore());
		labelPlayer.setTextFill(Color.WHITE);
		playerScore.setTextFill(Color.WHITE);
		labelPlayer.setStyle("-fx-font: normal 32px 'Arial' ");
		playerScore.setStyle("-fx-font: normal 32px 'Arial' ");
		labelPlayer.setPadding(new Insets(100,0,0,(stageW/5)*i));
		playerScore.setPadding(new Insets(100,0,0,(stageW/5)*i));
		StackPane.setAlignment(labelPlayer,Pos.TOP_LEFT);
		StackPane.setAlignment(playerScore, Pos.TOP_LEFT);
		sp.getChildren().add(labelPlayer);
		sp.getChildren().add(playerScore);
		return playerScore;
	}
	
	public static ImageView drawSelected(StackPane sp, int i) {
		ImageView buzzedIV = null;
		try {
			FileInputStream buzzedfip = new FileInputStream(nameThatTune.Constants.folderLocation+"/sprites/spr_blue_"+i+".png");
			Image buzzedImage = new Image(buzzedfip);
			buzzedIV = new ImageView(buzzedImage);
			buzzedIV.setOpacity(0);
			StackPane.setAlignment(buzzedIV,Pos.CENTER);
			sp.getChildren().add(buzzedIV);
			buzzedfip.close();
		}catch(Exception e) {
			System.out.println(e);
		}
		return buzzedIV;
	}
	
	public static MediaPlayer getSong(String cat,String song) {
		MediaPlayer mediaPlayer = null;
		try {
			if (Integer.parseInt(song) < 10) {
				song = "0"+song;
			}
			File music = new File (nameThatTune.Constants.folderLocation+"/categories/"+cat+"/"+song+".mp3");
			if (music.isFile()) {
		    	Media gameMusic = new Media(music.toURI().toString());
		        mediaPlayer = new MediaPlayer(gameMusic);
			}
			else {
				System.out.println("Song doesn't exist. SONG: "+cat+"/"+song+".mp3");
				System.exit(0);
			}
		} catch(Exception e) {
			
		}
		return mediaPlayer;	
	}
	
	public synchronized static void keyBoardControlListener(Stage stage,Scene scene,StackPane sp,GamePads buzzers,Group players,Label answer,Map<String, Map<String,String>> songsGame,Map<String,Integer> used,Player currentPlayerObj,Label[] scores,ImageView[] selected,MediaPlayer songToPlay) {
		EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	int i;
            	ImageView selectedPlayerImage;
            	if(event.getCode().toString().equals("ENTER")) {
            		if (buzzers.getBuzzed()) {
            			if (!buzzers.getDone()){
                			buzzers.getWrongSound().play();
                    		selectedPlayerImage = selected[buzzers.getPlayerBuzzed().getPosition()-1];
                    		selectedPlayerImage.setOpacity(0);
                			buzzers.setBuzzed(false);
                			buzzers.setPlayerBuzzed(new Player("Player 5",5));
                			if (!buzzers.getSongToPlay().getStatus().equals(Status.PLAYING)) {
                				buzzers.getSongToPlay().play();
                			}
                		}
                	}	
            	}
            		
            	else if (event.getCode().toString().equals("SPACE")) {
            		if (buzzers.getBuzzed()) {
                		if(!buzzers.getDone()) {
                    		buzzers.setDone(true);
                    		answer.setOpacity(100);
                    		buzzers.getPlayerBuzzed().addScore(100);
                    		selectedPlayerImage = selected[buzzers.getPlayerBuzzed().getPosition()-1];
                    		selectedPlayerImage.setOpacity(0);
                    		Player[] player = new Player[4];
                    		player[0] = buzzers.getPlayers().getFirst();
                    		player[1] = buzzers.getPlayers().getSecond();
                    		player[2] = buzzers.getPlayers().getThird();
                    		player[3] = buzzers.getPlayers().getFourth();
                    		for(i=0;i<4;i++) {
                    			scores[i].setText("\n$"+String.valueOf(player[i].getScore()));
                    			player[i].sethasBuzzed(false);
                    		}
                    		buzzers.getSongToPlay().play();
                		}	
            		}
            	}
            	else if(event.getCode().toString().equals("BACK_SPACE")) {
            		if (buzzers.getDone()) {
                		if (buzzers.getSongToPlay().getStatus().equals(Status.PLAYING)) {
                			buzzers.getSongToPlay().stop();
                			buzzers.getSongToPlay().dispose();
                		}
                		sp.getChildren().clear();
                		try {
                			Player player = currentPlayerObj;
                			if (buzzers.getPlayerBuzzed().getPosition() != 5) {
                				player = buzzers.getPlayerBuzzed();
                			}
                			System.gc();
                			buzzers.setDone(true);
                			GamePads Qbuzzers = null;
                			Qbuzzers = new GamePads(player,"main",used);
                			Qbuzzers.start();
    						GameBoard.displayBoard(stage,scene,sp,players,songsGame,player,used,Qbuzzers);
    						scene.removeEventHandler(KeyEvent.KEY_PRESSED,this);
    					} catch (Exception e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
            		}
            	}
            	else if (event.getCode().toString().equals("A")) {
            		answer.setOpacity(100);
            		buzzers.setDone(true);
            	}
            }
		};
		scene.addEventHandler(KeyEvent.KEY_PRESSED, eh);
	}
	
    public static void questionNumber(StackPane sp, Stage stage,String question,int select) throws Exception {
    	if (select == 1) {
        	Label questionNumber = new Label(question);
        	questionNumber.setTextFill(Color.WHITE);
        	questionNumber.setStyle("-fx-font: normal 64px 'Arial' ");
        	sp.getChildren().add(questionNumber);
        	StackPane.setAlignment(questionNumber, Pos.BOTTOM_RIGHT);
    	}
    	else {
        	Label categoryLabel = new Label(question);
        	categoryLabel.setTextFill(Color.WHITE);
        	categoryLabel.setStyle("-fx-font: normal 64px 'Arial' ");
        	sp.getChildren().add(categoryLabel);
        	StackPane.setAlignment(categoryLabel, Pos.BOTTOM_LEFT);
    	}
    }
	
}

