package com.example.shopbot1;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shopbot1.ui.home.FiltersMainActivity;
import com.example.shopbot1.ui.home.FragmentLeft;
import com.example.shopbot1.ui.home.ItemsList;
import com.example.shopbot1.ui.home.productList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

public class amazon extends AppCompatActivity {
    String productName,productPrice,productRating,imageUrl="",productUrl="";
    static public String url="";
    static public String subcategory= FiltersMainActivity.stuff;
    static public String filter="",changed_Keycategory="";
    ArrayList<ItemsList.item> amazonProducts =new ArrayList<>();
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
    //this is newly added
    public String getFilter(){return filter;}
    public void setUrl(String key, String filter){
        if(filter==""){
            url="https://www.amazon.in/s?k="+key+"&ref=nb_sb_noss_1";
        }else{
            url="https://www.amazon.in/s?k="+key+"&bbn=1571283031&rh="+filter+"&dc&qid=1609156109&rnid=3837712031&ref=sr_nr_p_89_6";
        }
    }
    public String getUrl(){
        return url;
    }
    public void searchQuery(){
        int flag=0; String n="";
        try {

            amazonProducts.clear();
            Document document = Jsoup.connect(url).get();
            ListIterator<Element> items=document.getElementsByAttribute("data-index").listIterator();
            if(!items.hasNext()) {
                System.out.println("empty");

            }
            while(items.hasNext()) {

                Element item=items.next();
                Elements e=item.getElementsByClass("s-image");
                imageUrl=e.attr("src");
                Elements name1=item.getElementsByClass("a-link-normal a-text-normal");
                productName=name1.text();
                Elements price1=item.getElementsByClass("a-price-whole");
                productPrice=price1.text();
                Elements rating1=item.getElementsByClass("a-icon-alt");
                productRating=rating1.text();
                productUrl="https://www.amazon.in/"+item.getElementsByClass("a-link-normal a-text-normal").attr("href");
                if(productRating.length()>=3){
                    productRating=productRating.substring(0,3);
                }

                if(!productName.isEmpty()&&!productPrice.isEmpty()&&!productRating.isEmpty()) {
                    addProductToList();
                }
            }

        }catch (IOException e) {
            System.out.println("error while scraping");
        }
    }
    public void addProductToList(){
        ItemsList.item i = new ItemsList.item();
        i.name=getProductName();
        i.price=getProductPrice();
        i.rating=getProductRating();
        i.website="amazon";
        i.img_url=imageUrl;
        i.productUrl=productUrl;
        amazonProducts.add(i);
    }

    public ArrayList<ItemsList.item> getProductList(){
        return amazonProducts;
    }



//selectedChipData is same as selItems
    public String filter(ArrayList<String> selectedFilters){
        filter="";changed_Keycategory="";
        ArrayList<String> rh_priceandreviewarray=new ArrayList<>();

        url="https://www.amazon.in/s?k="+subcategory+"&bbn=1571283031&rh="+filter+"&dc&qid=1609156109&rnid=3837712031&ref=sr_nr_p_89_6";
        String rh_price="",rh_review="",rh_brand="",rh_author=""; //these contain the selected values of filters -price,review etc


         for(int i=0;i<selectedFilters.size();i++){
            String s=selectedFilters.get(i);

            //sub_selitems is a hashmap which contains the keys as the filter-values(like above rs 500 etc) and values as the subcategories
            if(!FragmentLeft.sub_selitems.containsKey(s)|| FragmentLeft.sub_selitems.get(s)!= productList.subcategory){ continue;}
//price...........
            if(s.contains("₹")){
                String[] arr=new String[2];arr[0]="";arr[1]="";
                if(s.contains("-")){
                    arr=s.split("-",2);
                    arr[0]= arr[0].replaceAll("[^0-9]", "");arr[1]= arr[1].replaceAll("[^0-9]", "");
                    arr[0]=arr[0]+"00";arr[1]=arr[1]+"00";
                }else if(s.contains("Under")){
                    String s1=s.replaceAll("[^0-9]", "");
                    arr[0]="00";arr[1]=s1+"00";
                }else if(s.contains("Above")){
                    String s1=s.replaceAll("[^0-9]", "");
                    arr[0]=s1+"00";arr[1]="";
                }
                rh_price="p_36%3A"+arr[0]+"-"+arr[1];
                rh_priceandreviewarray.add(rh_price);
            }else if(Character.isDigit(s.charAt(0))){
//review..........
                char c=s.charAt(0);
                if(c=='4'){ rh_review="p_72%3A1318476031";
                }else if(c=='3'){ rh_review="p_72%3A1318477031";
                }else if(c=='2'){ rh_review="p_72%3A1318478031";
                }else{ rh_review="p_72%3A1318479031";
                }
                rh_priceandreviewarray.add(rh_review);
            }else if(FragmentLeft.brand_array.contains(s)){
//brand............
                if(rh_brand.isEmpty()){ rh_brand+="p_89%3A"+s;
                }else{ rh_brand+="%7C"+s;
                }
//author...............
            }else if(FragmentLeft.author_array.contains(s)){
                    rh_author="n%3A976389031%2Cp_lbr_books_authors_browse-bin%3A"+s;
            }
            else{
//for subject
                String[] s1=s.split(" ");
                for(int i2=0;i2<s1.length;i2++){
                    changed_Keycategory+=s1[i2]+"+";
                }
        }
        }
        if(!rh_priceandreviewarray.isEmpty()){
            filter=rh_priceandreviewarray.get(0);
        }
        if(rh_priceandreviewarray.size()>1){
            for(int i=1;i<rh_priceandreviewarray.size();i++){
                filter+="%2C";
                filter+=rh_priceandreviewarray.get(i);
            }
        }
        if(!rh_brand.isEmpty()){
            filter=rh_brand+"%2C"+filter;
        }
        if(!rh_author.isEmpty()){
            filter=rh_author+"%2C"+filter;
        }
        return filter;
    }



    public String sort(String sortby){
        String sort_url="";
        if(sortby.equalsIgnoreCase("price- low to high")){
            sort_url="&s=price-asc-rank";
        }
        else if(sortby.equalsIgnoreCase("price- high to low")){
            sort_url="&s=price-desc-rank";
        }
        else if(sortby.equalsIgnoreCase("popularity")){
            sort_url="&s=review-rank";
       }
        else if(sortby.equalsIgnoreCase("relevance")){
            sort_url="";
        }
        return sort_url;
    }

}
