package com.example.news_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.news_app.Models.NewsApiResponse;
import com.example.news_app.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener, View.OnClickListener {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button b1,b2,b3,b4,b5,b6,b7;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    searchView= findViewById(R.id.search_view);

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            dialog.setTitle("Fetching news articles of " + query);
            dialog.show();
            RequestManager manager = new RequestManager(MainActivity.this);
            manager.getNewsHeadlines(listener , "general",query );

            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    });

        dialog= new ProgressDialog(this);
    dialog.setTitle("Fetching news articles...");
    dialog.show();

    b1=findViewById(R.id.btn1);
    b1.setOnClickListener(this);
        b2=findViewById(R.id.btn2);
        b2.setOnClickListener(this);
        b3=findViewById(R.id.btn3);
        b3.setOnClickListener(this);
        b4=findViewById(R.id.btn4);
        b4.setOnClickListener(this);
        b5=findViewById(R.id.btn5);
        b5.setOnClickListener(this);
        b6=findViewById(R.id.btn6);
        b6.setOnClickListener(this);
        b7=findViewById(R.id.btn7);
        b7.setOnClickListener(this);


        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener , "general",null );
    }

    private final OnFetchDataListener<NewsApiResponse> listener= new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            if(list.isEmpty()){
                Toast.makeText(MainActivity.this, "No Data Found!!", Toast.LENGTH_SHORT).show();
            }else {
                showNews(list);
                dialog.dismiss();
            }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, "An Error Ocurred!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager((new GridLayoutManager(this,1)));
        adapter = new CustomAdapter(this,list,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsCLicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this,DetailsActivity.class)
                .putExtra("data",headlines)
        );
    }

    @Override
    public void onClick(View v) {
        Button button=(Button) v;
        String category=button.getText().toString();

        dialog.setTitle("Fetching News articles of " +category);

        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener , category,null );

    }
}