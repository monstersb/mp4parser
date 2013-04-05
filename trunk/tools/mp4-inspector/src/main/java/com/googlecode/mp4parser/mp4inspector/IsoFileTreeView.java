package com.googlecode.mp4parser.mp4inspector;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.ContainerBox;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.IOException;
import java.util.*;


public class IsoFileTreeView extends TreeView<Box> {

    public IsoFileTreeView() {
        setCellFactory(new BoxToStringCallback());
        setShowRoot(false);
    }

    public String getOpenFile() {
        Map<String, Object> map = (Map<String, Object>) getUserData();
        if (map != null) {
            return (String) map.get("file");
        } else {
            return null;
        }
    }

    public void loadIsoFile(String file) throws IOException {
        Map<String, Object> map = (Map<String, Object>) getUserData();
        if (map != null) {
            IsoFile isoFile = (IsoFile) map.get("isoFile");
            if (isoFile != null) {
                isoFile.close();
            }

        } else {
            map = new HashMap<String, Object>();
            setUserData(map);
        }
        IsoFile isoFile = new IsoFile(file);
        setRoot(createNode(isoFile));
        map.put("isoFile", isoFile);
        map.put("file", file);

    }

    public TreeItem<Box> createNode(final Box b) {
        return new TreeItem<Box>(b) {

            private boolean isLeaf;

            private boolean isFirstTimeChildren = true;
            private boolean isFirstTimeLeaf = true;

            @Override
            public ObservableList<TreeItem<Box>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;
                    buildChildren(super.getChildren());
                }
                return super.getChildren();
            }

            @Override
            public boolean isLeaf() {
                if (isFirstTimeLeaf) {
                    isFirstTimeLeaf = false;

                    isLeaf = !(getValue() instanceof ContainerBox);
                }

                return isLeaf;
            }

            private void buildChildren(final ObservableList<TreeItem<Box>> children) {
                Box b = this.getValue();
                if (b != null && b instanceof ContainerBox) {
                    final Iterator<Box> boxes = ((ContainerBox) b).getBoxes().iterator();
                    int i = 100;
                    while (boxes.hasNext() && i-- > 0) {
                        children.add(createNode(boxes.next()));
                    }
                    if (boxes.hasNext()) {
                        Task<Integer> task = new Task<Integer>() {
                            @Override
                            protected Integer call() throws Exception {
                                System.err.println("call called");
                                while (boxes.hasNext()) {
                                    children.add(createNode(boxes.next()));
                                }
                                System.err.println("call ended");
                                return 0;
                            }
                        };
                        new Thread(task).start();
                        System.err.println("Start returned");
                    }
                }
            }
        };
    }



}
