package nameThatTune.screen;

import java.io.*;
import java.lang.Exception;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import nameThatTune.Constants;
import nameThatTune.model.GamePads;
import nameThatTune.model.Group;
import nameThatTune.model.Player;

public class gameStart {
	public static void getNames(Stage stage,Scene currentScene, StackPane sp) throws Exception {
		MediaPlayer gsm = playSong();
		Player player1 = new Player("Player 1",1);
		Player player2 = new Player("Player 2",2);
		Player player3 = new Player("Player 3",3);
		Player player4 = new Player("Player 4",4);
		player1.setPlayerName(setName(player1,stage));
		player2.setPlayerName(setName(player2,stage));
		player3.setPlayerName(setName(player3,stage));
		player4.setPlayerName(setName(player4,stage));
		Group gameGroup = new Group(player1,player2,player3,player4);
		Map<String,Map<String,String>> songlist = loadSongs();
		displayBoard(stage, currentScene,sp,gameGroup,songlist,gsm);
	}
	
	public static Map<String, Map<String,String>> loadSongs() throws Exception {
		File songListFile = new File("songList.dow");
		Properties used = new Properties();
		try {
			used.load(new FileInputStream("used.dow"));
		}catch (Exception ex){
			System.out.println("No used.dow file detected.");
		}
		INIConfiguration iniConfiguration = new INIConfiguration();
		try {
			FileReader fileReader = new FileReader(songListFile);
			iniConfiguration.read(fileReader);
		} catch (Exception e) {
			System.out.println("Cannot load songlist.dow");
		}
		Map<String,Map<String,String>> songList = processINIsongfile(iniConfiguration);
		if (!used.isEmpty()) {
			Map<String,Map<String,String>> newSongList = removeUsedSongs(songList,used);
			return newSongList;
		}
		return songList;
		
	}
	public static Map<String,Map<String,String>> processINIsongfile(INIConfiguration iniConfiguration){
		Map<String, Map<String, String>> sl = new HashMap<>();
		for (String section : iniConfiguration.getSections()) {
		    Map<String, String> subSectionMap = new HashMap<>();
		    SubnodeConfiguration confSection = iniConfiguration.getSection(section);
		    Iterator<String> keyIterator = confSection.getKeys();
		    while (keyIterator.hasNext()) {
		        String key = keyIterator.next();
		        String value = confSection.getProperty(key).toString();
		        subSectionMap.put(key, value);
		    }
		    sl.put(section, subSectionMap);
		}
		return sl;
	}
	public static Map<String,Map<String,String>> removeUsedSongs(Map<String,Map<String,String>> sl,Properties used){
		Map<String, Map<String, String>> newSongList = sl;
		for (Map.Entry<String,Map<String,String>> category: newSongList.entrySet()) {
			for (Map.Entry<String,String> song : category.getValue().entrySet()) {
				if (used.containsKey(category.getKey().toLowerCase()+"."+song.getKey())) {
					newSongList.remove(category.getKey(), song.getKey());
				}
			}
		}
		return newSongList;
	}
	
	
	public static Map<String,Map<String,String>> pick4Game(Map<String,Map<String,String>> selectedCats){
//		HashMap<String,Map<String,String>> list4Game = new HashMap<String,Map<String,String>>();
//		Set<String> slcat;
//		Object[] slcatArray;
//		String category;
//		Map<String,String> catobject;
		Map<String,String> csl;
		HashMap<String,Map<String,String>> finalSelection = new HashMap<String,Map<String,String>>();
		for (Map.Entry<String,Map<String,String>> category : selectedCats.entrySet()) {
			csl = getSongs(selectedCats,category.getValue(),category.getKey());
			finalSelection.put(category.getKey(),csl);
		}
//		for (int i=1;i<=4;i++) {
//			slcat = selectedCats.keySet();
//			slcatArray = slcat.toArray();
//			category = slcatArray.toString();
//			catobject = selectedCats.get(category);
//		}
		
		//saveUsedDowFile(list4Game);
		return finalSelection;
	}
	
	public static void saveUsedDowFile(Map<String,Map<String,String>> sl) {
		Properties used = new Properties();
		try {
			used.load(new FileInputStream("used.dow"));
		}catch (Exception ex){
			System.out.println("No used file detected, making new used list.");
		}
		try {
			for (Map.Entry<String,Map<String,String>> category : sl.entrySet())
			{
				for (Map.Entry<String,String> song : category.getValue().entrySet())
					used.put(category.getKey().toLowerCase()+"."+song.getKey(),"true");
			}
			FileOutputStream fos = new FileOutputStream("used.dow");
			used.store(fos, "Updated used songlist");
			System.out.println("Created/Updated used.dow");
			fos.close();
		}
		catch(Exception e)
		{
			System.out.println("Unable to write used file\n"+e);
		}
		
	}
	
