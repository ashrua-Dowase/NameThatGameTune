package nameThatTune.screen;
	
import java.io.File;
import java.io.FileInputStream;

import com.sun.glass.ui.Menu;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text; 
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;


public class titleScreen {
	public static String VER_NO = "Version 0.10";
	public MediaPlayer mediaPlayer;
	public static Integer cursor = 0;

    public static void titleScreenDisplay(Stage stage,Scene currentScene, StackPane sp, MediaPlayer tsm) throws Exception {
    	FileInputStream bgurl = new FileInputStream(nameThatTune.Constants.folderLocation+"/background/background.png");
		Image bg = new Image(bgurl);
		BackgroundImage bgImage = new BackgroundImage(bg,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		StackPane titleScreen = new StackPane();
		titleScreen.setBackground(new Background(bgImage));
        File styleSheetURL = new File("src/main/resources/stylesheets/title.css");
        titleScreen.getStylesheets().add(styleSheetURL.toURI().toString());
        stage.setScene(currentScene);
        titleName(sp, stage);
        kurisuSpr(sp,stage);
        dowaseTitleLogo(sp,stage);
        menu(sp,stage,currentScene,tsm);
        versionNumber(sp, stage);
        if (!(tsm.getStatus().equals(Status.PLAYING))) {
        	tsm.play();
        }
    }
	
    public static void titleName(StackPane mainScene, Stage stage) throws Exception {
    	File path = new File (nameThatTune.Constants.folderLocation+"/fonts/gaslight.ttf");
    	Font font = Font.loadFont(path.toURI().toString(), 72);
		Text text = new Text("\nName that Tune\nVideo Game Edition!");
		StackPane.setAlignment(text,Pos.TOP_CENTER);
		text.setFont(font);
        text.setFill(Color.WHITE);
        text.setOpacity(0);
		mainScene.getChildren().add(text);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), text);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }
    public static void kurisuSpr(StackPane mainScene,Stage stage) throws Exception {
    	FileInputStream kurisuURL = new FileInputStream(nameThatTune.Constants.folderLocation+"/sprites/spr_kurisu_0.png");
    	Image kurisuImage = new Image(kurisuURL);
    	ImageView kurisuIV = new ImageView(kurisuImage);
    	mainScene.getChildren().add(kurisuIV);
    	kurisuIV.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), kurisuIV);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    	StackPane.setAlignment(kurisuIV,Pos.BOTTOM_RIGHT);
    	kurisuURL.close();
    }
    
    public static void dowaseTitleLogo(StackPane mainStage,Stage stage) throws Exception {
    	FileInputStream dowaseTitle = new FileInputStream(nameThatTune.Constants.folderLocation+"/sprites/spr_dlogo_0.png");
    	Image dowaseTitleImage = new Image(dowaseTitle);
    	ImageView dtiiv = new ImageView(dowaseTitleImage);
    	dtiiv.setFitHeight(100);
    	dtiiv.setFitWidth(100);
    	dtiiv.setOpacity(0);
    	mainStage.getChildren().add(dtiiv);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), dtiiv);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    	StackPane.setAlignment(dtiiv, Pos.TOP_RIGHT);
    	dowaseTitle.close();
    }
    
    public static void menu(final StackPane mainStage,Stage stage,Scene scene,MediaPlayer tsm) throws Exception {
    	cursor = 0;
    	File sou_tick = new File (nameThatTune.Constants.folderLocation+"/music/sou_tick.wav");
    	Media sou_tickMedia = new Media(sou_tick.toURI().toString());
    	AudioClip tickMediaPlayer = new AudioClip(sou_tickMedia.getSource());
    	tickMediaPlayer.setVolume(0.25);
    	Label start = new Label("Start");
    	Label options = new Label("\n\n\n\nOptions");
    	Label quit = new Label("\n\n\n\n\n\n\n\nQuit Game");
    	start.setStyle("-fx-font: normal 16px 'Arial' ");
    	options.setStyle("-fx-font: normal 16px 'Arial' ");
    	quit.setStyle("-fx-font: normal 16px 'Arial' ");
		start.setTextFill(Color.CYAN);
		options.setTextFill(Color.WHITE);
		quit.setTextFill(Color.WHITE);
		EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
	            @Override
	            public void handle(KeyEvent event) {
	            	if (event.getCode().toString().equals("UP")) {
	            		tickMediaPlayer.play();
	            		cursor -= 1;
	                    if (cursor == 0) {
	                    	start.setTextFill(Color.CYAN);
	                    	options.setTextFill(Color.WHITE);
	                    	quit.setTextFill(Color.WHITE);
	                        	
	                    	}
	                    else if (cursor == 1) {
	                    	start.setTextFill(Color.WHITE);
	                    	options.setTextFill(Color.CYAN);
	                    	quit.setTextFill(Color.WHITE);
	                    	}
	                    else if (cursor == 2) {
	                    	start.setTextFill(Color.WHITE);
	                    	options.setTextFill(Color.WHITE);
	                    	quit.setTextFill(Color.CYAN);
	                    	}
	                    else if (cursor < 0) {
	                    	cursor = 2;
	                    	start.setTextFill(Color.WHITE);
	                    	options.setTextFill(Color.WHITE);
	                    	quit.setTextFill(Color.CYAN);                    	
	                    }
	                }
	                else if (event.getCode().toString().equals("DOWN")){
	            		cursor += 1;
	            		tickMediaPlayer.play();
	                	if (cursor == 0) {
	                		start.setTextFill(Color.CYAN);
	                		options.setTextFill(Color.WHITE);
	                		quit.setTextFill(Color.WHITE);
	                    	
	                	}
	                	else if (cursor == 1) {
	                		start.setTextFill(Color.WHITE);
	                		options.setTextFill(Color.CYAN);
	                		quit.setTextFill(Color.WHITE);
	                	}
	                	else if (cursor == 2) {
	                		start.setTextFill(Color.WHITE);
	                		options.setTextFill(Color.WHITE);
	                		quit.setTextFill(Color.CYAN);
	                	}
	                	else if (cursor > 2) {
	                		cursor = 0;
	                		start.setTextFill(Color.CYAN);
	                		options.setTextFill(Color.WHITE);
	                		quit.setTextFill(Color.WHITE);
	                	}
	            	}
	                else if (event.getCode().toString().equals("Z")) {
	                	if (cursor == 0) {
	                		mainStage.getChildren().clear();
	                		try {
	                			scene.removeEventHandler(KeyEvent.KEY_PRESSED,this);
	                			tsm.stop();
	                			gameStart.getNames(stage,scene,mainStage);
	                		} catch (Exception e) {
	                			e.printStackTrace();
	                		}
	                	}
	                	else if (cursor == 1) {
	            			mainStage.getChildren().clear();
	            			try {
	            				scene.removeEventHandler(KeyEvent.KEY_PRESSED,this);
	            				tsm.stop();
								settingsScreen.settingsScreenDisplay(stage, scene, mainStage, tickMediaPlayer,tsm);
							} catch (Exception e) {
								e.printStackTrace();
							}
	            		}
	            		else if (cursor == 2) {
	            			stage.close();
	            		}
	            		
	            	}
	                else if (event.getCode().toString().equals("ENTER")) {
	                	if (cursor == 0) {
	                		mainStage.getChildren().clear();
	                		try {
	                			scene.removeEventHandler(KeyEvent.KEY_PRESSED,this);
	                			tsm.stop();
	                			tsm.dispose();
	                			System.gc();
	                			gameStart.getNames(stage,scene,mainStage);
	                		} catch (Exception e) {
	                			e.printStackTrace();
	                		}
	                	}
	            		if (cursor == 1) {
	            			mainStage.getChildren().clear();
	            			try {
	            				scene.removeEventHandler(KeyEvent.KEY_PRESSED,this);
	            				tsm.stop();
								settingsScreen.settingsScreenDisplay(stage, scene, mainStage, tickMediaPlayer,tsm);
							} catch (Exception e) {
								e.printStackTrace();
							}
	            		}
	            		else if (cursor == 2) {
	            			stage.close();
	            		}
	            		
	            	}
	            }
		};
		scene.addEventHandler(KeyEvent.KEY_PRESSED, eh);
        start.setOpacity(0);
        options.setOpacity(0);
        quit.setOpacity(0);
        
        ///////ADDING TO SP///////
    	mainStage.getChildren().add(start);
    	mainStage.getChildren().add(options);
    	mainStage.getChildren().add(quit);
    	
    	///////FADES/////
        FadeTransition ftstart = new FadeTransition(Duration.millis(3000), start);
        ftstart.setFromValue(0.0);
        ftstart.setToValue(1.0);
        ftstart.play();
        FadeTransition ftoptions = new FadeTransition(Duration.millis(3000), options);
        ftoptions.setFromValue(0.0);
        ftoptions.setToValue(1.0);
        ftoptions.play();
        FadeTransition ftquit = new FadeTransition(Duration.millis(3000), quit);
        ftquit.setFromValue(0.0);
        ftquit.setToValue(1.0);
        ftquit.play();
    }
    
    
    public static void versionNumber(StackPane mainStage, Stage stage) throws Exception {
    	Label versionNumber = new Label(VER_NO);
    	versionNumber.setTextFill(Color.WHITE);
    	versionNumber.setStyle("-fx-font: normal 12px 'Arial' ");
    	mainStage.getChildren().add(versionNumber);
    	versionNumber.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), versionNumber);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    	StackPane.setAlignment(versionNumber, Pos.BOTTOM_CENTER);
    }
    
    public void controller (Scene scene) {
    }
}