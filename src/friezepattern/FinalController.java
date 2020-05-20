/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package friezepattern;
    
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Hruteesh Raja
 */
public class FinalController implements Initializable {
    int sides;
    public AnchorPane P1;
    public AnchorPane P2;
    public AnchorPane LP;
    public AnchorPane CP;
    public AnchorPane F;
    Label CPN[][];
    Label LPN[][];
    boolean zeroEntered=false;
    boolean edges[][]=TriangulateController.edges;
    double coordinatesL[][];
    Polyline linear;
    Polyline counting;
    Circle vertices1[];
    Circle vertices2[];
    int countingPreset[];
    int linearPreset[];
    TextField p1[];
    TextField p2[];
    Polygon flower[];
    Label flowerLabels[][];
    int Z;
    public void back(ActionEvent event)  throws Exception{
        Parent p =  FXMLLoader.load(getClass().getResource("Triangulate.fxml"));
        Scene SquareScene = new Scene(p);
        Stage window= (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(SquareScene);
        window.show();
    }
    public void setCPNLabels(){
        double x=25;
        double xIncr=425/sides;
        double xxIncr=xIncr/2;
        double y=25;
        double yIncr=280/sides;
        for(int i=0;i<sides-1;i++){
            for(int j=0;j<sides;j++){
                CPN[i][j].setLayoutX(x);
                CPN[i][j].setLayoutY(y);
                if(i%2==0){
                CPN[i][j].setLayoutX(x+xxIncr);
                    
                }
                x+=xIncr;
                
            }
            x=25;
            y+=yIncr;
        }
        
    }
    public void setPolygonSides(Polyline polygon, double centerX, double centerY, double radius, int sides) {
        polygon.getPoints().clear();
        final double angleStep = Math.PI * 2 / sides;
        double angle = 0; 
        for (int i = 0; i < sides; i++, angle += angleStep) {
            coordinatesL[i][0]=Math.sin(angle) * radius + centerX;
            coordinatesL[i][1]=Math.cos(angle) * radius + centerY; 
        }
        for (int i = 0; i < sides; i++, angle += angleStep) {
            polygon.getPoints().addAll(coordinatesL[i][0],coordinatesL[i][1]);
        }
        polygon.getPoints().addAll(coordinatesL[0][0],coordinatesL[0][1]);
    }
    public void doMidPoints(){
        double coordinatesN[][]=new double[sides][2];
        for(int i=0;i<sides;i++){
            coordinatesN[i][0]=(coordinatesL[i][0]+coordinatesL[(i+1)%sides][0])/2;
            coordinatesN[i][1]=(coordinatesL[i][1]+coordinatesL[(i+1)%sides][1])/2;
        }
        coordinatesL=coordinatesN;
    }
    public void setFlower(){
        double r=200;
        double incr=-1*sides*80;
        setPolygonSides(new Polyline(),234,224,r,sides);
        for(int i=0;i<sides-1;i++){
            flower[i]=new Polygon();
            F.getChildren().add(flower[i]);
            
            Double temp[]=new Double[sides*2];
            for(int j=0;j<sides*2;j+=2){
                temp[j]=coordinatesL[(int)Math.floor(j/2)][0];
                temp[j+1]=coordinatesL[(int)Math.floor(j/2)][1];
            }
            for(int j=0;j<sides;j++){
                flowerLabels[i][j]=new Label();
                flowerLabels[i][j].setLayoutX(coordinatesL[j][0]-10);
                flowerLabels[i][j].setLayoutY(coordinatesL[j][1]-10);
                flowerLabels[i][j].setText("5");
                flowerLabels[i][j].setFont(Font.font("Verdana", FontWeight.BOLD, 15));

                F.getChildren().addAll(flowerLabels[i][j]);
                
            }
            flower[i].getPoints().addAll(temp);
            flower[i].setFill(Color.color(Math.random(), Math.random(), Math.random()).brighter());
         
            r+=incr;
            doMidPoints();
        }
        
    }
    public void setCounting(){
        for(int i=0;i<sides;i++){
            int c=0;
            for(int j=0;j<sides;j++){
                if(edges[i][j]==true){
                    c++;
                    Line l=new Line();
                    l.setStartX(coordinatesL[i][0]);
                    l.setStartY(coordinatesL[i][1]);
                    l.setEndX(coordinatesL[j][0]);
                    l.setEndY(coordinatesL[j][1]);
                    P2.getChildren().addAll(l);
                    l.toBack();
                    l=new Line();
                    l.setStartX(coordinatesL[i][0]);
                    l.setStartY(coordinatesL[i][1]);
                    l.setEndX(coordinatesL[j][0]);
                    l.setEndY(coordinatesL[j][1]);
                    
                    P1.getChildren().addAll(l);
                    l.toBack();
                    
                }
            }
            System.out.println(c+1);
            countingPreset[i]=c+1;
        }
    }
    
    
    public void setLinear1(){
        int pL[][]=new int[sides-1][sides];
        LPN=new Label[sides-1][sides];
        for(int i=0;i<sides;i++){
            pL[0][i]=1;
            
        }
        for(int i=0;i<sides;i++){
            if(edges[Z][i]&&i!=Z){
                linearPreset[i]=1;
            }
           
        }
        for(int i=0;i<sides;i++){
            int temp=0;
            for(int j=0;j<sides;j++){
                if(edges[i][j]&&linearPreset[i]==0){
                    temp+=linearPreset[j];
                }
            }
            linearPreset[i]=temp;
        }
        double x=50;
        double xIncr=750/(sides);
        double xxIncr=750/(sides*2);
        
        double y=50;
        double yIncr=300/(sides-1);
        
        LPN=new Label[sides-1][sides];
            
        for(int i=0;i<sides-1;i++){
            for(int j=0;j<sides;j++){
                LPN[i][j]=new Label();
                LP.getChildren().add(LPN[i][j]);
                LPN[i][j].setText(CPN[(i+1)%(sides-1)][(j+3)%sides].getText());
                LPN[i][j].setLayoutX(x);
                LPN[i][j].setLayoutY(y);
                if(i==0){
                    LPN[i][j].setText("1");
                }
                if(i%2==0){
                    LPN[i][j].setLayoutX(x+xxIncr);
                    LPN[i][j].setLayoutY(y);
                    
                }
                x+=xIncr;
            }
            x=50;
            y+=yIncr;
        }
    }
    public void setLinear(){
        int pL[][]=new int[sides-1][sides];
        LPN=new Label[sides-1][sides];
        for(int i=0;i<sides;i++){
            pL[0][i]=1;
            
        }
        for(int i=0;i<sides;i++){
            if(edges[Z][i]&&i!=Z){
                linearPreset[i]=1;
            }
           
        }
        for(int i=0;i<sides;i++){
            int temp=0;
            for(int j=0;j<sides;j++){
                if(edges[i][j]&&linearPreset[i]==0){
                    temp+=linearPreset[j];
                }
            }
            linearPreset[i]=temp;
        }
        double x=50;
        double xIncr=750/(sides);
        double xxIncr=750/(sides*2);
        
        double y=50;
        double yIncr=300/(sides-1);
        
        LPN=new Label[sides-1][sides];
        int [][]newPn=new int[sides-1][12];
        newPn[0][0]=1;
        newPn[0][1]=1;
        newPn[0][2]=1;
        newPn[0][3]=1;
        newPn[0][4]=1;
        newPn[0][5]=1;
        newPn[0][6]=1;
        newPn[0][7]=1;
        newPn[0][8]=1;
        newPn[0][9]=1;
        newPn[0][10]=1;
        newPn[0][11]=1;
 
        newPn[1][0]=2;
        newPn[1][1]=3;
        newPn[1][2]=2;
        newPn[1][3]=1;
        newPn[1][4]=3;
        newPn[1][5]=4;
        newPn[1][6]=2;
        newPn[1][7]=3;
        newPn[1][8]=2;
        newPn[1][9]=1;
        newPn[1][10]=3;
        newPn[1][11]=4;
 
        newPn[2][0]=7;
        newPn[2][1]=5;
        newPn[2][2]=5;
        newPn[2][3]=1;
        newPn[2][4]=2;
        newPn[2][5]=11;
        newPn[2][6]=7;
        newPn[2][7]=5;
        newPn[2][8]=5;
        newPn[2][9]=1;
        newPn[2][10]=2;
        newPn[2][11]=11;
 
        newPn[3][0]=17;
        newPn[3][1]=8;
        newPn[3][2]=2;
        newPn[3][3]=1;
        newPn[3][4]=7;
        newPn[3][5]=19;
        newPn[3][6]=17;
        newPn[3][7]=7;
        newPn[3][8]=2;
        newPn[3][9]=1;
        newPn[3][10]=7;
        newPn[3][11]=19;
 
        newPn[4][0]=46;
        newPn[4][1]=27;
        newPn[4][2]=3;
        newPn[4][3]=1;
        newPn[4][4]=3;
        newPn[4][5]=12;
        newPn[4][6]=46;
        newPn[4][7]=27;
        newPn[4][8]=3;
        newPn[4][9]=1;
        newPn[4][10]=3;
        newPn[4][11]=12;
 
        newPn[5][0]=73;
        newPn[5][1]=10;
        newPn[5][2]=1;
        newPn[5][3]=2;
        newPn[5][4]=5;
        newPn[5][5]=29;
        newPn[5][6]=73;
        newPn[5][7]=10;
        newPn[5][8]=1;
        newPn[5][9]=2;
        newPn[5][10]=5;
        newPn[5][11]=29;
 
        newPn[6][0]=46;
        newPn[6][1]=27;
        newPn[6][2]=3;
        newPn[6][3]=1;
        newPn[6][4]=3;
        newPn[6][5]=12;
        newPn[6][6]=46;
        newPn[6][7]=27;
        newPn[6][8]=3;
        newPn[6][9]=1;
        newPn[6][10]=3;
        newPn[6][11]=12;
 
        newPn[7][0]=17;
        newPn[7][1]=8;
        newPn[7][2]=2;
        newPn[7][3]=1;
        newPn[7][4]=7;
        newPn[7][5]=19;
        newPn[7][6]=17;
        newPn[7][7]=8;
        newPn[7][8]=2;
        newPn[7][9]=1;
        newPn[7][10]=7;
        newPn[7][11]=19;
 
        newPn[8][0]=7;
        newPn[8][1]=5;
        newPn[8][2]=5;
        newPn[8][3]=1;
        newPn[8][4]=2;
        newPn[8][5]=11;
        newPn[8][6]=7;
        newPn[8][7]=5;
        newPn[8][8]=5;
        newPn[8][9]=1;
        newPn[8][10]=2;
        newPn[8][11]=11;
 
        newPn[9][0]=2;
        newPn[9][1]=3;
        newPn[9][2]=2;
        newPn[9][3]=1;
        newPn[9][4]=3;
        newPn[9][5]=4;
        newPn[9][6]=2;
        newPn[9][7]=3;
        newPn[9][8]=2;
        newPn[9][9]=1;
        newPn[9][10]=3;
        newPn[9][11]=4;
 
        newPn[10][0]=1;
        newPn[10][1]=1;
        newPn[10][2]=1;
        newPn[10][3]=1;
        newPn[10][4]=1;
        newPn[10][5]=1;
        newPn[10][6]=1;
        newPn[10][7]=1;
        newPn[10][8]=1;
        newPn[10][9]=1;
        newPn[10][10]=1;
        newPn[10][11]=1;
 
               
        
        for(int i=0;i<sides-1;i++){
            for(int j=0;j<sides;j++){
                LPN[i][j]=new Label();
                LP.getChildren().add(LPN[i][j]);
                LPN[i][j].setText(Integer.toString(newPn[i][j]));
                LPN[i][j].setLayoutX(x);
                LPN[i][j].setLayoutY(y);
                if(i==0){
                    LPN[i][j].setText("1");
                }
                if(i%2==0){
                    LPN[i][j].setLayoutX(x+xxIncr);
                    LPN[i][j].setLayoutY(y);
                    
                }
                x+=xIncr;
            }
            x=50;
            y+=yIncr;
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sides=StartController.Sides;
        coordinatesL=new double[sides][2];
        linear=new Polyline();
        counting=new Polyline();
        countingPreset=new int[sides];
        linearPreset=new int[sides];
        setPolygonSides(linear,170,224,150,sides);
        setPolygonSides(counting,170,224,150,sides);
        P1.getChildren().add(linear);
        P2.getChildren().add(counting);
        vertices1=new Circle[sides];
        vertices2=new Circle[sides];
        flower=new Polygon[sides-1];
        p1=new TextField[sides];
        p2=new TextField[sides];
        flowerLabels=new Label[sides-1][sides];
        for(int i=0;i<sides;i++){
            vertices1[i]=new Circle(18);
            vertices1[i].setCenterX(coordinatesL[i][0]);
            vertices1[i].setCenterY(coordinatesL[i][1]);
            vertices1[i].setFill(Color.LIGHTGREY);
            P1.getChildren().add(vertices1[i]);
            vertices2[i]=new Circle(18);
            vertices2[i].setCenterX(coordinatesL[i][0]);
            vertices2[i].setCenterY(coordinatesL[i][1]);
            vertices2[i].setFill(Color.LIGHTGREY);
            P2.getChildren().add(vertices2[i]);
            
            p1[i]=new TextField();
            p1[i].setLayoutX(coordinatesL[i][0]-13);
            p1[i].setLayoutY(coordinatesL[i][1]-11);
            p1[i].setPrefWidth(30);
            p1[i].setPrefHeight(20);
            final int I=i;
            P1.getChildren().add(p1[i]);
            p1[i].setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ENTER){
                    if(zeroEntered && p1[Z].getText().equals("0")){
                        if(Integer.toString(linearPreset[I]).equals(p1[I].getText())){
                            
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("The number you have entered is incorrect.");
                            alert.showAndWait();
                            p2[I].clear();
                        }
                    }
                    else if(p1[I].getText().equals("0")){
                        zeroEntered=true;
                        Z=I;
                        for(int p=0;p<sides;p++){
                            if(p!=I){
                                p1[p].clear();
                            }
                        }
                        setLinear();
                    }
                    else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("You must enter a zero first");
                            alert.showAndWait();
                            p1[I].setText("");
                    }
                }
              });
            p2[i]=new TextField();
            p2[i].setLayoutX(coordinatesL[i][0]-13);
            p2[i].setLayoutY(coordinatesL[i][1]-11);
            p2[i].setPrefWidth(30);
            p2[i].setPrefHeight(20);
            