	public static Map<String,String> getSongs(Map<String,Map<String,String>> sl,Map<String,String> cat,String category) {
		Map<String,String> csl = sl.get(category);
		Map<String,String> finalCat = new HashMap<>();
		Set<String> key;
		Random randomize;
		Object[] cslArray;
		String songc;
		int max;
		int randomInt;
		int numberOfSongs = Constants.numberOfSongs;
		randomize = new Random();
		for (int i = 0;i<numberOfSongs;i++) {
			key = csl.keySet();
			cslArray = key.toArray();
			max = key.size();
			randomInt = randomize.nextInt(max);
			songc = cslArray[randomInt].toString();
			finalCat.put(songc,csl.get(songc));
			csl.remove(songc);
		}
		return finalCat;
	}
	public static String setName(Player player,Stage stage) {
        TextInputDialog inputdialog = new TextInputDialog(player.getPlayerName());
        inputdialog.setContentText("Name:");
        inputdialog.setHeaderText("Please enter "+player.getPlayerName()+"'s name");
        inputdialog.initOwner(stage);
        inputdialog.showAndWait();
        return inputdialog.getEditor().getText();
		
	}
	
	
	//////////////////////Display Methods///////////////////////////////////////////
	public static void displayBoard(Stage stage,Scene scene,StackPane sp,Group players,Map<String, Map<String,String>> songsGame,MediaPlayer gsm) throws Exception {
		Double stageH = stage.getHeight();
		Double stageW = stage.getWidth();
		List<String> songKeys = new ArrayList<String>(songsGame.keySet());
		Collections.shuffle(songKeys);
		Iterator<String> cliter = songKeys.iterator();
		for (int i=0;i<4;i++) {
			drawcategories(sp,cliter,i,players,stageH,stageW);
		}
		///////////////Start new thread for buzzers////////
		GamePads buzzers = null;
		buzzers = new GamePads("setup",players);
		buzzers.start();
		waitForInput(stage,scene,sp,players,songsGame,buzzers,gsm,songKeys);
	}
	
	public static void updateBoard(Stage stage, Scene scene,StackPane sp,Group players,MediaPlayer gsm,List<String> songKeys,GamePads buzzers) throws Exception {
		Double stageH = stage.getHeight();
		Double stageW = stage.getWidth();
		Iterator<String> cliter = songKeys.iterator();
		for (int i=0;i<4;i++) {
			updateCategories(sp,cliter,i,players,buzzers,stageH,stageW);
		}
	}
	
	public static void updateCategories(StackPane sp,Iterator<String> categories,int place,Group players,GamePads buzzers,Double h,Double w) throws Exception {
		Text playerName = new Text(players.getPlayer(place+1).getPlayerName());
		sp.getChildren().add(playerName);
		StackPane.setAlignment(playerName, Pos.TOP_LEFT);
		playerName.setStyle("-fx-font: normal 16px 'Arial' ");
		playerName.setFill(Color.WHITE);
		StackPane.setMargin(playerName, new Insets(h/7,0,0,(w/4)*place));
		for (int i=1; i<5;i++) {
			String catName = categories.next();
			updateCategory(sp,catName,i-1,place,h,w,players.getPlayer(place+1),buzzers);
		}
	}
	
