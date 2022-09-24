package mich.gwan.katharaka.model.welding;

public class Welding {

    String welding_category;
    int welding_id;
    //String welding_qnty;
    int welding_bp;
    int welding_sp;

    public String getWelding_category() {
        return welding_category;
    }
    public void setWelding_category(String welding_category) {
        this.welding_category = welding_category;
    }
    public int getWelding_id(){
        return welding_id;
    }
    public void setWelding_id(int welding_id) {
        this.welding_id = welding_id;
    }

    public int getWelding_bp() {
        return welding_bp;
    }

    public void setWelding_bp(int welding_bp) {
        this.welding_bp = welding_bp;
    }

    public int getWelding_sp() {
        return welding_sp;
    }

    public void setWelding_sp(int welding_sp) {
        this.welding_sp = welding_sp;
    }

   /** public String getWelding_qnty() {
        return welding_qnty;
    }

    public void setWelding_qnty(String welding_qnty) {
        this.welding_qnty = welding_qnty;
    }
    **/
}
