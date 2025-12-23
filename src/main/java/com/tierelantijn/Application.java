package com.tierelantijn;

import com.tierelantijn.dumper.MidiDumper;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {

    public static Stage stage;

    @Override
    public void start(final Stage stage) {
        Application.stage = stage;
        new MidiDumper().dump();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
