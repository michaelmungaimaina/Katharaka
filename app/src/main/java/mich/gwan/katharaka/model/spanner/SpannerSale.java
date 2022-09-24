package mich.gwan.katharaka.model.spanner;

public class SpannerSale {
    String spannerSaleDate;
    String spannerSaleTime;
    String spannerSalecategory;
    int spannerSaleQnty;
    int spannerSaleUprice;
    int spannerSaleTotal;
    int spannerSaleProfit;

    public String getSpannerSaleDate() {
        return spannerSaleDate;
    }
    public void setSpannerSaleDate(String spannerSaleDate) {
        this.spannerSaleDate = spannerSaleDate;
    }
    public String getSpannerSaleTime() {
        return spannerSaleTime;
    }
    public void setSpannerSaleTime(String spannerSaleTime){
        this.spannerSaleTime = spannerSaleTime;
    }

    public int getSpannerSaleProfit() {
        return spannerSaleProfit;
    }

    public void setSpannerSaleProfit(int spannerSaleProfit) {
        this.spannerSaleProfit = spannerSaleProfit;
    }

    public String getSpannerSalecategory() {
        return spannerSalecategory;
    }

    public void setSpannerSalecategory(String spannerSalecategory) {
        this.spannerSalecategory = spannerSalecategory;
    }
    public int getSpannerSaleQnty(){
        return spannerSaleQnty;
    }
    public void setSpannerSaleQnty(int spannerSaleQnty){
        this.spannerSaleQnty = spannerSaleQnty;
    }
    public int getSpannerSaleUprice(){
        return spannerSaleUprice;
    }
    public void setSpannerSaleUprice(int spannerSaleUprice){
        this.spannerSaleUprice = spannerSaleUprice;
    }

    public int getSpannerSaleTotal() {
        return spannerSaleTotal;
    }

    public void setSpannerSaleTotal(int spannerSaleTotal) {
        this.spannerSaleTotal = spannerSaleTotal;
    }
}
