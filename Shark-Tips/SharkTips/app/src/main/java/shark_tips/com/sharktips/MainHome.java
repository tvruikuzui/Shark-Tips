package shark_tips.com.sharktips;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainHome extends Fragment {

    private GifTextView gifTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        gifTextView = (GifTextView) view.findViewById(R.id.imgGif);
        new AsyncTask<Void, Void, Integer>() {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            @Override
            protected Integer doInBackground(Void... params) {
                return null;
            }
        }.execute();
        return view;
    }

}
