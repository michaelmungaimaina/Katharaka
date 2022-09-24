package mich.gwan.katharaka.model.powersaw;

public class Powersaw {

    String powersaw_category;
    int powersaw_id;
    //String powersaw_qnty;
    int powersaw_bp;
    int powersaw_sp;

    public String getPowersaw_category() {
        return powersaw_category;
    }
    public void setPowersaw_category(String powersaw_category) {
        this.powersaw_category = powersaw_category;
    }
    public int getPowersaw_id(){
        return powersaw_id;
    }
    public void setPowersaw_id(int powersaw_id) {
        this.powersaw_id = powersaw_id;
    }

    public int getPowersaw_bp() {
        return powersaw_bp;
    }

    public void setPowersaw_bp(int powersaw_bp) {
        this.powersaw_bp = powersaw_bp;
    }

    public int getPowersaw_sp() {
        return powersaw_sp;
    }

    public void setPowersaw_sp(int powersaw_sp) {
        this.powersaw_sp = powersaw_sp;
    }

    /**public String getPowersaw_qnty() {
        return powersaw_qnty;
    }

    public void setPowersaw_qnty(String powersaw_qnty) {
        this.powersaw_qnty = powersaw_qnty;
    }**/
}
