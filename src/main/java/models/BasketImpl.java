package models;

import props.Basket;
import props.Customer;
import props.Products;
import utils.DB;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BasketImpl implements IBasket {
   DB db=new DB();

   CustomersImpl customersImpl=new CustomersImpl();
   ProductsImpl productImpl=new ProductsImpl();

   public List<Products> lsProduct =new ArrayList<>();

    List<Customer> lsCustomer=new ArrayList<>();
    List<Customer> lsCustomerSearch =new ArrayList<>();


    public BasketImpl() {

    lsCustomer= customersImpl.customerList();
    lsCustomerSearch =lsCustomer;
    lsProduct=productImpl.productsList();

    }


    @Override
    public int insertBasket(Basket basket) {
        int status=0;
        try{
            String sql="insert into basket (bid,cid,pid,date,count,status) values (null,?,?,?,?,0)";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setInt(1,basket.getCid());
            pre.setInt(2,basket.getPid());
            pre.setString(3,basket.getDate());
            pre.setInt(4,basket.getCount());
            status=pre.executeUpdate();

        }catch (Exception ex){

        }finally {
            db.close();
        }


        return status;
    }

    @Override
    public int deleteBasket(int bid) {

        int status = 0;
        try {
            String sql = "Delete from basket where bid=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1, bid);
            status = pre.executeUpdate();
        } catch (Exception ex) {
            System.out.println("customerDelete Error: " + ex);
        } finally {
            db.close();
        }
        return status;
    }



    @Override
    public List<Basket> basketList(int customerId) {
        List<Basket> lstbasket=new ArrayList<>();
        try {
            String sql="select bid,cid,b.pid,p.productname,p.saleprice," +
                    "b.count,purchaseprice,date,status ,stockquantity from " +
                    "basket b join products p on b.pid = p.pid where cid=? and status=0";

        PreparedStatement pre=db.connect().prepareStatement(sql);
        pre.setInt(1, customerId);
        ResultSet rs=pre.executeQuery();
        while(rs.next()){
            int bid= rs.getInt(1);
            int cid=rs.getInt(2);
            int pid=rs.getInt(3);
            String productName= rs.getString(4);
            Double salePrice=rs.getDouble(5);
            int count=rs.getInt(6);
            Double purchasePrice= rs.getDouble(7);
            String date=rs.getString(8);
            int status1=rs.getInt(9);
            int stock=rs.getInt(10);
            Products p=new Products(productName,purchasePrice,salePrice,stock);
            Basket b=new Basket(bid,cid,pid,date,count,status1,p);
            lstbasket.add(b);
        }

        }catch (Exception ex){
            System.out.println("basketList error: "+ex);
        }finally {
            db.close();
        }
        return lstbasket;
    }




    @Override
    public DefaultTableModel customerTableModelBasket(String data) {

        lsCustomer = lsCustomerSearch;
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Cid");
        model.addColumn("Name");
        model.addColumn("Surname");
        model.addColumn("E-mail");
        model.addColumn("Phone");
        model.addColumn("Address");

        if (data != null && !data.equals("")) {
            data=data.toLowerCase();
            // arama sonuçlarını gönder
            List<Customer> subLs = new ArrayList<>();
            for (Customer item : lsCustomer) {
                if (item.getName().toLowerCase(Locale.ROOT).contains(data)
                        || item.getSurname().toLowerCase(Locale.ROOT).contains(data)
                        || item.getEmail().toLowerCase(Locale.ROOT).contains(data)
                        || item.getPhone().toLowerCase(Locale.ROOT).contains(data)
                        || item.getAddress().toLowerCase(Locale.ROOT).contains(data))
                {
                    subLs.add(item);
                }
            }
            lsCustomer = subLs;
        }

        for (Customer item : lsCustomer) {
            Object[] row = {item.getCid(), item.getName(), item.getSurname(), item.getEmail(), item.getPhone(), item.getAddress()};
            model.addRow(row);
        }

        return model;
    }

    @Override
    public DefaultTableModel ProductSale_TableModel(int catid, String data) {
        DefaultTableModel md = new DefaultTableModel();
        md.addColumn("Pid");
        md.addColumn("catId");
        md.addColumn("Product Name ");
        md.addColumn("Purchase Price");
        md.addColumn("Stock Quantity");
        md.addColumn("Sale Price");
        List<Products> subList = new ArrayList<>();

        for (Products item : lsProduct) {
            if (item.getCatid() == catid && (!data.equals(null) || !data.equals(""))) {
                if (item.getProductname().toLowerCase(Locale.ROOT).contains(data.toLowerCase())) {
                    subList.add(item);
                }
            }else if (item.getCatid() == catid && data.equals("")) {
                subList.add(item);
            }

        }

        for (Products item : subList) {
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




    @Override
    public DefaultTableModel basketTableModel(int customerId) {
        List<Basket> basketList=new ArrayList<>();
        basketList=basketList(customerId);
        DefaultTableModel baskettblModel=new DefaultTableModel();
        baskettblModel.addColumn("Basket No");
        baskettblModel.addColumn("Customer No");
        baskettblModel.addColumn("Product No");
        baskettblModel.addColumn("Product Name");
        baskettblModel.addColumn("Quantity");
        baskettblModel.addColumn("Price");
        baskettblModel.addColumn("Stock");

        for(Basket item:basketList){
            Object[] row={item.getBid(),
                    item.getCid(),
                    item.getPid(),
                    item.getProduct().getProductname(),
                    item.getCount(),
                    item.getProduct().getSaleprice(),
                    item.getProduct().getStockquantity()};
                    baskettblModel.addRow(row);

        }
      return baskettblModel;

    }


}


