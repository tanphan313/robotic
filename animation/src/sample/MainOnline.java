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
import sample.stc.STCOFFLINE;
import sample.stc.STCONLINE;
import sample.stc.subCell;

import java.util.ArrayList;
import java.util.Random;


public class MainOnline extends Application {

    /**
     * time line for loop update roboot
     */
    public static Timeline timeline;
    public Timeline timelineTrap;
    /**
     * EvenHandler update for roboot
     */
    public static EventHandler onFinished;

    public static  AnchorPane root;

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
    int i=0;
    //for movingTrap
    int j=0;

    //
    /**
     * mang chua toa do cua tung cell tren ban do
     */
    public static int[][] arrayXPosition = new int [20][20];
    public static int[][] arrayYPosition = new int [20][20];

    public Dirt[][] arrayDirt = new Dirt[20][20];
    public static Trap[][] arrayTrap = new Trap[20][20];
    public static ArrayList<Trap> arrayListTrap = new ArrayList<Trap>();

    KeyFrame keyFrame, keyFrameMoving;

    /**
     * thoi gian load hinh cua roboot
     */
    public static Duration duration,durationMoving;
    String pathDirtClear = "sample/dirt_clear.png";
    String pathDirt = "sample/dirt.png";

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

    int[] xLastTrap = new int[90];
    int[] yLastTrap = new int[90];

    int[] indexXTrap = new int[90];
    int[] indexYTrap = new int[90];

