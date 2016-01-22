package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by HieuApp on 11/12/2015.
 */
public class Roboot extends ImageView {
    private ImageView icon;
    private int currentXPos = 0;
    private int currentYPos = 0;
    private String path = "sample/clearner.png";
    public Roboot(int currentXPos, int currentYPos){
        //Load anh cho roboot
        Image robot = new Image(path);
        setIcon(new ImageView(robot));
        getIcon().setFitHeight(30);
        getIcon().setFitWidth(30);
        setCurrentXPos(currentXPos);
        setCurrentYPos(currentYPos);
        getIcon().setTranslateX(this.currentXPos);
        getIcon().setTranslateY(this.currentYPos);
    }

    public Roboot() {
    }


    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    /**
     * di chuyen toi vi tri co toa do x,y
     * @param xPos hoanh do dich
     * @param yPos tung do dich
     */
    public boolean moveToPos(int xPos, int yPos){

        if(this.currentXPos == xPos && this.currentYPos == yPos){
            return true;
        }
        if(getCurrentXPos() == xPos){
            if(getCurrentYPos() < yPos){
                setCurrentYPos(getCurrentYPos()+2);
                getIcon().setTranslateX(xPos);
                getIcon().setTranslateY(getCurrentYPos());
                return false;
            }else {
                setCurrentYPos(getCurrentYPos()-2);
                getIcon().setTranslateX(xPos);
                getIcon().setTranslateY(getCurrentYPos());
                return false;
            }
        }

        if(getCurrentYPos() == yPos){
            if(getCurrentXPos() < xPos){
                setCurrentXPos(getCurrentXPos()+2);
                getIcon().setTranslateX(this.currentXPos);
                getIcon().setTranslateY(yPos);
                return false;
            }else {
                this.currentXPos = this.currentXPos - 2;
                getIcon().setTranslateX(this.currentXPos);
                getIcon().setTranslateY(yPos);
                return false;

            }
        }
        return false;
    }

    /**
     * di chuyen toi vi tri co toa do x,y
     * @param xPos hoanh do dich
     * @param yPos tung do dich
     */
    public boolean moveToPos2(int xPos, int yPos){

        if(this.currentXPos == xPos && this.currentYPos == yPos){
            return true;
        }
        if(getCurrentXPos() == xPos){
            if(getCurrentYPos() < yPos){
                setCurrentYPos(getCurrentYPos()+1);
                getIcon().setTranslateX(xPos);
                getIcon().setTranslateY(getCurrentYPos());
                return false;
            }else {
                setCurrentYPos(getCurrentYPos()-1);
                getIcon().setTranslateX(xPos);
                getIcon().setTranslateY(getCurrentYPos());
                return false;
            }
        }

        if(getCurrentYPos() == yPos){
            if(getCurrentXPos() < xPos){
                setCurrentXPos(getCurrentXPos()+1);
                getIcon().setTranslateX(this.currentXPos);
                getIcon().setTranslateY(yPos);
                return false;
            }else {
                this.currentXPos--;
                getIcon().setTranslateX(this.currentXPos);
                getIcon().setTranslateY(yPos);
                return false;

            }
        }
        return false;
    }

    public int getCurrentXPos() {
        return currentXPos;
    }

    public void setCurrentXPos(int currentXPos) {
        this.currentXPos = currentXPos;
    }

    public int getCurrentYPos() {
        return currentYPos;
    }

    public void setCurrentYPos(int currentYPos) {
        this.currentYPos = currentYPos;
    }
}
