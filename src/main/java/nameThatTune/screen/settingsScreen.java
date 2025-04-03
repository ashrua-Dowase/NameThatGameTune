package nameThatTune.screen;

import java.io.File;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class settingsScreen {
	public AudioClip mediaPlayer;
	public static Integer cursor = 0;
	
	public static void settingsScreenDisplay(Stage stage,Scene currentScene, StackPane sp,AudioClip tickMediaPlayer,MediaPlayer tsm) throws Exception {
		MediaPlayer ssm = playSong();
		displayMenu(sp,stage,currentScene,tickMediaPlayer, tsm, ssm);
	}

	public static void displayMenu(StackPane sp, Stage stage, Scene scene,AudioClip tickMediaPlayer,MediaPlayer tsm,MediaPlayer ssm) throws Exception {
		cursor = 0;
    	File sou_toggle = new File (nameThatTune.Constants.folderLocation+"/music/sou_toggle.wav");
    	Media sou_toggleMedia = new Media(sou_toggle.toURI().toString());
    	AudioClip toggleMediaPlayer = new AudioClip(sou_toggleMedia.getSource());
    	Integer musicStyle = 0;
    	tickMediaPlayer.setVolume(0.25);
    	Label header = new Label ("Options:");
    	StackPane.setAlignment(header, Pos.TOP_CENTER);
    	Text fullscreen = new Text();
    	header.setTextFill(Color.WHITE);
    	if (stage.isFullScreen()) {
    		fullscreen.setText("\n\n\n\nFullscreen [x]");
    	}
    	else {
    		fullscreen.setText("\n\n\n\nFullscreen []");	
    	}
    	fullscreen.setStyle("-fx-font: normal 16px 'Arial' ");
    	fullscreen.setFill(Color.CYAN);
    	StackPane.setAlignment(fullscreen, Pos.TOP_CENTER);
    	Text debug = new Text("\n\n\n\n\n\n\n\nDebug Game");
    	debug.setStyle("-fx-font: normal 16px 'Arial' ");
    	debug.setFill(Color.WHITE);
    	StackPane.setAlignment(debug, Pos.TOP_CENTER);
    	Text songSelection = new Text("\n\n\n\n\n\n\n\n\n\n\nTitle Screen Music Style : ORIGINAL");
    	songSelection.setStyle("-fx-font: normal 16px 'Arial' ");
    	songSelection.setFill(Color.WHITE);
    	StackPane.setAlignment(songSelection, Pos.TOP_CENTER);
    	Text backToMM = new Text("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nBack to Main Menu");
    	backToMM.setStyle("-fx-font: normal 16px 'Arial' ");
    	backToMM.setFill(Color.WHITE);
    	StackPane.setAlignment(backToMM, Pos.TOP_CENTER);
    	sp.getChildren().add(header);
    	sp.getChildren().add(fullscreen);
    	sp.getChildren().add(debug);
    	sp.getChildren().add(songSelection);
    	sp.getChildren().add(backToMM);
    	
    	//setStyle("-fx-font: normal 16px 'Arial' ");
    	EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
            	if (event.getCode().toString().equals("UP")) {
            		tickMediaPlayer.play();
            		cursor -= 1;
            		if (cursor == 0) {
            			fullscreen.setFill(Color.CYAN);
            			debug.setFill(Color.WHITE);
            			songSelection.setFill(Color.WHITE);
            			backToMM.setFill(Color.WHITE);
            		}
            		else if (cursor == 1) {
            			fullscreen.setFill(Color.WHITE);
            			debug.setFill(Color.CYAN);
            			songSelection.setFill(Color.WHITE);
            			backToMM.setFill(Color.WHITE);
            		}
            		else if (cursor == 2) {
            			fullscreen.setFill(Color.WHITE);
            			debug.setFill(Color.WHITE);
            			songSelection.setFill(Color.CYAN);
            			backToMM.setFill(Color.WHITE);
            		}
            		else if (cursor == 3) {
            			fullscreen.setFill(Color.WHITE);
            			debug.setFill(Color.WHITE);
            			songSelection.setFill(Color.WHITE);
            			backToMM.setFill(Color.CYAN);
            		}	
            		else if (cursor < 0) {
            			cursor = 3;
            			fullscreen.setFill(Color.WHITE);
            			debug.setFill(Color.WHITE);
            			songSelection.setFill(Color.WHITE);
            			backToMM.setFill(Color.CYAN);            			            			
            		}
    		}
            	if (event.getCode().toString().equals("DOWN")) {
            		tickMediaPlayer.play();
            		cursor += 1;
            		if (cursor < 0) {
            			fullscreen.setFill(Color.WHITE);            			
            			debug.setFill(Color.WHITE);
            			songSelection.setFill(Color.WHITE);
            			backToMM.setFill(Color.CYAN);
            		}
            		if (cursor == 0) {
            			fullscreen.setFill(Color.CYAN);
            			debug.setFill(Color.WHITE);
            			songSelection.setFill(Color.WHITE);
            			backToMM.setFill(Color.WHITE);
            		}
            		else if (cursor == 1) {
            			fullscreen.setFill(Color.WHITE);
            			debug.setFill(Color.CYAN);
            			songSelection.setFill(Color.WHITE);
            			backToMM.setFill(Color.WHITE);
            		}
            		else if (cursor == 2) {
            			fullscreen.setFill(Color.WHITE);            			
            			debug.setFill(Color.WHITE);
            			songSelection.setFill(Color.CYAN);
            			backToMM.setFill(Color.WHITE);
            		}
            		else if (cursor == 3) {
            			fullscreen.setFill(Color.WHITE);
            			debug.setFill(Color.WHITE);
            			songSelection.setFill(Color.WHITE);
            			backToMM.setFill(Color.CYAN);
            		}
            		else if (cursor > 3) {
            			cursor = 0;
            			fullscreen.setFill(Color.CYAN);
            			debug.setFill(Color.WHITE);
            			songSelection.setFill(Color.WHITE);
            			backToMM.setFill(Color.WHITE);
            		}
    		}
            	else if (event.getCode().toString().equals("Z") || event.getCode().toString().equals("ENTER")) {
            		if (cursor == 0) {
            			toggleMediaPlayer.play();
            			if (stage.isFullScreen() == true) {
            				stage.setFullScreen(false);
            				fullscreen.setText("\n\n\n\nFullscreen []");
            			}
            			else {
            				stage.setFullScreen(true);
            				fullscreen.setText("\n\n\n\nFullscreen [x]");
            			}
            		}
            		else if (cursor == 1) {
            			
            		}
            		else if (cursor == 2) {
            			if (musicStyle == 0) {
            				
            			}
            		}
            		else if (cursor == 3) {
            			try {
            				tickMediaPlayer.play();
            				scene.removeEventHandler(KeyEvent.KEY_PRESSED,this);
            				sp.getChildren().clear();
            				ssm.stop();
            				ssm.dispose();
							titleScreen.titleScreenDisplay(stage, scene, sp, tsm);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		}
            	}
    		}};
    		scene.addEventHandler(KeyEvent.KEY_PRESSED, eh);
	};
	
    public static MediaPlayer playSong() throws Exception {
    	File path = new File (nameThatTune.Constants.folderLocation+"/music/options.mp3");
    	Media settingsScreenMusic = new Media(path.toURI().toString()); 
        MediaPlayer mediaPlayer = new MediaPlayer(settingsScreenMusic);
        mediaPlayer.play();
        return mediaPlayer;
    }
}
