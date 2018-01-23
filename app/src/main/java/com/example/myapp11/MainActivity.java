package com.example.myapp11;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private static final String QUERY_URL = "http://openlibrary.org/search.json?q=";
    Button searchButton;
    EditText searchBookEditText;
    JSONAdapter mJSONAdapter;
    ListView mainListView;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("Searching for Book");
        mDialog.setCancelable(false);

        searchButton = findViewById(R.id.searchButton);
        searchBookEditText = findViewById(R.id.searchBookEditText);
        mainListView = findViewById(R.id.listView);

        mJSONAdapter = new JSONAdapter(this, getLayoutInflater());

// Set the ListView to use the ArrayAdapter
        mainListView.setAdapter(mJSONAdapter);
        searchButton.setOnClickListener((v) -> {
            queryBooks(searchBookEditText.getText().toString());
        });

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 12. Now that the user's chosen a book, grab the cover data
                JSONObject jsonObject = (JSONObject) mJSONAdapter.getItem(i);
                String coverID = jsonObject.optString("cover_i", "");
                String bookTitle = "";
                String authorName = "";
                String publisherName = "";
                String pageCount = "";
                String isBool1 = "";
                String link = "";

                if (jsonObject.has("title")) {
                    bookTitle = jsonObject.optString("title");
                }

                if (jsonObject.has("author_name")) {
                    authorName = jsonObject.optJSONArray("author_name").optString(0);
                }

                if (jsonObject.has("publisher")) {
                    publisherName = jsonObject.optJSONArray("publisher").optString(0);
                }

                if (jsonObject.has("first_sentence")) {
                    pageCount = jsonObject.optString("first_sentence");
                }

                if (jsonObject.has("public_scan_b")) {
                    isBool1 = jsonObject.optString("public_scan_b");
                }

                if (jsonObject.has("key")) {
                    link = jsonObject.optString("key");
                }

// create an Intent to take you over to a new DetailActivity
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);

// pack away the data about the cover
// into your Intent before you head out
                detailIntent.putExtra("coverID", coverID);
                detailIntent.putExtra("bookTitle", bookTitle);
                detailIntent.putExtra("authorName", authorName);
                detailIntent.putExtra("publisherName", publisherName);
                detailIntent.putExtra("pageCount", pageCount);
                detailIntent.putExtra("isBool1", isBool1);
                detailIntent.putExtra("link", link);


// start the next Activity using your prepared Intent
                startActivity(detailIntent);
            }
        });
    }


    private void queryBooks(String searchString) {

        // Prepare your search string to be put in a URL
        // It might have reserved characters or something
        String urlString = "";
        try {
            urlString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException e) {

            // if this fails for some reason, let the user know why
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Create a client to perform networking
        AsyncHttpClient client = new AsyncHttpClient();
// Show ProgressDialog to inform user that a task in the background is occurring
        mDialog.show();
        // Have the client get a JSONArray of data
        // and define how to respond
        client.get(QUERY_URL + urlString,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // 11. Dismiss the ProgressDialog
                        mDialog.dismiss();
                        // Display a "Toast" message
                        // to announce your success
                        Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
                        mJSONAdapter.updateData(jsonObject.optJSONArray("docs"));
                    }

                    @Override
                    public void onFailure(int statusCode, Throwable throwable, JSONObject error) {
                        // 11. Dismiss the ProgressDialog
                        mDialog.dismiss();
                        // Display a "Toast" message
                        // to announce the failure
                        Toast.makeText(getApplicationContext(), "Error: " + statusCode + " " + throwable.getMessage(), Toast.LENGTH_LONG).show();

                        // Log error message
                        // to help solve any problems
                        Log.e("MainActivity", statusCode + " " + throwable.getMessage());
                    }
                });
    }
}
