package props;

public class Sales {
    private int sid;
    private int cid;
    private double total;
    private String date;
    private Customer customer;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Sales() {
    }

    public Sales(int sid, int cid,double total, String date) {
        this.sid = sid;
        this.cid = cid;
        this.total=total;
        this.date = date;
    }
    public Sales(int sid, int cid,double total, String date, Customer customer) {
        this.sid = sid;
        this.cid = cid;
        this.total=total;
        this.date = date;
        this.customer = customer;
    }
    public int getSid() {
        return sid;
    }


    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "sid=" + sid +
                ", cid=" + cid +
                ", date='" + date + '\'' +
                '}';
    }
}
