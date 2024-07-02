package com.musicplayer;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MusicPlayer {
    private String filePath;
    private AdvancedPlayer player;
    private Thread playerThread;
    private ExecutorService executorService;


    public MusicPlayer() {
        this.executorService = Executors.newSingleThreadExecutor();
        this.playerThread = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(filePath);
                player = new AdvancedPlayer(fis);
                player.play();
            } catch (FileNotFoundException | JavaLayerException e) {
                e.printStackTrace();
            }
        });
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public void play() {
        stop(); // Stop any currently playing song
        executorService.submit(playerThread);

    }

    public void stop() {
        if (player != null) {
            player.close();
            player = null; // Set player to null after closing
        }
        if (executorService != null) {
            executorService.shutdownNow(); // Stop all running tasks and shut down the executor service
            executorService = Executors.newSingleThreadExecutor(); // Create a new executor service for the next play
        }
    }
}