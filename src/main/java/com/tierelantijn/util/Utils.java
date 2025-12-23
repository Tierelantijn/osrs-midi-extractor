package com.tierelantijn.util;

import com.tierelantijn.Application;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.runelite.cache.fs.Store;

import java.io.File;
import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static Store loadStore() throws IOException {
        Store store = new Store(getDirectory("Choose OSRS cache directory"));
        store.load();
        return store;
    }

    public static File getDirectory(final String title) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        return directoryChooser.showDialog(Application.stage);
    }

    public static void alert(final Alert.AlertType type, final String title, final String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
