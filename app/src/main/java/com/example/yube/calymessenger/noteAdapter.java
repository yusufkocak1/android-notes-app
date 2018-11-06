package com.example.yube.calymessenger;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        public TextView  contentTV, dateTV;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);

            //headTV = view.findViewById(R.id.headTV);
            contentTV = view.findViewById(R.id.contentTV);
            dateTV = view.findViewById(R.id.dateTV);

           // title = (TextView) view.findViewById(R.id.title);
          //  count = (TextView) view.findViewById(R.id.count);
          //  thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
          //  overflow = (ImageView) view.findViewById(R.id.overflow);

            final deleteAlert deletealert=new deleteAlert();

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Snackbar.make(view, "long pressed", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    deletealert.showdialog("sdfg");
                    return false;
                }
            });
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
//if (note.getHead().equals("")){
//    LinearLayout.LayoutParams lastTxtParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//    lastTxtParams.setMargins(0, -40, 0, 40);
//
//    holder.contentTV.setLayoutParams(lastTxtParams);
//    holder.contentTV.invalidate();
//    //lastTxtParams.height=0;
//    holder.headTV.setVisibility(false);
//
//}
       // holder.headTV.setText(note.getHead());
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





    public class deleteAlert {


        public boolean showdialog(String note_id) {
            final Dialog dialog = new Dialog(mContext);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.delete_alert);


            dialog.show();

            return false;
        }


    }

}