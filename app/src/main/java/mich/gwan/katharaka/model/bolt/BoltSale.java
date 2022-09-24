package mich.gwan.katharaka.model.bolt;

public class BoltSale {
    String boltSaleDate;
    String boltSaleTime;
    String boltSalecategory;
    int boltSaleQnty;
    int boltSaleUprice;
    int boltSaleTotal;
    int boltSaleProfit;

    public String getBoltSaleDate() {
        return boltSaleDate;
    }
    public void setBoltSaleDate(String boltSaleDate) {
        this.boltSaleDate = boltSaleDate;
    }
    public String getBoltSaleTime() {
        return boltSaleTime;
    }
    public void setBoltSaleTime(String boltSaleTime){
        this.boltSaleTime = boltSaleTime;
    }

    public int getBoltSaleProfit() {
        return boltSaleProfit;
    }

    public void setBoltSaleProfit(int boltSaleProfit) {
        this.boltSaleProfit = boltSaleProfit;
    }

    public String getBoltSalecategory() {
        return boltSalecategory;
    }

    public void setBoltSalecategory(String boltSalecategory) {
        this.boltSalecategory = boltSalecategory;
    }
    public int getBoltSaleQnty(){
        return boltSaleQnty;
    }
    public void setBoltSaleQnty(int boltSaleQnty){
        this.boltSaleQnty = boltSaleQnty;
    }
    public int getBoltSaleUprice(){
        return boltSaleUprice;
    }
    public void setBoltSaleUprice(int boltSaleUprice){
        this.boltSaleUprice = boltSaleUprice;
    }

    public int getBoltSaleTotal() {
        return boltSaleTotal;
    }

    public void setBoltSaleTotal(int boltSaleTotal) {
        this.boltSaleTotal = boltSaleTotal;
    }
}
