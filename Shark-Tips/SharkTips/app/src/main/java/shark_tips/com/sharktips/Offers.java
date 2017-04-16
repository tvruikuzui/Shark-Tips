package shark_tips.com.sharktips;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Offers extends Fragment {

    private WebView offerOne,offerTwo;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        offerOne = (WebView) view.findViewById(R.id.offerOne);
        offerTwo = (WebView) view.findViewById(R.id.offerTwo);
        offerOne.loadUrl("http://pointshop.co.il/sharkTips/one.png");
        offerOne.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.offerOne) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.shark-tips.com/shark-tips-registration/"));
                    startActivity(intent);
                }
                return false;
            }
        });
        offerTwo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.offerTwo) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.trade-24.com/content/lp/sharks-tips.html?lName=2297&tag1=SharkTips"));
                    startActivity(intent);

                }
                return false;
            }
        });


        return view;
    }

}
