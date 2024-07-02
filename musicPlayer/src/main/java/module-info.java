module com.musicplayer {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.media;
    requires javafx.fxml;
    requires jlayer;


    opens com.musicplayer to javafx.fxml;
    exports com.musicplayer;
}