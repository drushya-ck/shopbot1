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
    static public String filter="";
    static public String modifiedKey="";
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
    public String getFilter(){return filter;}
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
        i.website="snapdeal";
        i.productUrl=productUrl;
        snapdealProducts.add(i);
    }

    public ArrayList<ItemsList.item> getProductList(){
        return snapdealProducts;
    }

    public String filter(ArrayList<String> selectedChipData){

        int i=0,j=0,brandFlag=0,authorFlag=0;
        String price_url = "",brand_url="",review_url="",author_url="";
        modifiedKey="";

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
                    arr[0]="0";arr[1]=s1;
                }else if(s.contains("Above")){
                    String s1=s.replaceAll("[^0-9]", "");
                    arr[0]=s1;arr[1]="200000";
                }
                price_url="Price%3A"+arr[0]+"%2C"+arr[1]+"%7C";
            }

            else if(Character.isDigit(s.charAt(0))){
                char c=s.charAt(0);
                review_url="avgRating%3A"+c+"%7C";
            }

            else if(FragmentLeft.brand_array.contains(s)){
                brand_url+=s+"%5E";
                brandFlag=1;
            }

            else if(FragmentLeft.author_array.contains(s)){
                String[] s1=s.split(" ");
                j=0;
                while(j<s1.length){
                    author_url=s1[j].trim()+"%20";
                    j++;
                }
                author_url+="%5E";
                authorFlag=1;
            }

            else {
                modifiedKey+=s+"%20";
            }
            i++;
        }
        if(brandFlag==1) brand_url="Brand%3A"+brand_url+"%7C";
        if(authorFlag==1) author_url="Author_s%3A"+author_url+"%7C";
        filter = "&q=" + brand_url + price_url + review_url + author_url;
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
