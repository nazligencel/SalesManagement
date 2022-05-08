package models;

import props.Categories;
import props.Products;
import utils.DB;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ProductsImpl implements IProducts {

    DB db = new DB();



    @Override
    public int productUpdate(Products products) {
        int status = 0;

        try {
            String sql = "update products set catid=? , productname=?,purchaseprice=?, stockquantity=?, saleprice=? where pid=? ";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1, products.getCatid());
            pre.setString(2, products.getProductname());
            pre.setDouble(3, products.getPurchaseprice());
            pre.setInt(4, products.getStockquantity());
            pre.setDouble(5, products.getSaleprice());
            pre.setInt(6,products.getPid());
            status = pre.executeUpdate();


        } catch (Exception ex) {
            System.out.println("update error" + ex);
            ex.printStackTrace();
        } finally {
            db.close();
        }
        return status;

    }

    @Override
    public int productInsert(Products products) {

        int status = 0;
        try {
            String sql = "INSERT INTO products values(null,?,?,?,?,?)";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1, products.getCatid());
            pre.setString(2, products.getProductname());
            pre.setDouble(3, products.getPurchaseprice());
            pre.setInt(4, products.getStockquantity());
            pre.setDouble(5, products.getSaleprice());
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Insert error" + ex);
        } finally {
            db.close();
        }
        return status;
    }

    @Override
    public int productDelete(int pid) {
        int status = 0;
        try {
            String sql = "delete from products where pid=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1, pid);
            status=pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println("delete error" + ex);

        } finally {
            db.close();
        }
        return status;

    }

    @Override
    public List<Products> productsList() {
        List<Products> productsList = new ArrayList<>();
        try {
            String sql = "Select * from products  ";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {

                int pid = rs.getInt("pid");
                int catid = rs.getInt("catid");
                String productname = rs.getString("productname");
                double purchaseprice = rs.getDouble("purchaseprice");
                int stockquantity = rs.getInt("stockquantity");
                double saleprice = rs.getDouble("saleprice");
                Products products = new Products(pid, catid, productname, purchaseprice, stockquantity, saleprice);
                productsList.add(products);

            }
        } catch (Exception ex) {
            System.out.println("ProductsList Error:" + ex);
        } finally {
            db.close();
        }
        return productsList;
    }

    @Override
    public DefaultTableModel productTableModel() {

        DefaultTableModel md = new DefaultTableModel();
        md.addColumn("Pid");
        md.addColumn("catId");
        md.addColumn("Product Name ");
        md.addColumn("Purchase Price");
        md.addColumn("Stock Quantity");
        md.addColumn("Sale Price");


        for (Products item : productsList()) {
            Object[] row = {item.getPid(),
                    item.getCatid(),
                    item.getProductname(),
                    item.getPurchaseprice(),
                    item.getStockquantity(),
                    item.getSaleprice()};
            md.addRow(row);
        }
        return md;
    }
}


