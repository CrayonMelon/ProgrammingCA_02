package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Controller {

    @FXML
    private ImageView mapHolder;

    @FXML
    private ChoiceBox fromBox;

    @FXML
    private ChoiceBox toBox;

    @FXML
    private ListView shortestRoot;

    private List<Node> testNodes;
    private List<Node> shortestList;
    private Graph testGraph;

    public  void initialize(){

        this.testNodes = new ArrayList<Node>();

        testNodes.add(new Node("RiverRun", 1400, 1400));
        testNodes.add(new Node("The Twins", 2000, 1600));
        testNodes.add(new Node("Moat Cailin", 2200, 1500));
        testNodes.add(new Node("China", 2300, 2200));
        testNodes.add(new Node("China 2", 2600, 2300));

        testNodes.get(0).addTwoWayNeighbor(testNodes.get(1), 5);
        testNodes.get(1).addTwoWayNeighbor(testNodes.get(2), 5);
        testNodes.get(2).addTwoWayNeighbor(testNodes.get(0), 5);
        testNodes.get(2).addTwoWayNeighbor(testNodes.get(3), 5);
        testNodes.get(3).addTwoWayNeighbor(testNodes.get(4), 5);

        System.out.println(testNodes);

        List<String> list = new ArrayList<String>();

        for(int i= 0; testNodes.size() > i; i++) {
            list.add(testNodes.get(i).getName());

        }

        ObservableList<String> obList = FXCollections.observableList(list);

        fromBox.setItems(obList);
        fromBox.setValue("The Twins");

        toBox.setItems(obList);

        toBox.setValue("RiverRun");

        this.testGraph = new Graph(this.testNodes);
        System.out.println(testGraph);

        BufferedImage bwImage = SwingFXUtils.fromFXImage(mapHolder.getImage(), null);

        Graphics graphics = bwImage.getGraphics();
        graphics.setColor(Color.PINK);
        ((Graphics2D) graphics).setStroke(new BasicStroke(8f));

        for(int i= 0; testNodes.size() > i; i++){
            graphics.fillOval(testNodes.get(i).getxLocation()-50, testNodes.get(i).getyLocation()-50, 100, 100);

            for(int j = 0; testNodes.get(i).getNeighbors().size() > j; j++){

                graphics.drawLine(testNodes.get(i).getxLocation(), testNodes.get(i).getyLocation(), testNodes.get(i).getNeighbors().get(j).getxLocation(), testNodes.get(i).getNeighbors().get(j).getyLocation());
            }

            try {
                testNodes.get(i).addTwoWayNeighbor(testNodes.get(i+1), 5);

            }catch (Exception e){}
        }

        graphics.dispose();

        mapHolder.setImage(SwingFXUtils.toFXImage(bwImage, null));
    }

    public void testy(ActionEvent event){

        BufferedImage bwImage = SwingFXUtils.fromFXImage(mapHolder.getImage(), null);

        Graphics graphics = bwImage.getGraphics();
        graphics.setColor(Color.PINK);

        for(int i= 0; testNodes.size() > i; i++) {
            graphics.fillOval(testNodes.get(i).getxLocation()-50, testNodes.get(i).getyLocation()-50, 100, 100);

        }

        graphics.setColor(Color.red);

       System.out.println();

        DijkstraGraph dGraph = new DijkstraGraph(this.testGraph);
        List<Node> path = dGraph.getShortestPath(testNodes.get(toBox.getSelectionModel().getSelectedIndex()), testNodes.get(fromBox.getSelectionModel().getSelectedIndex()));
        System.out.println(path.size());

        List<String> list = new ArrayList<String>();

        for(int i= 0; path.size() > i; i++) {

            list.add(path.get(i).getName());
            Collections.reverse(list);

            graphics.fillOval(testNodes.get(i).getxLocation()-50, testNodes.get(i).getyLocation()-50, 100, 100);

        }

        mapHolder.setImage(SwingFXUtils.toFXImage(bwImage, null));

        ObservableList<String> obListPath = FXCollections.observableList(list);

        shortestRoot.setItems(obListPath);

        System.out.println(path);
        System.out.println(path);
        System.out.println();

    }
}