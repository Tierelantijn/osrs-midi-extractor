package com.tierelantijn.util;

import com.tierelantijn.Application;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.runelite.cache.fs.Store;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static Store loadStore() throws IOException {
        File directory = getDirectory("Choose OSRS cache directory");
        if (!Files.exists(Paths.get(directory.getAbsolutePath() + File.separator + "main_file_cache.dat2"))) {
            alert(Alert.AlertType.ERROR, "Error", "File main_file_cache.dat2 not found");
            return null;
        }
        if (!Files.exists(Paths.get(directory.getAbsolutePath() + File.separator + "main_file_cache.idx255"))) {
            alert(Alert.AlertType.ERROR, "Error", "File main_file_cache.idx255 not found");
            return null;
        }
        Store store = new Store(directory);
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
