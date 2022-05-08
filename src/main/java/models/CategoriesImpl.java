package models;
import utils.DB;
import props.Categories;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoriesImpl implements ICategories{
    DB db=new DB();
    List<Categories>  listCategory=new ArrayList<>();

    @Override

    public List<Categories> categoryList() {

        try {
            String sql="select * from categories";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            ResultSet rs= pre.executeQuery();
            listCategory.clear();
            while (rs.next()){
                int catid=rs.getInt("catid");
                String categoryname= rs.getString("categoryname");
                String categorydescription=rs.getString("categorydescription");
                Categories cat=new Categories(catid,categoryname,categorydescription);
                listCategory.add(cat);
            }
        }catch (Exception ex){
            System.err.println("categoryList Error"+ex);
        }finally {
            db.close();
        }
        return listCategory;
    }


    @Override
    public DefaultTableModel categoryTable() {
       listCategory= categoryList();
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("Category Id");
        model.addColumn("Category Name");
        model.addColumn("Category Description");

        for(Categories item: listCategory){
            Object[] row={item.getCatid(),item.getCatecoryname(),item.getCategoryDescription()};
            model.addRow(row);
        }

        return model;
    }

    @Override
    public DefaultComboBoxModel cmbmodel() {

        int lenghtLs=listCategory.size();
        Categories[] categories = listCategory.toArray(new Categories[lenghtLs]);

        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<>(categories);
        return comboBoxModel;
    }

    @Override
    public DefaultComboBoxModel cmbmodel(int catid) {
        List<Categories> sublist=new ArrayList<>();
        for(Categories item :listCategory){
            if(item.catid==catid){
                sublist.add(item);
            }
        }

        Categories[] categories = sublist.toArray(new Categories[0]);
        DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel<>(categories);
        return comboBoxModel;
    }



    @Override
    public int categoryInsert(Categories categories) {
        int status=0;
        try {
            String sql="insert into categories  values(null,?,?)";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,categories.getCatecoryname());
            pre.setString(2,categories.getCategoryDescription());

            status=pre.executeUpdate();

        }catch (Exception ex){
            System.err.println("categoryInsert Error:"+ex);
            if (ex.toString().contains("UNIQUE")){
                status=-1;
            }
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public int categoryUpdate(Categories categories) {
        int status=0;
        try{
            String sql="update categories set categoryname=?,categorydescription=? where catid=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,categories.getCatecoryname());
            pre.setString(2,categories.getCategoryDescription());
            pre.setInt(3,categories.getCatid());
            status=pre.executeUpdate();
        }catch (Exception ex){
            System.err.println("categoryUpdate Error:"+ex);
        }finally {
            db.close();
        }
        return status;
    }

    @Override
    public int categoryDelete(int catid) {
        int status=0;
        try {
            String sql="delete from categories where catid=?";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setInt(1,catid);
            status= pre.executeUpdate();
        }catch (Exception ex){
            System.err.println("categoryDelete Error:"+ ex);
        }finally {
            db.close();
        }
        return status;
    }


}
