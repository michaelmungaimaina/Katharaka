package mich.gwan.katharaka.model.spanner;

public class Spanner {

    String spanner_category;
    int spanner_id;
    //String spanner_qnty;
    int spanner_bp;
    int spanner_sp;

    public String getSpanner_category() {
        return spanner_category;
    }
    public void setSpanner_category(String spanner_category) {
        this.spanner_category = spanner_category;
    }
    public int getSpanner_id(){
        return spanner_id;
    }
    public void setSpanner_id(int spanner_id) {
        this.spanner_id = spanner_id;
    }

    public int getSpanner_bp() {
        return spanner_bp;
    }

    public void setSpanner_bp(int spanner_bp) {
        this.spanner_bp = spanner_bp;
    }

    public int getSpanner_sp() {
        return spanner_sp;
    }

    public void setSpanner_sp(int spanner_sp) {
        this.spanner_sp = spanner_sp;
    }

    /**public String getSpanner_qnty() {
        return spanner_qnty;
    }

    public void setSpanner_qnty(String spanner_qnty) {
        this.spanner_qnty = spanner_qnty;
    }
     **/
}
