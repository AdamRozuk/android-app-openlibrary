package com.example.myapp11;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;
import com.inverce.mod.core.IM;

import java.util.List;

/**
 * Created by adam on 05.01.18.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{

    public List<Entry> data;

    public ListAdapter(List<Entry> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(IM.context()).inflate(R.layout.library_entry_row, parent, false);

        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Entry> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addTask(Entry entry) {
        data.add(entry);
        notifyDataSetChanged();
    }

    public void removeSelected() {
        int firstSize = data.size();
        data = Stream.of(data)
                .filter(new Predicate<Entry>() {
                    @Override
                    public boolean test(Entry value) {
                        return !value.isSelected();
                    }
                }).collect(Collectors.<Entry>toList());
        notifyDataSetChanged();
        int afterSize = data.size();
        int deleted = firstSize - afterSize;
        Toast.makeText(IM.context(), "Usunięto ", Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView date;
        private View itemView;
        private Entry entry;

        public ViewHolder(View itemView){
            super(itemView);
            this.itemView = itemView;
            title = (TextView) itemView.findViewById(R.id.list_item_entry_title);
            date = (TextView) itemView.findViewById(R.id.list_item_entry_date);

            itemView.setOnLongClickListener(e -> onClick1());
        }

        public boolean onClick1(){
            entry.setSelected(!entry.isSelected());
            itemView.setBackgroundResource(entry.isSelected() ? R.drawable.light_brawn : R.drawable.light_brawn);
            Baza.getInstance().deleteBase(entry);
            notifyDataSetChanged();
            Toast.makeText(IM.context(), "Usunięto ", Toast.LENGTH_SHORT).show();

            return true;
        }



        public void bindData(Entry entry) {
            title.setText(entry.getTitle());
            date.setText(entry.getDate());
            this.entry=entry;
        }

    }



}
