package com.yusuf.yube.mynotes.alert;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yusuf.yube.mynotes.Contact.Note;
import com.yusuf.yube.mynotes.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateAlert {

    Button yes;
    Button no;
    Button copyBTN;
    Button pasteBTN;
    EditText contentET;
    TextView dateTV;
    boolean temp = false;
    Dialog dialog;



    public boolean showdialog(final Note note, final Activity mContext) {
        dialog = new Dialog(mContext);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.update_alert);
        yes = dialog.findViewById(R.id.alertUpdateAddBTN);
        no = dialog.findViewById(R.id.alertUpdateCancelBTN);
        contentET = dialog.findViewById(R.id.alertUpdateContentET);
        dateTV = dialog.findViewById(R.id.alertUpdateDateTV);
        copyBTN=dialog.findViewById(R.id.alertUpdateCopyBTN);
        pasteBTN=dialog.findViewById(R.id.alertUpdatePasteBTN);

        contentET.setText(note.getContent());
        dateTV.setText(note.getDate());


        pasteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                try {
                    contentET.setText(contentET.getText().toString()+clipboard.getPrimaryClip().getItemAt(0).getText());
                } catch (Exception e) {
                    return;
                }
            }
        });
        copyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("text", contentET.getText());
                clipboard.setPrimaryClip(clip);
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase mF = FirebaseDatabase.getInstance();
                DatabaseReference mFirebaseDatabase = mF.getReference("notes");
                Toast.makeText(mContext,note.getId(),Toast.LENGTH_SHORT).show();

                mFirebaseDatabase.child(note.getId()).setValue(new Note(contentET.getText().toString(),note.getDate(),note.getType(),note.getEmail()));

dialog.dismiss();

                temp = true;
                mContext.finish();
                mContext.startActivity(mContext.getIntent());

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = false;
                dialog.dismiss();
            }
        });


        dialog.show();
        return temp;

    }


}


