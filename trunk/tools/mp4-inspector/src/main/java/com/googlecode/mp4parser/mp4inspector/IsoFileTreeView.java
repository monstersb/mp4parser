package com.googlecode.mp4parser.mp4inspector;

import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.util.Path;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class IsoFileTreeView extends TreeView<Box> {

    public IsoFileTreeView() {
        setCellFactory(new Callback<TreeView<Box>,TreeCell<Box>>(){
            @Override
            public TreeCell<Box> call(TreeView<Box> p) {
                return new BoxTreeCellImpl();
            }
        });
        setShowRoot(false);
        setEditable(false);
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
        final IsoFile isoFile = new IsoFile(file);
        setRoot(new TreeItem<Box>() {
            public boolean isFirstTimeChildren = true;

            @Override
            public boolean isLeaf() {
                return false;
            }

            public ObservableList<TreeItem<Box>> getChildren() {
                if (isFirstTimeChildren) {
                    isFirstTimeChildren = false;
                    buildChildren(super.getChildren());
                }
                return super.getChildren();
            }

            private void buildChildren(final ObservableList<TreeItem<Box>> children) {

                    final Iterator<Box> boxes = isoFile.getBoxes().iterator();
                    int i = 100;
                    while (boxes.hasNext() && i-- > 0) {
                        // show 100 now and the rest later
                        children.add(createNode(boxes.next()));
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            while (boxes.hasNext()) {
                                int i = 100;
                                List<TreeItem<Box>> treeItemList = new LinkedList<TreeItem<Box>>();
                                while (boxes.hasNext() && i-- > 0) {
                                    // show 100 now and the rest later
                                    treeItemList.add(createNode(boxes.next()));
                                }
                                final TreeItem<Box> treeItems[] = treeItemList.toArray(new TreeItem[treeItemList.size()]);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        children.addAll(treeItems);
                                    }
                                });
                            }
                        }
                    }.start();
                }


        });

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

                    isLeaf = !(getValue() instanceof Container);
                }

                return isLeaf;
            }

            private void buildChildren(final ObservableList<TreeItem<Box>> children) {
                Box b = this.getValue();
                if (b != null && b instanceof Container) {
                    final Iterator<Box> boxes = ((Container) b).getBoxes().iterator();
                    int i = 100;
                    while (boxes.hasNext() && i-- > 0) {
                        // show 100 now and the rest later
                        children.add(createNode(boxes.next()));
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            while (boxes.hasNext()) {
                                int i = 100;
                                List<TreeItem<Box>> treeItemList = new LinkedList<TreeItem<Box>>();
                                while (boxes.hasNext() && i-- > 0) {
                                    // show 100 now and the rest later
                                    treeItemList.add(createNode(boxes.next()));
                                }
                                final TreeItem<Box> treeItems[] = treeItemList.toArray(new TreeItem[treeItemList.size()]);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        children.addAll(treeItems);
                                    }
                                });
                            }
                        }
                    }.start();

                }
            }
        };
    }

    private final class BoxTreeCellImpl extends TreeCell<Box> {

        public BoxTreeCellImpl() {
        }


        @Override
        public void updateItem(Box item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());
            }
        }

        private String getString() {
            return getItem() == null ? "" : Path.createPath(getItem());
        }
    }
}
