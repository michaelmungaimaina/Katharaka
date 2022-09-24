package mich.gwan.katharaka.model.oil;

public class Oil {
    //Oils attributes
    String oil_category;
    int oil_id;
    //String oil_qnty;
    int oil_bp;
    int oil_sp;

    //Oils accessors and modifiers


    public String getOil_category() {
        return oil_category;
    }
    public void setOil_category(String oil_category) {
        this.oil_category = oil_category;
    }
    public int getOil_id(){
        return oil_id;
    }
    public void setOil_id(int oil_id) {
        this.oil_id = oil_id;
    }

    public int getOil_bp() {
        return oil_bp;
    }

    public void setOil_bp(int oil_bp) {
        this.oil_bp = oil_bp;
    }

    public int getOil_sp() {
        return oil_sp;
    }

    public void setOil_sp(int oil_sp) {
        this.oil_sp = oil_sp;
    }


    //public void setOil_qnty(String oil_qnty) {
       // this.oil_qnty = oil_qnty;
    //}
}
