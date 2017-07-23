package android.example.com.news;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mohanad on 22/07/17.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(@NonNull Context context, @NonNull ArrayList<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        //getting the News object
        News news = getItem(position);

        //getting the news data
        String title = news.getTitle();
        String section = news.getSection();

        //getting the list item views
        TextView newsTitle=(TextView)convertView.findViewById(R.id.news_title);
        TextView newsSection=(TextView)convertView.findViewById(R.id.news_section);

        //setting the list item views to display news data
        newsTitle.setText(title);
        newsSection.setText(section);

        return  convertView;
    }
}
