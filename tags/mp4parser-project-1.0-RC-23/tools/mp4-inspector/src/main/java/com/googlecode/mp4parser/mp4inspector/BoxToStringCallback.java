package com.googlecode.mp4parser.mp4inspector;

import com.coremedia.iso.boxes.Box;
import com.googlecode.mp4parser.util.Path;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * Creates a <code>TreeCell</code> from a <code>Box</code>
 */
public class BoxToStringCallback implements Callback<TreeView<Box>, TreeCell<Box>> {

    @Override
    public TreeCell<Box> call(TreeView<Box> boxTreeView) {
        TextFieldTreeCell<Box> tftc = new TextFieldTreeCell<Box>();
        tftc.setConverter(new StringConverter<Box>() {
            @Override
            public String toString(Box box) {
                return Path.createPath(box);
            }

            @Override
            public Box fromString(String s) {
                return null;
            }
        });
        return tftc;
    }
}

