package com.googlecode.mp4parser.mp4inspector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.prefs.Preferences;


public class Mp4InspectorMain extends Application {
    Stage stage;
    IsoFileTreeView treeView;
    SplitPane splitPane;
    Preferences userPrefs = Preferences.userNodeForPackage(getClass());

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage stage) throws IOException {
        this.stage = stage;
        BorderPane hBox = new BorderPane();
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);
        Menu fileMenu = new Menu();
        fileMenu.setText("_File");
        MenuItem openMenuItem = new MenuItem("_Open");
        openMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        fileMenu.getItems().addAll(openMenuItem);
        TabPane tabPane = new TabPane();
        treeView = new IsoFileTreeView();
        treeView.getSelectionModel().selectedItemProperty().addListener(new AddBoxTabListener(tabPane));
        openMenuItem.setOnAction(new FileOpenEventHandler(stage, treeView, tabPane));
        menuBar.getMenus().addAll(fileMenu);

        stage.setTitle("MP4 Inspector");
        Scene scene = new Scene(hBox);

        hBox.setTop(menuBar);
        splitPane = new SplitPane();
        StackPane stackPaneTreeView = new StackPane();
        treeView.setPrefHeight(400);
        treeView.setPrefWidth(400);
        stackPaneTreeView.getChildren().addAll(treeView);
        stackPaneTreeView.setPrefWidth(300);
        splitPane.getItems().addAll(stackPaneTreeView, tabPane);

        stage.setScene(scene);
        hBox.setCenter(splitPane);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
        loadPosAndSize();

        stage.show();
        if (!getParameters().getUnnamed().isEmpty() && new File(getParameters().getUnnamed().get(0)).exists()) {
            stage.setTitle(getParameters().getUnnamed().get(0));
            treeView.loadIsoFile(getParameters().getUnnamed().get(0));
        } else {
            loadLastFile(treeView);
        }

    }

    private void loadLastFile(IsoFileTreeView treeView) {
        String openFile = userPrefs.get("openFile", "no-actual-file");
        if (new File(openFile).exists()) {
            try {
                treeView.loadIsoFile(openFile);
            } catch (IOException e) {
            }
        }

    }

    @Override
    public void stop() throws Exception {
        storePosAndSize(stage);
        storeLastFile(treeView);
    }

    private void storeLastFile(IsoFileTreeView treeView) {
        userPrefs.put("openFile", treeView.getOpenFile());
    }

    public void loadPosAndSize() {
        // get window location from user preferences: use x=100, y=100, width=400, height=400 as default
        double x = userPrefs.getDouble("stage.x", 100);
        double y = userPrefs.getDouble("stage.y", 100);
        double w = userPrefs.getDouble("stage.width", 400);
        double h = userPrefs.getDouble("stage.height", 400);
        stage.setX(x);
        stage.setY(y);
        stage.setWidth(w);
        stage.setHeight(h);
        splitPane.setDividerPositions(userPrefs.getDouble("splitpane.divider", 150));

    }

    public void storePosAndSize(Stage stage) {
        Preferences userPrefs = Preferences.userNodeForPackage(getClass());
        userPrefs.putDouble("stage.x", stage.getX());
        userPrefs.putDouble("stage.y", stage.getY());
        userPrefs.putDouble("stage.width", stage.getWidth());
        userPrefs.putDouble("stage.height", stage.getHeight());
        userPrefs.putDouble("splitpane.divider", splitPane.getDividerPositions()[0]);
    }
}