    static int tmpIndex = 0;
    /**
     * vi tri bat dau
     */
    public static  int X_START = 0;
    public static  int Y_START = 4;
    public  static STCOFFLINE robootOff = new STCOFFLINE();
    //    public static STCONLINE robootOff = new STCONLINE();
    private ArrayList<MovingTrap> arrMovingTrap = new ArrayList<MovingTrap>();
    /**
     * Ham khoi tao khi bat dau chuong trinh, tuong tu onCreate trong Android
     * @param stage
     */
    @Override public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root,860,600);
        stage.setScene(scene);
        stage.show();

        initArray(); //mang chua toa do theo pixel cua ban do
        robootOff.generateMaxtric();

        String pathGach = "sample/gach.png";
        for(int i=0; i<9; i++){
            Random random = new Random();
            int x = random.nextInt(19);
            int y = random.nextInt(19);
            robootOff.makeTrap(x, y);

        }
        /**
         * ham bat su kien chuot dat roboot
         */
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int x = (int) ((event.getX()-260)/30);
                int y = (int) (event.getY()/30);
                System.out.println("X: " + x + " Y: " + y);

                if(Controller.makeTrap )
                {
                    return;
                }
                //neu chua tao roboot thi tao ngay
                if(Controller.onclickRoboot && !makeRoboot){
                    X_START = y;
                    Y_START = x;

                    roboot = new Roboot(arrayXPosition[X_START][Y_START],
                            arrayYPosition[X_START][Y_START]);
                    root.getChildren().add(roboot.getIcon());

                    robootOff.getNeighborOfParCell(robootOff.listParCell);
                    robootOff.DFS(robootOff.listParCell.get(Y_START/2 + (X_START/2)*robootOff.numRow));
                    robootOff.makeRoute(Y_START, X_START);
                    makeRoboot = true;

                }

                //neu radiobutton chon make trap
                if(!Controller.onclickRoboot){
                    int yMovingTrap = (int) ((event.getX()-260)/30);
                    int xMovingTrap = (int) ((event.getY())/30);
                    /**
                     * Vat can di dong
                     */
                    xLastTrap[tmpIndex] = xMovingTrap;
                    indexXTrap[tmpIndex] = xLastTrap[tmpIndex];

                    yLastTrap[tmpIndex] = yMovingTrap;
                    indexYTrap[tmpIndex] = yLastTrap[tmpIndex];
                    tmpIndex++;
                    MovingTrap movingTrap = new MovingTrap(arrayXPosition[xMovingTrap][yMovingTrap],arrayYPosition[xMovingTrap][yMovingTrap]);
                    root.getChildren().add(movingTrap.getIcon());
                    arrMovingTrap.add(movingTrap);

                }

                //create a timeline for moving the roboot
                timeline = new Timeline();
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.setAutoReverse(true);
                /**
                 * Update anh cua vat can di dong.
                 */

                //Ham nay chua thuat toan dieu khien robot
                onFinished = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent t) {
                        toaDoX = robootOff.queueSubCell.get(i).y;
                        toaDoY = robootOff.queueSubCell.get(i).x;
                        boolean isDest = false;

                        //Roboot
                        if(robootOff.listSubCell.get(toaDoY + toaDoX*2*robootOff.numRow).getMovingTrap() == 0){
                            robootOff.queueSubCell.get(i).setMovingRobot(1);
                            isDest = roboot.moveToPos(arrayXPosition[toaDoX][toaDoY],
                                    arrayYPosition[toaDoX][toaDoY]);
                        }else { //la vat can thi dung yen
                            if(i != 0){
                                robootOff.queueSubCell.get(i-1).setMovingRobot(1);
                                System.out.println("Gap nhau");
                                System.out.println("Toa do Roboot = ["+robootOff.queueSubCell.get(i-1).x+"]["+robootOff.queueSubCell.get(i-1).y+"]");

                            }
                        }
                        if(isDest){
                            if(i == (robootOff.queueSubCell.size()-1)){
                                timeline.stop();
                            }
                            System.out.println("dest ["+robootOff.queueSubCell.get(i).x+"]["+robootOff.queueSubCell.get(i).y+"]");
                            robootOff.queueSubCell.get(i).setMovingRobot(1);
                            if(i!=0){
                                robootOff.queueSubCell.get(i-1).setMovingRobot(0);
                            }
                            if(i>=0){
                                int lastX = robootOff.queueSubCell.get(i).y;
                                int lastY = robootOff.queueSubCell.get(i).x;

                                root.getChildren().remove(arrayDirt[lastX][lastY].getIcon());
                            }

                            i++;
                        }

                        //tao movingTrap
                        for(int i = 0; i<arrMovingTrap.size(); i++){
                            boolean isDest1= false;
                            isDest1 = arrMovingTrap.get(i).moveToPos(arrayXPosition[indexXTrap[i]][indexYTrap[i]],
                                    arrayYPosition[indexXTrap[i]][indexYTrap[i]]);
                            robootOff.listSubCell.get(indexYTrap[i] + indexXTrap[i]*2*robootOff.numRow).setMovingTrap(1);

                            if (isDest1) {
                                robootOff.listSubCell.get(yLastTrap[i] + xLastTrap[i]*2*robootOff.numRow).setMovingTrap(0);
                                subCell nextSubMovingTrap = robootOff.listSubCell.get(indexYTrap[i] + indexXTrap[i] * 2 * robootOff.numRow).findNextForMovingTrap(robootOff.listSubCell, robootOff.numRow, robootOff.numCol);
                                if(nextSubMovingTrap == null){
                                    System.out.println("no way to go");
                                }
                                yLastTrap[i] = indexYTrap[i];
                                xLastTrap[i] = indexXTrap[i];
                                indexXTrap[i] = nextSubMovingTrap.y;
                                indexYTrap[i] = nextSubMovingTrap.x;
                                robootOff.listSubCell.get(indexYTrap[i] + indexXTrap[i]*2*robootOff.numRow).setMovingTrap(1);
//                                System.out.println("Den dich >>Tim duong moi: ["+indexYTrap+"]["+indexXTrap+"]");
                            }
                        }
                    }
                };

                duration = Duration.millis(3);
                keyFrame = new KeyFrame(duration, Main.onFinished );
                timeline.getKeyFrames().add(keyFrame);
            }
        });

        for(int i=0; i<400; i++){
            arrDirt.add(null);
        }




        for(int i = 0; i<robootOff.listSubCell.size(); i++){
//            if(robootOff.listSubCell.get(i).getTrap() == 1){
//                VatCan vatCan = new VatCan(pathGach,arrayXPosition[robootOff.listSubCell.get(i).y][robootOff.listSubCell.get(i).x],
//                        arrayYPosition[robootOff.listSubCell.get(i).y][robootOff.listSubCell.get(i).x]);
//                root.getChildren().add(vatCan.getIcon());
//            }else {
            Dirt dirt = new Dirt(pathDirt,arrayXPosition[robootOff.listSubCell.get(i).y][robootOff.listSubCell.get(i).x],
                    arrayYPosition[robootOff.listSubCell.get(i).y][robootOff.listSubCell.get(i).x]);
            arrayDirt[robootOff.listSubCell.get(i).y][robootOff.listSubCell.get(i).x] = dirt;
            root.getChildren().add(arrayDirt[robootOff.listSubCell.get(i).y][robootOff.listSubCell.get(i).x].getIcon());
//            }
        }



    }


    public static void main(String[] args) {
        Application.launch(args);

    }

    /**
     * khoi tao toa do tuong ung voi map 2D
     */
    public void initArray(){
        for(int i=0; i<20; i++){
            for(int j=0; j< 20; j++){
                arrayXPosition[i][j] = 260 + j*30;
                arrayYPosition[i][j] = i*30;
            }
        }

    }


    /**
     * Sinh cac o vat can ngau nhien
     */
    public static void RandomTrap(){
        for(int i = 0; i<robootOff.listSubCell.size(); i++){
            robootOff.listSubCell.get(i).setTrap(0);
        }
        for(int i = 0; i<robootOff.listParCell.size(); i++){
            robootOff.listParCell.get(i).setTrap(0);
        }

        //reset lai ban do ve 0 co vat can nao
//            for(int i=0; i<arrayTrap.length; i++){
//                for(int j=0; j<arrayTrap.length; j++){
//                if(arrayTrap[i][j] != null){
//
//                    root.getChildren().remove(arrayTrap[i][j].getIcon());
//                }
//            }
//        }
        for(int i=0; i< arrayListTrap.size(); i++){
            root.getChildren().remove(arrayListTrap.get(i).getIcon());

        }


//        //lam null mang de chua trap
//        for(int i=0; i<arrayTrap.length; i++) {
//            for (int j = 0; j < arrayTrap.length; j++) {
//                arrayTrap[i][j] = null;
//            }
//        }

        arrayListTrap.clear();
        String pathGach = "sample/gach.png";
        for(int i=0; i<9; i++){
            Random random = new Random();
            int x = random.nextInt(19);
            int y = random.nextInt(19);
            robootOff.makeTrap(x, y);
            Trap vatCan = new Trap(pathGach,arrayXPosition[y][x], arrayYPosition[y][x]);
//            arrayTrap[y][x] = vatCan;
            arrayListTrap.add(vatCan);
            root.getChildren().add(arrayListTrap.get(i).getIcon());

        }
        for(int i = 0; i<robootOff.listSubCell.size(); i++){
            if(robootOff.listSubCell.get(i).getTrap() == 1){
                VatCan vatCan = new VatCan(pathGach,arrayXPosition[robootOff.listSubCell.get(i).y][robootOff.listSubCell.get(i).x],
                        arrayYPosition[robootOff.listSubCell.get(i).y][robootOff.listSubCell.get(i).x]);
                root.getChildren().add(vatCan.getIcon());
            }
        }

        //restart lai bien khoi tao chap; cho phep bat su kien click chuot
        Controller.makeTrap = false;
    }
}