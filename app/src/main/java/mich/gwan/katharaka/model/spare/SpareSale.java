package mich.gwan.katharaka.model.spare;

public class SpareSale {
    String spareSaleDate;
    String spareSaleTime;
    String spareSalecategory;
    int spareSaleQnty;
    int spareSaleUprice;
    int spareSaleTotal;
    int spareSaleProfit;

    public String getSpareSaleDate() {
        return spareSaleDate;
    }
    public void setSpareSaleDate(String spareSaleDate) {
        this.spareSaleDate = spareSaleDate;
    }
    public String getSpareSaleTime() {
        return spareSaleTime;
    }
    public void setSpareSaleTime(String spareSaleTime){
        this.spareSaleTime = spareSaleTime;
    }

    public int getSpareSaleProfit() {
        return spareSaleProfit;
    }

    public void setSpareSaleProfit(int spareSaleProfit) {
        this.spareSaleProfit = spareSaleProfit;
    }

    public String getSpareSalecategory() {
        return spareSalecategory;
    }

    public void setSpareSalecategory(String spareSalecategory) {
        this.spareSalecategory = spareSalecategory;
    }
    public int getSpareSaleQnty(){
        return spareSaleQnty;
    }
    public void setSpareSaleQnty(int spareSaleQnty){
        this.spareSaleQnty = spareSaleQnty;
    }
    public int getSpareSaleUprice(){
        return spareSaleUprice;
    }
    public void setSpareSaleUprice(int spareSaleUprice){
        this.spareSaleUprice = spareSaleUprice;
    }

    public int getSpareSaleTotal() {
        return spareSaleTotal;
    }

    public void setSpareSaleTotal(int spareSaleTotal) {
        this.spareSaleTotal = spareSaleTotal;
    }
}
