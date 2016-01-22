package sample.stc;

import java.util.ArrayList;

public class parCell {
    public int x;
    public int y;
    protected int trap;
    protected ArrayList<parCell> listNeighbor;
    protected boolean edgeTop;
    protected boolean edgeBottom;
    protected boolean edgeRight;
    protected boolean edgeLeft;
    public parCell(){
        
    }
    public parCell(int x, int y, int trap){
        this.x = x;
        this.y = y;
        this.trap = trap;
        this.edgeBottom = this.edgeLeft = this.edgeRight = this.edgeTop = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTrap() {
        return trap;
    }

    public void setTrap(int trap) {
        this.trap = trap;
    }

    public boolean isEdgeTop() {
        return edgeTop;
    }

    public void setEdgeTop(boolean edgeTop) {
        this.edgeTop = edgeTop;
    }

    public boolean isEdgeBottom() {
        return edgeBottom;
    }

    public void setEdgeBottom(boolean edgeBottom) {
        this.edgeBottom = edgeBottom;
    }

    public boolean isEdgeRight() {
        return edgeRight;
    }

    public void setEdgeRight(boolean edgeRight) {
        this.edgeRight = edgeRight;
    }

    public boolean isEdgeLeft() {
        return edgeLeft;
    }

    public void setEdgeLeft(boolean edgeLeft) {
        this.edgeLeft = edgeLeft;
    }
    
    /**
     * 
     * @param listParCell
     * get list Neighbors
     */
    public void getNeighbor(ArrayList<parCell> listParCell){
        ArrayList<parCell> listNeighbor = new ArrayList();
        for(int i = 0; i<listParCell.size(); i++){
            if(isNeighbor(this, listParCell.get(i))){
                listNeighbor.add(listParCell.get(i));
            }
        }
        this.listNeighbor = listNeighbor;
    }
    //kiểm tra xem có là hàng xóm hay ko
    public boolean isNeighbor(parCell par, parCell neighbor){
        if(neighbor.trap == 1){
            return false;
        }else{
            if(( (neighbor.x == (par.x-1)) || (neighbor.x == (par.x +1)) ) && (neighbor.y == par.y)){
                return true;
            }else if(( (neighbor.y == (par.y-1)) || (neighbor.y == (par.y+1)) ) && (neighbor.x == par.x)){
                return true;
            }
        }
        return false;
    }
    /**
     * 
     * @param queueParCell
     * @return true if parCell has not exited in stack yet 
     */
    public boolean notInQueue(ArrayList<parCell> queueParCell){
        for(int i = 0; i<queueParCell.size();i++){
            if(queueParCell.get(i).x == this.x && queueParCell.get(i).y == this.y){
                return false;
            }
        }
        return true;
    }
    
}
