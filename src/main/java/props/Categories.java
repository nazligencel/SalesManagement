package props;

public class Categories {
    public int catid;
    public String categoryname;
    public String categorydescription;

    public Categories() {
    }

    public Categories(int catid, String catecoryname,String categorydescription) {
        this.catid = catid;
        this.categoryname = catecoryname;
        this.categorydescription=categorydescription;
    }

    public int getCatid() {
        return catid;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }

    public String getCatecoryname() {
        return categoryname;
    }

    public void setCatecoryname(String catecoryname) {
        this.categoryname = catecoryname;
    }

    public String getCategoryDescription() {
        return categorydescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categorydescription = categoryDescription;
    }

    @Override
    public String toString() {

           return  this.categoryname;
    }
}
