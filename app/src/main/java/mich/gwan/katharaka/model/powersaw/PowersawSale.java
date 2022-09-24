package mich.gwan.katharaka.model.powersaw;

public class PowersawSale {
    String powersawSaleDate;
    String powersawSaleTime;
    String powersawSalecategory;
    int powersawSaleQnty;
    int powersawSaleUprice;
    int powersawSaleTotal;
    int powersawSaleProfit;

    public String getPowersawSaleDate() {
        return powersawSaleDate;
    }
    public void setPowersawSaleDate(String powersawSaleDate) {
        this.powersawSaleDate = powersawSaleDate;
    }
    public String getPowersawSaleTime() {
        return powersawSaleTime;
    }
    public void setPowersawSaleTime(String powersawSaleTime){
        this.powersawSaleTime = powersawSaleTime;
    }

    public int getPowersawSaleProfit() {
        return powersawSaleProfit;
    }

    public void setPowersawSaleProfit(int powersawSaleProfit) {
        this.powersawSaleProfit = powersawSaleProfit;
    }

    public String getPowersawSalecategory() {
        return powersawSalecategory;
    }

    public void setPowersawSalecategory(String powersawSalecategory) {
        this.powersawSalecategory = powersawSalecategory;
    }
    public int getPowersawSaleQnty(){
        return powersawSaleQnty;
    }
    public void setPowersawSaleQnty(int powersawSaleQnty){
        this.powersawSaleQnty = powersawSaleQnty;
    }
    public int getPowersawSaleUprice(){
        return powersawSaleUprice;
    }
    public void setPowersawSaleUprice(int powersawSaleUprice){
        this.powersawSaleUprice = powersawSaleUprice;
    }

    public int getPowersawSaleTotal() {
        return powersawSaleTotal;
    }

    public void setPowersawSaleTotal(int powersawSaleTotal) {
        this.powersawSaleTotal = powersawSaleTotal;
    }
}
