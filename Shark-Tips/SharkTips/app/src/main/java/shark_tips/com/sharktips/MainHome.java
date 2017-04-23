package shark_tips.com.sharktips;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pl.droidsonroids.gif.GifDrawable;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainHome extends Fragment {

   // private GifDrawable gifDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        return view;
    }

}
