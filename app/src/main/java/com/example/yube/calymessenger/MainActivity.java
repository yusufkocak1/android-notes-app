package com.example.yube.calymessenger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.yube.calymessenger.Contact.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("notes");

        exitBTN = findViewById(R.id.logoutBtn);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.note_recyc);

        final ArrayList noteList  = new ArrayList<>();
        final noteAdapter adapter = new noteAdapter(this, noteList);

        

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds:dataSnapshot.getChildren()
                     ) {
                    noteList.add(new Note(1, ds.child("head").getValue(String.class), ds.child("content").getValue(String.class), ds.child("date").getValue(String.class), ds.child("type").getValue(String.class), 1));
                adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

         


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

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
}
