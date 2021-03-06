/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import static controller.ArbitraryKernelViewController.isNumeric;
import java.awt.image.BufferedImage;
import object.BlankPic;
import object.ImageSize;
import object.Stack;
import object.UserActions;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.max;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelFormat;
import javafx.stage.FileChooser;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import object.Convolution;
import object.MedianCut;
import static object.MedianCut.create_Palette;
import object.OpenCVUtils;
import object.PointXY;
import object.StructuringElem;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;





/**
 *
 * @author Jose Pio
 */
public class MainController implements Initializable {
    
    MainController mainInstanceController;
    
    private BlankPic pic;
    
    private Image image;
    private WritableImage writableImage;
    private WritableImage writableNetpbm;
    private WritableImage zoomWritable;
    private WritableImage regionImage;

    
    private PixelReader pixelReader;
    private PixelReader zoomReader;
    
    private PixelWriter pixelWriter;
    private PixelWriter pixelWriterNetpbm;
    private PixelWriter zoomWriter;
    
    private Mat complexImage;

    private ArrayList planes;

    
    private int imageWidth;
    private int imageHeight;
    
    private PointXY pixelPicker;
    private PointXY pixelDelta;
    
    private Mat kernelStructuring;
    
    private int imageX;
    private int imageY;
    
    private boolean isBlackWhite;

    public UserActions userActions;
    private int capacityActions;
    
    private Color [][] bufferNetpbm;
    private Color varChooseColor;
             
    @FXML
    private ImageView imageView;
    
    @FXML
    private Button buttonToNegative;
    
    @FXML
    private Button buttonToBlackWhite;
    
    @FXML
    private Label labelLoadMessage;
    
    @FXML
    private Label infoDimensionsImage;
    
    @FXML
    private Label infoFormatPixelImage;
    
    private ArrayList uniqueColorsList;
    
    @FXML
    private Label infoUniqueColorsImage;
    @FXML
    private Slider sliderToBrightness;
    @FXML
    private TitledPane labelBrightness;
    
    private String numberMagic;
    @FXML
    private Button btnDefault;
    @FXML
    private TitledPane labelGrayscale;
    @FXML
    private Slider sliderToGrayscale;
    @FXML
    private TitledPane labelContrast;
    @FXML
    private Slider sliderToContrast;
    @FXML
    private Button btnLoadImage;
    @FXML
    private TitledPane labelThresholding;
    @FXML
    private Slider sliderToThresholding;
    @FXML
    private Label labelAverageX;
    @FXML
    private Label labelAverageY;
    @FXML
    private Slider sliderToAverageX;
    @FXML
    private Slider sliderToAverageY;
    @FXML
    private Slider sliderToMedianX;
    @FXML
    private Slider sliderToMedianY;
    @FXML
    private Label labelMedianX;
    @FXML
    private Label labelMedianY;
    @FXML
    private Slider sliderToGaussianX;
    @FXML
    private Slider sliderToGaussianY;
    @FXML
    private Label labelGaussianX;
    @FXML
    private Label labelGaussianY;
    @FXML
    private Slider sliderToLaplacianY;
    @FXML
    private Label labelLaplacianX;
    @FXML
    private Label labelLaplacianY;
    @FXML
    private Slider sliderToLaplacianX;
    @FXML
    private Slider sliderToSobelY;
    @FXML
    private Label labelSobelX;
    @FXML
    private Label labelSobelY;
    @FXML
    private Slider sliderToSobelX;
    @FXML
    private Slider sliderToPrewittY;
    @FXML
    private Label labelPrewittX;
    @FXML
    private Label labelPrewittY;
    @FXML
    private Slider sliderToPrewittX;
    @FXML
    private Slider sliderToZoom;
    @FXML
    private Label labelZoom;
    @FXML
    private ToggleGroup zoomMethod;
    
        
    @FXML
    private RadioButton zoomNeighbor;
    @FXML
    private RadioButton zoomInterpolation;
    @FXML
    private ToggleGroup sobelDerivate;
    @FXML
    private ToggleGroup prewittDerivate;
    @FXML
    private CheckBox overflowZoom;
    private Slider sliderToRobertsY;
    private Label labelRobertsX;
    private Label labelRobertsY;
    private Slider sliderToRobertsX;
    @FXML
    private ToggleGroup robertsDerivate;
    @FXML
    private Slider sliderToRoberts;
    @FXML
    private Label labelRoberts;
    @FXML
    private Slider sliderToLoGY;
    @FXML
    private Label labelLoGX;
    @FXML
    private Label labelLoGY;
    @FXML
    private Slider sliderToLoGX;
    @FXML
    private Slider sliderToArbitraryX;
    @FXML
    private Slider sliderToArbitraryY;
    @FXML
    private Label labelArbitraryX;
    @FXML
    private Label labelArbitraryY;
    
