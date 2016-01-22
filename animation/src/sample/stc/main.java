/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.stc;

/**
 *
 * @author phan
 */
public class main {
    public static void main(String[] args) {
        
        STCOFFLINE  cstt = new STCOFFLINE();
        cstt.generateMaxtric();//make maxtric, make listSubCell and listParCell
        
        cstt.makeTrap(0, 0);
        cstt.makeTrap(1, 0);
        cstt.makeTrap(2, 0);
        cstt.makeTrap(7, 0);
        cstt.makeTrap(8, 2);
        cstt.makeTrap(6, 6);
        cstt.makeTrap(6, 8);
        //mỗi parCell phải biết được ai là hàng xóm của mình thông qua hàm này
        cstt.getNeighborOfParCell(cstt.listParCell);
        //chạy DFS sau khi biết hàng xóm, [4][0] là tọa độ subcell đầu tiên đưa vào
        cstt.DFS(cstt.listParCell.get(4/2 + (0/2)*cstt.numRow));
        
        cstt.printMaxtric();
        
//        for(int i = 0; i<cstt.listSubCell.size(); i++){
//            System.out.println("subcel ["+cstt.listSubCell.get(i).x+"]["+cstt.listSubCell.get(i).y+"]: top["+cstt.listSubCell.get(i).borderTop+"] "
//                    + "bottom["+cstt.listSubCell.get(i).borderBottom+"] "
//                    + "left["+cstt.listSubCell.get(i).borderLeft+"] "
//                    + "right["+cstt.listSubCell.get(i).borderRight+"]");
//        }
        
        //tạo lịch trình từ điểm xuất phát là (4,0)
        cstt.makeRoute(4, 0);
        
        System.out.println("list parcell: ");
        for(int i = 0; i<cstt.queueParCell.size(); i++){
            System.out.println("order "+i+"["+cstt.queueParCell.get(i).x+"]["+cstt.queueParCell.get(i).y+"]");
        }
        System.out.println(" ");
        System.out.println("list subcell: ");
        for(int i = 0;  i<cstt.queueSubCell.size();i++){
            System.out.println("order "+i+"["+cstt.queueSubCell.get(i).x+"]["+cstt.queueSubCell.get(i).y+"]");
        }
    }
}
