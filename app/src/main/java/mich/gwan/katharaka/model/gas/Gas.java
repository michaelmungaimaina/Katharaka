package mich.gwan.katharaka.model.gas;

public class Gas {

    String gas_category;
    int gas_id;
    //String gas_qnty;
    int gas_bp;
    int gas_sp;

    public String getGas_category() {
        return gas_category;
    }
    public void setGas_category(String gas_category) {
        this.gas_category = gas_category;
    }
    public int getGas_id(){
        return gas_id;
    }
    public void setGas_id(int gas_id) {
        this.gas_id = gas_id;
    }

    public int getGas_bp() {
        return gas_bp;
    }

    public void setGas_bp(int gas_bp) {
        this.gas_bp = gas_bp;
    }

    public int getGas_sp() {
        return gas_sp;
    }

    public void setGas_sp(int gas_sp) {
        this.gas_sp = gas_sp;
    }

    /**public String getGas_qnty() {
        return gas_qnty;
    }

    public void setGas_qnty(String gas_qnty) {
        this.gas_qnty = gas_qnty;
    }**/
}