        @FXML
    private ToggleGroup saveMethod;
    @FXML
    private AnchorPane imageWrapper;
    @FXML
    private Button buttonRotateLeft;
    @FXML
    private Button buttonRotateRight;
    @FXML
    private CheckBox includeBorders;
    @FXML
    private Button structuringElemButton;
    @FXML
    private Button erosionButton;
    @FXML
    private Button dilationButton;
    @FXML
    private Button apertureButton;
    @FXML
    private Button closureButton;
    @FXML
    private AnchorPane showColorPick;
    @FXML
    private RadioButton regionFixedButton;
    @FXML
    private ToggleGroup regionRank;
    @FXML
    private RadioButton regionFloatButton;
    @FXML
    private RadioButton regionNeighbor4;
    @FXML
    private ToggleGroup regionNeighbors;
    @FXML
    private RadioButton regionNeighbor8;
    @FXML
    private Label colorPickerRGB;
    private Slider sliderToKMeans;
    private Label labelKMeans;
    @FXML
    private TextField inputKMeans;
    @FXML
    private Slider sliderToTolerance;
    @FXML
    private ToggleGroup toggleButtonImage;
    @FXML
    private ToggleButton toggleRotation;
    @FXML
    private ToggleButton togglePanning;
    @FXML
    private AnchorPane mainPaneScene;
    @FXML
    private ToggleButton togglePickerButton;
    @FXML
    private ColorPicker buttonChooseColor;
    @FXML
    private TextField inputBitReduction;
    @FXML
    private RadioButton radioBmp;
    @FXML
    private RadioButton radioNetpbm;
    @FXML
    private RadioButton radioJpg;
    @FXML
    private RadioButton radioPng;
    @FXML
    private Button defaultElemButton;
    @FXML
    private Slider sliderToMedianCut;
    @FXML
    private Label labelMedianCut;


    
   
    
    final ChangeListener<Number> sliderGaussianX = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToGaussianX.valueProperty().set(roundedValue);
        labelGaussianX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderGaussianY = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToGaussianY.valueProperty().set(roundedValue);
        labelGaussianY.setText(Integer.toString(roundedValue));
    };

    final ChangeListener<Number> sliderLaplacianX = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToLaplacianX.valueProperty().set(roundedValue);
        labelLaplacianX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderLaplacianY = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToLaplacianY.valueProperty().set(roundedValue);
        labelLaplacianY.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderSobelX = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToSobelX.valueProperty().set(roundedValue);
        labelSobelX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderSobelY = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToSobelY.valueProperty().set(roundedValue);
        labelSobelY.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderPrewittX = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToPrewittX.valueProperty().set(roundedValue);
        labelPrewittX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderPrewittY = (obs, old, val) -> {
        final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToPrewittY.valueProperty().set(roundedValue);
        labelPrewittY.setText(Integer.toString(roundedValue));
    };   
    
    final ChangeListener<Number> sliderZoom = (obs, old, val) -> {
        final double roundedValue = Math.round(0.1 + val.doubleValue() * 10) / 10.0;
        sliderToZoom.valueProperty().set(roundedValue);
        labelZoom.setText(Double.toString(roundedValue) + "x");
    };
    
    final ChangeListener<Number> sliderRoberts = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToRoberts.valueProperty().set(roundedValue);
        labelRoberts.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderLoGX = (obs, old, val) -> {
       final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToLoGX.valueProperty().set(roundedValue);
        labelLoGX.setText(Integer.toString(roundedValue));
    };
    final ChangeListener<Number> sliderLoGY = (obs, old, val) -> {
       final int roundedValue = (val.intValue() /2) * 2 + 1;
        sliderToLoGY.valueProperty().set(roundedValue);
        labelLoGY.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderAverageX = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToAverageX.valueProperty().set(roundedValue);
        labelAverageX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderAverageY = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToAverageY.valueProperty().set(roundedValue);
        labelAverageY.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderMedianX = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToMedianX.valueProperty().set(roundedValue);
        labelMedianX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderMedianY = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToMedianY.valueProperty().set(roundedValue);
        labelMedianY.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderArbitraryX = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToArbitraryX.valueProperty().set(roundedValue);
        labelArbitraryX.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderArbitraryY = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToArbitraryY.valueProperty().set(roundedValue);
        labelArbitraryY.setText(Integer.toString(roundedValue));
    };
    
    final ChangeListener<Number> sliderTolerance = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToTolerance.valueProperty().set(roundedValue);
    };
    final ChangeListener<Number> sliderMedianCut = (obs, old, val) -> {
        final int roundedValue = val.intValue();
        sliderToMedianCut.valueProperty().set(roundedValue);
        labelMedianCut.setText(Integer.toString(roundedValue));
    };


    
    
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mainInstanceController = this;
        pixelPicker = null;
        
        sliderToGaussianX.valueProperty().addListener(sliderGaussianX);
        sliderToGaussianY.valueProperty().addListener(sliderGaussianY);

        sliderToLaplacianX.valueProperty().addListener(sliderLaplacianX);
        sliderToLaplacianY.valueProperty().addListener(sliderLaplacianY);
        
        sliderToSobelX.valueProperty().addListener(sliderSobelX);
        sliderToSobelY.valueProperty().addListener(sliderSobelY);
        
        sliderToPrewittX.valueProperty().addListener(sliderPrewittX);
        sliderToPrewittY.valueProperty().addListener(sliderPrewittY);
        
        sliderToLoGX.valueProperty().addListener(sliderLoGX);
        sliderToLoGY.valueProperty().addListener(sliderLoGY);
        
        sliderToZoom.valueProperty().addListener(sliderZoom);
        
        sliderToRoberts.valueProperty().addListener(sliderRoberts);
        
        sliderToAverageX.valueProperty().addListener(sliderAverageX);
        sliderToAverageY.valueProperty().addListener(sliderAverageY);
        
        sliderToMedianX.valueProperty().addListener(sliderMedianX);
        sliderToMedianY.valueProperty().addListener(sliderMedianY);
        
        sliderToArbitraryX.valueProperty().addListener(sliderArbitraryX);
        sliderToArbitraryY.valueProperty().addListener(sliderArbitraryY);
        
        sliderToTolerance.valueProperty().addListener(sliderTolerance);
        
        sliderToMedianCut.valueProperty().addListener(sliderMedianCut);
        
        initEventListener();
        
        buttonChooseColor.setValue(new Color(0.4352,0.76470,0.9333,1));
        varChooseColor = buttonChooseColor.getValue();
        
        defaultKernelConfiguration();
    }
    
    private void initEventListener() {
                
        imageView.setPickOnBounds(true);
        imageView.setOnMouseDragged(e -> {
            if(image != null) {
                if(togglePickerButton.isSelected()) {
                    handleDraggedPickColor(e);
                }else if(togglePanning.isSelected()) {
                    handlePanning(e);
                }else if(toggleRotation.isSelected()){
                    handleRotation(e);
                }
            }
        });
        
        imageView.setOnMousePressed(e -> {
            if(image != null) {
                if(togglePanning.isSelected() || toggleRotation.isSelected()) {
                    double zoomValue = sliderToZoom.getValue();
                    int x = (int) (e.getX() / zoomValue);
                    int y = (int) (e.getY() / zoomValue);
                    
                    pixelDelta = new PointXY(x, y, 0,0);
                }
            }

        });
        
        imageView.setOnMouseReleased(e -> {
            if(image != null) {
                if(togglePanning.isSelected()) {
                    panningContext();
                } else if(toggleRotation.isSelected()) {
                    rotationContext();
                }
            }

        });

        imageView.setOnMouseEntered(e -> {
            if(image != null) {
                if(togglePanning.isSelected()) { 
                    mainPaneScene.getScene().getRoot().setCursor(Cursor.MOVE);
                } else if(toggleRotation.isSelected()) {
                    mainPaneScene.getScene().getRoot().setCursor(Cursor.CLOSED_HAND);
                } else if(togglePickerButton.isSelected()) {
                    mainPaneScene.getScene().getRoot().setCursor(Cursor.CROSSHAIR);
                }
            }
        });
        
        imageView.setOnMouseExited(e -> {
            if(image != null) {
                mainPaneScene.getScene().getRoot().setCursor(Cursor.DEFAULT);
            }
        });
        
    }
    
    private void handleDraggedPickColor(MouseEvent e) {
        double zoomValue = sliderToZoom.getValue();

        int eX = (int) (e.getX() / zoomValue);
        int eY = (int) (e.getY() / zoomValue);

        if(eX < 0) eX = 0;
        if(eX > imageWidth - 1) eX = imageWidth - 1;
        if(eY < 0) eY = 0;
        if(eY > imageHeight - 1) eY = imageHeight - 1;


        pixelReader = writableImage.getPixelReader();
        Color pick = pixelReader.getColor(eX, eY);
        pixelPicker = new PointXY(eX, eY, 0, 0);
        int red = (int) (pick.getRed() * 255);
        int green = (int) (pick.getGreen() * 255);
        int blue = (int) (pick.getBlue() * 255);
        showColorPick.setStyle(
                "-fx-background-color: rgba("
                + red + ","
                + green + "," 
                + blue + "," 
                +"1.0"
                + ");"
        );
        colorPickerRGB.setText("rgb(" + red + "," + blue + "," + green + ")");
    }
    
    
    private void handlePanning(MouseEvent e) {
        double zoomValue = sliderToZoom.getValue();

        int eX = (int) (e.getX() / zoomValue);
        int eY = (int) (e.getY() / zoomValue);

        if(eX < 0) eX = 0;
        if(eX > imageWidth - 1) eX = imageWidth - 1;
        if(eY < 0) eY = 0;
        if(eY > imageHeight - 1) eY = imageHeight - 1;
        
        int deltaX = pixelDelta.getPosX();
        int deltaY = pixelDelta.getPosY();
        
        int diffX = deltaX - eX;
        int diffY = deltaY - eY;
        
        int yInit = Integer.signum(diffY) > 0 ? 0 : Math.abs(diffY);
        int yEnd = Integer.signum(diffY) > 0 ? imageHeight - Math.abs(diffY) : imageHeight;
        
        int xInit = Integer.signum(diffX) > 0 ? 0 : Math.abs(diffX);
        int xEnd = Integer.signum(diffX) > 0 ? imageWidth - Math.abs(diffX) : imageWidth;
                

        Color[][] matrixWhite = new Color[imageWidth][imageHeight];
        Color[][] current = pic.getColorMatrix();
        Color[][] scaleMatrix = new Color [imageWidth][imageHeight];
//        WritableImage imagePanning = new WritableImage(imageWidth, imageHeight);
        PixelWriter panningWriter = writableImage.getPixelWriter();

        for(int y = 0; y < imageHeight; y++) {
            for(int x = 0; x< imageWidth; x ++) {
                matrixWhite[x][y] = scaleMatrix[x][y] = new Color(1,1,1,1.0);
                panningWriter.setColor(x,y,matrixWhite[x][y]);
            }
        }
        
        for(int y = yInit; y < yEnd; y++) {
            for(int x = xInit; x < xEnd; x ++) {
                int valueX = Integer.signum(diffX) > 0 ? x + Math.abs(diffX) : x - xInit;
                int valueY = Integer.signum(diffY) > 0 ? y + Math.abs(diffY) : y - yInit;
                matrixWhite[x][y] = scaleMatrix[x][y] = current[valueX][valueY];
                panningWriter.setColor(x,y,matrixWhite[x][y]);
            }
        }

        pic.setScaleMatrix(scaleMatrix);
        handleZoom();
    }
    
    private void handleRotation(MouseEvent e) {
        double zoomValue = sliderToZoom.getValue();
        
        int mX = Math.round(imageWidth / 2);
        int mY = Math.round(imageHeight / 2);

        int eX = (int) (e.getX() / zoomValue);
        int eY = (int) (e.getY() / zoomValue);

        if(eX < 0) eX = 0;
        if(eX > imageWidth - 1) eX = imageWidth - 1;
        if(eY < 0) eY = 0;
        if(eY > imageHeight - 1) eY = imageHeight - 1;
        
        int deltaX = pixelDelta.getPosX();
        int deltaY = pixelDelta.getPosY();
        
        int diffX = mX - eX;
        int diffY = mY - eY;
        
        
        double radians = Math.atan2(diffY,diffX);
        
//        diffY = Math.abs(diffY);
//        diffX = Math.abs(diffX);
//        double dist = Math.sqrt((diffY*diffY)+(diffX*diffX)); 
        
       
        Color[][] matrixWhite = new Color[imageWidth][imageHeight];
        Color[][] current = pic.getColorMatrix();
        Color[][] scaleMatrix = new Color [imageWidth][imageHeight];

        PixelWriter panningWriter = writableImage.getPixelWriter();
        

        for(int y = 0; y < imageHeight; y++) {
            for(int x = 0; x< imageWidth; x ++) {
                matrixWhite[x][y] = scaleMatrix[x][y] = new Color(1,1,1,1.0);
                panningWriter.setColor(x,y,matrixWhite[x][y]);
            }
        }
        
        for(int y = 0; y < imageHeight; y++) {
            for(int x = 0; x < imageWidth; x ++) {
                int rX = (int) Math.round(Math.cos(radians) * (x-mX) - Math.sin(radians) * (y-mY) + mX);
                int rY = (int) Math.round((x-mX) * Math.sin(radians) + (y-mY) * Math.cos(radians) + mY);
                if(rX >= 0 && rX < imageWidth && rY >= 0 && rY < imageHeight) {
                    matrixWhite[x][y] = scaleMatrix[x][y] = current[rX][rY];
                    panningWriter.setColor(x,y,matrixWhite[x][y]);                    
                }
            }
        }
        
        pic.setScaleMatrix(scaleMatrix);
        handleZoom();        
        
    }
    
    
    private void setImageLoaded(Image image) {
        this.image = image;
    }
    
    
    private void recalculateInfo() {
        if(image != null) {
            calculateUniqueColor();
            displayUniqueColor();
            displayDimensionsLabel();
            displayPixelsFormatLabel();
        }
    }
    
    private void calculateUniqueColor() {
        uniqueColorsList.clear();
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                addColorsUnique(pixelReader.getArgb(x, y));
            }
        }

    }
    
     
    private void configurationInit() {
        imageView.setImage(pic.getImageOriginal());
        configurationImageView();        
        labelLoadMessage.setText("Loaded successfully!");
        recalculateInfo();
    }
    
    private void configurationImageView() {
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(0);
        imageView.setFitHeight(0);   
    }
    
    


    @FXML
    private void handleLoadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load image in BMP or Netpbm format");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Allowed formats (Bmp | Jpg | Png | Netpbm)", "*.bmp", "*.jpg", "*.png", "*.pbm", "*.pgm", "*.ppm"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),               
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),               
                new FileChooser.ExtensionFilter("PNG", "*.png"),               
                new FileChooser.ExtensionFilter("Netpbm", "*.pbm", "*.pgm", "*.ppm")
        );

        File imgPath = fileChooser.showOpenDialog(null);

        boolean compatible = true;
        
        if (imgPath != null) {
            pic = new BlankPic();
            pic.setFileFormat(
                    imgPath.getName().substring(imgPath.getName().lastIndexOf(".") + 1,
                    imgPath.getName().length())
            );
            uniqueColorsList = new ArrayList();
            capacityActions = 30;
            userActions = new UserActions(capacityActions);
            switch(pic.getFileFormat()) {
                case "png":
                case "jpg":
                case "bmp":                
                    restartZoom();
                    bmpLoader(imgPath);
                    break;
                case "pbm":
                    restartZoom();
                    pbmLoader(imgPath);
                    break;
                case "ppm":
                case "pgm":
                    restartZoom();
                    pgmLoader(imgPath);
                    break;
                  default:
                    labelLoadMessage.setText("Incompatible Format.");
                    compatible = false;
                    break;
            }
            if(compatible) {
                restartUI();
                WritableImage changeImage = imageWriter();

                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Default Image", dimensions);
                userActions.addStep(step);                   
            }
            
        }       
    }
    
    @FXML
    private void convertToNegative(ActionEvent event) {
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            Color [][] current = pic.getColorMatrix();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
               for (int x = 0; x < imageWidth; x++) {                
                   Color negative = new Color(
                           1 - current[x][y].getRed(), 
                           1 - current[x][y].getGreen(),
                           1 - current[x][y].getBlue(),
                           1.0
                   );
                   pixelWriter.setColor(x,y,negative);
//                   current[x][y] = negative;
               }
            }
            negativeContext();
