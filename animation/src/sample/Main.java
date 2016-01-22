package sample;


import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.util.Duration;
import sample.stc.STC;
import sample.stc.STCOFFLINE;
import sample.stc.STCONLINE;
import sample.stc.subCell;

import java.util.ArrayList;
import java.util.Random;


public class Main extends Application {

    /**
     * time line for loop update roboot
     */
    public static Timeline timeline;
    public Timeline timelineTrap;
    /**
     * EvenHandler update for roboot
     */
    public static EventHandler onFinished;

    public static AnchorPane root;

    /**
     * Quan ly hinh anh cua roboot
     */
    static Roboot roboot;


    /**
     * vi tri hien tai cua Roboot
     */
    int toaDoX;
    int toaDoY;
    /**
     * bien dem buoc chay cua roboot
     */
    int i = 0;
    //for movingTrap
    int j = 0;

    //
    /**
     * mang chua toa do cua tung cell tren ban do
     */
    public static int[][] arrayXPosition = new int[20][20];
    public static int[][] arrayYPosition = new int[20][20];

    //    public Dirt[][] arrayDirt = new Dirt[20][20];
    public static Trap[][] arrayTrap = new Trap[20][20];
    public static ArrayList<Trap> arrayListTrap = new ArrayList<Trap>();

    KeyFrame keyFrame, keyFrameMoving;

    /**
     * thoi gian load hinh cua roboot
     */
    public static Duration duration, durationMoving;
    String pathDirtClear = "sample/dirt_clear.png";
    public static String pathDirt = "sample/dirt.png";
    public static String pathHardDirt = "sample/harddirt.png";

    ArrayList<Dirt> arrDirt = new ArrayList<Dirt>();
    /**
     * true neu da make roboot
     */
    public boolean makeRoboot = false;

    /**
     * vi tri (x,y) tiep theo cua Trap
     */
//    int indexXTrap = xLastTrap;
//    int indexYTrap = yLastTrap;
//
//    int indexXTrap2 = xLastTrap2;
//    int indexYTrap2 = yLastTrap2;

    public static int[] xLastTrap = new int[90];
    public static int[] yLastTrap = new int[90];

    public static int[] indexXTrap = new int[90];
    public static int[] indexYTrap = new int[90];

    static int tmpIndex = 0;
    String pathGach = "sample/gach.png";
    //    public static STCOFFLINE robootOff = new STCOFFLINE();
    //    public static STCONLINE robootOn = new STCONLINE();
    public static ArrayList<MovingTrap> arrMovingTrap = new ArrayList<MovingTrap>();

