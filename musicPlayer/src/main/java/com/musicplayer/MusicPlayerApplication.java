package com.musicplayer;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MusicPlayerApplication extends Application {
    private static final String MUSIC_PLAYER_VIEW_FXML = "musicPlayer_view.fxml";
    private static final double WINDOW_WIDTH = 500;
    private static final double WINDOW_HEIGHT = 500;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MusicPlayerApplication.class.getResource(MUSIC_PLAYER_VIEW_FXML));
        Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
        String css = this.getClass().getResource("stylesheet.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        stage.setTitle("Music Player Application");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