//            handleZoom();
            configurationImageView();

        }
    }
    
 
    
    @FXML
    private void convertToBlackWhite() {        
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            Color [][] current = pic.getColorMatrix();
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
               for (int x = 0; x < imageWidth; x++) {
                   double average = (
                           current[x][y].getRed() +
                           current[x][y].getGreen() +
                           current[x][y].getBlue()
                           )/3;
                   Color blackwhite;
                    if(average > 0.5) {
                        blackwhite = new Color(1, 1, 1, 1.0);
                    } else {
                        blackwhite = new Color(0, 0, 0, 1.0);
                    }

                    pixelWriter.setColor(x,y,blackwhite);
                    current[x][y] = blackwhite;
               }
            }

            blackWhiteContext();
            configurationImageView();
        }
    }
 
    
    private void  setColorPixelsBmp() {
        Color [][] current = new Color[imageWidth][imageHeight];
        Color [][] original = new Color[imageWidth][imageHeight];
        pixelWriter = writableImage.getPixelWriter();

        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                original[x][y] = current[x][y] = pixelReader.getColor(x, y);
//                addColorsUnique(pixelReader.getArgb(x, y));
                pixelWriter.setColor(x,y,current[x][y]);
            }
        }
        pic.setUniqueColors(uniqueColorsList);        
        pic.setColorMatrix(current);
        pic.setScaleMatrix(current);
        pic.setOriginalMatrix(original);
    }
    
    private void  addColorsUnique(int colorPixel) {
        if(!uniqueColorsList.contains(colorPixel)){
            uniqueColorsList.add(colorPixel);
        } 
    }
        
    private void setImageSize(int width, int height) {
        imageWidth = width;
        imageHeight = height;
    }
    
        
    private void displayDimensionsLabel() {
        infoDimensionsImage.setText("Dimensions in pixels: " + imageHeight + " x " + imageWidth + ".");
    }
    
    private void  displayPixelsFormatLabel() {
        String format = pic.getFileFormat();
        
        switch(format) {
            case "bmp":
                displayPixelsFormatLabelBmp();
                break;
            case "pbm":
                infoFormatPixelImage.setText("Bits per pixel original image: 1bpp.");  
                break;
            case "pgm":
                infoFormatPixelImage.setText("Bits per pixel original image: 8bpp.");
                break;
            case "ppm":
                infoFormatPixelImage.setText("Bits per pixel original image: 24bpp.");
                break;
        }
    }
    
    private void  displayPixelsFormatLabelBmp() {
        PixelFormat.Type type = pixelReader.getPixelFormat​().getType();
        switch(type) {
            case INT_ARGB_PRE:
            case INT_ARGB:
            case BYTE_BGRA_PRE:
            case BYTE_BGRA:
                infoFormatPixelImage.setText("Bits per pixel original image: 32bpp.");
                break;
            case BYTE_INDEXED:
                infoFormatPixelImage.setText("The pixel colors are referenced by byte index stored in the pixel array.");
                break;
            case BYTE_RGB:
                infoFormatPixelImage.setText("Bits per pixel original image: 24bpp.");                
        }        
    }
 
    private void displayUniqueColor() {
        infoUniqueColorsImage.setText(
                "The image contains " + uniqueColorsList.size() + " unique color/s."
        );
    }
    
    @FXML
    private void handleGrayscale(MouseEvent event) {
            if(image != null) {
            restartBrightness();
            restartContrast();
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            Color [][] current = pic.getColorMatrix();
            double gv = (double) sliderToGrayscale.getValue();
            pixelWriter = writableImage.getPixelWriter();
            Color[][] scaleMatrix = new Color [imageWidth][imageHeight];
            double gred, ggreen, gblue;
                for (int y = 0; y < imageHeight; y++) {
                   for (int x = 0; x < imageWidth; x++) {
                       gred = current[x][y].getRed();
                       ggreen = current[x][y].getGreen();
                       gblue = current[x][y].getBlue();
                       double average = (gred + ggreen + gblue)/3;
                       Color grayScale = new Color(
                               gred * (1 - gv) + average * gv,
                               ggreen * (1 - gv) + average * gv,
                               gblue * (1 - gv) + average * gv,
                               1.0);
                        pixelWriter.setColor(x,y,grayScale);
                        scaleMatrix[x][y] = grayScale;
                   }
                }
            pic.setScaleMatrix(scaleMatrix);
            handleZoom();
            configurationImageView();
            int text = (int) (gv * 100);
            labelGrayscale.setText("Grayscale: " + text + "%");           
        }    
    }
    
    private double truncatePixel(double pixel) {
        if(pixel > 1) return pixel = 1;
        if(pixel < 0) return pixel = 0;
        return pixel;
    }

    @FXML
    private void handleBrightness(MouseEvent event) {
        if(image != null) {
            restartGrayscale();
            restartContrast();
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            Color [][] current = pic.getColorMatrix();            
            double brightnessValue = (double) sliderToBrightness.getValue();
            pixelWriter = writableImage.getPixelWriter();
            Color[][] scaleMatrix = new Color [imageWidth][imageHeight];
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    double r = truncatePixel(current[x][y].getRed() + brightnessValue);
                    double g = truncatePixel(current[x][y].getGreen() + brightnessValue);
                    double b = truncatePixel(current[x][y].getBlue() + brightnessValue);
                    Color brightness = new Color(r,g,b,1.0);
                    pixelWriter.setColor(x,y,brightness);
                    scaleMatrix[x][y] = brightness;
                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();
            int text = (int) (brightnessValue * 100);
            labelBrightness.setText("Brightness: " + text + "%");           
        }
    }
    
        @FXML
    private void handleContrast(MouseEvent event) {
        if(image != null) {
            restartGrayscale();
            restartBrightness();
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            Color [][] current = pic.getColorMatrix();
            double contrastValue = (double) sliderToContrast.getValue();
            double factor = (1.0156 *(1 + contrastValue)) / (1 * (1.0156 - contrastValue));
            pixelWriter = writableImage.getPixelWriter();
            Color[][] scaleMatrix = new Color [imageWidth][imageHeight];
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    double r = truncatePixel(
                        factor * (current[x][y].getRed() - 0.5) + 0.5
                    );
                    double g = truncatePixel(
                        factor * (current[x][y].getGreen() - 0.5) + 0.5
                    );
                    double b = truncatePixel(
                        factor * (current[x][y].getBlue() - 0.5) + 0.5
                    );
                    Color contrast = new Color(r,g,b,1.0);
                    pixelWriter.setColor(x,y,contrast);
                    scaleMatrix[x][y] = contrast;
                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();
            int text = (int) (contrastValue * 100);
            labelContrast.setText("Contrast: " + text + "%");           
        }
    }

    
    
        @FXML
    private void handleThresholding() {
        if(image != null) {
            restartGrayscale();
            restartBrightness();
            restartContrast();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            Color [][] current = pic.getColorMatrix();
            double thresholdingValue = (double) sliderToThresholding.getValue();
            Color [][] scaleMatrix = new Color[imageWidth][imageHeight];
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    double average = (
                           current[x][y].getRed() +
                           current[x][y].getGreen() +
                           current[x][y].getBlue()
                           )/3;
                   Color thresholding;
                    if(average > thresholdingValue) {
                        thresholding = new Color(1, 1, 1, 1.0);
                    }else{
                        thresholding = new Color(0, 0, 0, 1.0);
                    }
  
                    pixelWriter.setColor(x,y,thresholding);
                    scaleMatrix[x][y] = thresholding;
                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();
            int text = (int) (thresholdingValue * 255);
            labelThresholding.setText("Thresholding: " + text);           
        }        
        
    }
    
    
    
    private void setDimensions(String line) {
        String widthString = "", heightString = "";
        boolean secondEntry = false;
        
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' '){
                secondEntry = true;
                continue;
            }
            if(!secondEntry){
                widthString = widthString + line.charAt(i);
            }else {
                heightString = heightString + line.charAt(i);
            }
        }
        int width = Integer.parseInt(widthString.trim());
        int height = Integer.parseInt(heightString.trim());
        
        pic.setDimensions(new ImageSize(width, height));
        pic.setOriginalDimensions(new ImageSize(width, height));
        setImageSize(width, height);

    }

    private void setNumberMagic(String line) {
        numberMagic = line;
    }

    private void bmpLoader(File path) {
        Image imageLoaded = new Image("file:" + path.getAbsolutePath());
        pic.setImageOriginal(imageLoaded);
        pic.setImageModified(imageLoaded);
        
        setImageLoaded(pic.getImageOriginal());
        
        pic.setDimensions(new ImageSize((int)image.getWidth(), (int)image.getHeight()));
        pic.setOriginalDimensions(new ImageSize((int)image.getWidth(), (int)image.getHeight()));

        
        int width = pic.getDimensions().getWidth();
        int height = pic.getDimensions().getHeight();
        
        setImageSize(width, height);
        
        pixelReader = pic.getImageOriginal().getPixelReader();
        writableImage = new WritableImage(width, height);
        
        setColorPixelsBmp();
        configurationInit();
        
    }
    
    private void pgmLoader(File path) {
        imageX = 0; imageY = 0;
        File pathAbs = path.getAbsoluteFile();
        Scanner scan;
        try {
            scan = new Scanner(pathAbs);           
            int lineNumber = 1;
            int row = 0;
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if(!line.startsWith("#")) {
                    switch (lineNumber) {
                        case 1:
                            setNumberMagic(line);
                            lineNumber++;
                            break;
                        case 2:
                            setDimensions(line);
                            bufferNetpbm = new Color[imageWidth][imageHeight];
                            lineNumber++;
                            break;
                        case 3:
                            int maxColor = Integer.parseInt(line.trim());
                            pic.setMaxColor(maxColor);
                            lineNumber++;
                            break;
                        default:
                            if("pgm".equals(pic.getFileFormat())) {
                                buildMatrixPgm(line, row);
                                pic.setInitGrayScale(true);
                            }else{
                                buildMatrixPpm(line, row);
                            }
                            row++;
                            lineNumber++;
                            break;
                    }
                }
            }
            renderImagePgm();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Fail Load");
        }
    }
    
    private void pbmLoader(File path) {
        imageX = 0; imageY = 0;
        File pathAbs = path.getAbsoluteFile();
        Scanner scan;
        try {
            scan = new Scanner(pathAbs);           
            int lineNumber = 1;
            int row = 0;
            while(scan.hasNextLine()){
                String line = scan.nextLine();
                if(!line.startsWith("#")) {
                    switch (lineNumber) {
                        case 1:
                            setNumberMagic(line);
                            lineNumber++;
                            break;
                        case 2:
                            setDimensions(line);
                            bufferNetpbm = new Color[imageWidth][imageHeight];
                            lineNumber++;
                            break;
                        default:
                            line = line.replaceAll("\\s+","");   
                            buildMatrixPbm(line);
                            row++;
                            lineNumber++;
                            break;
                    }
                }
            }
            renderImagePbm();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Fail Load");
        }
    }
    
    private void buildMatrixPbm(String line) {
        int i = 0;
        while(i < line.length()) {
            if(line.charAt(i) == '#') {
                break;
            } else {
                double color = Character.getNumericValue(line.charAt(i));
                if(color == 0) {
                    color = 1;
                }else {
                    color = 0;
                }
                bufferNetpbm[imageX][imageY] = new Color(color, color, color, 1.0);
                i++;
                imageX++;
                if(imageX == imageWidth) {
                    imageX = 0;
                    imageY++;
                }             
            }

        }
    }
    
        private void buildMatrixPgm(String line, int row) {       
        int i = 0;
        int x = 0;
        String numberString = "";
        while(i < line.length()) {
            if(line.charAt(i) == '#') {
                break;
            } else {
                if(line.charAt(i) == ' ' && !"".equals(numberString) && !" ".equals(numberString)){
                    double number = Double.parseDouble(numberString);
                    double color = pic.mapRangePgm(number);
                    bufferNetpbm[x][row] = new Color(color,color,color,1.0);
                    x++;
                    i++;
                    numberString = "";
                } else {
                    numberString = numberString + line.charAt(i);
                    i++;
                    if(i == line.length()) {
                        double number = Double.parseDouble(numberString);
                        double color = pic.mapRangePgm(number);
                        bufferNetpbm[x][row] = new Color(color,color,color,1.0);   
                    }
                }                
            }

        }
    }
        
    private void buildMatrixPpm(String line, int row) {
        int i = 0;
        int x = 0;
//        int lineX = row % imageWidth;
//        int lineY = row / imageWidth;
        double color1 = 0; double color2 = 0; double color3 = 0;
        String numberString = "";
        while(i < line.length()) {
            if(line.charAt(i) == '#') {
                break;
            } else {
                if(line.charAt(i) == ' ' && !"".equals(numberString) && !" ".equals(numberString)){
                    double number = Double.parseDouble(numberString);                
                    switch (x) {
                        case 0:
                            color1 = pic.mapRangePgm(number);
                            x++;
                            break;
                        case 1:
                            color2 = pic.mapRangePgm(number);
                            x++;
                            break;
                        case 2:
                            color3 = pic.mapRangePgm(number);
                            if(imageY < imageHeight && imageX < imageWidth) {
                                bufferNetpbm[imageX][imageY] = new Color(color1,color2,color3,1.0);
                                x = 0;
                            }
                            imageX++;
                            if(imageX == imageWidth) {
                                imageX = 0;
                                imageY++;
                            }
                            break;
                        default:
                            break;
                    }
                    i++;
                    numberString = "";
                } else {
                    numberString = numberString + line.charAt(i);
                    i++;
                    if(i == line.length()) {
                        double number = Double.parseDouble(numberString);
                        color3 = pic.mapRangePgm(number);
                        if(imageY < imageHeight && imageX < imageWidth) {
                            bufferNetpbm[imageX][imageY] = new Color(color1,color2,color3,1.0);
                            x = 0;
                        }
                         imageX++;
                            if(imageX == imageWidth) {
                                imageX = 0;
                                imageY++;
                            }
                    }
                }                
            }

        }
    }
    
    private void setColorPixelsNetbpm() {
        Color [][] current = new Color[imageWidth][imageHeight];
        Color [][] original = new Color[imageWidth][imageHeight];
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                original[x][y] = current[x][y] = bufferNetpbm[x][y];
            }
        }
        pic.setColorMatrix(current);
        pic.setScaleMatrix(current);
        pic.setOriginalMatrix(original);    
    }
    
    private void renderImagePbm() {
        int width = pic.getDimensions().getWidth();
        int height = pic.getDimensions().getHeight();
        writableImage = new WritableImage(width, height);
        writableNetpbm = new WritableImage(width, height);
        
        pixelWriter = writableImage.getPixelWriter();
        pixelWriterNetpbm = writableNetpbm.getPixelWriter();

        setColorPixelsNetbpm();
        
        Color [][] current = pic.getColorMatrix();
        for (int y = 0; y < height; y++) {
           for (int x = 0; x < width; x++) {
               Color pbm;
               if(current[x][y].getRed() == 0){
                   pbm = new Color(0,0,0,1.0);
               } else {
                   pbm = new Color(1,1,1,1.0);
               }
               pixelWriter.setColor(x,y,pbm);
               pixelWriterNetpbm.setColor(x,y,pbm);
           }
        }
                
        pixelReader = writableImage.getPixelReader();

        
        pic.setImageOriginal(writableNetpbm);
        pic.setImageModified(writableNetpbm);
        pic.setUniqueColors(uniqueColorsList);
        setImageLoaded(writableNetpbm);
        configurationInit();       
    }
    
    private void renderImagePgm() {
        writableImage = new WritableImage(imageWidth, imageHeight);
        writableNetpbm = new WritableImage(imageWidth, imageHeight);
        
        pixelWriter = writableImage.getPixelWriter();
        pixelWriterNetpbm = writableNetpbm.getPixelWriter();

        setColorPixelsNetbpm();
        
        Color [][] current = pic.getColorMatrix();
        for (int y = 0; y < imageHeight; y++) {
           for (int x = 0; x < imageWidth; x++) {
                Color pgm = current[x][y];
                pixelWriter.setColor(x,y,pgm);
                pixelWriterNetpbm.setColor(x,y,pgm);
           }
        }
                
        pixelReader = writableImage.getPixelReader();
        
        pic.setImageOriginal(writableNetpbm);
        pic.setImageModified(writableNetpbm);
        pic.setUniqueColors(uniqueColorsList);
        setImageLoaded(writableNetpbm);
        configurationInit();       
    }
    
    @FXML
    private void sliderContext() {
        if(image != null) {
            Color [][] current = new Color[imageWidth][imageHeight];
            pixelReader = writableImage.getPixelReader();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    current[x][y] = pixelReader.getColor(x, y);
                }
            }
            pic.setColorMatrix(current);
            pic.setScaleMatrix(current);
            pic.setImageModified(writableImage);
            handleZoom();
            recalculateInfo();
        }
    }
    
    private WritableImage imageWriter() {
        int width = pic.getDimensions().getWidth();
        int heigth = pic.getDimensions().getHeight();
        WritableImage imageWriter = new WritableImage(width, heigth);
        PixelWriter pW = imageWriter.getPixelWriter();
        Color [][] current = pic.getColorMatrix();
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                pW.setColor(x,y,current[x][y]);
            }
       }
        return imageWriter;
    }
    
    private void negativeContext() {
        if(image != null) {
            sliderContext();
            
            WritableImage changeImage = imageWriter();
            
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Negative", dimensions);
            userActions.addStep(step);            
        }
    }
    
    private void blackWhiteContext() {
        if(image != null) {
            sliderContext();
            WritableImage changeImage = imageWriter();  
             
            
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Black and White", dimensions);
            userActions.addStep(step);                    
        }
    }
    
    private void rotateNegaviteContext() {
          if(image != null) {
            sliderContext();
            WritableImage changeImage = imageWriter();  
             
            
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Rotate -90°", dimensions);
            userActions.addStep(step);                    
        }      
    }
    
    private void rotatePositiveContext() {
         if(image != null) {
            sliderContext();
            WritableImage changeImage = imageWriter();  
             
            
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Rotate 90°", dimensions);
            userActions.addStep(step);                    
        }       
    }

    
    @FXML
    private void growthContext(ActionEvent event) {
        if(image != null && regionImage != null) {
            writableImage = regionImage;
            sliderContext();
            restartGrowth();
            
            WritableImage changeImage = imageWriter();
            
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Region Growth", dimensions);
            userActions.addStep(step);
            regionImage = null;
        }
    }
     
    
    @FXML
    private void grayscaleContext(ActionEvent event) {
        if(image != null) {
            double grayscaleValue = (double) sliderToGrayscale.getValue();
            if(grayscaleValue != 0) {
                sliderContext();
                restartGrayscale();
                if(grayscaleValue == 1.0 ) pic.setGrayScaleFlag(true);
                
                WritableImage changeImage = imageWriter();  
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Grayscale", dimensions);
                userActions.addStep(step);   
            }            
        }
    }

    @FXML
    private void brightnessContext(ActionEvent event) {
        if(image != null) {
            double brightnessValue = (double) sliderToBrightness.getValue();
            if(brightnessValue != 0) {
                sliderContext();
                restartBrightness();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Brigthness", dimensions);
                userActions.addStep(step);   
            }            
        }
    }

    @FXML
    private void contrastContext(ActionEvent event) {
        if(image != null) {
            double contrastValue = (double) sliderToContrast.getValue();
            if(contrastValue != 0) {
                sliderContext();
                restartContrast();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Contrast", dimensions);
                userActions.addStep(step);  
            }            
        }
    }


    @FXML
    private void thresholdingContext(ActionEvent event) {
        if(image != null) {
            handleThresholding();
            sliderContext();
            restartThresholding(); 
            
            WritableImage changeImage = imageWriter();
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Thresholding", dimensions);
            userActions.addStep(step);              
        }
    }
    
    @FXML
    private void filterAverageContext(ActionEvent event) {
        if(image != null) {
            int axisX = (int) sliderToAverageX.getValue();
            int axisY = (int) sliderToAverageY.getValue();
            if(axisX + axisY > 2) {
                sliderContext();
                restartAverage();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Average filter", dimensions);
                userActions.addStep(step);              
            }                    
        }
    }
    
    @FXML
    private void filterMedianContext(ActionEvent event) {
        if(image != null) {
            int axisX = (int) sliderToMedianX.getValue();
            int axisY = (int) sliderToMedianY.getValue();
            if(axisX + axisY > 2) {
                sliderContext();
                restartMedian();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Median filter", dimensions);
                userActions.addStep(step);                      
            }        
            
        }
    }
    
    @FXML
    private void filterGaussianContext(ActionEvent event) {
        if(image != null) {
            int axisX = (int) sliderToGaussianX.getValue();
            int axisY = (int) sliderToGaussianY.getValue();
            if(axisX + axisY > 2) {
                sliderContext();
                restartGaussian();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Gaussian filter", dimensions);
                userActions.addStep(step);                     
            }        
            
        }
    }

    @FXML
    private void filterLaplacianContext(ActionEvent event) {
        if(image != null) {
            int axisX = (int) sliderToLaplacianX.getValue();
            int axisY = (int) sliderToLaplacianY.getValue();
            if(axisX + axisY > 2) {
                sliderContext();
                restartLaplacian();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Laplacian filter", dimensions);
                userActions.addStep(step);                     
            }        
            
        }
    }
    
    @FXML
    private void filterSobelContext(ActionEvent event) {
        if(image != null) {
            int axisX = (int) sliderToSobelX.getValue();
            int axisY = (int) sliderToSobelY.getValue();
            if(axisX + axisY > 2) {
                sliderContext();
                restartSobel();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Sobel filter", dimensions);
                userActions.addStep(step);                        
            }
            
        }
    }
    
    @FXML
    private void filterPrewittContext(ActionEvent event) {
        if(image != null) {
            int axisX = (int) sliderToPrewittX.getValue();
            int axisY = (int) sliderToPrewittY.getValue();
            if(axisX + axisY > 2) {
                sliderContext();
                restartPrewitt();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Prewitt filter", dimensions);
                userActions.addStep(step);                       
            }
            
        }
    }
    
    @FXML
    private void filterRobertsContext(ActionEvent event) {
        if(image != null) {
            int axis = (int) sliderToRoberts.getValue();
            if(axis >= 2 ) {
                sliderContext();
                restartRoberts();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "Roberts filter", dimensions);
                userActions.addStep(step);                       
            }
            
        }
    }
    
    @FXML
    private void filterLoGContext(ActionEvent event) {
        if(image != null) {
            int axisX = (int) sliderToLoGX.getValue();
            int axisY = (int) sliderToLoGY.getValue();
            if(axisX + axisY > 2) {
                sliderContext();
                restartLoG();
                
                WritableImage changeImage = imageWriter();
                ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
                Stack step = new Stack(changeImage, "LoG filter", dimensions);
                userActions.addStep(step);                      
            }
            
        }
    }
    
    public void filterArbitraryContext() {
        if(image != null) {
            sliderContext();
            restartArbitrary();
            
            WritableImage changeImage = imageWriter();
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Arbitrary filter", dimensions);
            userActions.addStep(step);                      
        }
    }   
    
  
    private void 
        generalContext() {
        if(image != null) {
            sliderContext();

            WritableImage changeImage = imageWriter();
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "General", dimensions);
            userActions.addStep(step);                      
        }
    }
    
    private void kMeansContext() {
        if(image != null) {
            sliderContext();
            restartKMeans();

            WritableImage changeImage = imageWriter();
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "K-means", dimensions);
            userActions.addStep(step);                      
        }
    }    
    
    private void panningContext() {
        if(image != null) {
            sliderContext();

            WritableImage changeImage = imageWriter();
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Panning", dimensions);
            userActions.addStep(step);                      
        }
    }    
    private void rotationContext() {
        if(image != null) {
            sliderContext();

            WritableImage changeImage = imageWriter();
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Rotation", dimensions);
            userActions.addStep(step);                      
        }
    }    
    
    @FXML
    private void medianCutContext(ActionEvent event) {
        if(image != null) {
            sliderContext();

            WritableImage changeImage = imageWriter();
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Median Cut", dimensions);
            userActions.addStep(step);                      
        }
    }


    
    
    

    @FXML
    private void convertToDefault(ActionEvent event) {
        if(image != null) {
            int height = pic.getOriginalDimensions().getHeight();
            int width = pic.getOriginalDimensions().getWidth();
            writableImage = new WritableImage(width, height);
            pixelWriter = writableImage.getPixelWriter();
            Color [][] current = new Color[width][height];
            Color [][] original = pic.getOriginalMatrix();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    current[x][y] = original[x][y];
                    pixelWriter.setColor(x, y, original[x][y]);
                }
            }
            pixelReader = writableImage.getPixelReader();
            setImageSize(width, height);
            pic.setDimensions(new ImageSize(width, height));
            pic.setColorMatrix(current);
            pic.setScaleMatrix(current);
            pic.setImageModified(pic.getImageOriginal());
            pic.setGrayScaleFlag(false);
            imageView.setImage(pic.getImageOriginal());
            configurationImageView();
            restartUI();
            recalculateInfo();
            userActions.resetSteps();
            WritableImage changeImage = imageWriter();
            
            ImageSize dimensions = new ImageSize(imageWidth, imageHeight);
            Stack step = new Stack(changeImage, "Default Image", dimensions);
            userActions.addStep(step);   
        }

    }

    private void restartUI() {
        restartGrayscale();
        restartBrightness();
        restartContrast();
        restartThresholding();
        restartAverage();
        restartMedian();
        restartGaussian();
        restartLaplacian();
        restartSobel();
        restartPrewitt();
        restartRoberts();
        restartLoG();
        restartGrowth();
        restartKMeans();
        handleZoom();
    }

    private void restartGrayscale() {
        sliderToGrayscale.setValue(0);
        labelGrayscale.setText("Grayscale: 0%");        
    }
    private void restartBrightness() {
        sliderToBrightness.setValue(0);
        labelBrightness.setText("Brightness: 0%");     
    }
    private void restartContrast() {
        sliderToContrast.setValue(0);        
        labelContrast.setText("Contrast: 0%");     
    }
    private void restartThresholding() {
        sliderToThresholding.setValue(0.5);        
        labelThresholding.setText("Thresholding: 127");     
    }   
    private void restartAverage() {
        labelAverageX.setText("1");
        labelAverageY.setText("1");
        sliderToAverageX.setValue(1);
        sliderToAverageY.setValue(1);        
    }
    private void restartMedian() {
        labelMedianX.setText("1");
        labelMedianY.setText("1");
        sliderToMedianX.setValue(1);
        sliderToMedianY.setValue(1);        
    }
    private void restartGaussian() {
        labelGaussianX.setText("1");
        labelGaussianY.setText("1");
        sliderToGaussianX.setValue(1);
        sliderToGaussianY.setValue(1);        
    }
    private void restartLaplacian() {
        labelLaplacianX.setText("1");
        labelLaplacianY.setText("1");        
        sliderToLaplacianX.setValue(1);
        sliderToLaplacianY.setValue(1);        
    }      
    
    private void restartSobel() {
        labelSobelX.setText("1");
        labelSobelY.setText("1");            
        sliderToSobelX.setValue(1);
        sliderToSobelY.setValue(1);        
    } 
    
    private void restartPrewitt() {
        labelPrewittX.setText("1");
        labelPrewittY.setText("1");           
        sliderToPrewittX.setValue(1);
        sliderToPrewittY.setValue(1);        
    }
    
    private void restartRoberts() {
        labelRoberts.setText("1");
        sliderToRoberts.setValue(1);
    }
    
    private void restartZoom() {
        sliderToZoom.setValue(1);
    }  
    
    private void restartLoG() {
        labelLoGX.setText("1");
        labelLoGY.setText("1");    
        sliderToLoGX.setValue(1);
        sliderToLoGY.setValue(1);
    }
    
    private void restartArbitrary() {
        labelArbitraryX.setText("1");
        labelArbitraryY.setText("1");    
        sliderToArbitraryX.setValue(1);
        sliderToArbitraryY.setValue(1);     
    }
    
    private void restartGrowth() {
        colorPickerRGB.setText("-");
        showColorPick.setStyle("-fx-background-color: rgba(0,0,0,0.2)"); 
        togglePickerButton.setSelected(false);
        sliderToTolerance.setValue(0);
        pixelPicker = null;
        regionImage = null;
    }
    private void restartKMeans() {
          inputKMeans.setText("");
    }
 

    @FXML
    private void generateHistogram(ActionEvent event) {
        if(image != null) {
            try {
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/HistogramView.fxml")));
                Parent root =  loader.load();
                
                HistogramController histogramInstanceController = (HistogramController)loader.getController();
                
                histogramInstanceController.allColors(mainInstanceController, pic);
               
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.setScene(scene);
               stage.showAndWait();

           } catch (IOException ex) {
               Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
           }           
        }

    }
    
    public void setEqualizedImage(WritableImage imageStrecth) {
        writableImage = imageStrecth;
        generalContext();
    }

