package mich.gwan.katharaka.model.oil;

public class OilSale {
    String oilSaleDate;
    String oilSaleTime;
    String oilSalecategory;
    int oilSaleQnty;
    int oilSaleUprice;
    int oilSaleTotal;
    int oilSaleProfit;

    public String getOilSaleDate() {
        return oilSaleDate;
    }
    public void setOilSaleDate(String oilSaleDate) {
        this.oilSaleDate = oilSaleDate;
    }
    public String getOilSaleTime() {
        return oilSaleTime;
    }
    public void setOilSaleTime(String oilSaleTime){
        this.oilSaleTime = oilSaleTime;
    }

    public int getOilSaleProfit() {
        return oilSaleProfit;
    }

    public void setOilSaleProfit(int oilSaleProfit) {
        this.oilSaleProfit = oilSaleProfit;
    }

    public String getOilSalecategory() {
        return oilSalecategory;
    }

    public void setOilSalecategory(String oilSalecategory) {
        this.oilSalecategory = oilSalecategory;
    }
    public int getOilSaleQnty(){
        return oilSaleQnty;
    }
    public void setOilSaleQnty(int oilSaleQnty){
        this.oilSaleQnty = oilSaleQnty;
    }
    public int getOilSaleUprice(){
        return oilSaleUprice;
    }
    public void setOilSaleUprice(int oilSaleUprice){
        this.oilSaleUprice = oilSaleUprice;
    }

    public int getOilSaleTotal() {
        return oilSaleTotal;
    }

    public void setOilSaleTotal(int oilSaleTotal) {
        this.oilSaleTotal = oilSaleTotal;
    }
}
