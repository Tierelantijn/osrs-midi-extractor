package com.tierelantijn.dumper;

import com.tierelantijn.util.Utils;
import javafx.scene.control.Alert;
import lombok.extern.slf4j.Slf4j;
import net.runelite.cache.IndexType;
import net.runelite.cache.definitions.loaders.TrackLoader;
import net.runelite.cache.fs.Archive;
import net.runelite.cache.fs.Index;
import net.runelite.cache.fs.Store;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.tierelantijn.util.Utils.alert;

@Slf4j
public class MidiDumper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    private static final TrackLoader TRACK_LOADER = new TrackLoader();

    public void dump() {
        try (Store store = Utils.loadStore()) {
            if (store != null) {
                dumpAllMidi(store);
            }
        } catch (IOException e) {
            alert(Alert.AlertType.ERROR, "Error", "Failed to load cache: " + e.getMessage());
        }
    }

    private void dumpAllMidi(final Store store) {
        Index index = store.getIndex(IndexType.MUSIC_TRACKS);
        if (index != null) {
            File parentDir = Utils.getDirectory("Choose output location");
            File outputDir = new File(parentDir, "cachedump-" + LocalDateTime.now().format(FORMATTER));
            if (outputDir.mkdirs()) {
                index.getArchives().forEach(archive -> writeMidiToFile(store, outputDir, archive));
                alert(Alert.AlertType.INFORMATION, "Success", "Successfully dumped all midi to " + outputDir.getAbsolutePath());
            } else {
                alert(Alert.AlertType.ERROR, "Error", "Output directory already exists: " + outputDir.getAbsolutePath());
            }
        } else {
            alert(Alert.AlertType.ERROR, "Error", "No index found");
        }
    }

    private void writeMidiToFile(final Store store, final File outputDir, final Archive archive) {
        try (FileOutputStream fos = new FileOutputStream(outputDir + File.separator + archive.getArchiveId() + ".mid")) {
            byte[] archiveData = store.getStorage().loadArchive(archive);
            byte[] content = List.copyOf(archive.getFiles(archiveData).getFiles()).getFirst().getContents();
            fos.write(TRACK_LOADER.load(content).getMidi());
            log.debug("Wrote midi file for id: {}", archive.getArchiveId());
        } catch (IOException e) {
            log.warn("Failed to write midi file for id: {}", archive.getArchiveId());
            alert(Alert.AlertType.ERROR, "Error", "Failed to load data for archive with id " + archive.getArchiveId() + ": " + e.getMessage());
        }
    }
}
