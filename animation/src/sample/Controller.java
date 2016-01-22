package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import sample.stc.STC;
import sample.stc.STCOFFLINE;
import sample.stc.STCONLINE;
import sample.stc.subCell;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    /*Btn turn on robot*/
    public ToggleButton pauseRobot = new ToggleButton();
    /*VBox*/
    public VBox controler;
    /*Phan map ch�nh*/
    public Pane map;
    //Sinh map button
    public Button randomMap = new Button();
    /**
     * co trang thai roboot, true neu robot dang hoat dong, false neu robot dung.
     */
    public static boolean makeTrap = false;
    public static boolean robootPause = false;
    public static boolean resume = false;
    /**
     * STC online and offline
     */
    public static final String ONLINE_MODE = "online";
    public static final String OFFLINE_MODE = "offline";

    public static boolean BUILD_OFFLINE = false;
    public RadioButton radioOnline = new RadioButton();
    public RadioButton radioOffline = new RadioButton();
    //Che do lau mac dinh la online
    public static boolean stcOnline = true;

    /**
     * clearn mode, lau nhanh, lau sach, lau tu dong
     */
    public RadioButton radioRoboot = new RadioButton();
    public RadioButton movingTrap = new RadioButton();
    public RadioButton hardDirt = new RadioButton();
    public static boolean onclickRoboot = false;
    public static boolean onclickMovingTrap = false;
    public static boolean onclickHardDirt = true;

    /**
     * mang chua toa do cua tung cell tren ban do
     */
    public static int[][] arrayXPosition = new int[20][20];
    public static int[][] arrayYPosition = new int[20][20];

    public static Dirt[][] arrayDirt = new Dirt[20][20];
    public static Dirt[][] arrayHardDirt = new Dirt[20][20];

    /**
     * Roboot offline
     */
    public static STCOFFLINE robootOff = new STCOFFLINE();
    public static STCONLINE robootOn = new STCONLINE();
//    /**
//     * Quan ly hinh anh cua roboot
//     */
//    static Roboot roboot;
    /**
     * vi tri bat dau
     */
    public static int X_START = 0;
    public static int Y_START = 4;

    /**
     * vi tri hien tai cua Roboot
     */
    int toaDoX;
    int toaDoY;
    /**
     * bien dem buoc chay cua roboot
     */
    int i = 0;
    Timeline timeline = new Timeline();

    /**
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image image = new Image(String.valueOf(Controller.class.getResource("bg_controler.png")));
        BackgroundSize backgroundSize = new BackgroundSize(260, 640, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        controler.setBackground(background);


        //set background for Map
        setBackgroundMap("map_roboot_2020.png");
        //Radio button Online, offline
        ToggleGroup groupRadio = new ToggleGroup();

        radioOnline.setToggleGroup(groupRadio);
        radioOnline.setUserData(ONLINE_MODE);
        //default radio online is checked
        radioOnline.setSelected(true);
        radioOnline.requestFocus();

        radioOffline.setToggleGroup(groupRadio);
        radioOffline.setUserData(OFFLINE_MODE);

        //Processing Events for Radio Buttons
        groupRadio.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (groupRadio.getSelectedToggle() != null) {
                    if (groupRadio.getSelectedToggle().getUserData().equals(ONLINE_MODE)) {
                        //algorithm STC online
                        System.out.println("MODE: STC online");
                        stcOnline = true;
                        BUILD_OFFLINE = false;
                    } else {
                        // algorithm STC offline
                        System.out.println("MODE: STC offline");
                        stcOnline = false;
                        BUILD_OFFLINE = true;
                    }
                }
            }
        });

        /**
         * Radio button sinh roboot va trap
         */
        ToggleGroup groupClearn = new ToggleGroup();
        radioRoboot.setToggleGroup(groupClearn);
        radioRoboot.setUserData("roboot_radio");

        movingTrap.setToggleGroup(groupClearn);
        movingTrap.setUserData("movingtrap_radio");

        hardDirt.setToggleGroup(groupClearn);
        hardDirt.setSelected(true);
        hardDirt.setUserData("harddirt_radio");

        groupClearn.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (groupClearn.getSelectedToggle() != null) {
                    if (groupClearn.getSelectedToggle().getUserData().equals("roboot_radio")) {
                        onclickRoboot = true;
                        onclickMovingTrap = false;
                        onclickHardDirt = false;
                        System.out.println("onclick roboot");
                        //setup Clearn perfect mode
                    } else if(groupClearn.getSelectedToggle().getUserData().equals("movingtrap_radio")){
                        System.out.println("onclick moving trap trap");
                        onclickMovingTrap = true;
                        onclickRoboot = false;
                        onclickHardDirt = false;
                        //setup auto clearn
                    } else{//set up hard dirt
                        System.out.println("onclick hard dirt");
                        onclickHardDirt = true;
                        onclickRoboot = false;
                        onclickMovingTrap = false;
                    }
                }
            }
        });

        initArray();
        robootOff.generateMaxtric();
        robootOn.generateMaxtric();

        /**
         * sinh trap cho roboot offline nhung dung chung cho online
         */
        randomMap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                makeTrap = true;
                if (BUILD_OFFLINE) {
                    Main.RandomTrap(robootOff);
                } else {
                    Main.RandomTrap(robootOn);
                }

            }
        });

        //Ham nay chua thuat toan dieu khien robot
        /**
         * EvenHandler update for roboot
         */
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

                if(BUILD_OFFLINE){
                    RemoteRoboot(robootOff);
                }else {
                    RemoteRoboot(robootOn);
                }