//
//    private void handleRotate(String direction) {
//        if(image != null) {
//            restartBrightness();
//            restartContrast();
//            restartGrayscale();            
//            restartThresholding();
//            restartAverage();
//            restartMedian();
//            restartGaussian();
//            restartLaplacian();
//            restartLoG();
//            restartSobel();
//            restartPrewitt();
//            restartRoberts();
//            int width = imageHeight;
//            int height = imageWidth;
//            Color [][] rotate = new Color[imageHeight][imageWidth];
//            writableImage = new WritableImage(imageHeight, imageWidth);
//            pixelWriter = writableImage.getPixelWriter();
//            
//            if(direction == "positive") {
//                rotate = rotatePositive(width, height, rotate);
//            } else {
//                rotate = rotateNegative(width, height, rotate);
//            }
//
//            setImageSize(width, height);
//            pic.setDimensions(new ImageSize(width, height));
//            pic.setColorMatrix(rotate);
//            pic.setScaleMatrix(rotate);
//            pic.setImageModified(writableImage);
//            handleZoom();
//            //Not Context
//            configurationImageView();
//                
//        }
//    }
    
    @FXML
    private void handleRotateNegative() {                
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            int width = imageHeight;
            int height = imageWidth;
            Color [][] rotate = new Color[imageHeight][imageWidth];
            writableImage = new WritableImage(imageHeight, imageWidth);
            pixelWriter = writableImage.getPixelWriter();
            
            Color [][] current = pic.getColorMatrix();
            for (int y = 0; y < width; y++) {
              for (int x = 0; x < height; x++) {                
                  rotate[y][x] = current[imageWidth - 1 - x][y];
                  pixelWriter.setColor(y,x,rotate[y][x]);
              }
           }
            
            setImageSize(width, height);
            pic.setDimensions(new ImageSize(width, height));
            pic.setColorMatrix(rotate);
            pic.setScaleMatrix(rotate);
            pic.setImageModified(writableImage);
            handleZoom();
            rotateNegaviteContext();
            configurationImageView(); 
        }
    }
    

    @FXML
    private void handleRotatePositive() {
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            int width = imageHeight;
            int height = imageWidth;
            Color [][] rotate = new Color[imageHeight][imageWidth];
            writableImage = new WritableImage(imageHeight, imageWidth);
            pixelWriter = writableImage.getPixelWriter();
            
            Color [][] current = pic.getColorMatrix();
            for (int y = 0; y < width; y++) {
                for (int x = 0; x < height; x++) {                
                  rotate[y][x] = current[x][imageHeight - 1 - y];
                  pixelWriter.setColor(y,x,rotate[y][x]);
                }
            }
            
            setImageSize(width, height);
            pic.setDimensions(new ImageSize(width, height));
            pic.setColorMatrix(rotate);
            pic.setScaleMatrix(rotate);
            pic.setImageModified(writableImage);
            handleZoom();
            rotatePositiveContext();
            configurationImageView();
                
        }
    }

 
    private Color[][] rotateNegative(int width, int height, Color[][] rotate) {
        Color [][] current = pic.getColorMatrix();
        for (int y = 0; y < width; y++) {
          for (int x = 0; x < height; x++) {                
              rotate[y][x] = current[imageWidth - 1 - x][y];
              pixelWriter.setColor(y,x,rotate[y][x]);
          }
       }
        return rotate;
    }

    private Color[][] rotatePositive(int width, int height, Color[][] rotate) {
        Color [][] current = pic.getColorMatrix();
        for (int y = 0; y < width; y++) {
          for (int x = 0; x < height; x++) {                
              rotate[y][x] = current[x][imageHeight - 1 - y];
              pixelWriter.setColor(y,x,rotate[y][x]);
          }
       }
        return rotate;
    }



    @FXML
    private void handleAverage(MouseEvent event) {
        int axisX = (int) sliderToAverageX.getValue();
        int axisY = (int) sliderToAverageY.getValue();
        labelAverageX.setText("" + axisX);
        labelAverageY.setText("" + axisY);
        if(image != null && (axisX + axisY >= 2)) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "average", pic);
                    mc.searchNS(x,y);
                    Color averageColor = mc.setMatrixConvolution();  
                    pixelWriter.setColor(x,y,averageColor);
                    scaleMatrix[x][y] = averageColor;
                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();
        }   
    }

    @FXML
    private void handleMedian(MouseEvent event) {
        int axisX = (int) sliderToMedianX.getValue();
        int axisY = (int) sliderToMedianY.getValue();
        labelMedianX.setText("" + axisX);
        labelMedianY.setText("" + axisY);    
        if(image != null && (axisX + axisY >= 2)) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
            pixelWriter = writableImage.getPixelWriter();
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "median", pic);
                    mc.searchNS(x,y);
                    Color medianColor = mc.setMatrixConvolution();  
                    pixelWriter.setColor(x,y,medianColor);
                    scaleMatrix[x][y] = medianColor;
                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();
        }  
        
    }

    @FXML
    private void handleGaussian(MouseEvent event) {
        int axisX = (int) sliderToGaussianX.getValue();
        int axisY = (int) sliderToGaussianY.getValue();
        if(axisX % 2 == 1 && axisY % 2 == 1){
           labelGaussianX.setText("" + axisX);
           labelGaussianY.setText("" + axisY);    
           if(image != null) {
                restartBrightness();
                restartContrast();
                restartGrayscale();            
                restartThresholding();
                restartAverage();
                restartMedian();
                restartLaplacian();
                restartLoG();
                restartSobel();
                restartPrewitt();
                restartRoberts();
               Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
               pixelWriter = writableImage.getPixelWriter();              
               for (int y = 0; y < imageHeight; y++) {
                   for (int x = 0; x < imageWidth; x++) {
                       Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "gaussian", pic);
                       mc.searchNS(x,y);
                       Color gaussianColor = mc.setMatrixConvolution();  
                       pixelWriter.setColor(x,y,gaussianColor);
                       scaleMatrix[x][y] = gaussianColor;
                   }
               }
                pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
                handleZoom();
               configurationImageView();
           }            
        }
    }

    @FXML
    private void handleLaplacian(MouseEvent event) {
        int axisX = (int) sliderToLaplacianX.getValue();
        int axisY = (int) sliderToLaplacianY.getValue();
        labelLaplacianX.setText("" + axisX);
        labelLaplacianY.setText("" + axisY);    
        if(image != null && (axisY + axisX > 2)) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            restartRoberts();
           Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
            pixelWriter = writableImage.getPixelWriter();              
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "laplacian", pic);
                    mc.searchNS(x,y);
                    Color laplacianColor = mc.setMatrixConvolution();  
                    pixelWriter.setColor(x,y,laplacianColor);
                    scaleMatrix[x][y] = laplacianColor;
                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();
        }        
    }
    
    @FXML
    private void handleLoG(MouseEvent event) {
        int axisX = (int) sliderToLoGX.getValue();
        int axisY = (int) sliderToLoGY.getValue();
        labelLoGX.setText("" + axisX);
        labelLoGY.setText("" + axisY);    
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartSobel();
            restartPrewitt();
            restartRoberts();
            Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
            pixelWriter = writableImage.getPixelWriter();              
            for (int y = 0; y < imageHeight; y++) {
                for (int x = 0; x < imageWidth; x++) {
                    Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "log", pic);
                    mc.searchNS(x,y);
                    Color logColor = mc.setMatrixConvolution();  
                    pixelWriter.setColor(x,y,logColor);
                    scaleMatrix[x][y] = logColor;
                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();
        }         
        
    }

    @FXML
    private void handleSobel() {
        int sobelIndex = sobelDerivate.getToggles().indexOf(sobelDerivate.getSelectedToggle());
        int axisX = (int) sliderToSobelX.getValue();
        int axisY = (int) sliderToSobelY.getValue();
        labelSobelX.setText("" + axisX);
        labelSobelY.setText("" + axisY);    
        if(image != null && axisY + axisX > 2) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLoG();
            restartLaplacian();
            restartPrewitt();
            restartRoberts();
            pixelWriter = writableImage.getPixelWriter();
            int aX = (int) Math.round(axisX / 2.0 + 0.5);
            int aY = (int) Math.round(axisY / 2.0 + 0.5);
            Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
            for(int y = 0; y < imageHeight; y++) {
                for(int x = 0; x< imageWidth; x ++) {
                    scaleMatrix[x][y] = new Color(0,0,0,1.0);
                    pixelWriter.setColor(x,y,scaleMatrix[x][y]); 
                }
            }
            for (int y = aY; y < imageHeight - aY; y++) {
                for (int x = aX; x < imageWidth - aX; x++) {
                    if(sobelIndex == 0){ // Derivate in X
                        Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "sobelX", pic);
                        mc.searchNS(x,y);
                        Color sobelColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,sobelColor); 
                        scaleMatrix[x][y] = sobelColor;
                    } else { //Derivate in Y
                        Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "sobelY", pic);
                        mc.searchNS(x,y);
                        Color sobelColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,sobelColor); 
                        scaleMatrix[x][y] = sobelColor;

                    }

                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();

        }
    }

    @FXML
    private void handlePrewitt() {
        int prewittIndex = prewittDerivate.getToggles().indexOf(prewittDerivate.getSelectedToggle());
        int axisX = (int) sliderToPrewittX.getValue();
        int axisY = (int) sliderToPrewittY.getValue();
        labelPrewittX.setText("" + axisX);
        labelPrewittY.setText("" + axisY);    
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartRoberts();
            pixelWriter = writableImage.getPixelWriter();
            int aX = (int) Math.round(axisX / 2.0 + 0.5);
            int aY = (int) Math.round(axisY / 2.0 + 0.5);
            Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
            for(int y = 0; y < imageHeight; y++) {
                for(int x = 0; x< imageWidth; x ++) {
                    scaleMatrix[x][y] = new Color(0,0,0,1.0);
                    pixelWriter.setColor(x,y,scaleMatrix[x][y]); 
                }
            }
            for (int y = aY; y < imageHeight - aY; y++) {
                for (int x = aX; x < imageWidth - aX; x++) {
                    if(prewittIndex == 0) { //Derivate in X
                        Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "prewittX", pic);
                        mc.searchNS(x,y);
                        Color prewittColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,prewittColor);   
                        scaleMatrix[x][y] = prewittColor;
                    } else {
                        Convolution mc = new Convolution(axisY, axisX, imageWidth, imageHeight, "prewittY", pic);
                        mc.searchNS(x,y);
                        Color prewittColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,prewittColor);
                        scaleMatrix[x][y] = prewittColor;
                    }
                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();
        }        
        
    }

    @FXML
    private void handleRoberts() {
        int robertsIndex = robertsDerivate.getToggles().indexOf(robertsDerivate.getSelectedToggle());
        int axis = (int) sliderToRoberts.getValue();
        labelRoberts.setText("" + axis);
        if(image != null) {
            restartBrightness();
            restartContrast();
            restartGrayscale();            
            restartThresholding();
            restartAverage();
            restartMedian();
            restartGaussian();
            restartLaplacian();
            restartLoG();
            restartSobel();
            restartPrewitt();
            pixelWriter = writableImage.getPixelWriter();
            Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
            for(int y = 0; y < imageHeight; y++) {
                for(int x = 0; x< imageWidth; x ++) {
                    scaleMatrix[x][y] = new Color(0,0,0,1.0);
                    pixelWriter.setColor(x,y,scaleMatrix[x][y]);  
                }
            }
            int aXY = (int) Math.round(axis / 2.0 + 0.5);
            for (int y = aXY; y < imageHeight - aXY; y++) {
                for (int x = aXY; x < imageWidth - aXY; x++) {
                    if(robertsIndex == 0) { //Derivate in X
                        Convolution mc = new Convolution(axis, axis, imageWidth, imageHeight, "robertsX", pic);
                        mc.searchNS(x,y);
                        Color robertsColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,robertsColor);    
                        scaleMatrix[x][y] = robertsColor;
                    } else {
                        Convolution mc = new Convolution(axis, axis, imageWidth, imageHeight, "robertsY", pic);
                        mc.searchNS(x,y);
                        Color robertsColor = mc.setMatrixConvolution();  
                        pixelWriter.setColor(x,y,robertsColor);
                        scaleMatrix[x][y] = robertsColor;
                    }
                }
            }
            pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
            handleZoom();
            configurationImageView();
        }          
        
    }
    
    @FXML
    private void handleArbitrary(MouseEvent event) {
        int axisX = (int) sliderToArbitraryX.getValue();
        int axisY = (int) sliderToArbitraryY.getValue();
        labelArbitraryX.setText("" + axisX);
        labelArbitraryY.setText("" + axisY);
    }

    @FXML
    private void handleZoom() {
        if(image != null) {
            int zoomButton = zoomMethod.getToggles().indexOf(zoomMethod.getSelectedToggle());
            // 0  Neighbor   -    1  Interpolation
            double zoomValue = sliderToZoom.getValue();
            boolean overflow = overflowZoom.isSelected();

            if(zoomButton == 0) {
                zoomNeighbor(zoomValue);
            } else {
                zoomInterpolation(zoomValue);
            }
            if(overflow) {
                imageWrapper.setPrefWidth(zoomValue * imageWidth);
                imageWrapper.setPrefHeight(zoomValue * imageHeight);                
            }else {
                imageWrapper.setPrefWidth(0);
                imageWrapper.setPrefHeight(0);       
            }
        }
    }
    
    
    private void zoomNeighbor(double zoomValue) {
            int width = (int) Math.round(imageWidth * zoomValue);
            int height = (int) Math.round(imageHeight * zoomValue);
            width = width > 0 ? width : 1;
            height = height > 0 ? height : 1;
            Color [][] current = pic.getScaleMatrix();
            zoomWritable = new WritableImage(width, height);
            zoomWriter = zoomWritable.getPixelWriter();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if(x < width  && y < height ){
                        int zX = (int) (x/zoomValue);
                        int zY = (int) (y/zoomValue);
                        Color zoomColor = current[zX][zY];
                        zoomWriter.setColor(x,y,zoomColor);
                    }
                }
            }
            imageView.setImage(zoomWritable);
            configurationImageView();            
    }
        
    private void zoomInterpolation(double zoomValue) {
            int width = (int) Math.round(imageWidth * zoomValue);
            int height = (int) Math.round(imageHeight * zoomValue);
            width = width > 0 ? width : 1;
            height = height > 0 ? height : 1;
            Color [][] current = pic.getScaleMatrix();            
            zoomWritable = new WritableImage(width, height);
            zoomWriter = zoomWritable.getPixelWriter();
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {                   
                        int i = (int) Math.floor((double)x/zoomValue);
                        int d = i + 1;
                        d = d == imageWidth ? imageWidth - 1 : d;
                        int s = (int) Math.floor((double)y/zoomValue);
                        int r = s + 1;
                        r = r == imageHeight ? imageHeight - 1 : r;
                        double a = ((double) x / zoomValue) - i;
                        double b = ((double) y / zoomValue) - s;

                        Color bilinear1 = bilinearCoefficient(i,s, 1-a, 1-b, current);
                        Color bilinear2 = bilinearCoefficient(d,s, a, 1-b, current);
                        Color bilinear3 = bilinearCoefficient(i,r, 1-a, b, current);
                        Color bilinear4 = bilinearCoefficient(d,r, a, b, current);

                        Color zoomColor = addColors(bilinear1,bilinear2,bilinear3,bilinear4);
                        zoomWriter.setColor(x,y,zoomColor);
                }
            }
            imageView.setImage(zoomWritable);
            configurationImageView();  

    }
       
    private Color bilinearCoefficient(int x, int y, double a, double b, Color[][] current) {
        double red = current[x][y].getRed();
        double green = current[x][y].getGreen();
        double blue = current[x][y].getBlue();
        
        red = truncatePixel(a*b*red);
        green = truncatePixel(a*b*green);
        blue = truncatePixel(a*b*blue);
        
        return new Color(red, green, blue, 1.0);
    }
      
    private Color addColors(Color a, Color b, Color c, Color d) {
        double aRed = a.getRed();
        double aGreen = a.getGreen();
        double aBlue = a.getBlue();
        
        double bRed = b.getRed();
        double bGreen = b.getGreen();
        double bBlue = b.getBlue();
        
        double cRed = c.getRed();
        double cGreen = c.getGreen();
        double cBlue = c.getBlue();
        
        double dRed = d.getRed();
        double dGreen = d.getGreen();
        double dBlue = d.getBlue();
        
        double red = truncatePixel(aRed + bRed + cRed + dRed);
        double green = truncatePixel(aGreen + bGreen + cGreen + dGreen);
        double blue = truncatePixel(aBlue + bBlue + cBlue + dBlue);
        
        
        return new Color(red,green,blue,1.0);
    }

    @FXML
    private void setAribitraryKernel(ActionEvent event) {
        if(image != null) {
            try {
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ArbitraryKernelView.fxml")));
                Parent root =  loader.load();
                
               ArbitraryKernelViewController arbitrarykernelInstanceController = (ArbitraryKernelViewController)loader.getController();
                
                int axisX = (int) sliderToArbitraryX.getValue();
                int axisY = (int) sliderToArbitraryY.getValue();
                
                if(axisX + axisY >= 3) {
                              
                    arbitrarykernelInstanceController.setArbitraryMatrix(mainInstanceController, axisX, axisY);


                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.showAndWait();     
                }
               


           } catch (IOException ex) {
               Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
           }           
        }
    }
    
    public void receiveParamsArbitraryKernel(double[][] matrixConvol, int width, int height) {
        boolean borders = includeBorders.isSelected();
        int aY, aX;
        if(borders) {
            aX = 0;
            aY = 0;
        } else {
            aY = (int) Math.round(width / 2.0 + 0.5);
            aX = (int) Math.round(height / 2.0 + 0.5);
        }
        pixelWriter = writableImage.getPixelWriter();
        Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                    scaleMatrix[x][y] = new Color(0,0,0,1.0);
                    pixelWriter.setColor(x,y,scaleMatrix[x][y]); 
            }
        }        
        for (int y = aY; y < imageHeight - aY; y++) {
            for (int x = aX; x < imageWidth - aX; x++) {
                Convolution mc = new Convolution(width, height, imageWidth, imageHeight, "arbitrary", pic);
                mc.searchNS(x,y);
                Color arbitraryColor = mc.setMatrixArbitrary(matrixConvol);  
                pixelWriter.setColor(x,y,arbitraryColor);
                scaleMatrix[x][y] = arbitraryColor;
                
            }
        }
        pic.setScaleMatrix(scaleMatrix);
