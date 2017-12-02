package shark_tips.com.sharktips;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewOffersPage extends Fragment {

    private TextView lblShowTs;
    private String getUserEmail;
    private Button btnForexGrand,btnAvaTrade,btnTrade;
    private int id;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_offers_page, container, false);
        btnForexGrand = (Button) view.findViewById(R.id.btnForexGrand);
        btnTrade = (Button) view.findViewById(R.id.btnTrade);
        btnAvaTrade = (Button) view.findViewById(R.id.btnAvaTrade);
        btnForexGrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://forexgrand.com/demoreal2/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnAvaTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.avatrade.com/trading-account?tradingplatform=2&dealtype=1&mtsrv=mt1&tag=68943&tag2=~profile_default";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        btnTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.trade.com/en/live-registration?cid=CFF682AE950F5BE02319BCD699F914AE&zid=Mexos_SBSP&pid=501101&mid=13524368&custom=4321&c=CFF682AE950F5BE02319BCD699F914AE&af_sub1=13524368&af_sub2=Mexos_SBSP&af_sub3=4321";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        getUserEmail = MyHelper.getUserEmailFromSharedPreferences(getContext());
        lblShowTs = (TextView) view.findViewById(R.id.lblShowTs);


        new AsyncTask<String, Void, Integer>() {
            @Override
            protected Integer doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                InputStream inputStream = null;
                int daysResult = 0;
                try {
                    URL url = new URL("http://35.202.187.67/shark2/ts/"+params[0]+"/");
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
                if (integer == -404) {
                    lblShowTs.setText("");
                    lblShowTs.setVisibility(View.GONE);
                }else if (integer <= 1){
                    lblShowTs.setText("Remaining time " + integer + " day");

                }
                MyHelper.saveTimeToSharedPreferences(getContext(),integer);
            }
        }.execute(getUserEmail);

        return view;



    }

}
