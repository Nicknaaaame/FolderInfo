package sample.presenter;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import sample.Main;
import sample.view.Activity;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;

import static java.nio.file.StandardWatchEventKinds.*;

public class Presenter {
    private Activity activity;

    private TextArea taMain;
    private StackPane spMain;
    private File directory;
    private final String n = "\n";
    private Thread daemonThread;
    private String pathToDir;
    private TreeItem<String> rootNode;

    public Presenter(TextArea ta, StackPane sp){
        spMain = sp;
        taMain = ta;
        activity = new Activity();
        daemonThread = new Thread(this::directoryWatcher);
        daemonThread.setDaemon(true);
        daemonThread.setPriority(1);
    }

    public void onDirChoose(ActionEvent e){
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(new File("C://Users//Mi//Desktop"));
            directory = directoryChooser.showDialog(Main.getPrimaryStage());
            String path = directory.getAbsolutePath();
            taMain.setText("");
            pathToDir = (path.substring(0, path.length() - directory.getName().length()) + n);
            taMain.appendText(pathToDir);
            rootNode = new TreeItem<>(directory.getName());
            printDirectoryInfo(directory, rootNode);
            TreeView<String> treeView = new TreeView<>(rootNode.getChildren().get(0));
            spMain.getChildren().add(treeView);
            if(!daemonThread.isAlive())
                daemonThread.start();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    private void printDirectoryInfo(File directory, TreeItem<String> rootNode){
        File[] files = directory.listFiles(File::isDirectory);
        long dirSize = 0;
        try {
            dirSize = Files.walk(Paths.get(directory.toURI()))
                    .filter(p -> p.toFile().isFile())
                    .mapToLong(p -> p.toFile().length())
                    .sum();
        } catch (Exception e) {
            return;
        }

        int fileCount = 0;
        int dirCount = 0;
        Date dirDate;

        dirDate = new Date(directory.lastModified());
        fileCount = Objects.requireNonNull(directory.listFiles(File::isFile)).length;
        dirCount = Objects.requireNonNull(files).length;

        TreeItem<String> treeItem = new TreeItem<>(directory.getName());
        treeItem.setExpanded(true);

        LinkedList<TreeItem> info = new LinkedList<>();
        info.add(new TreeItem<>("Data: "+dirDate));
        info.add(new TreeItem<>("Dir count: "+dirCount));
        info.add(new TreeItem<>("File count: "+fileCount));
        info.add(new TreeItem<>("Size: "+dirSize));

        for(TreeItem i : info)
            treeItem.getChildren().add(i);

        if(files.length!=0)
            try{
                for(File f : files)
                    printDirectoryInfo(f, treeItem);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }

        rootNode.getChildren().add(treeItem);
    }

    private void directoryWatcher(){
        while (true){
            WatchService watcher;
            try {
                watcher = FileSystems.getDefault().newWatchService();
            } catch (IOException e) {
                return;
            }
            Path dirPath = Paths.get(directory.toURI());
            try {
                dirPath.register(watcher,ENTRY_CREATE,ENTRY_MODIFY, ENTRY_DELETE);
            } catch (IOException e) {
                return;
            }
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
            taMain.setText("");
            taMain.appendText(pathToDir);
            printDirectoryInfo(directory, rootNode);
        }
    }
}
