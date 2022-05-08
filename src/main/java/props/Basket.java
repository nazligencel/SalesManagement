package props;

public class Basket {

    private int bid;
    private int cid;
    private int pid;
    private String date;
    private int count;
    private int status;
    Products product;
    Customer customer;
    public Basket() {
    }

    public Basket(int bid, int cid, int pid, String date, int count, int status, Products product) {
        this.bid = bid;
        this.cid = cid;
        this.pid = pid;
        this.date = date;
        this.count = count;
        this.status = status;
        this.product=product;
    }
    public Basket(int bid, int cid, int pid, String date, int count, int status, Products product,Customer customer) {
        this.bid = bid;
        this.cid = cid;
        this.pid = pid;
        this.date = date;
        this.count = count;
        this.status = status;
        this.product=product;
        this.customer=customer;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Basket{" +
                "sid=" + bid +
                ", cid=" + cid +
                ", pid=" + pid +
                ", date='" + date + '\'' +
                ", count=" + count +
                ", status=" + status +
                ", product=" + product +
                ", customer=" + customer +
                '}';
    }
}

