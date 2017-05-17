package shark_tips.com.sharktips;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Offers extends Fragment {

    private ImageView imgOfferOne,imgOfferTwo;
    private FrameLayout frame;
    private WebView loadWeb;
    private TextView lblShowTs;
    private String getUserEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
         getUserEmail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        lblShowTs = (TextView) view.findViewById(R.id.lblShowTs);
        frame = (FrameLayout) view.findViewById(R.id.webFrame);
        frame.setVisibility(View.GONE);
        loadWeb = (WebView) view.findViewById(R.id.loadWeb);
        loadWeb.setVisibility(View.GONE);
        imgOfferOne = (ImageView) view.findViewById(R.id.imgofferOne);
        imgOfferTwo = (ImageView) view.findViewById(R.id.imgofferTwo);
        Picasso.with(getContext()).load("http://pointshop.co.il/sharkTips/two.png").resize(1300,1500).into(imgOfferTwo);
        Picasso.with(getContext()).load("http://pointshop.co.il/sharkTips/one.png").resize(1300,1500).into(imgOfferOne);

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
                if (integer <= 1) {
                    lblShowTs.setText("Remaining time " + integer + " day");
                }
                MyHelper.saveTimeToSharedPreferences(getContext(),integer);
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

        return view;

    }

}

