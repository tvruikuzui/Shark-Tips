package shark_tips.com.sharktips;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ShowOffers extends AppCompatActivity {

    private ImageView imgOfferOne,imgOfferTwo;
    private FrameLayout frame;
    private WebView loadWeb;
    private TextView lblShowTs;
    private String getUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_offers);

        lblShowTs = (TextView) findViewById(R.id.lblShowTs);
        frame = (FrameLayout) findViewById(R.id.webFrame);
        frame.setVisibility(View.GONE);
        loadWeb = (WebView)findViewById(R.id.loadWeb);
        loadWeb.setVisibility(View.GONE);
        imgOfferOne = (ImageView) findViewById(R.id.imgofferOne);
        imgOfferTwo = (ImageView) findViewById(R.id.imgofferTwo);
        Picasso.with(this).load("http://pointshop.co.il/sharkTips/two.png").resize(1300,1500).into(imgOfferTwo);
        Picasso.with(this).load("http://pointshop.co.il/sharkTips/one.png").resize(1300,1500).into(imgOfferOne);

        new AsyncTask<String, Void, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                int daysResult = 0;
                try {
                    URL url = new URL("http://35.184.144.226/shark2/ts/"+params[0]+"/");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setUseCaches(false);
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    byte [] buffer = new byte[64];
                    int actuallyRead = inputStream.read(buffer);
                    daysResult = Integer.parseInt(new String(buffer,0,actuallyRead));
                    inputStream.close();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return daysResult;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                lblShowTs.setText("Remaining time " + integer + " days");
                MyHelper.saveTimeToSharedPreferences(ShowOffers.this,integer);
            }
        }.execute(getUserEmail);

        imgOfferOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgOfferOne.setVisibility(View.GONE);
                imgOfferTwo.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
                loadWeb.setVisibility(View.VISIBLE);
                WebSettings webSettings = loadWeb.getSettings();
                webSettings.setJavaScriptEnabled(true);
                loadWeb.loadUrl("https://www.trade-24.com/content/lp/sharks-tips.html?lName=2297&tag1=SharkTips");
            }
        });
        imgOfferTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgOfferOne.setVisibility(View.GONE);
                imgOfferTwo.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
                loadWeb.setVisibility(View.VISIBLE);
                WebSettings webSettings = loadWeb.getSettings();
                webSettings.setJavaScriptEnabled(true);
                loadWeb.loadUrl("https://www.shark-tips.com/shark-tips-registration/");
            }

        });

    }
}