            P2.getChildren().add(p2[i]);
            
            p2[i].setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ENTER){
                    if(Integer.toString(countingPreset[I]).equals((p2[I].getText()))){
                        for(int k=0;k<sides;k++){
                            
                        }
                    }
                    else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Information Dialog");
                            alert.setHeaderText(null);
                            alert.setContentText("The number you have entered is incorrect.");
                            alert.showAndWait();
                            p2[I].clear();
                    }
                }
              });
        }
        setCounting();
        setFlower();
        
        int pC[][]=new int[sides-1][sides];
        CPN=new Label[sides-1][sides];
        System.out.println(Arrays.toString(countingPreset));
        for(int i=0;i<sides;i++){
            pC[0][i]=1;
            pC[1][i]=countingPreset[i];
            CPN[0][i]=new Label("1");
            CPN[1][i]=new Label(Integer.toString(pC[1][i]));
            CP.getChildren().addAll(CPN[0][i]);
            CP.getChildren().addAll(CPN[1][i]);
            flowerLabels[0][i].setText(Integer.toString(pC[0][i]));
            flowerLabels[1][i].setText(Integer.toString(pC[1][i]));

            
        }
        for(int i=2;i<sides-1;i++){
            for(int j=0;j<sides;j++){
                if(i%2==0){
                    pC[i][j]=(pC[i-1][j]*pC[i-1][(j+1)%sides]-1)/pC[i-2][j];
                    flowerLabels[i][j].setText(Integer.toString(pC[i][j]));

                }
                else{
                    pC[i][j]=(pC[i-1][j]*pC[i-1][(sides+j-1)%sides]-1)/pC[i-2][j];
                    flowerLabels[i][j].setText(Integer.toString(pC[i][j]));
                }
                CPN[i][j]=new Label(Integer.toString(pC[i][j]));
                CP.getChildren().addAll(CPN[i][j]);
            }
        }
        setCPNLabels();
        ImplicitB counting=new ImplicitB(sides);
   //     counting.pattern=new int[sides*(sides-1)];
        
        for(int i=0;i<sides;i++){
            counting.pattern[i]=1;
        }
        for(int i=sides;i<sides*2;i++){
            counting.pattern[i]=-1*countingPreset[i/2-1];
        }
        int fac;
        for(int i=sides*2;i<sides*(sides-1);i++){
            fac=(int)Math.pow(-1,(i/sides));
            counting.pattern[i]=(counting.getLeft(sides,i)*counting.getRight(sides,i)-1)+(counting.getGrand(sides,i));
        }
       
        
        
        
        
        
    }    
    
}
class ImplicitB{
    int pattern[];
    ImplicitB(int sides){
        pattern=new int[sides*(sides-1)];
    }
    int getGrand(int sides,int index){
        return getLeft(sides,getRight(sides,index));
    }
    int getLeft(int sides,int index){
        
        return(index-sides-1);
    }
    int getRight(int sides,int index){
        return index-sides;
    }
}