	public static void updateCategory(StackPane sp,String catName,int number,int place,Double h,Double w,Player player,GamePads buzzers) throws Exception {
		Text categoryDisp = new Text(catName);
		FileInputStream category = null;
		try {
			if (player.getChoice() == -1) {
				category = new FileInputStream(nameThatTune.Constants.folderLocation+"/sprites/spr_category_"+number+".png");
			}	
			else {
				if (player.getChoice() == number) {
					category = new FileInputStream(nameThatTune.Constants.folderLocation+"/sprites/spr_category_"+number+".png");
					player.setSelectedCategory(catName);
				}
				else {
					category = new FileInputStream(nameThatTune.Constants.folderLocation+"/sprites/spr_category_4.png");
				}
			}
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image catImage = new Image(category);
		ImageView catIV = new ImageView(catImage);
		sp.getChildren().add(catIV);
		sp.getChildren().add(categoryDisp);
		categoryDisp.setStyle("-fx-font: normal 16px 'Arial' ");
		categoryDisp.setFill(Color.WHITE);
        /*FadeTransition ft = new FadeTransition(Duration.millis(3000), catIV);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();*/
        StackPane.setMargin(catIV, new Insets((h/5)*(number+1),0,0,(w/4)*place));
        StackPane.setMargin(categoryDisp, new Insets((h/5)*(number+1)+50,0,0,((w/4)*place)+100));
		StackPane.setAlignment(catIV,Pos.TOP_LEFT);
		StackPane.setAlignment(categoryDisp,Pos.TOP_LEFT);
		category.close();
	}
	
	public static void drawcategories(StackPane sp,Iterator<String> categories,int place,Group players,Double h,Double w) throws Exception {
		Text playerName = new Text(players.getPlayer(place+1).getPlayerName());
		sp.getChildren().add(playerName);
		StackPane.setAlignment(playerName, Pos.TOP_LEFT);
		playerName.setStyle("-fx-font: normal 16px 'Arial' ");
		playerName.setFill(Color.WHITE);
		StackPane.setMargin(playerName, new Insets(h/7,0,0,(w/4)*place));
		for (int i=1; i<5;i++) {
			String catName = categories.next();
			drawCategory(sp,catName,i-1,place,h,w);
		}
	}
	
	public static void drawCategory(StackPane sp,String catName,int number,int place,Double h,Double w) throws Exception {
		Text categoryDisp = new Text(catName);
		FileInputStream category = null;
		try {
			category = new FileInputStream(nameThatTune.Constants.folderLocation+"/sprites/spr_category_"+number+".png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image catImage = new Image(category);
		ImageView catIV = new ImageView(catImage);
		sp.getChildren().add(catIV);
		sp.getChildren().add(categoryDisp);
		categoryDisp.setStyle("-fx-font: normal 16px 'Arial' ");
		categoryDisp.setFill(Color.WHITE);
        /*FadeTransition ft = new FadeTransition(Duration.millis(3000), catIV);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();*/
        StackPane.setMargin(catIV, new Insets((h/5)*(number+1),0,0,(w/4)*place));
        StackPane.setMargin(categoryDisp, new Insets((h/5)*(number+1)+50,0,0,((w/4)*place)+100));
		StackPane.setAlignment(catIV,Pos.TOP_LEFT);
		StackPane.setAlignment(categoryDisp,Pos.TOP_LEFT);
		category.close();
	}

	
	public static MediaPlayer playSong() {
    	File path = new File (nameThatTune.Constants.folderLocation+"/music/competition.mp3");
    	Media gameSetupMusic = new Media(path.toURI().toString()); 
        MediaPlayer mediaPlayer = new MediaPlayer(gameSetupMusic);
        mediaPlayer.play();
        return mediaPlayer;
	}
	/////////////////////////////Buzzer Method//////////////////////////////
	public static void waitForInput(Stage stage, Scene scene, StackPane sp, Group players,Map<String, Map<String,String>> songsGame,GamePads buzzers,MediaPlayer gsm,List<String> songKeys) throws Exception {
		EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	if(buzzers.getPlayers().getFirst().getChoice() != -1) {
    				//System.out.print("1,"+players.getFirst().getChoice());
    				sp.getChildren().clear();
    				try {
    					updateBoard(stage, scene,sp,players,gsm,songKeys,buzzers);
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			else if (buzzers.getPlayers().getSecond().getChoice() != -1) {
    				//System.out.print("2,"+players.getSecond().getChoice());
    				sp.getChildren().clear();
    				try {
    					updateBoard(stage, scene,sp,players,gsm,songKeys,buzzers);
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			else if (buzzers.getPlayers().getThird().getChoice() != -1) {
    				sp.getChildren().clear();
    				try {
    					updateBoard(stage, scene,sp,players,gsm,songKeys,buzzers);
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			else if (buzzers.getPlayers().getFourth().getChoice() != -1) {
    				sp.getChildren().clear();
    				try {
    					updateBoard(stage, scene,sp,players,gsm,songKeys,buzzers);
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			if (buzzers.getDone()) {
    				HashMap<String,Map<String,String>> selectedCats = new HashMap<String,Map<String,String>>();
    				for(int i=0;i<4;i++) {
    					selectedCats.put(buzzers.getPlayers().getPlayer(i+1).getSelectedCategory(),songsGame.get(buzzers.getPlayers().getPlayer(i+1).getSelectedCategory()));
    				}
    				Map<String, Map<String,String>> songs4game = pick4Game(selectedCats);
    				if(gsm != null) {
    					gsm.stop();
    					gsm.dispose();
    				}	
    				sp.getChildren().clear();
    				try {
    					buzzers.join();
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				System.gc();
    				scene.removeEventHandler(KeyEvent.KEY_PRESSED,this);
    				System.out.println("Categories set");
    				try {
    					GameBoard.displayBoard(stage, scene,sp,songs4game,players);
    				} catch (Exception e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    		}
    	};
    		scene.addEventHandler(KeyEvent.KEY_PRESSED, eh);
	}};
