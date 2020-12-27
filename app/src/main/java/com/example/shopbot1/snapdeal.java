package com.example.shopbot1;

import android.content.res.TypedArray;
import android.util.Log;

import com.example.shopbot1.ui.home.ItemsList;
import com.example.shopbot1.ui.home.productList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class snapdeal {
    String url,productName,productPrice,productRating,imageUrl="",productUrl="";
    ArrayList<ItemsList.item> snapdealProducts =new ArrayList<>();
    public String getProductName(){
        return productName;
    }
    public String getProductPrice(){
        return productPrice;
    }
    public String getProductRating(){
        return productRating;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setUrl(String key, String filter){
       url= "https://www.snapdeal.com/search?keyword="+key+"&santizedKeyword=&catId=&categoryId=0&suggested=false&vertical=&noOfResults=20&searchState=&clickSrc=go_header&lastKeyword=&prodCatId=&changeBackToAll=false&foundInAll=false&categoryIdSearched=&cityPageUrl=&categoryUrl=&url=&utmContent=&dealDetail="+filter;
        //url="https://www.flipkart.com/search?q=" + key + "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"+ filter;
    }
    public String getUrl(){
        return url;
    }
    public void searchQuery(){
        int flag=0; String n="";

        try {

            snapdealProducts.clear();
            Document document = Jsoup.connect(url).get();
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            Elements content = document.select("#products");
            for(Element item:content.select("section")){
//                Log.d("snapdeal_name","name=="+item.select("div.product-tuple-description > div.product-desc-rating > a > p").attr("title"));
                productName=item.select("div.product-tuple-description > div.product-desc-rating > a > p.product-title").attr("title");
//                Log.d("snapdeal_price","price=="+item.select("span.lfloat.product-price").attr("data-price"));
                productPrice=item.select("span.lfloat.product-price").attr("data-price");
                if(!item.select("div.product-tuple-description > div.product-desc-rating > a > div.clearfix.rating.av-rating").select("div.filled-stars").attr("style").replace("width:","").replace("%","").isEmpty()) {
//                    Log.d("snapdeal_price","rating=="+df.format((Float.parseFloat(item.select("div.clearfix.rating.av-rating").select("div.filled-stars").attr("style").replace("width:","").replace("%",""))/100)*5));
                    productRating=df.format((Float.parseFloat(item.select("div.product-tuple-description > div.product-desc-rating > a > div.clearfix.rating.av-rating").select("div.filled-stars").attr("style").replace("width:","").replace("%",""))/100)*5)+"";
                }
                imageUrl=item.select("div.product-tuple-image > a > picture > source").attr("srcset");
//                Log.d("snapdeal_img","imgurl=="+item.select("div.product-tuple-image > a > picture > source").attr("srcset"));
               productUrl=item.select("div.product-tuple-image > a").attr("href");
//                Log.d("snapdeal_pdturl","pdturl=="+item.select("div.product-tuple-image > a").attr("href"));
                addProductToList();
            }

        }catch (IOException e) {
            System.out.println("error while scraping");
            //e.printStackTrace();
        }

    }
    public void addProductToList(){
        ItemsList.item i = new ItemsList.item();
        i.name=getProductName();
        i.price="Rs. "+getProductPrice();
        i.rating=getProductRating();
        i.img_url=imageUrl;
        i.website="Snapdeal";
        i.productUrl=productUrl;
        snapdealProducts.add(i);
    }

    public ArrayList<ItemsList.item> getProductList(){
        return snapdealProducts;
    }

    public String filter(ArrayList<String> selectedChipData, List<String> price,List<String> brand){

        int i=0,j=0;
        String p_temp = "";
        int p1=0,p2=0;

        String price_url = "",brand_url="",filter="";
        Log.d("filter","inside filter");
        while(i<selectedChipData.size()) {
            Log.d("filter","inside filter while");
            if(price.contains(selectedChipData.get(i))) {
                Pattern p = Pattern.compile("\\d+");
                Matcher m = p.matcher(selectedChipData.get(i));
                while (m.find()) {
                    p_temp += m.group();
                    p_temp += ",";
                }
                if (!p_temp.isEmpty()) {
                    p1 = Integer.parseInt(p_temp.split(",")[0]);
                    p2 = Integer.parseInt(p_temp.split(",")[p_temp.split(",").length - 1]);
                    price_url="Price%3A"+p1+"%2C"+p2+"%7C";
                }
                Log.d("filter", "price modified=" + p1 + "==" + p2+" for "+selectedChipData.get(i));
            }
            if(brand.contains(selectedChipData.get(i))) {
                brand_url+="&q=Brand%3A"+selectedChipData.get(i)+"%7C";
                Log.d("filter","brand modified="+selectedChipData.get(i));
            }
            i++;
        }
        filter =  brand_url + price_url;
        return filter;
    }
    public String sort(String sortby){
        String sort_url="";
        Log.d("filter","sort in sort");
        if(sortby.equalsIgnoreCase("price- low to high")){
            sort_url="&sort=plth";
            Log.d("filter","sort in if");
        }
        else if(sortby.equalsIgnoreCase("price- high to low")){
            sort_url="&sort=phtl";
            Log.d("filter","sort in if");
        }
        else if(sortby.equalsIgnoreCase("popularity")){
            sort_url="&sort=plrty";
            Log.d("filter","sort in if");
        }
        else if(sortby.equalsIgnoreCase("relevance")){
            sort_url="&sort=rlvncy";
            Log.d("filter","sort in if");
        }
        return sort_url;
    }

}
