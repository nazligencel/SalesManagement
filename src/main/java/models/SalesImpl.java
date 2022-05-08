package models;

import props.*;
import utils.DB;

import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SalesImpl implements ISales{
    DB db=new DB();



   private List<Basket> lstReport=new ArrayList<>();

   public String dateFirst="";
   public String dateSecond="";
    public SalesImpl() {

    }//to do:

    public SalesImpl(String dateFirst, String dateSecond) {
        this.dateFirst = dateFirst;
        this.dateSecond = dateSecond;
        lstReport=baketReportList(dateFirst,dateSecond);
    }

    public void setLstReport(List<Basket> lstReport) {
        this.lstReport = lstReport;
    }

    public List<Basket> getLstReport() {
        return lstReport;
    }

    @Override
    public int salesInsert(Sales sales) {

        int status=0;
        try{
            String sql="insert into sales(sid,cid,total,date) values (null,?,?,?)";
            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setInt(1,sales.getCid());
            pre.setDouble(2,sales.getTotal());
            pre.setString(3,sales.getDate());
            status=pre.executeUpdate();

        }catch (Exception ex){
            System.out.println("salesInsert Error "+ex);
        }finally {
            db.close();
        }
  return status;
    }




    @Override
    public int updateBasket(List<Basket> baskets) {

        int totalrow=0;

        try {

            for(Basket item:baskets){
            String sql = "Update basket set status=1 where bid=?";
            PreparedStatement pre = db.connect().prepareStatement(sql);
            pre.setInt(1,item.getBid());
           int status= pre.executeUpdate();
            totalrow=totalrow+status;

            }
        } catch (Exception ex) {
            System.out.println("customerUpdate Error: " + ex);
        } finally {
            db.close();
        }
        return totalrow;
    }

    @Override
    public List<Basket> baketReportList(String datefirst,String datesecond) {
        List<Basket> lstbasketReport=new ArrayList<>();
        try {
            String sql="select bid,b.cid,p.pid,c.name,c.surname ,p.productname,b.count,p.saleprice,purchaseprice,date,status,categoryname,cat.catid from"+
           " basket b join products p on b.pid = p.pid join customer c on b.cid = c.cid join categories cat on cat.catid=p.catid where status=1 and strftime('%Y-%m-%d', date)" +
                    " between ? and ? order by strftime('%Y-%m-%d', date) desc ";

            PreparedStatement pre=db.connect().prepareStatement(sql);
            pre.setString(1,datefirst);
            pre.setString(2,datesecond);

            ResultSet rs=pre.executeQuery();
            while(rs.next()){
                int bid= rs.getInt(1);
                int cid= rs.getInt(2);
                int pid= rs.getInt(3);
                String name=rs.getString(4);
                String surname=rs.getString(5);
                String productName= rs.getString(6);
                int count=rs.getInt(7);
                Double salePrice=rs.getDouble(8);
                Double purchasePrice= rs.getDouble(9);
                String date=rs.getString(10);
                int status=rs.getInt(11);
                String categoryname=rs.getString(12);
                int catid=rs.getInt("catid");
             //   System.out.println(catid);


                Categories cat=new Categories(catid,categoryname,"");
                Customer c=new Customer(cid,name,surname,null,null,null);
                Products p=new Products(productName,purchasePrice,salePrice,cat);
                Basket b=new Basket(bid,cid,pid,date,count,status,p,c);
                lstbasketReport.add(b);
            }

        }catch (Exception ex){
            System.out.println("basketList error: "+ex);
        }finally {
            db.close();
        }
        return lstbasketReport;
    }

    public boolean isContainCustomer(int cid){
        List<Basket> lst=baketReportList("1900-12-01","2500-12-01");
        Boolean isContain=false;
        for(Basket item:lst){
            if(item.getCid()==cid){
                isContain=true;
                break;
            }
        }
        return isContain;
    }
    public boolean isContainCategories(int catid){
        List<Basket> lst=baketReportList("1900-12-01","2500-12-01");

        Boolean isContain=false;
        for(Basket item:lst){

            if(item.getProduct().getCategory().getCatid()==catid){
                isContain=true;

                break;
            }
        }
        return isContain;
    }
    public boolean isContainProduct(int pid){
        List<Basket> lst=baketReportList("1900-12-01","2500-12-01");
        Boolean isContain=false;
        for(Basket item:lst){
            if(item.getPid()==pid){
                isContain=true;
                break;
            }
        }
        return isContain;
    }

    @Override
    public DefaultTableModel bakettableModel_Report(String data ,String radiobtn) {


       DefaultTableModel dm=new DefaultTableModel();
       dm.addColumn("First Name");
       dm.addColumn("Second Name");
       dm.addColumn("Category Name");
       dm.addColumn("Product Name");
       dm.addColumn("Sale Quantity");
       dm.addColumn("Sale Price");
       dm.addColumn("Sale Date");
       dm.addColumn("Total");
       List<Basket> sublist =new ArrayList<>();
       List<Double> sublistTotal =new ArrayList<>();

        for(Basket item: lstReport) {
            if ( !data.equals("") &&!data.equals(null) && radiobtn.equals("customer")) {

                if (item.getCustomer().getName().toLowerCase(Locale.ROOT).contains(data.toLowerCase()) ||
                     item.getCustomer().getSurname().toLowerCase(Locale.ROOT).contains(data.toLowerCase())) {
                     sublist.add(item);
                }

            } else if (!data.equals("") && !data.equals(null) && radiobtn.equals("category") ) {
                if (item.getProduct().getCategory().categoryname.toLowerCase(Locale.ROOT).contains(data.toLowerCase())) {
                    sublist.add(item);
                }
            } else if (!data.equals("") &&!data.equals(null) && radiobtn.equals("product")) {
                if (item.getProduct().getProductname().toLowerCase(Locale.ROOT).contains(data.toLowerCase())) {
                    sublist.add(item);
                }

            }else{
                sublist.add(item);

            }
        }

        for(Basket item:sublist){
            Object[] row={item.getCustomer().getName(),item.getCustomer().getSurname(),
                    item.getProduct().getCategory().getCatecoryname(),item.getProduct().getProductname(),
                    item.getCount(),item.getProduct().getSaleprice(),item.getDate(),
                    item.getProduct().getSaleprice()*item.getCount()};
            dm.addRow(row);
        }
        return dm;
    }
    public double CalculateTotalProfit(List<Basket> baskets){
       double totalProfit=0.0;
          for (Basket item:baskets){
              double itemProfit =(item.getProduct().getSaleprice()-item.getProduct().getPurchaseprice())*item.getCount();
              totalProfit=totalProfit+ itemProfit;
          }
        return totalProfit;
    }

}
