package com.example.myapp11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;


public class JSONAdapter extends BaseAdapter {

    private static final String IMAGE_URL_BASE = "http://covers.openlibrary.org/b/id/";
    private static final String istrue2 = "true";
    private static final String isfalse2 = "false";
    private static final String inothink2 = "";

    Context mContext;
    LayoutInflater mInflater;
    JSONArray mJsonArray;

    public JSONAdapter(Context context, LayoutInflater inflater) {
        mContext = context;
        mInflater = inflater;
        mJsonArray = new JSONArray();
    }
    public void updateData(JSONArray jsonArray) {
        // update the adapter's dataset
        mJsonArray = jsonArray;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mJsonArray.length();
    }

    @Override
    public Object getItem(int i) {
        return mJsonArray.optJSONObject(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        public ImageView thumbnailImageView;
        public ImageView ebookView;
        public TextView titleTextView;
        public TextView authorTextView;
        public TextView publishTextView;
        public TextView pagecountTextView;
        public TextView public_scan_bTextView;
        public TextView boolTextView;
        public TextView e_bookTextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // check if the view already exists
        // if so, no need to inflate and findViewById again!
        if (convertView == null) {

            // Inflate the custom row layout from your XML.
            convertView = mInflater.inflate(R.layout.row_book, null);

            // create a new "Holder" with subviews
            holder = new ViewHolder();
            holder.thumbnailImageView = (ImageView) convertView.findViewById(R.id.lib_thumbnail);
            holder.titleTextView = (TextView) convertView.findViewById(R.id.lib_title);
            holder.authorTextView = (TextView) convertView.findViewById(R.id.lib_author);
            holder.publishTextView = (TextView) convertView.findViewById(R.id.text_publisher);
            holder.pagecountTextView = (TextView) convertView.findViewById(R.id.text_count);
            holder.public_scan_bTextView = (TextView) convertView.findViewById(R.id.row_public_scan_b);
            holder.boolTextView = (TextView) convertView.findViewById(R.id.row_bool);
            holder.ebookView = (ImageView) convertView.findViewById(R.id.img_e_book);
            holder.e_bookTextView = (TextView) convertView.findViewById(R.id.text_e_book);

            // hang onto this holder for future recyclage
            convertView.setTag(holder);
        } else {

            // skip all the expensive inflation/findViewById
            // and just get the holder you already made
            holder = (ViewHolder) convertView.getTag();
        }
        // More code after this
// Get the current book's data in JSON form
        JSONObject jsonObject = (JSONObject) getItem(position);

// See if there is a cover ID in the Object
        if (jsonObject.has("cover_i")) {

            // If so, grab the Cover ID out from the object
            String imageID = jsonObject.optString("cover_i");

            // Construct the image URL (specific to API)
            String imageURL = IMAGE_URL_BASE + imageID + "-M.jpg";//-M

            // Use Picasso to load the image
            // Temporarily have a placeholder in case it's slow to load
            Picasso.with(mContext).load(imageURL).placeholder(R.drawable.img_books_large).into(holder.thumbnailImageView);
        } else {

            // If there is no cover ID in the object, use a placeholder
            holder.thumbnailImageView.setImageResource(R.drawable.img_books_large);

        }
        // Grab the title and author from the JSON
        String bookTitle = "";
        String authorName = "";
        String publisherName = "";
        String pageCount = "";
        String isBool1  = "";
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
            if (isBool1.equals(isfalse2)) {
                holder.e_bookTextView.setText("e-book: ");
                holder.ebookView.setImageResource(R.drawable.delete);
            }
            else if (isBool1.equals(istrue2)){
                holder.e_bookTextView.setText("e-book: ");
                holder.ebookView.setImageResource(R.drawable.yes);
            }
            else if (isBool1.equals(inothink2)){
                holder.e_bookTextView.setText("e-book: ");
                holder.ebookView.setImageResource(R.drawable.help);
            }
            else {
                holder.e_bookTextView.setText("e-book: ");
                holder.ebookView.setImageResource(R.drawable.help);
            }
        }
        else {
            holder.e_bookTextView.setText("e-book: ");
            holder.ebookView.setImageResource(R.drawable.help);
        }

        if (jsonObject.has("key")) {
            isBool1 = jsonObject.optString("key");
        }


// Send these Strings to the TextViews for display
        holder.titleTextView.setText(bookTitle);
        holder.authorTextView.setText(authorName);
        holder.publishTextView.setText(publisherName);
        holder.pagecountTextView.setText(pageCount);
        holder.public_scan_bTextView.setText(link);
        holder.boolTextView.setText(isBool1);
        return convertView;
    }

}