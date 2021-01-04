package com.example.shopbot1;

public class HorizontalProductScrollModel {
    String productImage,productUrl;
    String productTitle,productPrice;
    public HorizontalProductScrollModel(String productImage,String productTitle,String productPrice,String productUrl){
        this.productImage=productImage;
        this.productTitle=productTitle;
        this.productPrice=productPrice;
        this.productUrl=productUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String  productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
