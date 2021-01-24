package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML private Shape Selected_Color;
    @FXML private TextField Selected_Color_Code;
    @FXML private TextField R;
    @FXML private TextField G;
    @FXML private TextField B;
    @FXML private ImageView img;
    @FXML private Button GetImg;
    @FXML private Button GetScrn;
    @FXML private Label LocX;
    @FXML private Label LocY;
    @FXML private Label Red;
    @FXML private Label Green;
    @FXML private Label Blue;
    @FXML private Label Hex;

    private Image image=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GetImg.setOnAction(e->{
            File tmp=null;
            if((tmp=new FileChooser().showOpenDialog(Main.MainWindow))!=null){
                FileInputStream img_stream=null;
                try {
                    img_stream=new FileInputStream(tmp);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                if(!(image=new Image(img_stream)).isError()){
                    img.setFitHeight(image.getHeight());
                    img.setFitWidth(image.getWidth());
                    img.setImage(image);
                }
                else {
                    Toolkit.getDefaultToolkit().beep();
                    Alert alert=new Alert(Alert.AlertType.ERROR,"The file you selected is not any image file or it is corrupted ...");
                    alert.setHeaderText("");
                    alert.showAndWait();
                }
            }
        });

        GetScrn.setOnAction(e->{
            Transferable transferable=Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                try {
                    image= SwingFXUtils.toFXImage((BufferedImage) transferable.getTransferData(DataFlavor.imageFlavor),null);
                    img.setFitHeight(image.getHeight());
                    img.setFitWidth(image.getWidth());
                    img.setImage(image);
                } catch (Exception e1) {
                    Toolkit.getDefaultToolkit().beep();
                    e1.printStackTrace();
                }
            }
            else Toolkit.getDefaultToolkit().beep();
        });

        img.setOnMouseClicked(event -> {
            if(image!=null){
                double x=event.getX(),y=event.getY();
                Color color=null;
                try{
                    color=image.getPixelReader().getColor(((int) x), ((int) y));
                }catch (Exception e1){ }

                int red=(int)(color.getRed()*255); String red_str=Integer.toHexString(red).toUpperCase(); red_str = red_str.length()<2 ? ("0"+red_str):red_str;
                int green=(int)(color.getGreen()*255); String green_str=Integer.toHexString(green).toUpperCase(); green_str = green_str.length()<2 ? ("0"+green_str):green_str;
                int blue=(int)(color.getBlue()*255); String blue_str=Integer.toHexString(blue).toUpperCase(); blue_str = blue_str.length()<2 ? ("0"+blue_str):blue_str;
                R.setText(Integer.toString(red));
                G.setText(Integer.toString(green));
                B.setText(Integer.toString(blue));
                String col="#"+red_str+green_str+blue_str;
                Selected_Color_Code.setText(col);
                Selected_Color.setFill(Paint.valueOf(col));
            }
        });

        img.setOnMouseMoved(event->{
            if(image!=null){
                double x=event.getX(),y=event.getY();
                Color color=null;
                try{
                    color=image.getPixelReader().getColor(((int) x), ((int) y));
                }catch (Exception e1){ }
                LocX.setText("X: "+x);
                LocY.setText("Y: "+y);
                int red=(int)(color.getRed()*255);
                int green=(int)(color.getGreen()*255);
                int blue=(int)(color.getBlue()*255);
                String red_str=Integer.toHexString(red).toUpperCase(); red_str = red_str.length()<2 ? ("0"+red_str):red_str;
                String green_str=Integer.toHexString(green).toUpperCase(); green_str = green_str.length()<2 ? ("0"+green_str):green_str;
                String blue_str=Integer.toHexString(blue).toUpperCase(); blue_str = blue_str.length()<2 ? ("0"+blue_str):blue_str;
                Red.setText("RED :  "+red);
                Green.setText("GREEN :  "+green);
                Blue.setText("BLUE :  "+blue);
                Hex.setText(" RGB HEX :  "+"#"+red_str+green_str+blue_str);
            }
        });

        img.setOnMouseExited(e->{
            LocX.setText("X :  N/A");
            LocY.setText("  Y :  N/A");
            Red.setText("RED :  N/A");
            Green.setText("  GREEN :  N/A");
            Blue.setText("  BLUE :  N/A");
            Hex.setText("  RGB HEX: N/A");
        });
    }
}
