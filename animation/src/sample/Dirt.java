package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by HieuApp on 11/29/2015.
 */
public class Dirt extends ImageView {
    private ImageView icon;
    private int currentXPos = 0;
    private int currentYPos = 0;
    public Dirt(String path, int currentXPos, int currentYPos) {
        //Load anh cho roboot
        Image robot = new Image(path);
        setIcon(new ImageView(robot));
        getIcon().setFitHeight(28);
        getIcon().setFitWidth(28);
        setCurrentXPos(currentXPos+1);
        setCurrentYPos(currentYPos+1);
        getIcon().setTranslateX(getCurrentXPos());
        getIcon().setTranslateY(getCurrentYPos());
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
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
