package mich.gwan.katharaka.model.bolt;

public class Bolts {

    String bolt_category;
    int bolt_id;
    //String bolt_qnty;
    int bolt_bp;
    int bolt_sp;
    static String databaseName = "UserManager.db";

    public String getDatabaseName(){return  databaseName;}

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getBolt_category() {
        return bolt_category;
    }
    public void setBolt_category(String bolt_category) {
        this.bolt_category = bolt_category;
    }
    public int getBolt_id(){
        return bolt_id;
    }
    public void setBolt_id(int bolt_id) {
        this.bolt_id = bolt_id;
    }

    public int getBolt_bp() {
        return bolt_bp;
    }

    public void setBolt_bp(int bolt_bp) {
        this.bolt_bp = bolt_bp;
    }

    public int getBolt_sp() {
        return bolt_sp;
    }

    public void setBolt_sp(int bolt_sp) {
        this.bolt_sp = bolt_sp;
    }

    /**public String getBolt_qnty() {
        return bolt_qnty;
    }

    public void setBolt_qnty(String bolt_qnty) {
        this.bolt_qnty = bolt_qnty;
    }**/
}
