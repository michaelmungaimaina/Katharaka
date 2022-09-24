package mich.gwan.katharaka.model.welding;

public class WeldingSale {
    String weldingSaleDate;
    String weldingSaleTime;
    String weldingSalecategory;
    int weldingSaleQnty;
    int weldingSaleUprice;
    int weldingSaleTotal;
    int weldingSaleProfit;

    public String getWeldingSaleDate() {
        return weldingSaleDate;
    }
    public void setWeldingSaleDate(String weldingSaleDate) {
        this.weldingSaleDate = weldingSaleDate;
    }
    public String getWeldingSaleTime() {
        return weldingSaleTime;
    }
    public void setWeldingSaleTime(String weldingSaleTime){
        this.weldingSaleTime = weldingSaleTime;
    }

    public int getWeldingSaleProfit() {
        return weldingSaleProfit;
    }

    public void setWeldingSaleProfit(int weldingSaleProfit) {
        this.weldingSaleProfit = weldingSaleProfit;
    }

    public String getWeldingSalecategory() {
        return weldingSalecategory;
    }

    public void setWeldingSalecategory(String weldingSalecategory) {
        this.weldingSalecategory = weldingSalecategory;
    }
    public int getWeldingSaleQnty(){
        return weldingSaleQnty;
    }
    public void setWeldingSaleQnty(int weldingSaleQnty){
        this.weldingSaleQnty = weldingSaleQnty;
    }
    public int getWeldingSaleUprice(){
        return weldingSaleUprice;
    }
    public void setWeldingSaleUprice(int weldingSaleUprice){
        this.weldingSaleUprice = weldingSaleUprice;
    }

    public int getWeldingSaleTotal() {
        return weldingSaleTotal;
    }

    public void setWeldingSaleTotal(int weldingSaleTotal) {
        this.weldingSaleTotal = weldingSaleTotal;
    }
}
