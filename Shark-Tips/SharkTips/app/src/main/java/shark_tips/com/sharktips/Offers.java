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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 */
public class Offers extends Fragment {

    private ImageView imgOfferOne,imgOfferTwo;
    private FrameLayout frame;
    private WebView loadWeb;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        frame = (FrameLayout) view.findViewById(R.id.webFrame);
        frame.setVisibility(View.GONE);
        loadWeb = (WebView) view.findViewById(R.id.loadWeb);
        loadWeb.setVisibility(View.GONE);
        imgOfferOne = (ImageView) view.findViewById(R.id.imgofferOne);
        imgOfferTwo = (ImageView) view.findViewById(R.id.imgofferTwo);
        Picasso.with(getContext()).load("http://pointshop.co.il/sharkTips/two.png").into(imgOfferTwo);
        Picasso.with(getContext()).load("http://pointshop.co.il/sharkTips/one.png").into(imgOfferOne);
        imgOfferOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgOfferOne.setVisibility(View.GONE);
                imgOfferTwo.setVisibility(View.GONE);
                frame.setVisibility(View.VISIBLE);
                loadWeb.setVisibility(View.VISIBLE);
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
                loadWeb.loadUrl("https://www.shark-tips.com/shark-tips-registration/");
            }
        });
        return view;
    }

}