//                toaDoX = robootOff.queueSubCell.get(i).y;
//                toaDoY = robootOff.queueSubCell.get(i).x;
//                boolean isDest = false;
//                //Roboot
//                if (robootOff.listSubCell.get(toaDoY + toaDoX * 2 * robootOff.numRow).getMovingTrap() == 0) {
//                    robootOff.queueSubCell.get(i).setMovingRobot(1);
//                    isDest = Main.roboot.moveToPos(arrayXPosition[toaDoX][toaDoY],
//                            arrayYPosition[toaDoX][toaDoY]);
//                } else { //la vat can thi dung yen
//                    if (i != 0) {
//                        robootOff.queueSubCell.get(i - 1).setMovingRobot(1);
//                        System.out.println("Gap nhau");
//                        System.out.println("Toa do Roboot = [" + robootOff.queueSubCell.get(i - 1).x + "][" + robootOff.queueSubCell.get(i - 1).y + "]");
//
//                    }
//                }
//
//                if (isDest) {
//                    if (i == (robootOff.queueSubCell.size() - 1)) {
//                        timeline.stop();
//                    }
//                    System.out.println("dest [" + robootOff.queueSubCell.get(i).x + "][" + robootOff.queueSubCell.get(i).y + "]");
//                    robootOff.queueSubCell.get(i).setMovingRobot(1);
//                    if (i != 0) {
//                        robootOff.queueSubCell.get(i - 1).setMovingRobot(0);
//                    }
//                    if (i >= 0) {
//                        int lastX = robootOff.queueSubCell.get(i).y;
//                        int lastY = robootOff.queueSubCell.get(i).x;
//                        Main.root.getChildren().remove(arrayDirt[lastX][lastY].getIcon());
//                    }
//
//                    i++;
//                }
//                //tao movingTrap
//                for (int i = 0; i < Main.arrMovingTrap.size(); i++) {
//                    boolean isDest1 = false;
//                    isDest1 = Main.arrMovingTrap.get(i).moveToPos(arrayXPosition[Main.indexXTrap[i]][Main.indexYTrap[i]],
//                            arrayYPosition[Main.indexXTrap[i]][Main.indexYTrap[i]]);
//                    robootOff.listSubCell.get(Main.indexYTrap[i] + Main.indexXTrap[i] * 2 * robootOff.numRow).setMovingTrap(1);
//
//                    if (isDest1) {
//                        robootOff.listSubCell.get(Main.yLastTrap[i] + Main.xLastTrap[i] * 2 * robootOff.numRow).setMovingTrap(0);
//                        subCell nextSubMovingTrap = robootOff.listSubCell.get(Main.indexYTrap[i] + Main.indexXTrap[i] * 2 * robootOff.numRow).findNextForMovingTrap(robootOff.listSubCell, robootOff.numRow, robootOff.numCol);
//                        if (nextSubMovingTrap == null) {
//                            System.out.println("no way to go");
//                        }
//                        Main.yLastTrap[i] = Main.indexYTrap[i];
//                        Main.xLastTrap[i] = Main.indexXTrap[i];
//                        Main.indexXTrap[i] = nextSubMovingTrap.y;
//                        Main.indexYTrap[i] = nextSubMovingTrap.x;
//                        robootOff.listSubCell.get(Main.indexYTrap[i] + Main.indexXTrap[i] * 2 * robootOff.numRow).setMovingTrap(1);
////                                System.out.println("Den dich >>Tim duong moi: ["+indexYTrap+"]["+indexXTrap+"]");
//                    }
//                }
            }
        };

        Duration duration = Duration.millis(5);
        KeyFrame keyFrame = new KeyFrame(duration, onFinished);
        //create a timeline for moving the roboot
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(keyFrame);
        /**
         * roboot pause and reseum
         */
        pauseRobot.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    System.out.println("Stop");
                    robootPause = true;
                    resume = false;
                    timeline.pause();
                } else {
                    System.out.println("Start");
                    timeline.play();
                    pauseRobot.setText("Stop");
                }
            }
        });

    }

    /**
     * setup background for map
     *
     * @param pathBG
     */
    public void setBackgroundMap(String pathBG) {
        Image image = new Image(String.valueOf(Controller.class.getResource(pathBG)));
        BackgroundSize backgroundSize = new BackgroundSize(600, 600, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        map.setBackground(background);
    }

    /**
     * khoi tao toa do tuong ung voi map 2D
     */
    public void initArray() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                arrayXPosition[i][j] = 260 + j * 30;
                arrayYPosition[i][j] = i * 30;
            }
        }

    }

    /**
     * ham thuc hien dieu khien huong di cua roboot
     *
     * @param roboot online hoac offline
     */
    public void RemoteRoboot(STC roboot) {
        toaDoX = roboot.queueSubCell.get(i).y;
        toaDoY = roboot.queueSubCell.get(i).x;
        boolean isDest = false;
        //Roboot
        if (roboot.listSubCell.get(toaDoY + toaDoX * 2 * roboot.numRow).getMovingTrap() == 0) {
            roboot.queueSubCell.get(i).setMovingRobot(1);
            if(roboot.queueSubCell.get(i).getHardDirt() == 0){//simple moving
//                System.out.println("simple moving");
                isDest = Main.roboot.moveToPos(arrayXPosition[toaDoX][toaDoY],
                        arrayYPosition[toaDoX][toaDoY]);
            }else{//hard moving
//                System.out.println("hard moving");
                isDest = Main.roboot.moveToPos2(arrayXPosition[toaDoX][toaDoY],
                        arrayYPosition[toaDoX][toaDoY]);
            }
        } else { //la vat can thi dung yen
            if (i != 0) {
                roboot.queueSubCell.get(i - 1).setMovingRobot(1);
                System.out.println("Gap nhau");
                System.out.println("Toa do Roboot = [" + roboot.queueSubCell.get(i - 1).x + "][" + roboot.queueSubCell.get(i - 1).y + "]");

            }
        }
        if (isDest) {
            if (i == (roboot.queueSubCell.size() - 1)) {
                timeline.stop();
            }
            System.out.println("dest [" + roboot.queueSubCell.get(i).x + "][" + roboot.queueSubCell.get(i).y + "]");
            roboot.queueSubCell.get(i).setMovingRobot(1);
            if (i != 0) {
                roboot.queueSubCell.get(i - 1).setMovingRobot(0);
            }
            if (i >= 0) {
                int lastX = roboot.queueSubCell.get(i).y;
                int lastY = roboot.queueSubCell.get(i).x;
                Main.root.getChildren().remove(arrayDirt[lastX][lastY].getIcon());
            }
            i++;
        }
        //tao movingTrap
        for (int i = 0; i < Main.arrMovingTrap.size(); i++) {
            boolean isDest1 = false;
            isDest1 = Main.arrMovingTrap.get(i).moveToPos(arrayXPosition[Main.indexXTrap[i]][Main.indexYTrap[i]],
                    arrayYPosition[Main.indexXTrap[i]][Main.indexYTrap[i]]);
            roboot.listSubCell.get(Main.indexYTrap[i] + Main.indexXTrap[i] * 2 * roboot.numRow).setMovingTrap(1);
            if (isDest1) {
                roboot.listSubCell.get(Main.yLastTrap[i] + Main.xLastTrap[i] * 2 * roboot.numRow).setMovingTrap(0);
                subCell nextSubMovingTrap = roboot.listSubCell.get(Main.indexYTrap[i] + Main.indexXTrap[i] * 2 * roboot.numRow)
                        .findNextForMovingTrap(roboot.listSubCell, roboot.numRow, roboot.numCol);
                if (nextSubMovingTrap.getMovingRobot() != 0) {
                    System.out.println("robot is here");
                }
                Main.yLastTrap[i] = Main.indexYTrap[i];
                Main.xLastTrap[i] = Main.indexXTrap[i];
                Main.indexXTrap[i] = nextSubMovingTrap.y;
                Main.indexYTrap[i] = nextSubMovingTrap.x;
                roboot.listSubCell.get(Main.indexYTrap[i] + Main.indexXTrap[i] * 2 * roboot.numRow).setMovingTrap(1);
            }
        }
    }
}