//            imageView.setImage(writableImage);
        handleZoom();
        configurationImageView();
    }

    public int log2(int x){
        return (int) (Math.log(x) / Math.log(2) + 1e-10);
    }
    
  
    private void bmpSaver(Image content, File file){      
        
        BufferedImage bfImage = SwingFXUtils.fromFXImage(content, null);
        BufferedImage bfImage2 = null;
        
        int size = uniqueColorsList.size();
        int rank = log2(size);
        
        if(rank >= 0 && rank <= 1) {
            bfImage2 = new BufferedImage(bfImage.getWidth(), bfImage.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        } else if((rank > 1 && rank <= 8 && pic.isGrayScaleFlag()) || pic.isInitGrayScale()) {
            bfImage2 = new BufferedImage(bfImage.getWidth(), bfImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        } else {
           bfImage2 = new BufferedImage(bfImage.getWidth(), bfImage.getHeight(), BufferedImage.TYPE_INT_RGB); 
        }
        
        bfImage2.getGraphics().drawImage(bfImage, 0, 0, null);
        try {
            ImageIO.write(bfImage2, "png", file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    


    private void netBpmSaver() {
        int size = uniqueColorsList.size();
        int rank = log2(size);
        
        if(rank >= 0 && rank <= 1) {
            pbmSaver();
        } else if((rank > 1 && rank <= 8 && pic.isGrayScaleFlag()) || pic.isInitGrayScale()) {
            pgmSaver();
        } else {
            ppmSaver();
        }
        
    }
    
    public int mapRange(int number, int maxColor, int maxNumber){
        double ent1 = 0;
        double ent2 = maxColor; 
        double ret1 = 0;
        double ret2 = maxNumber;
        return (int) (((ret2 - ret1)/(ent2 - ent1)) * (number - ent2) + ret2);
    }
    

    private void pbmSaver() {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save image in format");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Netpbm | pbm", "*.pbm"));

        File file = fileChooser.showSaveDialog(null);
        
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pixelReader = writableImage.getPixelReader();
        outFile.println("P1");
        outFile.println("" + imageWidth + " " + imageHeight);
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int pixel = pixelReader.getArgb(x, y);
                pixel = pixel == -1 ? 0 : 1;
                outFile.print(pixel + " ");
            }
            outFile.println();
        } 
        outFile.close();
    }
    
    
    private void pgmSaver() {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save image in Netpbm format");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Netpbm | pgm", "*.pgm"));

        File file = fileChooser.showSaveDialog(null);
        
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pixelReader = writableImage.getPixelReader();
        outFile.println("P2");
        outFile.println("" + imageWidth + " " + imageHeight);
        outFile.println(uniqueColorsList.size());
        
        int max = BlankPic.mappingRangeColor((int) uniqueColorsList.get(0));
        
        for(int i = 0; i< uniqueColorsList.size(); i++) {
            int color = (int) uniqueColorsList.get(i);
            int result = BlankPic.mappingRangeColor(color);
            max = Math.max(max, result);
        }
                
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                int pixel = pixelReader.getArgb(x, y);
                pixel = BlankPic.mappingRangeColor(pixel);
                pixel = mapRange(pixel, max, uniqueColorsList.size());
                outFile.print(pixel + " ");
            }
            outFile.println();

        } 
        outFile.close();        
    }
    
    
    private void ppmSaver() {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save image in Netpbm format");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Netpbm | ppm", "*.ppm"));

        File file = fileChooser.showSaveDialog(null);
        
        PrintWriter outFile = null;
        try {
            outFile = new PrintWriter(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pixelReader = writableImage.getPixelReader();
        outFile.println("P3");
        outFile.println("" + imageWidth + " " + imageHeight);
        outFile.println("255");
        
        int max = BlankPic.mappingRangeColor((int) uniqueColorsList.get(0));
        
        for(int i = 0; i< uniqueColorsList.size(); i++) {
            int color = (int) uniqueColorsList.get(i);
            int result = BlankPic.mappingRangeColor(color);
            max = Math.max(max, result);
        }
                
        for (int y = 0; y < imageHeight; y++) {
            for (int x = 0; x < imageWidth; x++) {
                Color pixel = pixelReader.getColor(x, y);
                int r = (int) (pixel.getRed() * 255);
                int g = (int) (pixel.getGreen()* 255);
                int b = (int) (pixel.getBlue()* 255);
                outFile.print(r + " " + g + " " + b + " ");
            }
            outFile.println();

        } 
        outFile.close();         
        
    }

    @FXML
    private void handleSaveMethod(ActionEvent event) {
        if(image != null) {
            int saveButton = saveMethod.getToggles().indexOf(saveMethod.getSelectedToggle());

            
            if(radioBmp.isSelected() || radioJpg.isSelected() || radioPng.isSelected()) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save image in BMP | JPG | PNG format");
                
                if(radioBmp.isSelected()) fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BMP", "*.bmp")); 
                if(radioJpg.isSelected()) fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg")); 
                if(radioPng.isSelected()) fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png")); 
                

                File file = fileChooser.showSaveDialog(null);
                if(file != null){
                    bmpSaver(writableImage, file);
                }
            }
            
            if(radioNetpbm.isSelected()) netBpmSaver();
            
        }
    }

    @FXML
    private void handleErosion() {
        if(image != null) {
            Mat dst = new Mat();
            Mat src = OpenCVUtils.image2Mat(writableImage);

            Imgproc.erode(src, dst, kernelStructuring);

            writableImage = OpenCVUtils.mat2WritableImage(dst);
            generalContext();            
        }
    }

    @FXML
    private void handleDilation() {
        if(image != null) {
            Mat dst = new Mat();
            Mat src = OpenCVUtils.image2Mat(writableImage);

            Imgproc.dilate(src, dst, kernelStructuring);

            writableImage = OpenCVUtils.mat2WritableImage(dst);
            generalContext();            
        }
    }

    @FXML
    private void handleAperture() {
        if(image != null) {
    //        handleErosion();        
    //        handleDilation();        
            Mat dst = new Mat();
            Mat src = OpenCVUtils.image2Mat(writableImage);

            Imgproc.erode(src, dst, kernelStructuring);
            Imgproc.dilate(dst, dst, kernelStructuring);

            writableImage = OpenCVUtils.mat2WritableImage(dst);
            generalContext();   
        }    
    }

    @FXML
    private void handleClosure() {
        if(image != null) {
    //        handleDilation();        
    //        handleErosion();
            Mat dst = new Mat();
            Mat src = OpenCVUtils.image2Mat(writableImage);

            Imgproc.dilate(src, dst, kernelStructuring);
            Imgproc.erode(dst, dst, kernelStructuring);

            writableImage = OpenCVUtils.mat2WritableImage(dst);
            generalContext();        
        }
    }
    
    @FXML
    private void defaultKernelConfiguration() {
        StructuringElem elem = new StructuringElem();
        kernelStructuring = elem.getStructuringElem();
    }
    
    @FXML
    private void setStructuringElem() {
        if(image != null) {
            try {
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/StructureElementsView.fxml")));
                Parent root =  loader.load();

               StructureElementsController structureElementsInstanceController = (StructureElementsController)loader.getController();

                structureElementsInstanceController.setValues(mainInstanceController);


                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

            } catch (IOException ex) {
               Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }           
        }
    }

    public void receiveStructuringElem(Mat kernel) {
        kernelStructuring = kernel;
    }

    @FXML
    private void handleKMeans() {
        String k = inputKMeans.getText();
        if(image != null && k != null && !k.isEmpty() && isNumeric(k)) {
            int kNumber = Integer.parseInt(k);
            if(kNumber >= 1 && kNumber <= uniqueColorsList.size() ) {
                Mat src = OpenCVUtils.image2Mat(writableImage);
                Mat dst = OpenCVUtils.kmeans(src, kNumber); 
                writableImage = OpenCVUtils.mat2WritableImage(dst);
                kMeansContext();    
            }
        }
    }

    @FXML
    private void handleThresholdOtsu(ActionEvent event) {
        if(image != null) {
            Mat dst = new Mat();
            Mat gray = new Mat();
            Mat src = OpenCVUtils.image2Mat(writableImage);
            Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
            Imgproc.threshold(gray, dst, 127, 255, Imgproc.THRESH_OTSU); 
            writableImage = OpenCVUtils.mat2WritableImage(dst);
            generalContext();
        }        
    }

    @FXML
    private void handleThresholdKmeans(ActionEvent event) {
        if(image != null) {
            Mat src = OpenCVUtils.image2Mat(writableImage);
            Mat dst = OpenCVUtils.kmeansThreshold(src, 2); 
            writableImage = OpenCVUtils.mat2WritableImage(dst);
            generalContext();
        }
    }

    @FXML
    private void handleQuantizeThreshold(ActionEvent event) {
        if(image != null) {
            Mat dst = new Mat();
            Mat src = OpenCVUtils.image2Mat(writableImage);
            Imgproc.threshold(src, dst, 180, 200, Imgproc.THRESH_BINARY); 
            writableImage = OpenCVUtils.mat2WritableImage(dst);
            generalContext();
        }        
    }

    @FXML
    private void handleGrowth() {
        if(image != null && pixelPicker != null) {
            int flags;
            int neighbors = regionNeighbors.getToggles().indexOf(regionNeighbors.getSelectedToggle());
            int fill = regionRank.getToggles().indexOf(regionRank.getSelectedToggle());
            int conectivity = neighbors == 0 ? 4 : 8;
            int t = (int) Math.round(sliderToTolerance.getValue());
                        
            if(fill == 0) {
                flags = conectivity | (100 <<8 ) | Imgproc.FLOODFILL_FIXED_RANGE;
            } else {
                flags = conectivity | (100 <<8 );
            }
            
            Mat dst = new Mat();
            Mat mask = new Mat();
            Mat src = OpenCVUtils.image2Mat(writableImage);
            Imgproc.cvtColor(src, src, Imgproc.COLOR_BGRA2BGR);
            
            int posX = pixelPicker.getPosX();
            int posY = pixelPicker.getPosY();
            
                    
            double r = varChooseColor.getRed() * 255;
            double g = varChooseColor.getGreen()* 255;
            double b = varChooseColor.getBlue()* 255;
            
            Imgproc.floodFill(src, mask, new Point(posX,posY), new Scalar(b,g,r), new Rect(), new Scalar(t, t, t), new Scalar(t, t, t), flags);


            regionImage = OpenCVUtils.mat2WritableImage(src);
            PixelReader regionReader = regionImage.getPixelReader();
            Color[][] scaleMatrix = new Color[imageWidth][imageHeight];
            for(int y = 0; y < imageHeight; y++) {
                for(int x = 0; x< imageWidth; x ++) {
                    scaleMatrix[x][y] = regionReader.getColor(x,y);
                }
            }
            pic.setScaleMatrix(scaleMatrix);
            handleZoom();
            configurationImageView();
        }          
        
    }
    
    @FXML
    private void handleGetColorPicker(ActionEvent event) {
        varChooseColor = buttonChooseColor.getValue();
        handleGrowth();
    }


        @FXML
    private void handleUndo(ActionEvent event) {
        if(image != null) {
            if(userActions.canDecrease()) {
                userActions.decreasePointer();
                Stack stack = userActions.getStackPointer();
                WritableImage imageStack = stack.getImageCurrent();
                ImageSize dim = stack.getDimensions();
                setImageSize(dim.getWidth(), dim.getHeight());
                pic.setDimensions(dim);
                writableImage = imageStack;
                sliderContext();                
            }
        }
    }

    @FXML
    private void handleRedo(ActionEvent event) {
        if(image != null) {
            if(userActions.canIncrease()) {
                userActions.increasePointer();
                Stack stack = userActions.getStackPointer();
                WritableImage imageStack = stack.getImageCurrent();
                ImageSize dim = stack.getDimensions();
                setImageSize(dim.getWidth(), dim.getHeight());
                pic.setDimensions(dim);
                writableImage = imageStack;
                sliderContext();                
            }
        }
    }

    @FXML
    private void viewHistory(ActionEvent event) {
        if(image != null) {
            try {
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/HistoryView.fxml")));
                Parent root =  loader.load();

               HistoryController historyInstanceController = (HistoryController)loader.getController();

                historyInstanceController.visualizeHistory(mainInstanceController);


                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

            } catch (IOException ex) {
               Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }           
        }
    }

    @FXML
    private void handleBitReduction(ActionEvent event) {
        
    }

    @FXML
    private void handleMedianCut() {
        if(image != null) {
            int k = (int) sliderToMedianCut.getValue();
            List<Color> l = new ArrayList<Color>();

            //Buscar canal con mayor rango
            double den_red = 0;
            double den_green = 0;
            double den_blue = 0;
            int c = 0;

            //Aca recorre toda la imagen y ve sumando los valores por cada canal y 
            //al mismo tiempo llena l con los colores(los tres canales juntos)
            Color [][] current = pic.getColorMatrix();
            for(int y = 0; y< imageHeight; y++){
                for(int x = 0; x< imageWidth; x++){ 
                    Color imColor = current[x][y];  
                    den_red += imColor.getRed();
                    den_green += imColor.getGreen();
                    den_blue += imColor.getBlue();
                    l.add(imColor);  
                    c++;
                }
            }   

            //Saca promedio para ver la densidad de cada canal
            den_red = den_red/imageHeight*imageWidth;
            den_green = den_green/imageHeight*imageWidth;
            den_blue = den_blue/imageHeight*imageWidth;

            //Determina por cual canal se van a ordenar los colores
            double den_max = max(den_blue,max(den_red,den_green));
            char channel;
            if (den_max  == den_red) channel = 'R';
            else if (den_max == den_green) channel = 'G';
            else channel ='B';  

            c = 0;
            //Te quedas solo con un color de cada combinacion
            Set<Color> uniquec = new HashSet<Color>(l); //Te quedas solo con un color de cada combinacion
            Color [] arrayColor =  new Color[uniquec.size()];
            //Llenas tu arreglo de colores para ordenarlos
            for (Color x : uniquec){
                arrayColor[c] = (x);
                c++;
            }

            //Los mandas a ordenar mandando cual va a ser el canal
            MedianCut.quickSort(arrayColor, 0, uniquec.size()-1, channel);
            HashMap <Color,Color> palette = create_Palette(arrayColor,0,"Media",k);

            pixelWriter = writableImage.getPixelWriter();
            Color[][] scaleMatrix = new Color [imageWidth][imageHeight];



            for(int y = 0; y< imageHeight; y++){
                for(int x = 0; x< imageWidth;x++){ 
                    Color imColor = current[x][y];  
                    Color newColor = palette.get(imColor);
                    pixelWriter.setColor(x,y,newColor);
                    scaleMatrix[x][y] = newColor;
                }
             } 
            pic.setScaleMatrix(scaleMatrix);
            handleZoom();
            configurationImageView();

        }        
    } 

    @FXML
    private void handleDFT(ActionEvent event) {
        complexImage = new Mat();
        this.planes = new ArrayList<>();
        Mat src = OpenCVUtils.image2Mat(writableImage);
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGRA2GRAY);
        src.convertTo(src, CvType.CV_32F);

        this.planes.add(src);
        this.planes.add(Mat.zeros(src.size(), CvType.CV_32F));

        Core.merge(this.planes, complexImage);
        Core.dft(complexImage, complexImage);

        Mat magnitude = OpenCVUtils.createOptimizedMagnitude(this.complexImage);
        
//        complexImage = magnitude;

        writableImage = OpenCVUtils.mat2WritableImage(magnitude);
        generalContext();   
    }

    @FXML
    private void handleLowPass(ActionEvent event) {

        Imgproc.blur(complexImage, complexImage, new Size(5, 5));
        
        writableImage = OpenCVUtils.mat2WritableImage(complexImage);
        generalContext();  

    }

    @FXML
    private void handleDFTInverse(ActionEvent event) {
        
//        complexImage.convertTo(complexImage, CvType.CV_32F);

        Core.idft(complexImage, complexImage);

        Mat restoredImage = new Mat();
        Core.split(this.complexImage, this.planes);
        Core.normalize((Mat) this.planes.get(0), restoredImage, 0, 255, Core.NORM_MINMAX);

        restoredImage.convertTo(restoredImage, CvType.CV_8U);
        Imgproc.cvtColor(restoredImage, restoredImage, Imgproc.COLOR_GRAY2BGR);

        writableImage = OpenCVUtils.mat2WritableImage(restoredImage);
        generalContext();   


//        this.antitransformButton.setDisable(true);
    }
    
}
