package com.example.yube.calymessenger;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yube.calymessenger.Contact.Note;

import java.util.List;

/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class noteAdapter extends RecyclerView.Adapter<noteAdapter.MyViewHolder> {

    private Context mContext;
    private List<Note> noteList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView headTV, contentTV, dateTV;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);

            headTV = view.findViewById(R.id.headTV);
            contentTV = view.findViewById(R.id.contentTV);
            dateTV = view.findViewById(R.id.dateTV);

           // title = (TextView) view.findViewById(R.id.title);
          //  count = (TextView) view.findViewById(R.id.count);
          //  thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
          //  overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public noteAdapter(Context mContext, List<Note> albumList) {
        this.mContext = mContext;
        this.noteList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.not_cardlayout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Note note = noteList.get(position);
      //  holder.title.setText(album.getName());
       // holder.count.setText(album.getNumOfSongs() + " songs");

        holder.headTV.setText(note.getHead());
        holder.contentTV.setText(note.getContent());
        holder.dateTV.setText(note.getDate());

        // loading album cover using Glide library
       // Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);


    }

    /**
     * Showing popup menu when tapping on 3 dots
     */


    /**
     * Click listener for popup menu items
     */


    @Override
    public int getItemCount() {
        return noteList.size();
    }
}