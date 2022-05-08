package models;

import props.Basket;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IBasket {
    int insertBasket(Basket basket);
    int deleteBasket(int bid);

    List<Basket> basketList(int customerId);



    DefaultTableModel customerTableModelBasket(String data);
    DefaultTableModel ProductSale_TableModel(int catid,String data);
    DefaultTableModel basketTableModel(int customerId);

}
