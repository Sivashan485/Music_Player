package com.musicplayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.stage.DirectoryChooser;


public class FileManager {
    private Path path;
    private List<Path> musicPaths;
    MusicPlayer player = new MusicPlayer();


    public FileManager() {
    }

    public FileManager(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
    public void setPath(Path path) {
        this.path = path;
    }

    public void chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select music folder");
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            path = selectedDirectory.toPath();
            try (Stream<Path> paths = Files.list(path)) {
                musicPaths = paths.collect(Collectors.toList());
                System.out.println("Music files found: " + musicPaths.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public List<Path> getMusicPaths() {
        return musicPaths;
    }
    public void playMusic(Path musicFilePath) {
        player.setFilePath(musicFilePath.toString());
        player.play();
    }
    public void stopMusic() {
        player.stop();
    }
}
