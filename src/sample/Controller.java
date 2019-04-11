package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private ImageView mapHolder;

    @FXML
    private ChoiceBox fromBox;

    @FXML
    private ChoiceBox toBox;

    private List<Node> testNodes;
    private Graph testGraph;

    public  void initialize(){

        this.testNodes = new ArrayList<Node>();

        testNodes.add(new Node("RiverRun", 3000, 3000));
        testNodes.add(new Node("The Twins", 2000, 2000));
        testNodes.add(new Node("Moat Cailin", 1000, 1000));
        testNodes.add(new Node("China", 1500, 2500));
        testNodes.add(new Node("China 2", 3720, 2000));
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

        for(int i= 0; testNodes.size() > i; i++){
            graphics.fillOval(testNodes.get(i).getxLocation(), testNodes.get(i).getyLocation(), 100, 100);

            try {
                graphics.drawLine(testNodes.get(i).getxLocation(), testNodes.get(i).getxLocation(), testNodes.get(i+1).getyLocation(), testNodes.get(i+1).getyLocation());
                testNodes.get(i).addTwoWayNeighbor(testNodes.get(i+1), 5);


            }catch (Exception e){}
        }

        graphics.dispose();

        mapHolder.setImage(SwingFXUtils.toFXImage(bwImage, null));
    }

    public void testy(ActionEvent event){

       System.out.println();

        DijkstraGraph dGraph = new DijkstraGraph(this.testGraph);
        List<Node> path = dGraph.getShortestPath(testNodes.get(toBox.getSelectionModel().getSelectedIndex()), testNodes.get(fromBox.getSelectionModel().getSelectedIndex()));
        System.out.println(path.size());
        System.out.println(path);
        System.out.println(path.toString());

    }
}
