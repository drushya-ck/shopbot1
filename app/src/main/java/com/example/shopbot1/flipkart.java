package com.example.shopbot1;

import android.content.res.TypedArray;
import android.util.Log;

import com.example.shopbot1.ui.home.FragmentLeft;
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
//#container > div > div._3LxdjL._3NzWOH > div._3FqKqJ > div.E2-pcE._3zjXRo > div:nth-child(2) > div:nth-child(2) > div > div > div > a > div.MIXNux > div._3wLduG > div > span > div > label > div::before
public class flipkart {
    String url,productName,productPrice,productRating,imageUrl="",productUrl="",productDesc="";
    static public String modifiedKey="";
    static public String filter="";
    ArrayList<ItemsList.item> flipkartProducts =new ArrayList<>();
    ArrayList<ItemsList.item> temp =new ArrayList<>();
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
    public String getFilter(){return filter;}
    public void searchQuery(){
            int flag=0,i=0; String n="";

                try {

                    flipkartProducts.clear();
                    Document document = Jsoup.connect(url).get();
                    Elements content = document.getElementsByClass("E2-pcE _3zjXRo");
                    Elements items = content.select("div:nth-child(2) > div._2pi5LC.col-12-12");
//                   Log.d("compare",items.select("div._13oc-S > div").attr("style").trim().equals("width:25%")+"");
                    if (items.select("div._13oc-S > div").attr("style").trim().equals("width:25%")) {
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
                            Log.d("feature",m.select("a").attr("title"));
                            productDesc=m.select("a").attr("title");
                            //ItemsList.item i = new ItemsList.item();
//                            productName = m.select("a").attr("title");
                            if (m.select("a").attr("title").contains("/")) {
                                n = m.select("a").attr("title");
                                n = n.replace("/", " ");
                                flag = 1;
                            }
                            // ItemsList.item i = new ItemsList.item();
                            if (flag == 1) {
                                productName = n;
                            } else {
                                productName = m.select("a").attr("title");
                            }
                            productPrice = m.select("a > div > div:nth-child(1)").text();
                            productRating = m.select("div:nth-child(4) > span:nth-child(1)").text();
                            productUrl=m.select("a").attr("href");
                            Log.d("pdturl",productUrl);
                            addProductToList();
                        }
                    }
                    else if(items.select("div._13oc-S > div").attr("style").trim().equals("width:100%")){
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
                            Log.d("feature",m.select("div.col.col-7-12").text());
                            productDesc=m.select("div.col.col-7-12").text();
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
        i.website="flipkart";
        i.productUrl="https://www.flipkart.com"+productUrl;
        i.productDesc=this.productDesc;
        flipkartProducts.add(i);
    }

    public ArrayList<ItemsList.item> getProductList(){
        return flipkartProducts;
    }


    public String filter(ArrayList<String> selectedChipData){

        int i=0,j=0;
        modifiedKey="";
        String price_url = "",brand_url="",review_url="";

        while(i<selectedChipData.size()) {
            String s=selectedChipData.get(i);
            if(!FragmentLeft.sub_selitems.containsKey(s)||FragmentLeft.sub_selitems.get(s)!=productList.subcategory){ continue;}

            if(s.contains("₹")){
                String[] arr=new String[2];arr[0]="";arr[1]="";
                if(s.contains("-")){
                    arr=s.split("-",2);
                    arr[0]= arr[0].replaceAll("[^0-9]", "");arr[1]= arr[1].replaceAll("[^0-9]", "");
                }else if(s.contains("Under")){
                    String s1=s.replaceAll("[^0-9]", "");
                    arr[0]="Min";arr[1]=s1;
                }else if(s.contains("Above")){
                    String s1=s.replaceAll("[^0-9]", "");
                    arr[0]=s1;arr[1]="Max";
                }
                price_url="&p%5B%5D=facets.price_range.from%3D"+arr[0]+"&p%5B%5D=facets.price_range.to%3D"+arr[1];
            }

            else if(Character.isDigit(s.charAt(0))){
                char c=s.charAt(0);
                review_url+="&p%5B%5D=facets.rating%255B%255D%3D"+c+"%25E2%2598%2585%2B%2526%2Babove";
            }

            else if(FragmentLeft.brand_array.contains(s)){
                brand_url+="&p%5B%5D=facets.brand%255B%255D%3D"+selectedChipData.get(i);
            }

            else {
                modifiedKey+=s+"%20";
            }

            i++;
        }
        filter = price_url + brand_url + review_url ;
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
