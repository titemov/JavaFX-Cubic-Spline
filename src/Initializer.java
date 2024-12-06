import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class Initializer extends Interface{
    public static Group initCartesian(){
        Group group = new Group();
        int startX=250;
        int startY=525;
        int len=510;
        Label[][] numbers = new Label[2][21];
        Line[][] numTicks = new Line[2][21];
        Line X = new Line(startX,startY,startX+len,startY); //len=500+10
        Line Y = new Line(startX,startY,startX,startY-len);//len=500+10

        for(int i=0;i<numbers.length;i++){
            for(int n=0;n<numbers[0].length;n++){
                numbers[i][n] = new javafx.scene.control.Label(String.valueOf(n));
                numTicks[i][n] = new Line();
                if (i==0){
                    numbers[i][n].setLayoutX(startX+25*n-2);
                    numbers[i][n].setLayoutY(startY);
                    numTicks[i][n].setStartX(startX+25*n);
                    numTicks[i][n].setStartY(startY);
                    numTicks[i][n].setEndX(startX+25*n);
                    numTicks[i][n].setEndY(startY-5);
                    group.getChildren().addAll(numbers[i][n],numTicks[i][n]);
                }else {
                    if (n!=0) {
                        numbers[i][n].setLayoutX(startX - 15);
                        numbers[i][n].setLayoutY(startY - 5 - 25*n);
                        numTicks[i][n].setStartX(startX);
                        numTicks[i][n].setStartY(startY-25*n);
                        numTicks[i][n].setEndX(startX+5);
                        numTicks[i][n].setEndY(startY-25*n);
                        group.getChildren().addAll(numbers[i][n],numTicks[i][n]);
                    }
                }
            }
        }
        group.getChildren().addAll(X,Y);
        return group;
    }

    public static Group initTF(TextField[][] textFields, int x,int y){
        Group group = new Group();
        Label[] endpoint_nums = new Label[textFields[0].length];
        for(int i=0;i<textFields.length;i++){
            for(int n=0;n<textFields[0].length;n++){
                textFields[i][n] = new TextField("0");
                textFields[i][n].setLayoutX(x+50*i);
                textFields[i][n].setLayoutY(y+28*n);
                textFields[i][n].setPrefColumnCount(3);
                group.getChildren().addAll(textFields[i][n]);
                endpoint_nums[n] = new Label((n+1)+")");
                endpoint_nums[n].setLayoutX(x-15);
                endpoint_nums[n].setLayoutY(y+28*n);
                group.getChildren().addAll(endpoint_nums[n]);
            }
        }
        return group;
    }

    public static Group initNodes(double[][] node_coor, Circle[] nodes, Label[] nodes_nums){
        Group group = new Group();
        int value;
        int startX=250;
        int startY=525;
        for(int i=0;i<node_coor.length;i++){
            for(int n=0;n<node_coor[0].length;n++){
                if (i==0){
                    do{
                        value=rng(1,20);
                    }while (contains(node_coor[i],value));
                    node_coor[i][n]=value;
                    nodes[n] = new Circle();
                    group.getChildren().addAll(nodes[n]);
                    nodes[n].setCenterX(startX+node_coor[i][n]*25); //absolute value (coor)
                    nodes[n].setRadius(5);
                    nodes_nums[n] = new Label(String.valueOf(n+1));
                    nodes_nums[n].setLayoutX(startX+node_coor[i][n]*25-2);
                    group.getChildren().add(nodes_nums[n]);
                }
                else{
                    do{
                        value=rng(1,20);
                    }while (contains(node_coor[i],value));
                    node_coor[i][n]=value;
                    nodes[n].setCenterY(startY-node_coor[i][n]*25);
                    nodes_nums[n].setLayoutY(startY-node_coor[i][n]*25+5);

                }
            }
        }
        return group;
    }

    public static Group initCurve(Line[] curve){
        Group group = new Group();
        int startX=250;
        int startY=525;
        for(int i=0;i<curve.length;i++){
            curve[i] = new Line();
            curve[i].setStroke(Color.DARKGREEN);
            curve[i].setStartX(startX+curve_coor[0][i]*25);
            curve[i].setEndX(startX+curve_coor[0][i]*25);//to hide them behind X-Y lines
            curve[i].setStartY(startY-curve_coor[1][i]*25);//because curve_coor filled with zeros
            curve[i].setEndY(startY-curve_coor[1][i]*25);
            curve[i].toBack();
            group.getChildren().add(curve[i]);
        }
        return group;
    }

}
