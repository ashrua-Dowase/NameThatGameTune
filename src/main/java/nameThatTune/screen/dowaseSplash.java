package nameThatTune.screen;

import java.io.File;
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.image.*;
import javafx.animation.FadeTransition;


public class dowaseSplash extends Application {
	@Override
    public void start(Stage stage) throws Exception {
		System.setProperty("net.java.games.input.librarypath", new File("natives").getAbsolutePath());
		FileInputStream bgurl = new FileInputStream(nameThatTune.Constants.folderLocation+"/background/background.png");
		StackPane splashScreen = new StackPane();
		Scene scene = new Scene(splashScreen, 1280, 720);
		Image bg = new Image(bgurl);
		BackgroundImage bgImage = new BackgroundImage(bg,BackgroundRepeat.REPEAT,BackgroundRepeat.REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
		splashScreen.setBackground(new Background(bgImage));
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setTitle("Name that Tune! (VGE)");
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        bgurl.close();
        MediaPlayer titleScreenMusic = playSong();
        dowaseLogo(scene,stage,splashScreen,titleScreenMusic);
	}

	public void dowaseLogo(final Scene scene,Stage stage,StackPane SP, MediaPlayer tsm) throws Exception {
		FileInputStream dowaseSplashURL = new FileInputStream(nameThatTune.Constants.folderLocation+"/sprites/spr_Dowase_0.png");
		Image dowaseSplashLogo = new Image(dowaseSplashURL);
		ImageView DSLIV = new ImageView(dowaseSplashLogo);
		SP.getChildren().add(DSLIV);
		StackPane.setAlignment(DSLIV,Pos.CENTER);
		dowaseSplashURL.close();
		FadeTransition ft = new FadeTransition(Duration.millis(3000), DSLIV);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setOnFinished((eventTransition) -> {
	        try {
	        	SP.getChildren().remove(DSLIV);
				titleScreen.titleScreenDisplay(stage,scene,SP,tsm);
			} catch (Exception e) {
				e.printStackTrace();
        }}); 
        ft.play();
	}
	
    public static MediaPlayer playSong() throws Exception {
    	MediaPlayer mediaPlayer = null;
    	try {
        	File path = new File (nameThatTune.Constants.folderLocation+"/music/titleScreen.mp3");
        	if (path.isFile()) {
            	Media titleScreenMusic = new Media(path.toURI().toString()); 
                mediaPlayer = new MediaPlayer(titleScreenMusic);
		        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		        mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();	
        	}
        	else {
        		System.out.println("Music does not exist");
        		System.exit(0);
        	}
    	}
    	catch (Exception e) {
    		System.exit(0);
    	}
        return mediaPlayer;
    }
}
