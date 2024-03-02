package nameThatTune.screen;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import nameThatTune.model.GamePads;
import nameThatTune.model.Group;
import nameThatTune.model.Player;

public class GameBoard {
	static Player[] player = new Player[4];
	
	public static void displayBoard(Stage stage,Scene scene,StackPane sp,Map<String, Map<String,String>> songsGame,Group players) throws Exception {
		//////////////Players from Group//////////////////
		player[0] = players.getFirst();
		player[1] = players.getSecond();
		player[2] = players.getThird();
		player[3] = players.getFourth();
		
		Map<String,Integer> used = new HashMap<String,Integer>();
		Long seed = System.currentTimeMillis();
		Random randomizer = new Random(seed);
		int currentPlayer = randomizer.nextInt(3);
		Player currentPlayerObj = player[currentPlayer];
		
		
		Set<String> songList = songsGame.keySet();
		Iterator<String> sliter = songList.iterator();
		////////////////////Draw the board//////////////////
		Double stageH = stage.getHeight();
		Double stageW = stage.getWidth();
		///////////////////////////////Category 1///////////////
		for (int i=3;i>=0;i--) {
				String catName = sliter.next();
				used.put(catName, 0);
				drawcategory(sp,catName,i,stageH,stageW,used);
		}
		/////////////Show the Active Player//////////////
		Text currentPlayerDisp = new Text(currentPlayerObj.getPlayerName());
		sp.getChildren().add(currentPlayerDisp);
        FadeTransition cpdft = new FadeTransition(Duration.millis(3000), currentPlayerDisp);
        cpdft.setFromValue(0.0);
        cpdft.setToValue(1.0);
        cpdft.play();
		currentPlayerDisp.setStyle("-fx-font: normal 16px 'Arial' ");
		currentPlayerDisp.setFill(Color.WHITE);
		StackPane.setAlignment(currentPlayerDisp, Pos.BOTTOM_LEFT);
		StackPane.setMargin(currentPlayerDisp, new Insets(0,0,stageH/16,0));
		///////////////Start new thread for buzzers////////
		GamePads buzzers = null;
		buzzers = new GamePads(currentPlayerObj,"main",used);
		buzzers.start();
		waitForInput(stage,scene,sp,players,buzzers,songsGame,currentPlayerObj,used);
	}
	public static void displayBoard(Stage stage,Scene scene, StackPane sp,Group players,Map<String, Map<String,String>> songsGame,Player currentPlayerObj,Map<String,Integer> used,GamePads buzzers) throws Exception {
		player[0] = players.getFirst();
		player[1] = players.getSecond();
		player[2] = players.getThird();
		player[3] = players.getFourth();
		Set<String> songList = songsGame.keySet();
		Iterator<String> sliter = songList.iterator();
		
		Double stageH = stage.getHeight();
		Double stageW = stage.getWidth();
		for (int i=3;i>=0;i--) {
			String catName = sliter.next(); 
			drawcategory(sp,catName,i,stageH,stageW,used);
		}
		///////////////Start new thread for buzzers////////
		Text currentPlayerDisp = new Text(currentPlayerObj.getPlayerName());
		sp.getChildren().add(currentPlayerDisp);
        FadeTransition cpdft = new FadeTransition(Duration.millis(3000), currentPlayerDisp);
        cpdft.setFromValue(0.0);
        cpdft.setToValue(1.0);
        cpdft.play();
		currentPlayerDisp.setStyle("-fx-font: normal 16px 'Arial' ");
		currentPlayerDisp.setFill(Color.WHITE);
		StackPane.setAlignment(currentPlayerDisp, Pos.BOTTOM_LEFT);
		StackPane.setMargin(currentPlayerDisp, new Insets(0,0,stageH/16,0));
		buzzers.setType("main");
		waitForInput(stage,scene,sp,players,buzzers,songsGame,currentPlayerObj,used);
	}
	
	public static void drawcategory(StackPane sp,String catName,int place,Double h,Double w,Map<String,Integer> used) throws Exception {
		Text categoryDisp = new Text(catName);
		FileInputStream category = new FileInputStream(nameThatTune.Constants.folderLocation+"/sprites/spr_category_"+place+".png");
		Image catImage = new Image(category);
		sp.getChildren().add(categoryDisp);
		StackPane.setAlignment(categoryDisp, Pos.TOP_LEFT);
		categoryDisp.setStyle("-fx-font: normal 16px 'Arial' ");
		categoryDisp.setFill(Color.WHITE);
        FadeTransition ftName = new FadeTransition(Duration.millis(3000), categoryDisp);
        ftName.setFromValue(0.0);
        ftName.setToValue(1.0);
        ftName.play();
		StackPane.setMargin(categoryDisp, new Insets(h/7,0,0,(w/4)*place));
		int usedint = used.get(catName);
		int unused = 5-usedint;
		for (int i=1;i<unused;i++) {
			drawQuestion(sp,catImage,i,place,h,w);
		}
		category.close();
	}
	
	public static void drawQuestion(StackPane sp,Image catImage,int number,int place,Double h,Double w) {
		ImageView catIV = new ImageView(catImage);
		sp.getChildren().add(catIV);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), catIV);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
        StackPane.setMargin(catIV, new Insets((h/5)*number,0,0,(w/4)*place));
		StackPane.setAlignment(catIV,Pos.TOP_LEFT);
	}
	public static void waitForInput(Stage stage, Scene scene, StackPane sp, Group players,GamePads buzzers,Map<String, Map<String,String>> songsGame,Player currentPlayerObj,Map<String,Integer> used) throws Exception {
		Timeline tl = new Timeline(
                new KeyFrame(Duration.millis(33.33333), 
                new EventHandler<ActionEvent>() {
                	boolean isPicked = false;
                	@Override
                	public void handle(ActionEvent event) {
                		if(!isPicked) {
                			try {
                				if(buzzers.getPick()!=-1) {
                					isPicked = true;
                					Set<String> keyset = songsGame.keySet();
                					int usedInt = 0;
                					String[] catString = new String[4];
                					int i = 0;
                					for (String cat:keyset) {
                						catString[i] = cat;
                						i++;
                					}
                					int pick = buzzers.getPick();
                					String thisCategoryString = catString[buzzers.getPick()];
                					if (used.containsKey(thisCategoryString)) {
                						usedInt = used.get(thisCategoryString);
                						usedInt += 1;
                					}
                					used.replace(thisCategoryString,usedInt);
                					sp.getChildren().clear();
                					buzzers.join();
                					buzzers.setDone(true);
                					System.gc();
                					Question.displayQuestionBoard(stage, scene, sp,players,pick,songsGame,currentPlayerObj,used);
                				}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                		}
                		
   		}
                }));
	tl.setCycleCount(Timeline.INDEFINITE);
	tl.play();
	}
}
