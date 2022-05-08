package models;

import props.Categories;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public interface ICategories {
    List<Categories> categoryList();
    DefaultTableModel categoryTable();

    DefaultComboBoxModel cmbmodel();
    DefaultComboBoxModel cmbmodel(int catid);

    int categoryInsert(Categories categories);
    int categoryUpdate(Categories categories);
    int categoryDelete(int catid);

}
