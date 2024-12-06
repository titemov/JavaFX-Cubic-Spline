import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.Random;

public class Interface extends Application {
    public static int amountOfDots=100; //lines per segment
    public static double[][] endpoint_coor = new double[2][2];
    private static String mode="";
    public static double[][] curve_coor = new double[2][amountOfDots*7+1];

    public static int rng(int low, int high) {
        Random r = new Random();
        return r.nextInt(high - low) + low;
    }

    public static boolean contains(double[] array, double value) {
        for(int i=0;i<array.length;i++) {
            if (array[i] == value) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNodeExist(double[][] array, double X, double Y){
        for(int i=0;i<array[0].length;i++){
            if(array[0][i]==X && array[1][i]==Y){
                return true;
            }
        }
        return false;
    }

    public static void clear(Line[] curve, int startX, int startY){
        for(int i=0;i<curve.length;i++){
            curve[i].setStartX(startX);
            curve[i].setEndX(startX);
            curve[i].setStartY(startY);
            curve[i].setEndY(startY);
        }
    }

    @Override
    public void start(Stage stage){
        Group interfaceGroup = new Group();//main group
        int startX=250;
        int startY=525;

        //###### INITIALIZATION ######

        interfaceGroup.getChildren().add(Initializer.initCartesian());

        TextField[][] coordinatesTF = new TextField[2][8];
        interfaceGroup.getChildren().add(Initializer.initTF(coordinatesTF,25,45));

        double[][] node_coor = new double[2][8];
        Circle[] nodes = new Circle[8];
        Label[] nodes_nums = new Label[8];
        interfaceGroup.getChildren().add(Initializer.initNodes(node_coor,nodes,nodes_nums));

        Line[] curve = new Line[amountOfDots*7];//each dot coordinate
        interfaceGroup.getChildren().add(Initializer.initCurve(curve));

        TextField[][] endpointTF = new TextField[2][2];
        interfaceGroup.getChildren().add(Initializer.initTF(endpointTF,25,390));

        //###### LABELS ######

        Label coordinatesLabel = new Label("Enter coordinates:\n   X \t\t Y");
        coordinatesLabel.setLayoutX(25);
        coordinatesLabel.setLayoutY(10);

        Label endpointLabel = new Label("Fixed endpoint:\n   X \t\t Y");
        endpointLabel.setLayoutX(25);
        endpointLabel.setLayoutY(355);

        Label selectedMode = new Label();
        selectedMode.setLayoutX(25);
        selectedMode.setLayoutY(305);

        Label onError = new Label();
        onError.setLayoutX(25);
        onError.setLayoutY(540);

        interfaceGroup.getChildren().addAll(coordinatesLabel,endpointLabel,selectedMode,onError);
        //###### BUTTONS ######

        Button clearButton = new Button("Clear");
        clearButton.setLayoutX(700);
        clearButton.setLayoutY(10);
        clearButton.setPrefWidth(50);
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    onError.setText("");
                    clear(curve,startX,startY);
                }catch (Exception e){
                    onError.setText("Error: "+e.getMessage());
                }
            }
        });

        Button enterBtn = new Button("Enter!");
        enterBtn.setLayoutX(25);
        enterBtn.setLayoutY(270);
        enterBtn.setPrefWidth(100);
        enterBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    onError.setText("");
                    for(int k=0;k<8;k++){
                        for(int i=0;i<coordinatesTF[0].length;i++){
                            double tempX=node_coor[0][i];
                            double tempY=node_coor[1][i];
                            double coorX, coorY;
                            if(Objects.equals(coordinatesTF[0][i].getText(), "")){
                                coorX=tempX;
                            }else{
                                coorX = Double.parseDouble(coordinatesTF[0][i].getText());
                                if (coorX<0 || coorX>20) {coorX=tempX;}
                            }
                            if(Objects.equals(coordinatesTF[1][i].getText(), "")){
                                coorY=tempY;
                            }else{
                                coorY = Integer.parseInt(coordinatesTF[1][i].getText());
                                if (coorY<0 || coorY>20) {coorY=tempY;}
                            }

                            if(isNodeExist(node_coor,coorX,coorY)){
                                coorX=tempX;
                                coorY=tempY;
                            }

                            node_coor[0][i]=coorX;
                            node_coor[1][i]=coorY;

                            nodes[i].setCenterX(startX+node_coor[0][i]*25);
                            nodes_nums[i].setLayoutX(startX+node_coor[0][i]*25-2);

                            nodes[i].setCenterY(startY-node_coor[1][i]*25);
                            nodes_nums[i].setLayoutY(startY-node_coor[1][i]*25+5);
                        }
                    }
                }catch (Exception e){
                    onError.setText("Incorrect coordinate input");
                }
            }
        });

        Button randomizeBtn = new Button("Random");
        randomizeBtn.setLayoutX(125);
        randomizeBtn.setLayoutY(15);
        randomizeBtn.setPrefWidth(75);
        randomizeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    onError.setText("");
                    int value;
                    clearButton.fire();
                    for (int i = 0; i < node_coor.length; i++) {
                        for (int n = 0; n < node_coor[0].length; n++) {
                            if (i == 0) {
                                do {
                                    value = rng(1, 20);
                                } while (contains(node_coor[i], value));
                                node_coor[i][n] = value;
                                nodes[n].setCenterX(startX + node_coor[i][n] * 25); //absolute value (coor)
                                nodes[n].setRadius(5);
                                nodes_nums[n].setLayoutX(startX + node_coor[i][n] * 25 - 2);
                            } else {
                                do {
                                    value = rng(1, 20);
                                } while (contains(node_coor[i], value));
                                node_coor[i][n] = value;
                                nodes[n].setCenterY(startY - node_coor[i][n] * 25);
                                nodes_nums[n].setLayoutY(startY - node_coor[i][n] * 25 + 5);
                            }
                        }
                    }
                    for (int i = 0; i < coordinatesTF.length; i++) {
                        for (int n = 0; n < coordinatesTF[0].length; n++) {
                            coordinatesTF[i][n].setText(String.valueOf((int) node_coor[i][n]));
                        }
                    }
                }catch(Exception e){
                    onError.setText("Error: "+e.getMessage());
                }
            }
        });

        randomizeBtn.fire();//initial randomization

        ObservableList<String> modes = FXCollections.observableArrayList("Fixed","Soft","Cyclic","Acyclic");
        ComboBox<String> modesCB = new ComboBox<String>(modes);
        modesCB.setLayoutX(25);
        modesCB.setLayoutY(325);
        modesCB.setPrefWidth(100);
        modesCB.setValue("Fixed");
        modesCB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mode = String.valueOf(modesCB.getValue());
                selectedMode.setText("Mode: "+mode);
            }
        });

        mode = String.valueOf(modesCB.getValue());
        selectedMode.setText("Mode: "+mode);

        Button calculateBtn = new Button("Calculate!");
        calculateBtn.setLayoutX(25);
        calculateBtn.setLayoutY(470);
        calculateBtn.setPrefWidth(100);
        calculateBtn.setPrefHeight(50);
        calculateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try{
                    onError.setText("");
                    for(int i=0;i<endpoint_coor.length;i++){
                        for(int n=0;n<endpoint_coor[0].length;n++){
                            if(!(Objects.equals(endpointTF[i][n].getText(), ""))){
                                endpoint_coor[i][n]=Double.parseDouble(endpointTF[i][n].getText());
                            }
                        }
                    }
                    clearButton.fire();
                    Splines.cubicSpline(node_coor, mode);
                    for(int i=0;i<curve.length;i++) {//draw curve
                        if (!(startX+curve_coor[0][i]*25<200 || startX+curve_coor[0][i+1]*25<200)){
                            curve[i].setStartX(startX + curve_coor[0][i] * 25);
                            curve[i].setEndX(startX + curve_coor[0][i+1] * 25);
                            curve[i].setStartY(startY - curve_coor[1][i] * 25);
                            curve[i].setEndY(startY - curve_coor[1][i+1] * 25);
                        }
                        else{
                            onError.setText("Some lines are not drawn: out of bounds");
                        }
                    }
                }catch (Exception e){
                    onError.setText("Error: "+e.getMessage());
                }
            }
        });

        interfaceGroup.getChildren().addAll(clearButton,enterBtn,randomizeBtn,modesCB,calculateBtn);

        Scene scene = new Scene(interfaceGroup, Color.SNOW);
        stage.setScene(scene);
        stage.setTitle("JavaFX Cubic Spline");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.show();
    }

    public static void show(){
        Application.launch();
    }
}
