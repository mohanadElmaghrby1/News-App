package android.example.com.news;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mohanad on 22/07/17.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    /*the  news api request url*/
    private String REQUEST_URL;

    /*the log tag*/
    private String LOG_TAG=NewsLoader.class.getSimpleName();

    public NewsLoader(Context context , String requestUrl) {
        super(context);
        this.REQUEST_URL=requestUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        //create arraylist of News
        ArrayList<News> list=null;
        try {
            list= QueryUtils.fetchListOfNewsData(REQUEST_URL);
        }
        catch (Exception e){
            Log.e(LOG_TAG, "error loading news data" , e);
        }
        return list;
    }
}
