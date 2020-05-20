/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friezepattern;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Hruteesh Raja
 */
public class TriangulateController implements Initializable {
    public AnchorPane CA;
    double coordinates[][];
    int sides;
    int cp=0;
    Polyline poly;
    Circle vertices[];
    int first=-1,second=-1;
    public static boolean edges[][];
    Line lines[];
    Line curLine;
    public void back(ActionEvent event)  throws Exception{
        Parent p =  FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene SquareScene = new Scene(p);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
    }
    public void finish(ActionEvent event) throws Exception{
        if(cp==sides-3){
        Parent p =  FXMLLoader.load(getClass().getResource("Final.fxml"));
        Scene SquareScene = new Scene(p);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Triangulation is not complete. Please complete.");
                alert.showAndWait();
        }
    }
    public void reset(ActionEvent event)  throws Exception{
        Parent p =  FXMLLoader.load(getClass().getResource("Triangulate.fxml"));
        Scene SquareScene = new Scene(p);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
    }
    public void setPolygonSides(Polyline polygon, double centerX, double centerY, double radius, int sides) {
        polygon.getPoints().clear();
        final double angleStep = Math.PI * 2 / sides;
        double angle = 0; 
        for (int i = 0; i < sides; i++, angle += angleStep) {
            coordinates[i][0]=Math.sin(angle) * radius + centerX;
            coordinates[i][1]=Math.cos(angle) * radius + centerY; 
        }
        for (int i = 0; i < sides; i++, angle += angleStep) {
            polygon.getPoints().addAll(coordinates[i][0],coordinates[i][1]);
        }
        polygon.getPoints().addAll(coordinates[0][0],coordinates[0][1]);
        
    }
    public boolean findIntersection(Line a,Line b){
        double intersection[]=new double[2];
        double m1=(a.getStartY()-a.getEndY())/(a.getStartX()-a.getEndX());
        double m2=(b.getStartY()-b.getEndY())/(b.getStartX()-b.getEndX());
        double b1=-m1*a.getStartX()+a.getStartY();
        double b2=-m2*b.getStartX()+b.getStartY();
        intersection[0]=(b2-b1)/(m1-m2);
        intersection[1]=m1*(intersection[0])+b1;
        double astartX=a.getStartX()>a.getEndX()?a.getEndX():a.getStartX();
        double astartY=a.getStartY()>a.getEndY()?a.getEndY():a.getStartY();
        double aendX=a.getStartX()>a.getEndX()?a.getStartX():a.getEndX();
        double aendY=a.getStartY()>a.getEndY()?a.getStartY():a.getEndY();
        double bstartX=b.getStartX()>b.getEndX()?b.getEndX():b.getStartX();
        double bstartY=b.getStartY()>b.getEndY()?b.getEndY():b.getStartY();
        double bendX=b.getStartX()>b.getEndX()?b.getStartX():b.getEndX();
        double bendY=b.getStartY()>b.getEndY()?b.getStartY():b.getEndY();
        
        if(intersection[0]>=astartX&&intersection[0]<=aendX &&intersection[1]>=astartY && intersection[1]<=aendY &&intersection[0]>=bstartX&&intersection[0]<=bendX &&intersection[1]>=bstartY && intersection[1]<=bendY  ){
            
                return true;
        }
         return false;
    }
    
    
    public void drawLine(){
        boolean possible=true;
        curLine.setStartX(vertices[first].getCenterX());
        curLine.setStartY(vertices[first].getCenterY());
        curLine.setEndX(vertices[second].getCenterX());
        curLine.setEndY(vertices[second].getCenterY());
        curLine.setVisible(true);
        if(first!=second-1 && first!=second+1 && !(first==sides-1&&second==0) && !(second==sides-1&& first==0)){
            

            for(int i=0;i<cp;i++){
                if(findIntersection(curLine,lines[i]) && !(curLine.getStartX()==lines[i].getStartX()&&curLine.getStartY()==lines[i].getStartY())&& !(curLine.getStartX()==lines[i].getEndX()&&curLine.getStartY()==lines[i].getEndY())&& !(curLine.getEndX()==lines[i].getStartX()&&curLine.getEndY()==lines[i].getStartY())&& !(curLine.getEndX()==lines[i].getEndX()&&curLine.getEndY()==lines[i].getEndY()) || (curLine.getStartX()==lines[i].getStartX()&&curLine.getStartY()==lines[i].getStartY()  && curLine.getEndX()==lines[i].getEndX()&&curLine.getEndY()==lines[i].getEndY()) || (curLine.getEndX()==lines[i].getStartX()&&curLine.getEndY()==lines[i].getStartY()  && curLine.getStartX()==lines[i].getEndX()&&curLine.getStartY()==lines[i].getEndY()))                         {
                    possible=false;
                    
                    break;
                }

            }
            if(possible){
                lines[cp].setStartX(vertices[first].getCenterX());
                lines[cp].setStartY(vertices[first].getCenterY());
                lines[cp].setEndX(vertices[second].getCenterX());
                lines[cp].setEndY(vertices[second].getCenterY());
                edges[first][second]=true;
                edges[second][first]=true;
                lines[cp].setVisible(true);
                cp++;
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Triangulation diagonals cannot intersect. Please try again.");
                alert.showAndWait();
            }
        }
        else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Triangulation cannot be formed by adjacent vertices. Please try again.");
                alert.showAndWait();

        }
                curLine.setVisible(false);

        first=-1;
        second=-1;
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       sides= StartController.Sides;
       coordinates=new double[sides][2];
       lines=new Line[sides-3];
       poly=new Polyline();
       poly.setStrokeWidth(3);
       setPolygonSides(poly, 444,444,200,sides);
       CA.getChildren().add(poly);
       curLine=new Line();
       edges=new boolean[sides][sides];
       vertices=new Circle[sides];
       CA.getChildren().add(curLine);
       curLine.setVisible(false);
       for(int i=0;i<sides-3;i++){
           lines[i]=new Line();
           CA.getChildren().add(lines[i]);
           lines[i].setVisible(false);
       }
       for(int i=0;i<sides;i++){
           vertices[i]=new Circle(10);
           vertices[i].setCenterX(coordinates[i][0]);
           vertices[i].setCenterY(coordinates[i][1]);
           CA.getChildren().add(vertices[i]);
           final int I=i;
           vertices[i].setOnMouseClicked(t -> {
               if(first==-1){
                   first=I;
                   
               }
               else{
                   second=I;
                   drawLine();
               }
               maxOPG poly=new maxOPG(sides);
           });
       }
    }    
    
}
class maxOPG{
    int [][]vertices;
    maxOPG(int sides){
        vertices=new int[sides][2];
    }
}

