package models;

import props.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import utils.DB;

import javax.swing.table.DefaultTableModel;

public class CustomersImpl implements ICustomers {
    DB db = new DB();

    @Override
    public int customerInsert(Customer customer) {
        int status = 0;

        try {
            String sql = "insert into customer values(null,?,?,?,?,?)";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setString(1, customer.getName());
            pre.setString(2, customer.getSurname());
            pre.setString(3, customer.getEmail());
            pre.setString(4, customer.getPhone());
            pre.setString(5, customer.getAddress());
            status = pre.executeUpdate();

        } catch (Exception ex) {
            System.err.println("Customer Insert Error" + ex);
            if (ex.toString().contains("UNIQUE")) {
                status = -1;
            }
            // ex.printStackTrace();
        } finally {
            db.close();
        }

        return status;
    }

    //==================================================================================
    @Override
    public int customerDelete(int cid) {
        int status = 0;
        try {

            String sql = "Delete from Customer where cid=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1, cid);
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println("customerDelete Error: " + ex);
        } finally {
            db.close();
        }
        return status;
    }

//========================================================================================

    @Override
    public int customerUpdate(Customer customer) {
        int status = 0;
        try {
            String sql = "Update Customer set name=?,surname=?,email=?,phone=?,address=? where cid=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setString(1, customer.getName());
            pre.setString(2, customer.getSurname());
            pre.setString(3, customer.getEmail());
            pre.setString(4, customer.getPhone());
            pre.setString(5, customer.getAddress());
            pre.setInt(6,customer.getCid());
           status= pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println("customerUpdate Error: " + ex);
        } finally {
            db.close();
        }
        return status;
    }

    //======================================================================================

    @Override
    public List<Customer> customerList() {
        List<Customer> customerList = new ArrayList<>();
        try {
            String sql = "Select*from Customer order by cid desc ";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int cid = rs.getInt("cid");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Customer customer = new Customer(cid, name, surname, email, phone, address);
                customerList.add(customer);

            }
        } catch (Exception ex) {
            System.out.println("customerList Error:" + ex);
        } finally {
            db.close();
        }
        return customerList;
    }



      //===================================================================================================



    @Override
    public DefaultTableModel tablemodelCreate() {
        List<Customer> lst=new ArrayList<>();
          DefaultTableModel tableModel=new DefaultTableModel();
          tableModel.addColumn("Customer no");
          tableModel.addColumn("Name");
          tableModel.addColumn("Surname");
          tableModel.addColumn("E-Mail");
          tableModel.addColumn("Phone");
          tableModel.addColumn("Address");
          lst=customerList();
          for(Customer item:lst){
              Object[] row={item.getCid(),item.getName(),item.getSurname(),item.getEmail(),item.getPhone(),item.getAddress()};
              tableModel.addRow(row);
          }
      return tableModel;

    }
}