package android.example.com.news;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SportActivity  extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<ArrayList<News>> {

    //progress dialog
    private ProgressDialog pDialog;

    /*the news business url*/
    private String SPORT_NEWS_URL="http://content.guardianapis.com/search?q=sport&api-key=test";

    /*the news listView*/
    private ListView newsListView;

    /*the emptystate TextView (no data , no internet connection)*/
    private TextView emptyState;

    /*the news data array list*/
    private ArrayList<News> newsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_list);

        //getting listview
        newsListView = (ListView)findViewById(R.id.news_listview);
        emptyState = (TextView) findViewById(R.id.emptystate_textview);
        newsListView.setEmptyView(emptyState);

        /*check if the device is connected to network */
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        } else{
            //set the emptystate to "no internet connection"
            emptyState.setText(getString(R.string.no_internet_connection));
        }

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(newsArrayList.get(position).getWebURL()));
                startActivity(browserIntent);
            }
        });
    }

    private void updateUi(ArrayList<News> news) {
        this.newsArrayList=news;
        NewsAdapter adapter = new NewsAdapter(this, news);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(adapter);
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        pDialog = new ProgressDialog(SportActivity.this);
        pDialog.setMessage("Loading..");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        return new NewsLoader(this,SPORT_NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        pDialog.dismiss();
        emptyState.setText(getString(R.string.no_data_found));
        updateUi(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {

    }
}