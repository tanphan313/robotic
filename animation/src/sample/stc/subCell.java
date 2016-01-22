package sample.stc;

import java.util.ArrayList;
import java.util.Random;

public class subCell {
    public int x;
    public int y;
    protected int trap;
    private int movingRobot;
    private int movingTrap;
    private int hardDirt;
    protected boolean borderTop;
    protected boolean borderBottom;
    protected boolean borderRight;
    protected boolean borderLeft;
    protected int xTB, yTB, xDB, yDB, xTN, yTN, xDN, yDN;
    protected ArrayList<subCell> listSubCellAvailable;
    protected parCell parCell;
    public subCell(){
        
    }
    public subCell(int x, int y, int trap){
        this.x = x;
        this.y = y;
        this.trap = trap;
        this.hardDirt = 0;
        this.movingRobot = 0;
        this.movingTrap = 0;
        this.borderBottom = this.borderLeft = this.borderRight = this.borderTop = false;
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

    public boolean isBorderTop() {
        return borderTop;
    }

    public void setBorderTop(boolean borderTop) {
        this.borderTop = borderTop;
    }

    public boolean isBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(boolean borderBottom) {
        this.borderBottom = borderBottom;
    }

    public boolean isBorderRight() {
        return borderRight;
    }

    public void setBorderRight(boolean borderRight) {
        this.borderRight = borderRight;
    }

    public boolean isBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(boolean borderLeft) {
        this.borderLeft = borderLeft;
    }

    public ArrayList<subCell> getListSubCellAvailable() {
        return listSubCellAvailable;
    }

    public void setListSubCellAvailable(ArrayList<subCell> listSubCellAvailable) {
        this.listSubCellAvailable = listSubCellAvailable;
    }

    public int getHardDirt() {
        return hardDirt;
    }

    public void setHardDirt(int hardDirt) {
        this.hardDirt = hardDirt;
    }

    public boolean notInQueue(ArrayList<subCell> queueSubCell){
        for(int i = 0; i<queueSubCell.size();i++){
            if(queueSubCell.get(i).x == this.x && queueSubCell.get(i).y == this.y){
                return false;
            }
        }
        return true;
    }
    
    //get List subCell Available for STC_OFFLINE
    public ArrayList<subCell> getListAvailable(ArrayList<subCell> listSubCell,ArrayList<subCell> queueSubCell, int numRow, int numCol){
        ArrayList<subCell> listSubCellAvailable = new ArrayList();
        int x = this.x;
        int y = this.y;
        if(this.borderBottom == false){
            if((y+1)<numRow*2){
                if((listSubCell.get(x + (y+1)*numRow*2).trap == 0) && (listSubCell.get(x + (y+1)*numRow*2).notInQueue(queueSubCell) == true)){
                    listSubCellAvailable.add(listSubCell.get(x + (y+1)*numRow*2));
                }
            }
        }
        if(this.borderTop == false){
            if((y-1)>=0){
                if((listSubCell.get(x + (y-1)*numRow*2).trap == 0) && (listSubCell.get(x + (y-1)*numRow*2).notInQueue(queueSubCell) == true)){
                    listSubCellAvailable.add(listSubCell.get(x + (y-1)*numRow*2));
                }
            }
        }
        if(this.borderLeft == false){
            if((x-1)>=0){
                if((listSubCell.get((x-1) + y*numRow*2).trap == 0) && (listSubCell.get((x-1) + y*numRow*2).notInQueue(queueSubCell) == true)){
                    listSubCellAvailable.add(listSubCell.get((x-1) + y*numRow*2));
                }
            }
        }
        if(this.borderRight == false){
            if((x+1)<numCol*2){
                if((listSubCell.get((x+1) + y*numRow*2).trap == 0) && (listSubCell.get((x+1) + y*numRow*2).notInQueue(queueSubCell) == true)){
                    listSubCellAvailable.add(listSubCell.get((x+1) + y*numRow*2));
                }
            }
        }
//        System.out.println("list sub cell available for "+"["+this.x+"]["+this.y+"]");
//        for(int i = 0; i<listSubCellAvailable.size(); i++){
//            System.out.println("subcel available:"+i+" ["+listSubCellAvailable.get(i).x+"]["+listSubCellAvailable.get(i).y+"]");
//        }
        return listSubCellAvailable;
    }

    //get list available for moving trap
    public subCell findNextForMovingTrap(ArrayList<subCell> listSubCell, int numRow, int numCol){
        ArrayList<subCell> arrAvailable = new ArrayList();
        subCell nextSub = null;
        subCell currentSub = listSubCell.get(this.getX() + this.getY()*2*numRow);
        if((this.y - 1)>=0){// go up
            if(listSubCell.get(this.x + (this.y - 1)*2*numRow).trap == 0
                    && listSubCell.get(this.x + (this.y - 1)*2*numRow).movingRobot == 0
                    && listSubCell.get(this.x + (this.y - 1)*2*numRow).movingTrap == 0){
                arrAvailable.add(listSubCell.get(this.x + (this.y - 1)*2*numRow));
            }
        }
        if((this.x + 1) < 2*numCol){//turn right
            if(listSubCell.get(this.x+1 + this.y*2*numRow).trap == 0
                    && listSubCell.get(this.x+1 + this.y*2*numRow).movingRobot == 0
                    && listSubCell.get(this.x+1 + this.y*2*numRow).movingTrap == 0){
                arrAvailable.add(listSubCell.get(this.x+1 + this.y*2*numRow));
            }
        }
        if((this.y + 1) <2*numRow){// go down
            if(listSubCell.get(this.x + (this.y+1)*2*numRow).trap == 0
                    && listSubCell.get(this.x + (this.y+1)*2*numRow).movingRobot == 0
                    && listSubCell.get(this.x + (this.y+1)*2*numRow).movingTrap == 0){
                arrAvailable.add(listSubCell.get(this.x + (this.y+1)*2*numRow));
            }
        }
        if((this.x - 1)>=0){//turn left
            if(listSubCell.get(this.x-1 + this.y*2*numRow).trap == 0
                    && listSubCell.get(this.x-1 + this.y*2*numRow).movingRobot == 0
                    && listSubCell.get(this.x-1 + this.y*2*numRow).movingTrap == 0){
                arrAvailable.add(listSubCell.get(this.x-1 + this.y*2*numRow));
            }
        }
        if(arrAvailable.size()>0){
            Random random = new Random();
            int index = random.nextInt(arrAvailable.size())+1;
            nextSub = arrAvailable.get(index-1);
        }
        return nextSub;
    }
    
    //find NextSubCell for STC_OFFLINE
    public subCell findNextSubCellOffLine(ArrayList<subCell> listSubCell, ArrayList<subCell> queueSubCell, int numRow, int numCol){
        subCell resultCell = null;
        this.setListSubCellAvailable(this.getListAvailable(listSubCell, queueSubCell,numRow, numCol));
        
        for(int i = 0; i<this.listSubCellAvailable.size(); i++){
            if((this.borderBottom == true) && (this.listSubCellAvailable.get(i).borderBottom == true)){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
            if((this.borderTop == true) && (this.listSubCellAvailable.get(i).borderTop == true)){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
            if((this.borderLeft == true) && (this.listSubCellAvailable.get(i).borderLeft == true)){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
            if((this.borderRight == true) && (this.listSubCellAvailable.get(i).borderRight == true)){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
            if(this.isBrother(this.listSubCellAvailable.get(i)) == true){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
        }
//        System.out.println("next subcell: ["+resultCell.x+"]["+resultCell.y+"]");
        return resultCell;
    }
    
    //find NextSubCell for STC_ONLINE
    public subCell findNextSubCellOnLine(ArrayList<subCell> listSubCell, ArrayList<subCell> queueSubCell, ArrayList<parCell> queueParCell, int numRow, int numCol){
        subCell resultCell = null;
        this.setListSubCellAvailable(this.getListAvailable(listSubCell, queueSubCell,numRow, numCol));
        //find first neighbor if exited
        for(int i = 0; i<this.listSubCellAvailable.size(); i++){
            if(this.isNewNeighbor(this.listSubCellAvailable.get(i), queueParCell)){
                resultCell = this.listSubCellAvailable.get(i);
                return resultCell;
            }
        }
        //if there is no neighbor available, move on counterclockwise order
        this.parCell = this.getParCelFromSubCell();
        xTB = xTN = this.parCell.x*2;
        xDB = xDN = this.parCell.x*2+1;
        yTB = yDB = this.parCell.y*2;
        yTN = yDN = this.parCell.y*2+1;
        for(int i = 0; i<this.listSubCellAvailable.size(); i++){
            if((this.x == xTB) && (this.y == yTB)
                    && (this.listSubCellAvailable.get(i).x == xTN) && (this.listSubCellAvailable.get(i).y == yTN)){
                resultCell = this.listSubCellAvailable.get(i);
                return resultCell;
            }
            if((this.x == xTN) && (this.y == yTN)
                    && (this.listSubCellAvailable.get(i).x == xDN) && (this.listSubCellAvailable.get(i).y == yDN)){
                resultCell = this.listSubCellAvailable.get(i);
                return resultCell;
            }
            if((this.x == xDN) && (this.y == yDN)
                    && (this.listSubCellAvailable.get(i).x == xDB) && (this.listSubCellAvailable.get(i).y == yDB)){
                resultCell = this.listSubCellAvailable.get(i);
                return resultCell;
            }
            if((this.x == xDB) && (this.y == yDB)
                    && (this.listSubCellAvailable.get(i).x == xTB) && (this.listSubCellAvailable.get(i).y == yTB)){
                resultCell = this.listSubCellAvailable.get(i);
                return resultCell;
            }
        }
        //ther is no subCell to move on counterclockwise order, move back
        for(int i = 0; i<this.listSubCellAvailable.size(); i++){
            if((this.borderBottom == true) && (this.listSubCellAvailable.get(i).borderBottom == true)){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
            if((this.borderTop == true) && (this.listSubCellAvailable.get(i).borderTop == true)){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
            if((this.borderLeft == true) && (this.listSubCellAvailable.get(i).borderLeft == true)){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
            if((this.borderRight == true) && (this.listSubCellAvailable.get(i).borderRight == true)){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
            if(this.isBrother(this.listSubCellAvailable.get(i)) == true){
               resultCell = this.listSubCellAvailable.get(i);
               break;
            }
        }
        return resultCell;
    }
    //is Neighbor of this subCell and new neighbor's parent is not in queue
    public boolean isNewNeighbor(subCell sub2, ArrayList<parCell> queueParCell){
        if(this.isBrother(sub2) == false){
//            System.out.println("["+sub2.x+"]["+sub2.y+"]is neighbor of ["+this.x+"]["+this.y+"]");
            if(sub2.getParCelFromSubCell().notInQueue(queueParCell)){
                return true;
            }
        }
        return false;
    }
    
    //is brother of this subCell
    public boolean isBrother(subCell sub2){
        parCell par = this.getParCelFromSubCell();
//        System.out.println("x par = "+par.x+" y par = "+par.y);
        if(sub2.isSon(par)){
            return true;
        }
        return false;
    }
    
    /**
    * 
    * @param sub
    * @return 
    * get parCell from subCell
    */
   public parCell getParCelFromSubCell(){
       int x0  = this.getX()/2;
       int y0 = this.getY()/2;
       int trap = this.getTrap();
       parCell par = new parCell(x0, y0, trap);
       return par;
   }
   //get real parCell
   public parCell getRealParCellFromSubCell(ArrayList<parCell> listParCell, int numRow){
       int x0  = this.getX()/2;
       int y0 = this.getY()/2;
       return listParCell.get(x0 +y0*numRow);
   }
   //is Son
   public boolean isSon(parCell par){
       if((this.x == (par.x*2)) && (this.y == (par.y*2))){
           return true;
       }
       if((this.x == (par.x*2)) && (this.y == (par.y*2 + 1))){
           return true;
       }
       if((this.x == (par.x*2 + 1)) && (this.y == (par.y*2))){
           return true;
       }
       if((this.x == (par.x*2 + 1)) && (this.y == (par.y*2 + 1))){
           return true;
       }
       return false;
   }

    public int getMovingRobot() {
        return movingRobot;
    }

    public void setMovingRobot(int movingRobot) {
        this.movingRobot = movingRobot;
    }

    public int getMovingTrap() {
        return movingTrap;
    }

    public void setMovingTrap(int movingTrap) {
        this.movingTrap = movingTrap;
    }
}
