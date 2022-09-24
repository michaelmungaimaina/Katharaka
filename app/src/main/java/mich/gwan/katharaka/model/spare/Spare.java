package mich.gwan.katharaka.model.spare;

public class Spare {

    //Oils attributes
    String spare_category;
    int spare_id;
    //String spare_qnty;
    int spare_bp;
    int spare_sp;

    //Oils accessors and modifiers


    public String getSpare_category() {
        return spare_category;
    }
    public void setSpare_category(String spare_category) {
        this.spare_category = spare_category;
    }
    public int getSpare_id(){
        return spare_id;
    }
    public void setSpare_id(int spare_id) {
        this.spare_id = spare_id;
    }

    public int getSpare_bp() {
        return spare_bp;
    }

    public void setSpare_bp(int spare_bp) {
        this.spare_bp = spare_bp;
    }

    public int getSpare_sp() {
        return spare_sp;
    }

    public void setSpare_sp(int spare_sp) {
        this.spare_sp = spare_sp;
    }

   /** public String getSpare_qnty() {
        return spare_qnty;
    }
    //public void setSpare_qnty(String spare_qnty) {
        this.spare_qnty = spare_qnty;
    }**/
}