    /**
     * Ham khoi tao khi bat dau chuong trinh, tuong tu onCreate trong Android
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root, 860, 600);
        stage.setScene(scene);
        stage.show();

        initArray(); //mang chua toa do theo pixel cua ban do

        //Tu dong sinh rac kin ban do
        for (int i = 0; i < Controller.robootOff.listSubCell.size(); i++) {
            Dirt dirt = new Dirt(pathDirt, arrayXPosition[Controller.robootOff.listSubCell.get(i).y][Controller.robootOff.listSubCell.get(i).x],
                    arrayYPosition[Controller.robootOff.listSubCell.get(i).y][Controller.robootOff.listSubCell.get(i).x]);
            Controller.arrayDirt[Controller.robootOff.listSubCell.get(i).y][Controller.robootOff.listSubCell.get(i).x] = dirt;
            root.getChildren().add(Controller.arrayDirt[Controller.robootOff.listSubCell.get(i).y][Controller.robootOff.listSubCell.get(i).x].getIcon());
        }
        /**
         * ham bat su kien chuot dat roboot
         */
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) ((event.getX() - 260) / 30);
                int y = (int) (event.getY() / 30);
                System.out.println("X: " + x + " Y: " + y);
                //Click chuot trong pham vi cua Map
                if(event.getX() >= 260 && event.getY() >= 0){
                    //neu radiobutton dang chon roboot thi sinh robot tai vi tri con tro chuot
                    if(Controller.onclickRoboot && !makeRoboot && Controller.makeTrap ){
                        //neu la che do offline
                        if(Controller.BUILD_OFFLINE){
                            System.out.println("Sinh roboot offline !");
                            Controller.X_START = y;
                            Controller.Y_START = x;
                            Controller.robootOff.getNeighborOfParCell(Controller.robootOff.listParCell);
                            Controller.robootOff.DFS(Controller.robootOff.listParCell.
                                    get(Controller.Y_START / 2 + (Controller.X_START / 2) * Controller.robootOff.numRow));
                            Controller.robootOff.makeRoute(Controller.Y_START, Controller.X_START);

                            roboot = new Roboot(arrayXPosition[Controller.X_START][Controller.Y_START],
                                    arrayYPosition[Controller.X_START][Controller.Y_START]);
                            root.getChildren().add(roboot.getIcon());
                        }else { //neu la che do Online
                            System.out.println("Sinh roboot online !");
                            Controller.X_START = y;
                            Controller.Y_START = x;

                            roboot = new Roboot(arrayXPosition[Controller.X_START][Controller.Y_START],
                                    arrayYPosition[Controller.X_START][Controller.Y_START]);
                            root.getChildren().add(roboot.getIcon());
                            Controller.robootOn.makeRoute(Controller.Y_START,Controller.X_START);
                        }
                        makeRoboot = true;
                    }

                    //sinh movingTrap
                    if(Controller.onclickMovingTrap == true){
                        xLastTrap[tmpIndex] = y;
                        indexXTrap[tmpIndex] = xLastTrap[tmpIndex];

                        yLastTrap[tmpIndex] = x;
                        indexYTrap[tmpIndex] = yLastTrap[tmpIndex];
                        tmpIndex++;
                        MovingTrap movingTrap = new MovingTrap(arrayXPosition[y][x],
                                arrayYPosition[y][x]);
                        root.getChildren().add(movingTrap.getIcon());
                        arrMovingTrap.add(movingTrap);
                    }

                    if(Controller.onclickHardDirt == true){
                        if(Controller.BUILD_OFFLINE){
                            System.out.println("Sinh vet ban cung dau offline tai["+x+"]["+y+"] !");
                            Controller.robootOff.listSubCell.get(x + y*2*Controller.robootOff.numRow).setHardDirt(1);

                            Main.root.getChildren().remove(Controller.arrayDirt[y][x].getIcon());
                            Dirt dirt = new Dirt(pathHardDirt, arrayXPosition[y][x],
                                    arrayYPosition[y][x]);
                            Controller.arrayDirt[y][x] = dirt;
                            root.getChildren().add(Controller.arrayDirt[y][x].getIcon());

                        }else { //neu la che do Online
                            System.out.println("Sinh vet ban cung dau online tai["+x+"]["+y+"] !");
                            Controller.robootOn.listSubCell.get(x + y*2*Controller.robootOn.numRow).setHardDirt(1);

                            Main.root.getChildren().remove(Controller.arrayDirt[y][x].getIcon());
                            Dirt dirt = new Dirt(pathHardDirt, arrayXPosition[y][x],
                                    arrayYPosition[y][x]);
                            Controller.arrayDirt[y][x] = dirt;
                            root.getChildren().add(Controller.arrayDirt[y][x].getIcon());
                        }
                    }

                }

                //Click chuot trong pham vi cua controler
                if( x < 0){
                    return;
                }
            }
        });

    }


    public static void main(String[] args) {
        Application.launch(args);
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
     * Sinh cac o vat can ngau nhien
     */
    public static void RandomTrap(STC roboot) {
        for (int i = 0; i < roboot.listSubCell.size(); i++) {
            roboot.listSubCell.get(i).setTrap(0);
        }
        for (int i = 0; i < roboot.listParCell.size(); i++) {
            roboot.listParCell.get(i).setTrap(0);
        }

        for (int i = 0; i < arrayListTrap.size(); i++) {
            root.getChildren().remove(arrayListTrap.get(i).getIcon());
        }

        arrayListTrap.clear();
        String pathGach = "sample/gach.png";
        for (int i = 0; i < 9; i++) {
            Random random = new Random();
            int x = random.nextInt(19);
            int y = random.nextInt(19);
            roboot.makeTrap(x, y);
            Trap vatCan = new Trap(pathGach, arrayXPosition[y][x], arrayYPosition[y][x]);
//            arrayTrap[y][x] = vatCan;
            arrayListTrap.add(vatCan);
            root.getChildren().add(arrayListTrap.get(i).getIcon());
        }
        for (int i = 0; i < roboot.listSubCell.size(); i++) {
            if (roboot.listSubCell.get(i).getTrap() == 1) {
                VatCan vatCan = new VatCan(pathGach, arrayXPosition[roboot.listSubCell.get(i).y][roboot.listSubCell.get(i).x],
                        arrayYPosition[roboot.listSubCell.get(i).y][roboot.listSubCell.get(i).x]);
                root.getChildren().add(vatCan.getIcon());
            }
        }
        //Them 3 o con lai cua trap
        for (int i = 0; i < roboot.listSubCell.size(); i++) {
            if (roboot.listSubCell.get(i).getTrap() == 1) {
                VatCan vatCan = new VatCan(pathGach, arrayXPosition[roboot.listSubCell.get(i).y][roboot.listSubCell.get(i).x],
                        arrayYPosition[roboot.listSubCell.get(i).y][roboot.listSubCell.get(i).x]);
                root.getChildren().add(vatCan.getIcon());
            }
        }

    }
}