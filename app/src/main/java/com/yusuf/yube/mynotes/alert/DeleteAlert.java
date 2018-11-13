package com.yusuf.yube.mynotes.alert;


import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.yusuf.yube.mynotes.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteAlert {

    Button yes;
    Button no;
boolean temp=false;
    Dialog dialog;

    public DeleteAlert() {
    }

    public boolean showdialog(final String note_id, final Activity mContext) {
        dialog = new Dialog(mContext);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.delete_alert);
        yes = dialog.findViewById(R.id.btn_yes);
        no = dialog.findViewById(R.id.btn_no);



        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
                DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("notes");
                mFirebaseDatabase.child(note_id).removeValue();
                temp=true;
                mContext.finish();
                mContext.startActivity(mContext.getIntent());
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp=false;
                dialog.dismiss();
            }
        });
        dialog.show();

return temp;

    }


}
