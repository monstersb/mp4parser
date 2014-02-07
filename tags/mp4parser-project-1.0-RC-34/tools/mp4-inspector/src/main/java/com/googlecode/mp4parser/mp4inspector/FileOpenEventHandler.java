package com.googlecode.mp4parser.mp4inspector;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FileOpenEventHandler implements EventHandler<ActionEvent> {
    Stage stage;
    IsoFileTreeView treeView;
    TabPane tabPane;

    public FileOpenEventHandler(Stage stage, IsoFileTreeView treeView, TabPane tabPane) {
        this.stage = stage;
        this.treeView = treeView;
        this.tabPane = tabPane;
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

//Set extension filter
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4 files", "*.mp4", "*.uvu", "*.m4v"),
                new FileChooser.ExtensionFilter("All files", "*.*"));

//Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        stage.setTitle(file.getPath());
        try {
            tabPane.getTabs().clear();
            treeView.loadIsoFile(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
