package shark_tips.com.sharktips;


import android.content.Context;
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
    private boolean isWebClicked;
    private WebClickedListener listener;
    private TextView lblTrailDays;

    public void setListener(WebClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        frame = (FrameLayout) view.findViewById(R.id.webFrame);
        frame.setVisibility(View.GONE);
        loadWeb = (WebView) view.findViewById(R.id.loadWeb);
        loadWeb.setVisibility(View.GONE);
        imgOfferOne = (ImageView) view.findViewById(R.id.imgofferOne);
        imgOfferTwo = (ImageView) view.findViewById(R.id.imgofferTwo);
        lblTrailDays = (TextView) view.findViewById(R.id.lblTrailDays);
        Picasso.with(getContext()).load("http://pointshop.co.il/sharkTips/two.png").resize(1300,1500).into(imgOfferTwo);
        Picasso.with(getContext()).load("http://pointshop.co.il/sharkTips/one.png").resize(1300,1500).into(imgOfferOne);
        imgOfferOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWebClicked = true;
                imgOfferOne.setVisibility(View.GONE);
                imgOfferTwo.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
                loadWeb.setVisibility(View.VISIBLE);
                WebSettings webSettings = loadWeb.getSettings();
                webSettings.setJavaScriptEnabled(true);
                loadWeb.loadUrl("https://www.trade-24.com/content/lp/sharks-tips.html?lName=2297&tag1=SharkTips");

                if (listener != null){
                    listener.handleClick(isWebClicked);
                }

            }
        });
        imgOfferTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWebClicked = true;
                imgOfferOne.setVisibility(View.GONE);
                imgOfferTwo.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
                loadWeb.setVisibility(View.VISIBLE);
                WebSettings webSettings = loadWeb.getSettings();
                webSettings.setJavaScriptEnabled(true);
                loadWeb.loadUrl("https://www.shark-tips.com/shark-tips-registration/");

                if (listener != null){
                    listener.handleClick(isWebClicked);
                }

            }

        });

        trailDays(getContext());

        return view;

    }

    private void trailDays (Context c){
        String mail = MyHelper.getUserEmailFromSharedPreferences(c);
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                InputStream inputStream = null;
                HttpURLConnection urlConnection = null;
                String res = null;
                try {
                    URL url = new URL("http://35.184.144.226/shark1/ts/"+params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setUseCaches(false);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    byte[] buffer = new byte[128];
                    int a = inputStream.read(buffer);
                    res = new String(buffer,0,a);
                    inputStream.close();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return res;
            }

            @Override
            protected void onPostExecute(String s) {
                lblTrailDays.setText("Remaining time for trial " + s +" days");
            }
        }.execute(mail);
    }

}

    interface WebClickedListener {
    void handleClick(boolean click);
}

