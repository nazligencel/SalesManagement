package models;

import props.Basket;
import props.Products;
import props.Sales;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface ISales {
    int salesInsert(Sales sales);

    //int salesUpdate(Sales sales);

    int updateBasket(List<Basket> baskets);
    List<Basket> baketReportList(String datefirst,String datesecond);
    DefaultTableModel bakettableModel_Report(String data,String radiobtn);


}

