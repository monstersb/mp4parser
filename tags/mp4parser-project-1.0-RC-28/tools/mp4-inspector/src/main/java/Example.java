    import javafx.application.Application;
    import javafx.application.Platform;
    import javafx.collections.ObservableList;
    import javafx.scene.Scene;
    import javafx.scene.control.TreeItem;
    import javafx.scene.control.TreeView;
    import javafx.scene.layout.HBox;
    import javafx.stage.Stage;

    import java.security.SecureRandom;
    import java.util.Random;


    public class Example extends Application {
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {
            TreeView<String> treeView = new TreeView<String>(createNode("root"));
            HBox hBox = new HBox();
            hBox.getChildren().addAll(treeView);
            Scene scene = new Scene(hBox);
            stage.setScene(scene);
            stage.show();
        }

        Random r = new SecureRandom();

        public TreeItem<String> createNode(final String b) {
            return new TreeItem<String>(b) {
                private boolean isLeaf;
                private boolean isFirstTimeChildren = true;
                private boolean isFirstTimeLeaf = true;

                @Override
                public ObservableList<TreeItem<String>> getChildren() {
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
                        isLeaf = r.nextBoolean() && r.nextBoolean() && r.nextBoolean();
                    }
                    return isLeaf;
                }

                private void buildChildren(final ObservableList<TreeItem<String>> children) {
                    if (!this.isLeaf()) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                int i;
                                int max = r.nextInt(500);
                                for (i = 0; i <= max; i++) {
                                    children.addAll(new TreeItem[]{createNode("#" + r.nextInt())});
                                }
                            }
                        });
                    }
                }
            };
        }
    }