package com.example.shopbot1;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class webScrap extends AppCompatActivity {
    TextView textView;
    Button button;
    private ImageView imgv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_scrap);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.btnView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new doIT().execute();
            }
        });
        imgv=(ImageView)findViewById(R.id.imgv);
       /* Picasso.with(MainActivity.this)
                .load("https://rukminim1.flixcart.com/image/612/612/k0wqwsw0/electric-kettle/g/t/d/butterfly-rapid-kettle-1-5-litre-wave-750-ml-water-bottle-rapid-original-imafkfy7zaekbubs.jpeg?q=70")
                .into(imgv);*/
    }
    public class doIT extends AsyncTask<Void,Void,Void> {
        String words;
        StringBuilder st=new StringBuilder();

        @Override
        protected Void doInBackground(Void... params) {
            try {
               /* Document document = Jsoup.connect("https://www.flipkart.com/search?q=earphones&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off&as-pos=1&as-type=HISTORY").get();

                String title=document.title();
                words = document.text();
                //Elements links=document.select("a[href]");
                //st.append(title+"\n");

                /*for(Element l:links){
                    st.append("\n"+" Link: "+l.attr("href")+"\n"+" Text: "+l.text());
                }
                Elements t=document.select("div._1HmYoV _35HD7C>div");
                for(Element i:t){
                    st.append("\n"+"Title: \n Link: "+t.attr("href")+"\n"+" Text: "+t.text());
                }
                */

                //Document document = Jsoup.connect("https://www.flipkart.com/search?q=electronics&otracker=search&otracker1=search&marketplace=FLIPKART&as-show=on&as=off").get();
                Document document = Jsoup.connect("https://www.amazon.in/b?node=1389396031").get();
                //Elements t=document.select("#container > div > div.t-0M7P._2doH3V > div._3e7xtJ > div._1HmYoV.hCUpcT > div:nth-child(2) > div:nth-child(2) > div");
                Elements item = document.select("ul li div div");
                //#container > div > div.t-0M7P._2doH3V > div._3e7xtJ > div._1HmYoV.hCUpcT > div:nth-child(2) > div:nth-child(2)
                for(Element i:item){
                    //st.append("\nName:"+ i.getElementsByClass("_1vC4OE").text());
                    if(i.getElementsByClass("a-link-normal s-access-detail-page s-color-twister-title-link a-text-normal").text().isEmpty() || i.getElementsByClass("a-link-normal a-text-normal").text().isEmpty() || i.getElementsByClass("a-popover-trigger a-declarative").text().isEmpty())continue;
                    st.append("\nName:"+ i.getElementsByClass("a-link-normal s-access-detail-page s-color-twister-title-link a-text-normal").text()+", Price: "+i.getElementsByClass("a-link-normal a-text-normal").text()+", Rating: "+i.getElementsByClass("a-popover-trigger a-declarative").text());

                }

                //div > div:nth-child(1)
                //div > div:nth-child(1) > div > a._2cLu-l
                //div > div:nth-child(1) > div > div.niH0FQ._36Fcw_
                //div > div:nth-child(1) > div > a.Zhf2z- > div:nth-child(1) > div > div > img
                //System.out.println(st);
                //textView.setText(st);
            } catch (IOException e) {
                System.out.println("hey");
                //e.printStackTrace();
            } return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView.setText(st);
        }
    }
}