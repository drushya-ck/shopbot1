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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class flipkart {
    String url,productName,productPrice,productRating,imageUrl="",productUrl="";
    ArrayList<ItemsList.item> flipkartProducts =new ArrayList<>();
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
        url="https://www.flipkart.com/search?q=" + key + "&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off"+ filter;
    }
    public String getUrl(){
        return url;
    }
    public void searchQuery(){
            int flag=0; String n="";

                try {

                    flipkartProducts.clear();
                    Document document = Jsoup.connect(url).get();
                    Elements content = document.getElementsByClass("E2-pcE _3zjXRo");
                    Elements items = content.select("div:nth-child(2) > div._2pi5LC.col-12-12");
                    if (items.select("div._13oc-S > div > div ").first().children().size() > 2) {
                        System.out.println("yoooooooooooooooooooo");
                        for (Element m : items.select("div._13oc-S > div > div")) {
                            // c++;
                            if (m.select("a").attr("title").isEmpty() || m.select("a > div > div:nth-child(1)").text().isEmpty())
                                continue;
                            if (m.select("a").attr("title").toLowerCase().contains("add to compare"))
                                continue;
                            if (m.select("a").attr("title").contains("/")) {
                                continue;
                            }
                            //ItemsList.item i = new ItemsList.item();
                            productName = m.select("a").attr("title");
                            productPrice = m.select("a > div > div:nth-child(1)").text();
                            productRating = m.select("div:nth-child(4) > span:nth-child(1)").text();
                            productUrl=m.select("a").attr("href");
                            Log.d("pdturl",productUrl);
                            addProductToList();
                        }
                    }
                    else {
                        System.out.println("hahahahhahahhahahhahhahah ");
                        for (Element m : items.select("div._13oc-S > div > div")) {
                            if (m.select("div._30jeq3._1_WHN1").text().isEmpty()) {
                                System.out.println("emptyyyyyyy price ");
                                continue;
                            }
                            if (m.select("div._4rR01T").text().isEmpty()) {
                                System.out.println("emptyyyyyyy name ");
                                continue;
                            }
//                            if(m.select("div.CXW8mj > img").attr("alt").isEmpty()){
//                                System.out.println("emptyyyyyyy imageurl ");
//                            }else System.out.println("nottt emptyyyyyyy imageurl ="+m.select("div.CXW8mj > img").attr("alt"));
                            if (m.select("div._4rR01T").text().contains("/")) {
                                n = m.select("div._4rR01T").text();
                                n = n.replace("/", " ");
                                flag = 1;
                            }
                            // ItemsList.item i = new ItemsList.item();
                            if (flag == 1) {
                                productName = n;
                            } else {
                                productName = m.select("div._4rR01T").text();
                            }
                            productPrice = m.select("div._30jeq3._1_WHN1").text();
                            productRating = m.select("div._3LWZlK").text();
//                            imageUrl=m.select("img").attr("href");
                            productUrl=m.select("a").attr("href");
                            Log.d("pdturl",productUrl);
                            addProductToList();
                        }
                    }
                }catch (IOException e) {
                    System.out.println("error while scraping");
                    //e.printStackTrace();
                }

    }
    public void addProductToList(){
        ItemsList.item i = new ItemsList.item();
        i.name=getProductName();
        i.price=getProductPrice();
        i.rating=getProductRating();
//        i.img_url=imageUrl;
        i.website="Flipkart";
        i.productUrl="https://www.flipkart.com"+productUrl;
        flipkartProducts.add(i);
    }

    public ArrayList<ItemsList.item> getProductList(){
        return flipkartProducts;
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
                    price_url="&p%5B%5D=facets.price_range.from%3D"+p1+"&p%5B%5D=facets.price_range.to%3D"+p2;
                }
                Log.d("filter", "price modified=" + p1 + "==" + p2+" for "+selectedChipData.get(i));
            }
            if(brand.contains(selectedChipData.get(i))) {
                brand_url+="&p%5B%5D=facets.brand%255B%255D%3D"+selectedChipData.get(i);
                Log.d("filter","brand modified="+selectedChipData.get(i));
            }
            i++;
        }
        filter = price_url + brand_url;
        return filter;
    }
    public String sort(String sortby){
        String sort_url="";
        Log.d("filter","sort in sort");
        if(sortby.equalsIgnoreCase("price- low to high")){
            sort_url="&sort=price_asc";
            Log.d("filter","sort in if");
        }
        else if(sortby.equalsIgnoreCase("price- high to low")){
            sort_url="&sort=price_desc";
            Log.d("filter","sort in if");
        }
        else if(sortby.equalsIgnoreCase("popularity")){
            sort_url="&sort=popularity";
            Log.d("filter","sort in if");
        }
        else if(sortby.equalsIgnoreCase("relevance")){
            sort_url="&sort=relevance";
            Log.d("filter","sort in if");
        }
       return sort_url;
    }

}
