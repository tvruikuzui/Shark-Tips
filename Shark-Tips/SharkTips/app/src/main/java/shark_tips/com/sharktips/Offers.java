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

    private ImageView imgOfferOne;
    private FrameLayout frame;
    private WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        frame = (FrameLayout) view.findViewById(R.id.webFrame);
        frame.setVisibility(View.GONE);
        webView = (WebView) view.findViewById(R.id.webFrame);
        imgOfferOne = (ImageView) view.findViewById(R.id.imgofferOne);
        Picasso.with(getContext()).load("http://pointshop.co.il/sharkTips/one.png").into(imgOfferOne);
        imgOfferOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

}
