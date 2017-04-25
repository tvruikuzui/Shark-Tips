package shark_tips.com.sharktips;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import pl.droidsonroids.gif.GifTextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainHome extends Fragment {

    private GifTextView gifTextView;
    private static final String BASE_URL = "http://35.184.144.226/shark2/performance/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        gifTextView = (GifTextView) view.findViewById(R.id.imgGif);
        new AsyncTask<Void, Void, String>() {
            @Override
                protected String doInBackground(Void... params) {
                    HttpURLConnection urlConnection = null;
                    InputStream inputStream = null;
                    try {
                        URL url = new URL(BASE_URL);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.setUseCaches(false);
                        urlConnection.connect();
                        inputStream = urlConnection.getInputStream();
                        byte [] buffer = new byte[64];
                        int actuallyRead = inputStream.read(buffer);
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(new String(buffer,0,actuallyRead));
                        inputStream.close();
                        return stringBuilder.toString();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (inputStream != null){
                            try {
                                inputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (urlConnection != null){
                            urlConnection.disconnect();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String s) {
                    int num = Integer.parseInt(s);
                    gifTextView.setText(s + "%");
                    if (num >= 90){
                        gifTextView.setBackgroundResource(R.drawable.nine);
                        return;
                    }else if (num >=80 && num <= 89){
                        gifTextView.setBackgroundResource(R.drawable.eight);
                        return;
                    }else if (num >=70 && num <= 79){
                        gifTextView.setBackgroundResource(R.drawable.eight);
                        return;
                    }else {
                        gifTextView.setBackgroundResource(R.drawable.six);
                    }

                }
            }.execute();

        return view;

    }

}
