package com.musicplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.nio.file.Path;
import java.util.List;

public class MusicPlayerController {
    @FXML
    private Label welcomeText;
    private final FileManager fileManager = new FileManager();
    @FXML
    private ListView musicList;
    @FXML
    protected Label currentMusic;
    @FXML
    protected Label nextMusic;

    @FXML
    protected void initialize() {



        ChangeListener<String> changeListener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Code to execute when the observed value changes
                currentMusic.setText(newValue);
                setNextMusicLabel();
                System.out.println("Observed value changed from " + oldValue + " to " + newValue);
            }
        };

        // Add the ChangeListener to the ListView's selectedItemProperty
        musicList.getSelectionModel().selectedItemProperty().addListener(changeListener);


        if (fileManager.getPath() == null) {
            selectFolderPath();
        }


    }

    private void setNextMusicLabel(){
        int currentIndex = musicList.getSelectionModel().getSelectedIndex();
        int nextIndex = (currentIndex) % musicList.getItems().size();
        if(nextIndex+1 < musicList.getItems().size()){
            nextMusic.setText("Next Song is :"+musicList.getItems().get(nextIndex+1).toString());
        }
    }

    @FXML
    protected void selectFolderPath() {
        fileManager.chooseDirectory();
        loadMusicList();

    }

    @FXML
    public void playSelectedMusic() {
        String selectedMusic = musicList.getSelectionModel().getSelectedItem().toString();
        System.out.println("Selected music: " + selectedMusic);
        fileManager.playMusic(fileManager.getPath().resolve(selectedMusic));
    }

    private void loadMusicList() {
        List<Path> musicPaths = fileManager.getMusicPaths();
        if (musicPaths != null) {
            ObservableList<String> items = FXCollections.observableArrayList();
            for (Path path : musicPaths) {
                String fileName = path.getFileName().toString();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (fileExtension.equalsIgnoreCase("mp3") || fileExtension.equalsIgnoreCase("wav") || fileExtension.equalsIgnoreCase("flac")) {
                    items.add(fileName);
                }
            }
            musicList.setItems(items);
        }
    }

    @FXML
    protected void toggleMusicButton() {
        fileManager.stopMusic();
    }

    //This method should play the next song from the listview.
    @FXML
    protected void playNextSong() {
        int currentIndex = musicList.getSelectionModel().getSelectedIndex();
        int nextIndex = (currentIndex + 1) % musicList.getItems().size();
        musicList.getSelectionModel().select(nextIndex);
        playSelectedMusic();
    }

    @FXML
    protected void playPreviousSong() {
        int currentIndex = musicList.getSelectionModel().getSelectedIndex();
        int nextIndex = (currentIndex - 1) % musicList.getItems().size();
        musicList.getSelectionModel().select(nextIndex);
        playSelectedMusic();
    }
}
