package android.example.com.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openBusinessActivity(View view) {
        Intent intent = new Intent(this, BusinessActivity.class);
        startActivity(intent);
    }

    public void openPoliticsActivity(View view) {
        Intent intent = new Intent(this, PoliticsActivity.class);
        startActivity(intent);
    }

    public void openSportActivity(View view) {
        Intent intent = new Intent(this, SportActivity.class);
        startActivity(intent);
    }

    public void openFashionActivity(View view) {
        Intent intent = new Intent(this, FashionActivity.class);
        startActivity(intent);
    }

}
