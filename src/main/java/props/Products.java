package props;
public class Products {
    private int pid;
    private int catid;
    private String productname;
    private double purchaseprice;
    private int stockquantity;
    private double saleprice;
    private Categories category;

    public Categories getCategory() {
        return category;

    }

    public void setCategory(Categories category) {
        this.category = category;
    }




    public Products(int pid, String productname, int stockquantity, double saleprice) {
        this.pid = pid;
        this.productname = productname;
        this.stockquantity = stockquantity;
        this.saleprice = saleprice;
    }


    public Products(String productname, double purchaseprice, double saleprice,int stockquantity) {
        this.productname = productname;
        this.purchaseprice = purchaseprice;
        this.saleprice = saleprice;
    }
    public Products(String productname, double purchaseprice, double saleprice,Categories category) {
        this.productname = productname;
        this.purchaseprice = purchaseprice;
        this.saleprice = saleprice;
        this.category=category;
    }
    public Products() {
    }

    public Products(int pid, int catid, String productname, double purchaseprice, int stockquantity, double saleprice) {
        this.pid = pid;
        this.catid = catid;
        this.productname = productname;
        this.purchaseprice = purchaseprice;
        this.stockquantity = stockquantity;
        this.saleprice = saleprice;

    }

    public Products(int pid, int catid, String productname, double purchaseprice, int stockquantity, double saleprice, Categories category) {
        this.pid = pid;
        this.catid = catid;
        this.productname = productname;
        this.purchaseprice = purchaseprice;
        this.stockquantity = stockquantity;
        this.saleprice = saleprice;
        this.category = category;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public double getPurchaseprice() {
        return purchaseprice;
    }

    public void setPurchaseprice(double purchaseprice) {
        this.purchaseprice = purchaseprice;
    }

    public int getStockquantity() {
        return stockquantity;
    }

    public void setStockquantity(int stockquantity) {
        this.stockquantity = stockquantity;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    @Override
    public String toString() {
        return "Products{" +
                "pid=" + pid +
                ", catid=" + catid +
                ", productname='" + productname + '\'' +
                ", purchaseprice=" + purchaseprice +
                ", stockquantity=" + stockquantity +
                ", saleprice=" + saleprice +
                '}';
    }
}
