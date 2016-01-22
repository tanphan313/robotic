package sample.stc;

import java.util.ArrayList;

public class STCOFFLINE extends STC{
    
    public static void main(String[] args) {
      //   TODO code application logic here
    
    }
    
    public void DFS(parCell par){
        this.queueParCell.add(par);
        for(int i = 0; i< par.listNeighbor.size();i++){
            if(par.listNeighbor.get(i).notInQueue(this.queueParCell)){
                System.out.println("make line from ["+par.x+"]["+par.y+"] to ["+par.listNeighbor.get(i).x+"]["+par.listNeighbor.get(i).y+"]");
                this.makeBorder(par, par.listNeighbor.get(i));
                DFS(par.listNeighbor.get(i));
            }
        }
    }
    //tạo đường đi, đưa các subCell vào hàng đợi
    @Override
    public void makeRoute(int xStart, int yStart){
        int maxIndex =  this.maxIndexOfSubCellInQueue();
        this.queueSubCell.add(this.listSubCell.get(xStart + yStart*this.numRow*2));
        subCell result = this.listSubCell.get(xStart + yStart*this.numRow*2).findNextSubCellOffLine(this.listSubCell, this.queueSubCell, this.numRow, this.numCol);
        this.queueSubCell.add(result);
        for(int i = 0; i<maxIndex-1; i++){
            result = result.findNextSubCellOffLine(this.listSubCell, this.queueSubCell, this.numRow, this.numCol);
            this.queueSubCell.add(result);
        }
    }
    
    //lấy danh sách các hàng xóm của parCell
    public void getNeighborOfParCell(ArrayList<parCell> listParCell){
       for(int i = 0; i<listParCell.size(); i++){
            listParCell.get(i).getNeighbor(listParCell);
        }
   }
   
}
