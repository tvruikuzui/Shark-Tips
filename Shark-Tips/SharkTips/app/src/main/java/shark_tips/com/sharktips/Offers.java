package shark_tips.com.sharktips;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class Offers extends Fragment {

    private ImageView imgOfferOne,imgOfferTwo;
    private FrameLayout frame;
    private WebView loadWeb;
    private boolean isWebClicked;
    private WebClickedListener listener;

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

        return view;

    }

}

    interface WebClickedListener {
    void handleClick(boolean click);
}

