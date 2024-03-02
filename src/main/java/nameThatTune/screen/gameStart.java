package nameThatTune.screen;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.SubnodeConfiguration;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
		//displayNames(stage,currentScene,sp,player1,player2,player3,player4);
		Map<String,Map<String,String>> songlist = loadSongs();
		Map<String, Map<String,String>> songs4game = pick4Game(songlist);
		gsm.stop();
		gsm.dispose();
		GameBoard.displayBoard(stage, currentScene,sp,songs4game,gameGroup);
	}
	
	public static Map<String, Map<String,String>> loadSongs() throws Exception {
		File songListFile = new File("songList.dow");
		INIConfiguration iniConfiguration = new INIConfiguration();
		try (FileReader fileReader = new FileReader(songListFile)) {
		    iniConfiguration.read(fileReader);
		}
		Map<String, Map<String, String>> songList = new HashMap<>();

		for (String section : iniConfiguration.getSections()) {
		    Map<String, String> subSectionMap = new HashMap<>();
		    SubnodeConfiguration confSection = iniConfiguration.getSection(section);
		    Iterator<String> keyIterator = confSection.getKeys();
		    while (keyIterator.hasNext()) {
		        String key = keyIterator.next();
		        String value = confSection.getProperty(key).toString();
		        subSectionMap.put(key, value);
		    }
		    songList.put(section, subSectionMap);
		}
		/*
		 * Text text = new Text(songList.toString()); text.setFill(Color.WHITE);
		 * text.setStyle("-fx-font: normal 16px 'Arial' "); sp.getChildren().add(text);
		 */
		return songList;
	}
	
	public static Map<String,Map<String,String>> pick4Game(Map<String,Map<String,String>> sl){
		Map<String,Map<String,String>> list4Game = sl;
		int max = sl.size()-1;
		Long seed = System.currentTimeMillis();
		Random randomize = new Random(seed);
		
		//CAT 1
		int randomInt = randomize.nextInt(max);
		Set<String> slcat = sl.keySet();
		Object[] slcatArray = slcat.toArray();
		String category1 = slcatArray[randomInt].toString();
		Map<String,String> cat1object = sl.get(category1);
		sl.remove(category1);
		max--;
		
		//CAT 2
		randomInt = randomize.nextInt(max);
		slcat = sl.keySet();
		slcatArray = slcat.toArray();
		String category2 = slcatArray[randomInt].toString();
		Map<String,String> cat2object = sl.get(category2);
		sl.remove(category2);
		max--;
		
		//CAT 3
		randomInt = randomize.nextInt(max);
		slcat = sl.keySet();
		slcatArray = slcat.toArray();
		String category3 = slcatArray[randomInt].toString();
		Map<String,String> cat3object = sl.get(category3);
		sl.remove(category3);
		max--;
		
		//CAT 4
		randomInt = randomize.nextInt(max);
		slcat = sl.keySet();
		slcatArray = slcat.toArray();
		String category4 = slcatArray[randomInt].toString();
		Map<String,String> cat4object = sl.get(category4);
		sl.clear();
		
		System.out.println("Categories set");
		list4Game.put(category1,cat1object);
		list4Game.put(category2,cat2object);
		list4Game.put(category3,cat3object);
		list4Game.put(category4,cat4object);
		
		//Get 4 Songs for each category
		Map<String,String> c1sl = getSongs(list4Game,cat1object,category1);
		Map<String,String> c2sl = getSongs(list4Game,cat2object,category2);
		Map<String,String> c3sl = getSongs(list4Game,cat3object,category3);
		Map<String,String> c4sl = getSongs(list4Game,cat4object,category4);
		
		//Remove category from list4Game and repopulate
		list4Game.remove(category1);
		list4Game.put(category1, c1sl);
		list4Game.remove(category2);
		list4Game.put(category2, c2sl);
		list4Game.remove(category3);
		list4Game.put(category3, c3sl);
		list4Game.remove(category4);
		list4Game.put(category4, c4sl);
		return list4Game;
	}
	
	public static Map<String,String> getSongs(Map<String,Map<String,String>> sl,Map<String,String> cat,String category) {
		Map<String,String> csl = sl.get(category);
		Map<String,String> finalCat = new HashMap<>();
		//Song 1
		Set<String> key = csl.keySet();
		Random randomize = new Random();
		Object[] cslArray = key.toArray();
		int max = key.size();
		int randomInt = randomize.nextInt(max);
		String songc = cslArray[randomInt].toString();
		finalCat.put(songc,csl.get(songc));
		csl.remove(songc);
		//Song 2
		key = csl.keySet();
		cslArray = key.toArray();
		max = key.size();
		randomInt = randomize.nextInt(max);
		songc = cslArray[randomInt].toString();
		finalCat.put(songc, csl.get(songc));
		csl.remove(songc);
		//Song 3
		key = csl.keySet();
		cslArray = key.toArray();
		max = key.size();
		randomInt = randomize.nextInt(max);
		songc = cslArray[randomInt].toString();
		finalCat.put(songc, csl.get(songc));
		csl.remove(songc);
		//Song 4
		key = csl.keySet();
		cslArray = key.toArray();
		max = key.size();
		randomInt = randomize.nextInt(max);
		songc = cslArray[randomInt].toString();
		finalCat.put(songc, csl.get(songc));
		csl.remove(songc);
		return finalCat;
	}
	/*
	public static Map<String,String> putSonginCategory(Map<String,String> csl,Map<String,String> finalCat){
		Set<String> key = csl.keySet();
		Random randomize = new Random();
		Object[] cslArray = key.toArray();
		int max = key.size();
		int randomInt = randomize.nextInt(max);
		String songc = cslArray[randomInt].toString();
		finalCat.put(songc,csl.get(songc));
		csl.remove(songc);
		return finalCat;
	}
	*/
	public static String setName(Player player,Stage stage) {
        TextInputDialog inputdialog = new TextInputDialog(player.getPlayerName());
        inputdialog.setContentText("Name:");
        inputdialog.setHeaderText("Please enter "+player.getPlayerName()+"'s name");
        inputdialog.initOwner(stage);
        inputdialog.showAndWait();
        return inputdialog.getEditor().getText();
		
	}
	
	public static MediaPlayer playSong() {
    	File path = new File (nameThatTune.Constants.folderLocation+"/music/competition.mp3");
    	Media gameSetupMusic = new Media(path.toURI().toString()); 
        MediaPlayer mediaPlayer = new MediaPlayer(gameSetupMusic);
        mediaPlayer.play();
        return mediaPlayer;
	}
}
