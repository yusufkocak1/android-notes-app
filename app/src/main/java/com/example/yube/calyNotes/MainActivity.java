package com.example.yube.calyNotes;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;

import com.example.yube.calyNotes.Contact.Note;
import com.example.yube.calymessenger.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button exitBTN;
  //  private TextView testET;
public WebView mebis;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();


      //  exitBTN = findViewById(R.id.logoutBtn);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.note_recyc);

        ArrayList noteList  = new ArrayList<>();
       noteAdapter adapter = new noteAdapter(this, noteList);

        noteList.add(new Note());
        noteList.add(new Note());
        noteList.add(new Note());
        noteList.add(new Note());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);















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
      /*  exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
*/
    }
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
}
