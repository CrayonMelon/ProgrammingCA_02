package sample;

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
    private ChoiceBox locationBox;


    private List<Node> testNodes;
    private Graph testGraph;



    public  void initialize(){

        this.testNodes = new ArrayList<Node>();

        testNodes.add(new Node("RiverRun", 3000, 3000));
        testNodes.add(new Node("The Twins", 2000, 2000));
        testNodes.add(new Node("Moat Cailin", 1000, 1000));

        System.out.println(testNodes);

        this.testGraph = new Graph(this.testNodes);

        System.out.println(testGraph);

        DijkstraGraph dGraph = new DijkstraGraph(this.testGraph);
        List<Node> path = dGraph.getShortestPath(testNodes.get(0), testNodes.get(2));

        System.out.println(path.size());

    }

    public void testy(ActionEvent event){

        BufferedImage bwImage = SwingFXUtils.fromFXImage(mapHolder.getImage(), null);

        Graphics graphics = bwImage.getGraphics();
        graphics.setColor(Color.PINK);


        for(int i= 0; testNodes.size() > i; i++){
            graphics.fillOval(testNodes.get(i).getxLocation(), testNodes.get(i).getyLocation(), 100, 100);

            try {
                graphics.drawLine(testNodes.get(i).getxLocation(), testNodes.get(i).getxLocation(), testNodes.get(i+1).getyLocation(), testNodes.get(i+1).getyLocation());

            }catch (Exception e){}

        }

        graphics.dispose();

        mapHolder.setImage(SwingFXUtils.toFXImage(bwImage, null));

    }




}
