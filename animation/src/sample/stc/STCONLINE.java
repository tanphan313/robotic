/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.stc;

import java.util.ArrayList;

/**
 *
 * @author phan
 */
public class STCONLINE extends STC{
    protected subCell nextSubCell;
    public int order = 0;
    public static void main(String[] args) {
        STCONLINE cstt = new STCONLINE();
        cstt.generateMaxtric();//make maxtric, make listSubCell and listParCell
        
        cstt.makeTrap(0, 0);
        cstt.makeTrap(2, 0);
        cstt.makeTrap(7, 0);
        cstt.makeTrap(8, 2);
        cstt.makeTrap(6, 8);
        cstt.printMaxtric();
        
//        for(subCell sub: cstt.listSubCell){
//            if(sub.trap  == 0)
//            sub.setListSubCellAvailable(sub.getListAvailable(cstt.listSubCell, cstt.queueSubCell, cstt.numRow, cstt.numCol));
//        }
//        for(subCell sub: cstt.listSubCell){
//            if(sub.trap  == 0){
//                System.out.println("list sub cell available for "+"["+sub.x+"]["+sub.y+"]");
//                for(int i = 0; i<sub.listSubCellAvailable.size(); i++){
//                    System.out.println("subcel available:"+i+" ["+sub.listSubCellAvailable.get(i).x+"]["+sub.listSubCellAvailable.get(i).y+"]");
//                }
//            }
//        }
        
        cstt.makeRoute(5, 0);
    }
    
    @Override
    public void makeRoute(int xStart, int yStart){
        //next subCell
        this.nextSubCell = this.listSubCell.get(xStart +yStart*2*this.numRow).findNextSubCellOnLine(this.listSubCell, this.queueSubCell, this.queueParCell, this.numRow, this.numCol);
        //make curent subCell as old subCell
        this.queueSubCell.add(this.listSubCell.get(xStart +yStart*2*this.numRow));
        //if it's parent is not in queue, add to queue
        if(this.listSubCell.get(xStart +yStart*2*this.numRow).getRealParCellFromSubCell(this.listParCell, this.numRow).notInQueue(this.queueParCell)){
            this.queueParCell.add(this.listSubCell.get(xStart +yStart*2*this.numRow).getRealParCellFromSubCell(this.listParCell, this.numRow));
        }
        System.out.println("In order subCell "+order+": ["+xStart+"]["+yStart+"]");
        order++;
        if(this.nextSubCell == null){
            return;
        }
        //make tree edge
        this.makeBorderSub(this.listSubCell.get(xStart +yStart*2*this.numRow), this.nextSubCell);
        //excute STC
        //dat luong
        makeRoute(this.nextSubCell.x, this.nextSubCell.y);
    }
    
}
