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
public class STC {
    public int xTB1,xTN1,xDB1,xDN1,yTB1,yDB1,yTN1,yDN1,xTB2,xTN2,xDB2,xDN2,yTB2,yDB2,yTN2,yDN2;
    public int numCol = 10;
    public int numRow = 10;
    public int trap = 0;
    public ArrayList<parCell> queueParCell = new ArrayList();
    public ArrayList<subCell> queueSubCell = new ArrayList();
    public ArrayList<parCell> listParCell = new ArrayList();
    public ArrayList<subCell> listSubCell = new ArrayList();
    
    public void makeRoute(int xStart,int yStart){
        
    }
    
    public void generateMaxtric(){
        for(int y = 0; y<this.numRow; y++){
            for(int x = 0; x<this.numCol ;x++){
                parCell par = new parCell(x, y, this.trap);
                this.listParCell.add(par);
            }
        }
        for(int y = 0; y<this.numRow*2; y++){
            for(int x = 0; x<this.numCol*2 ;x++){
                subCell sub = new subCell(x,y,this.trap);
                this.listSubCell.add(sub);
            }
        }
    }
    
    public void printMaxtric(){
       for(int y = 0; y<this.numRow*2; y++){
            for(int x = 0; x<this.numCol*2;x++){
                System.out.print("["+this.listSubCell.get(x+y*this.numRow*2).x+"]["+this.listSubCell.get(x+y*this.numRow*2).y+"]"+this.listSubCell.get(x+y*this.numRow*2).trap+"  ");
            }
            System.out.println(" ");
            if(y%2 == 0){
                for(int x = 0; x<this.numCol; x++){
                    System.out.print("     ["+x+"]["+y/2+"]"+this.listParCell.get(x+y*this.numRow/2).trap+"      ");
                }
            }
            System.out.println(" ");
        }
   }
    // chỉ số lớn nhất có thể của các subCell trong hàng đợi
    public int maxIndexOfSubCellInQueue(){
        int num = 0;
        for(int i = 0; i<this.queueParCell.size(); i++){
                num++;
        }
        num = num*4 - 1;
        return num;
    }
    //tạo 1 subCell thành vật cản, đồng thời tạo parCell của nó thành vật cản
    public void makeTrap(int xSub, int ySub){
        this.listSubCell.get(xSub + ySub*this.numRow*2).setTrap(1);
        this.changeListSubCellByParCell(this.listSubCell.get(xSub + ySub*this.numRow*2).getParCelFromSubCell(), this.listSubCell);
        this.changeListParCellBySubCel(this.listSubCell.get(xSub + ySub*this.numRow*2), this.listParCell);
   }
    /**
    * 
    * @param parCell
    * @param listSubCell 
    * change all subCell into trap by parCell
    */
   public void changeListSubCellByParCell(parCell parCell,ArrayList<subCell> listSubCell){
       int xTB = 0, xDB = 0, xTN = 0, xDN = 0, yTB = 0, yDB = 0, yTN = 0, yDN = 0;
       int x0 = parCell.getX();
       int y0 = parCell.getY();
       int trap = parCell.getTrap();
       xTB = xTN = x0*2;
       xDB = xDN = x0*2+1;
       yTB = yDB = y0*2;
       yTN = yDN = y0*2+1;
       listSubCell.get(xTB+yTB*this.numRow*2).setTrap(trap);
       listSubCell.get(xDB+yDB*this.numRow*2).setTrap(trap);
       listSubCell.get(xTN+yTN*this.numRow*2).setTrap(trap);
       listSubCell.get(xDN+yDN*this.numRow*2).setTrap(trap);
   }
   /**
    * 
    * @param sub
    * @param listParCell
    * change parCell into trap by subCell
    */
   public void changeListParCellBySubCel(subCell sub, ArrayList<parCell> listParCell){
       int x0  = sub.getX()/2;
       int y0 = sub.getY()/2;
       int trap = sub.getTrap();
       for(int i = 0; i<listParCell.size(); i++){
           if(listParCell.get(i).x == x0 && listParCell.get(i).y == y0){
               listParCell.get(i).setTrap(trap);
           }
       }
   }
   
   public void makeBorderSub(subCell sub1, subCell sub2){
       makeBorder(this.listParCell.get(sub1.getParCelFromSubCell().x + sub1.getParCelFromSubCell().y*this.numRow), this.listParCell.get(sub2.getParCelFromSubCell().x + sub2.getParCelFromSubCell().y*this.numRow));
   }
   
   //make line parCell and make border subCell
    public void makeBorder(parCell par1, parCell par2){
        xTB1 = xTN1 = par1.x*2;
        xDB1 = xDN1 = par1.x*2+1;
        yTB1 = yDB1 = par1.y*2;
        yTN1 = yDN1 = par1.y*2+1;
        xTB2 = xTN2 = par2.x*2;
        xDB2 = xDN2 = par2.x*2+1;
        yTB2 = yDB2 = par2.y*2;
        yTN2 = yDN2 = par2.y*2+1;
        if((par1.x == par2.x) && (par1.y == par2.y)){// in the same parCell
            //do nothing, robot go on counterclockwise order
        }else{// from this parCell to other
            if(par1.x == par2.x){
                if(par1.y > par2.y){
                    par1.setEdgeTop(true);
                    this.listSubCell.get(xTB1 + yTB1*2*this.numRow).setBorderRight(true);
                    this.listSubCell.get(xDB1 + yDB1*2*this.numRow).setBorderLeft(true);
                    par2.setEdgeBottom(true);
                    this.listSubCell.get(xTN2 + yTN2*2*this.numRow).setBorderRight(true);
                    this.listSubCell.get(xDN2 + yDN2*2*this.numRow).setBorderLeft(true);
                }else{
                    par1.setEdgeBottom(true);
                    this.listSubCell.get(xTN1 + yTN1*2*this.numRow).setBorderRight(true);
                    this.listSubCell.get(xDN1 + yDN1*2*this.numRow).setBorderLeft(true);
                    par2.setEdgeTop(true);
                    this.listSubCell.get(xTB2 + yTB2*2*this.numRow).setBorderRight(true);
                    this.listSubCell.get(xDB2 + yDB2*2*this.numRow).setBorderLeft(true);
                }
            }
            if(par1.y == par2.y){
                if(par1.x > par2.x){
                    par1.setEdgeLeft(true);
                    this.listSubCell.get(xTB1 + yTB1*2*this.numRow).setBorderBottom(true);
                    this.listSubCell.get(xTN1 + yTN1*2*this.numRow).setBorderTop(true);
                    par2.setEdgeRight(true);
                    this.listSubCell.get(xDB2 + yDB2*2*this.numRow).setBorderBottom(true);
                    this.listSubCell.get(xDN2 + yDN2*2*this.numRow).setBorderTop(true);
                }else{
                    par1.setEdgeRight(true);
                    this.listSubCell.get(xDB1 + yDB1*2*this.numRow).setBorderBottom(true);
                    this.listSubCell.get(xDN1 + yDN1*2*this.numRow).setBorderTop(true);
                    par2.setEdgeLeft(true);
                    this.listSubCell.get(xTB2 + yTB2*2*this.numRow).setBorderBottom(true);
                    this.listSubCell.get(xTN2 + yTN2*2*this.numRow).setBorderTop(true);
                }
            }
        }
    }

    public subCell findFirstSubCellAvailable(){
        subCell resultCell = null;
        for(int i = 0;i<this.listSubCell.size(); i++){
            if(this.listSubCell.get(i).trap == 0){
                return this.listSubCell.get(i);
            }
        }
        return resultCell;
    }
}
