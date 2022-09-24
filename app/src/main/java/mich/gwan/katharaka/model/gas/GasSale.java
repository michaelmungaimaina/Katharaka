package mich.gwan.katharaka.model.gas;

public class GasSale {
    String gasSaleDate;
    String gasSaleTime;
    String gasSalecategory;
    int gasSaleQnty;
    int gasSaleUprice;
    int gasSaleTotal;
    int gasSaleProfit;

    public String getGasSaleDate() {
        return gasSaleDate;
    }
    public void setGasSaleDate(String gasSaleDate) {
        this.gasSaleDate = gasSaleDate;
    }
    public String getGasSaleTime() {
        return gasSaleTime;
    }
    public void setGasSaleTime(String gasSaleTime){
        this.gasSaleTime = gasSaleTime;
    }

    public int getGasSaleProfit() {
        return gasSaleProfit;
    }

    public void setGasSaleProfit(int gasSaleProfit) {
        this.gasSaleProfit = gasSaleProfit;
    }

    public String getGasSalecategory() {
        return gasSalecategory;
    }

    public void setGasSalecategory(String gasSalecategory) {
        this.gasSalecategory = gasSalecategory;
    }
    public int getGasSaleQnty(){
        return gasSaleQnty;
    }
    public void setGasSaleQnty(int gasSaleQnty){
        this.gasSaleQnty = gasSaleQnty;
    }
    public int getGasSaleUprice(){
        return gasSaleUprice;
    }
    public void setGasSaleUprice(int gasSaleUprice){
        this.gasSaleUprice = gasSaleUprice;
    }

    public int getGasSaleTotal() {
        return gasSaleTotal;
    }

    public void setGasSaleTotal(int gasSaleTotal) {
        this.gasSaleTotal = gasSaleTotal;
    }
}
