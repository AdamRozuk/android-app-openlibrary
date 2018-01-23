package com.example.myapp11;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.String;
import java.util.List;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity  {
    private static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/"; // 13
    private static final String EBOOK_URL= "https://openlibrary.org";
    String mImageURL; // 13
    private static final String istrue1 = "true";
    //DetailInteractions interactions;
    //private void setInteractions(DetailInteractions interactions) {
    //    this.interactions = interactions;
    //}



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Tell the activity which XML layout is right
        setContentView(R.layout.activity_detail);

        // Enable the "Up" button for more navigation options
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Access the imageview from XML
        ImageView imageView = (ImageView) findViewById(R.id.img_cover);
        TextView titleView = (TextView) findViewById(R.id.detail_title);
        TextView authorView = (TextView) findViewById(R.id.detail_author);
        TextView publishView = (TextView) findViewById(R.id.tvPublisher);
        TextView pagecountView = (TextView) findViewById(R.id.tvPageCount);
        TextView public_scan_bView= (TextView) findViewById(R.id.bool);
        TextView boolView  = (TextView) findViewById(R.id.public_scan_b);
        // 13. unpack the coverID from its trip inside your Intent
        String coverID = this.getIntent().getExtras().getString("coverID");
        String bookTitle = this.getIntent().getExtras().getString("bookTitle");
        String authorName = this.getIntent().getExtras().getString("authorName");
        String publisherName = this.getIntent().getExtras().getString("publisherName");
        String pageCount = this.getIntent().getExtras().getString("pageCount");
        String isBool1 = this.getIntent().getExtras().getString("isBool1");
        String link = this.getIntent().getExtras().getString("link");



// See if there is a valid coverID
        if (coverID.length() > 0) {

            // Use the ID to construct an image URL
            mImageURL = IMAGE_URL_BASE + coverID + "-L.jpg";

            // Use Picasso to load the image
            Picasso.with(this).load(mImageURL).placeholder(R.drawable.img_books_loading).into(imageView);
        }
        if (bookTitle != null) {
            titleView.setText("Book title: "+bookTitle);

        }
        if (authorName != null) {
            authorView.setText("Author: "+authorName);

        }
        if (publisherName != null) {
            publishView.setText("Published by: "+publisherName);

        }
        if (pageCount != null) {
            pagecountView.setText("First sentence: "+pageCount);

        }
        if (isBool1 != null) {
            if (isBool1.equals(istrue1)){
                boolView.setText("We got book \""+bookTitle+"\" to read on-line for free, link below:");
                public_scan_bView.setText(EBOOK_URL+link);


            }
            else {
                boolView.setText("");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuitem){
        switch (menuitem.getItemId()){
            case R.id.menu_main_add:
                addToLibrary();
                return true;
            case R.id.menu_main_browse:
                browseLibrary();
                return true;
            default:
                return false;
        }
    }

    public void addToLibrary(){

        Entry entryy = new Entry();

        TextView titleView = (TextView) findViewById(R.id.detail_title);
        String bookTitle = this.getIntent().getExtras().getString("bookTitle");
        TextView authorView = (TextView) findViewById(R.id.detail_author);
        String authorName = this.getIntent().getExtras().getString("authorName");

        //Log.d("addtolibr2", bookTitle +" "+authorName);

        entryy.setTitle(bookTitle);
        entryy.setDate(authorName);
        Baza.getInstance().setBase(entryy);

        Toast.makeText(getApplicationContext(), "Dodano do biblioteki ", Toast.LENGTH_SHORT).show();
        //return entry;

    }

    private  void browseLibrary(){
        Toast.makeText(getApplicationContext(), "Jesteś w bibliotece ", Toast.LENGTH_SHORT).show();
        Intent libraryIntent = new Intent(DetailActivity.this, Library.class);
        startActivity(libraryIntent);

    }


}


