package models;

import props.Products;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IProducts {

    int productUpdate(Products products);
    int productInsert(Products products);
    int productDelete(int pid);
    List<Products> productsList();
    DefaultTableModel productTableModel();

}

