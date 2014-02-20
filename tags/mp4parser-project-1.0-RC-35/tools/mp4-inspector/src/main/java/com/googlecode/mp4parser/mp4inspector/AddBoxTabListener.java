package com.googlecode.mp4parser.mp4inspector;

import com.coremedia.iso.boxes.Box;
import com.googlecode.mp4parser.util.Path;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Method;


public class AddBoxTabListener implements ChangeListener<TreeItem<Box>> {
    private final TabPane tabPane;

    public AddBoxTabListener(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    @Override
    public void changed(ObservableValue<? extends TreeItem<Box>> observableValue, TreeItem<Box> boxTreeItem, TreeItem<Box> boxTreeItem2) {
        if (boxTreeItem2 == null) {
            return;
        }
        String id = Path.createPath(boxTreeItem2.getValue());
        Tab t = null;
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getId().equals(id)) {
                tabPane.getSelectionModel().select(tab);
                t = tab;
            }
        }
        if (t == null) {
            t = new Tab(id);
            t.setId(id);
            tabPane.getTabs().addAll(t);
            t.setContent(new BoxPane(boxTreeItem2.getValue()));
            tabPane.getSelectionModel().select(t);
        }


    }

}
