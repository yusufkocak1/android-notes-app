package com.example.yube.calymessenger;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.yube.calymessenger.Contact.Note;
import com.example.yube.calymessenger.Contact.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button exitBTN;

    //  private TextView testET;
//public WebView mebis;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        progDailog.setCancelable(false);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("notes");
        final DatabaseReference userref = database.getReference("users");

        final String[] name = {""};
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()
                        ) {
                    if (ds.child("email").getValue().toString().equals(mAuth.getCurrentUser().getEmail())) {
                        name[0] = ds.child("name").getValue().toString();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        exitBTN = findViewById(R.id.logoutBtn);


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.note_recyc);

        final ArrayList noteList = new ArrayList<>();
        final noteAdapter adapter = new noteAdapter(this, noteList);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()
                        ) {


                    if (ds.child("email").getValue().toString().equals(mAuth.getCurrentUser().getEmail())) {

                        noteList.add(new Note(ds.child("head").getValue(String.class), ds.child("content").getValue(String.class), ds.child("date").getValue(String.class), ds.child("type").getValue(String.class), ds.child("email").getValue(String.class)));



                        adapter.notifyDataSetChanged();
                    }

                }
                progDailog.dismiss();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                addDialog dialog = new addDialog();
                dialog.showdialog(mAuth.getCurrentUser().getEmail(), view);
//                Snackbar.make(view, "Üzgünüm dostum henüz not alamıyorsun Biliyorum çok saçma :D", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


//                adapter.notifyDataSetChanged();

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                fab.hide();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fab.show();                    }
                }, 1000);
            }
        });

// Kotlin



        exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    {



      /*  //testET = findViewById(R.id.testET);
       // new jsoup().execute();




mebis=findViewById(R.id.webviev);
        Activity activity = this;

        final ProgressDialog progDailog = ProgressDialog.show(activity, "Loading","Please wait...", true);
        progDailog.setCancelable(false);


        mebis.getSettings().setJavaScriptEnabled(true);
        mebis.getSettings().setLoadWithOverviewMode(true);
        mebis.getSettings().setUseWideViewPort(true);
        mebis.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progDailog.show();
                view.loadUrl(url);

                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progDailog.dismiss();
            }
        });



mebis.loadUrl("https://mebis.medipol.edu.tr/DersProgramlari?pProgramOID=2");
*/

/*
    private class jsoup extends AsyncTask<Void, Void, Void> {
        String text="İnternet bağlantınız kopmuş yahut kurbağa hızında :(";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://mebis.medipol.edu.tr/DersProgramlari?pProgramOID=2").timeout(10000).get();


                text=doc.getElementsByTag("td").html();



            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView

        //    testET.setText(text);
        }



    }*/
    }//silinecek

    class addDialog {

        Button saveBTN;
        Button cancelBTN;
        EditText headtext;
        EditText contenttext;


        public boolean showdialog(String email, final View v) {
            final Dialog dialog = new Dialog(MainActivity.this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.note_add_alert);

            headtext = dialog.findViewById(R.id.alertheadET);
            contenttext = dialog.findViewById(R.id.alertcontentET);
            saveBTN = dialog.findViewById(R.id.alertaddBTN);
            cancelBTN = dialog.findViewById(R.id.alertcancelBTN);


            saveBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (saveNote()) {
                        Snackbar.make(v, "your note has been saved", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        dialog.dismiss();

                        finish();
                        startActivity(getIntent());

                    } else
                        Snackbar.make(view, "your note could not be saved", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                }
            });

            cancelBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();

                }
            });

            dialog.show();

            return false;
        }

        private boolean saveNote() {
            FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
            String noteId = "";
            DatabaseReference mFirebaseDatabase = mFirebaseInstance.getReference("notes");

            if (TextUtils.isEmpty(noteId)) {
                noteId = mFirebaseDatabase.push().getKey();
            }
            Date date = new Date();
            CharSequence s = android.text.format.DateFormat.format("MM/dd/yyyy", date.getTime());
            //DateFormat.getDateInstance(DateFormat.SHORT).format(date);

            FirebaseAuth auth = FirebaseAuth.getInstance();
            Note note = new Note("" + headtext.getText().toString(), contenttext.getText().toString(), s.toString(), "warn", auth.getCurrentUser().getEmail().toString());

            mFirebaseDatabase.child(noteId).setValue(note);
            return true;


        }
    }

}
