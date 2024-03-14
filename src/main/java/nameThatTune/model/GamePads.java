package nameThatTune.model;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class GamePads extends Thread{
	int pick = -1;
	String type = "New String";
	Player currentPlayer,playerBuzzed;
	Group players;
	Boolean done = false;
	Boolean buzzed = false;
	Map<String,Integer> used;
	AudioClip buzzedSound = initBuzzedSound();
	AudioClip wrongSound = initWrongSound();
	MediaPlayer songToPlay;
	String[] catString;
	ImageView[] selected;
	
	public GamePads(Player currentPlayerObj) {
		currentPlayer = currentPlayerObj;
	}
	public GamePads(Player currentPlayerObj,String type,Map<String,Integer> used) {
		this.setCurrentPlayer(currentPlayerObj);
		this.setType(type);
		this.setPick(-1);
		this.setUsed(used);
		Set<String> keyset = this.getUsed().keySet();
		String[] catString = new String[4];
		int i = 0;
		for (String cat:keyset) {
			catString[i] = cat;
			i++;
			}
		this.setCatString(catString);
	}
	public GamePads(String type) {
		this.setType(type);
		this.setPick(-1);
	}
	public GamePads(String type,Group players) {
		this.setType(type);
		this.setPlayers(players);
		this.getPlayers().getFirst().setChoice(-1);
		this.getPlayers().getSecond().setChoice(-1);
		this.getPlayers().getThird().setChoice(-1);
		this.getPlayers().getFourth().setChoice(-1);
		this.setDone(false);
		this.setBuzzed(false);
	}
	public GamePads(String type,Group players,MediaPlayer music,ImageView[] selected) {
		this.setType(type);
		if (this.getType().equals("game")) {
			this.setPlayers(players);
			this.getPlayers().getFirst().sethasBuzzed(false);
			this.getPlayers().getSecond().sethasBuzzed(false);
			this.getPlayers().getThird().sethasBuzzed(false);
			this.getPlayers().getFourth().sethasBuzzed(false);
			this.setDone(false);
			this.setBuzzed(false);
			this.setPlayerBuzzed(new Player("",5));
			this.setSongToPlay(music);
			this.getSongToPlay().setOnReady(new Runnable() {
			    @Override
			    public void run() {
			        Random r = new Random((long) Math.random());
			        Double songLength = getSongToPlay().getMedia().getDuration().toSeconds();
			        Double halfWay = songLength / 2;
			        Double seekPoint = r.nextDouble(halfWay);
			        getSongToPlay().setCycleCount(MediaPlayer.INDEFINITE);
			    	getSongToPlay().seek(Duration.seconds(seekPoint));
			    	getSongToPlay().play();
			    }
			});
			this.selected = selected;
		}
	}
	public void run() {
		Event event = new Event();
		Controller[] controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		if (this.type.equals("main") && !this.getDone()) {
			int choice;
			while (this.getPick() == -1) {
				for (int j = 0; j < controllers.length; j++) {
					if (controllers[j].getName().equals("Buzz")){
						controllers[j].poll();			
						EventQueue queue = controllers[j].getEventQueue();
						while (queue.getNextEvent(event)) {
							Component comp = event.getComponent();								
							if (currentPlayer.getPosition() == 1) {
								if (comp.getName().equals("Button 1")) {
									//Blue
									choice = 0;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().equals("Button 2")) {
									//Orange
									choice = 1;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}	
								else if (comp.getName().equals("Button 3")) {
									//Green
									choice = 2;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().equals("Button 4")) {
									//Yella
									choice = 3;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
							}
							else if (currentPlayer.getPosition() == 2) {
								if (comp.getName().contains("6")) {
									//Blue
									choice = 0;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().contains("7")) {
									//Orange
									choice = 1;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().contains("8")) {
									//Green
									choice = 2;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().contains("9")) {
									//Yella
									choice = 3;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
							}
							else if (currentPlayer.getPosition() == 3) {
								if (comp.getName().contains("11")) {
									//Blue
									choice = 0;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().contains("12")) {
									//Orange
									choice = 1;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().contains("13")) {
									//Green
									choice = 2;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().contains("14")) {
									//Yella
									choice = 3;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
							}
							else if (currentPlayer.getPosition() == 4) {
								if (comp.getName().contains("16")) {
									//Blue
									choice = 0;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().contains("17")) {
									//Orange
									choice = 1;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().contains("18")) {
									//Green
									choice = 2;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
								else if (comp.getName().contains("19")) {
									//Yella
									choice = 3;
									if (this.used.get(catString[choice]) < 4) {
										this.setPick(choice);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		else if (this.getType().equals("game") && !this.getDone()){
			while (!this.getDone()) {
				if(!this.getBuzzed()) {
					for (int j = 0; j < controllers.length; j++) {
						if (controllers[j].getName().equals("Buzz")){
							controllers[j].poll();
							EventQueue queue = controllers[j].getEventQueue();
							while (queue.getNextEvent(event)) {
								Component comp = event.getComponent();
								if ((comp.getName().equals("Button 0"))) {
									if ((this.getPlayerBuzzed().getPosition() != 1) && !(this.getPlayers().getFirst().gethasBuzzed())) {
										this.setBuzzed(true);
										this.setPlayerBuzzed(this.getPlayers().getFirst());
										this.getPlayerBuzzed().sethasBuzzed(true);
										if (this.getSongToPlay().getStatus().equals(Status.PLAYING)) {
											this.getSongToPlay().pause();
										}
										ImageView selectedImageView = this.getSelected()[0];
										selectedImageView.setOpacity(100);
										this.getBuzzedSound().play();
										this.setCurrentPlayer(getPlayerBuzzed());
										//System.out.println("buzz");
									}
								}
								else if (comp.getName().equals("Button 5")) {
									if((this.getPlayerBuzzed().getPosition() != 2) && !(this.getPlayers().getSecond().gethasBuzzed())) {
										this.setPlayerBuzzed(this.getPlayers().getSecond());
										this.setBuzzed(true);
										this.getPlayerBuzzed().sethasBuzzed(true);
										if (this.getSongToPlay().getStatus().equals(Status.PLAYING)) {
											this.getSongToPlay().pause();
										}
										ImageView selectedImageView = this.getSelected()[1];
										selectedImageView.setOpacity(100);
										this.getBuzzedSound().play();
										this.setCurrentPlayer(getPlayerBuzzed());
										//System.out.println("buzz");
									}
								}
								else if (comp.getName().equals("Button 10")) {
									if ((this.getPlayerBuzzed().getPosition() != 3) && !(this.getPlayers().getThird().gethasBuzzed())) {
										this.setPlayerBuzzed(this.getPlayers().getThird());
										this.setBuzzed(true);
										this.getPlayerBuzzed().sethasBuzzed(true);
										if (this.getSongToPlay().getStatus().equals(Status.PLAYING)) {
											this.getSongToPlay().pause();
										}
										ImageView selectedImageView = this.getSelected()[2];
										selectedImageView.setOpacity(100);
										this.getBuzzedSound().play();
										this.setCurrentPlayer(getPlayerBuzzed());
										//System.out.println("buzz");
									}
								}
								else if (comp.getName().equals("Button 15")) {
									if((this.getPlayerBuzzed().getPosition() != 4) && !(this.getPlayers().getFourth().gethasBuzzed())) {
										this.setPlayerBuzzed(this.getPlayers().getFourth());
										this.setBuzzed(true);
										this.getPlayerBuzzed().sethasBuzzed(true);
										if (this.getSongToPlay().getStatus().equals(Status.PLAYING)) {
											this.getSongToPlay().pause();
										}
										ImageView selectedImageView = this.getSelected()[3];
										selectedImageView.setOpacity(100);
										this.getBuzzedSound().play();
										this.setCurrentPlayer(getPlayerBuzzed());
										//System.out.println("buzz");
									}
								}
							}
						}
					}
				}
			}
		}
		else if (this.type.equals("setup") && !this.getDone())
		{
			while (!this.getDone()) {
				for (int j = 0; j < controllers.length; j++) {
					if (controllers[j].getName().equals("Buzz")){
						controllers[j].poll();			
						EventQueue queue = controllers[j].getEventQueue();
						while (queue.getNextEvent(event)) {
							Component comp = event.getComponent();								
							if (comp.getName().equals("Button 4")) {
								//Blue
								if (!getPlayers().getFirst().getHasBuzzed()) {
									this.getPlayers().getFirst().setChoice(0);
									this.getPlayers().getFirst().setHasBuzzed(true);
								}
							}
							else if (comp.getName().equals("Button 3")) {
								//Orange
								if (!getPlayers().getFirst().getHasBuzzed()) {
									this.getPlayers().getFirst().setChoice(1);
									this.getPlayers().getFirst().setHasBuzzed(true);
								}
							}	
							else if (comp.getName().equals("Button 2")) {
								//Green
								if (!getPlayers().getFirst().getHasBuzzed()) {
									this.getPlayers().getFirst().setChoice(2);
									this.getPlayers().getFirst().setHasBuzzed(true);
								}
							}
							else if (comp.getName().equals("Button 1")) {
								//Yella
								if (!getPlayers().getFirst().getHasBuzzed()) {
									this.getPlayers().getFirst().setChoice(3);
									this.getPlayers().getFirst().setHasBuzzed(true);
								}
							}
							else if (comp.getName().equals("Button 6")) {
								//Yella
								if (!getPlayers().getSecond().getHasBuzzed()) {
									this.getPlayers().getSecond().setChoice(3);
									this.getPlayers().getSecond().setHasBuzzed(true);
								}
							}
							else if (comp.getName().equals("Button 7")) {
								//Green
								if (!getPlayers().getSecond().getHasBuzzed()) {
									this.getPlayers().getSecond().setChoice(2);
									this.getPlayers().getSecond().setHasBuzzed(true);
								}
								}
							else if (comp.getName().equals("Button 8")) {
								//Orange
								if (!getPlayers().getSecond().getHasBuzzed()) {
									this.getPlayers().getSecond().setChoice(1);
									this.getPlayers().getSecond().setHasBuzzed(true);
								}
								}
							else if (comp.getName().equals("Button 9")) {
								//Blue
								if (!getPlayers().getSecond().getHasBuzzed()) {
									this.getPlayers().getSecond().setChoice(0);
									this.getPlayers().getSecond().setHasBuzzed(true);
								}
							}
							else if (comp.getName().contains("11")) {
								//Yellow
								if (!getPlayers().getThird().getHasBuzzed()) {
									this.getPlayers().getThird().setChoice(3);
									this.getPlayers().getThird().setHasBuzzed(true);
								}
							}
							else if (comp.getName().contains("12")) {
								//Green
								if (!getPlayers().getThird().getHasBuzzed()) {
									this.getPlayers().getThird().setChoice(2);
									this.getPlayers().getThird().setHasBuzzed(true);
								}
							}
							else if (comp.getName().contains("13")) {
								//Orange
								if (!getPlayers().getThird().getHasBuzzed()) {
									this.getPlayers().getThird().setChoice(1);
									this.getPlayers().getThird().setHasBuzzed(true);
								}
							}
							else if (comp.getName().contains("14")) {
								//Blue
								if (!getPlayers().getThird().getHasBuzzed()) {
									this.getPlayers().getThird().setChoice(0);
									this.getPlayers().getThird().setHasBuzzed(true);
								}
							}
							else if (comp.getName().contains("16")) {
							//Blue
								if (!getPlayers().getFourth().getHasBuzzed()) {
									this.getPlayers().getFourth().setChoice(3);
									this.getPlayers().getFourth().setHasBuzzed(true);
								}
							}
							else if (comp.getName().contains("17")) {
								//Orange
								if (!getPlayers().getFourth().getHasBuzzed()) {
									this.getPlayers().getFourth().setChoice(2);
									this.getPlayers().getFourth().setHasBuzzed(true);
								}
							}
							else if (comp.getName().contains("18")) {
								//Green
								if (!getPlayers().getFourth().getHasBuzzed()) {
									this.getPlayers().getFourth().setChoice(1);
									this.getPlayers().getFourth().setHasBuzzed(true);
								}
							}
							else if (comp.getName().contains("19")) {
								//Blue
								if (!getPlayers().getFourth().getHasBuzzed()) {
									this.getPlayers().getFourth().setChoice(0);
									this.getPlayers().getFourth().setHasBuzzed(true);
								}
							}
							if (players.getFirst().gethasBuzzed() && players.getSecond().gethasBuzzed() && players.getThird().gethasBuzzed() && players.getFourth().gethasBuzzed()) {
								this.setDone(true);
							}
						}
					}
				}
			}
		}
		else {
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public static AudioClip initBuzzedSound() {
		AudioClip mediaPlayer = null;
        try {
           	File path = new File (nameThatTune.Constants.folderLocation+"/music/sou_buzzed.wav");
           	if (path.isFile()) {
               	Media buzzed = new Media(path.toURI().toString()); 
               	mediaPlayer = new AudioClip(buzzed.getSource());
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
	
	
	public static AudioClip initWrongSound() {
		AudioClip mediaPlayer = null;
        try {
           	File path = new File (nameThatTune.Constants.folderLocation+"/music/sou_x.wav");
           	if (path.isFile()) {
               	Media wrong = new Media(path.toURI().toString()); 
               	mediaPlayer = new AudioClip(wrong.getSource());
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
	
	public synchronized Boolean getBuzzed() {
		return buzzed;
	}
	public synchronized void setBuzzed(Boolean buzzed) {
		this.buzzed = buzzed;
	}
	public Boolean getDone() {
		return done;
	}
	public void setDone(Boolean done) {
		this.done = done;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPick() {
		return pick;
	}
	public void setPick(int pick) {
		this.pick = pick;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public Group getPlayers() {
		return players;
	}
	public void setPlayers(Group players) {
		this.players = players;
	}
	public Player getPlayerBuzzed() {
		return playerBuzzed;
	}
	public void setPlayerBuzzed(Player playerBuzzed) {
		this.playerBuzzed = playerBuzzed;
	}
	public AudioClip getBuzzedSound() {
		return buzzedSound;
	}
	public void setBuzzedSound(AudioClip buzzedSound) {
		this.buzzedSound = buzzedSound;
	}
	public AudioClip getWrongSound() {
		return wrongSound;
	}
	public void setWrongSound(AudioClip wrongSound) {
		this.wrongSound = wrongSound;
	}
	public MediaPlayer getSongToPlay() {
		return songToPlay;
	}
	public void setSongToPlay(MediaPlayer songToPlay) {
		this.songToPlay = songToPlay;
	}
	public Map<String, Integer> getUsed() {
		return used;
	}
	public void setUsed(Map<String, Integer> used) {
		this.used = used;
	}
	public String[] getCatString() {
		return catString;
	}
	public void setCatString(String[] catString) {
		this.catString = catString;
	}
	public ImageView[] getSelected() {
		return selected;
	}
	public void setSelected(ImageView[] selected) {
		this.selected = selected;
	}
	
	
}
	
