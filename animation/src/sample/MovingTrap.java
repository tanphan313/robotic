package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by HieuApp on 12/4/2015.
 */
public class MovingTrap extends Roboot {
    private String path = "sample/home5.png";
    public MovingTrap(int currentXPos, int currentYPos) {
        Image robot = new Image(path);
        setIcon(new ImageView(robot));
        getIcon().setFitHeight(30);
        getIcon().setFitWidth(30);
        setCurrentXPos(currentXPos);
        setCurrentYPos(currentYPos);

        getIcon().setTranslateX(currentXPos);
        getIcon().setTranslateY(currentYPos);
    }

    @Override
    public boolean moveToPos(int xPos, int yPos) {
        return super.moveToPos(xPos, yPos);
    }
}
