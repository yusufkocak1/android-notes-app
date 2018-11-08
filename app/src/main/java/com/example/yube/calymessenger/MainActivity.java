package com.example.yube.calymessenger;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
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
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yube.calymessenger.Contact.Note;
import com.example.yube.calymessenger.Contact.user;
import com.example.yube.calymessenger.alert.DeleteAlert;
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

        final ArrayList<Note> noteList = new ArrayList<>();
        final noteAdapter adapter = new noteAdapter(this, noteList);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()
                        ) {


                    if (ds.child("email").getValue().toString().equals(mAuth.getCurrentUser().getEmail())) {

                        noteList.add(new Note(ds.getKey(),ds.child("content").getValue(String.class), ds.child("date").getValue(String.class), ds.child("type").getValue(String.class), ds.child("email").getValue(String.class)));



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
               if( dialog.showdialog(mAuth.getCurrentUser().getEmail(), view)){

               }
//                Snackbar.make(view, "Üzgünüm dostum henüz not alamıyorsun Biliyorum çok saçma :D", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();


//                adapter.notifyDataSetChanged();

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                Toast.makeText(MainActivity.this, "Single Click on position        :"+position,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();

                new DeleteAlert().showdialog(noteList.get(position).getId(),MainActivity.this);

            }
        }));

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
        Button copyBTN;
        Button pasteBTN;
        EditText contenttext;
        TextView dateTV;

        public boolean showdialog(String email, final View v) {
            final Dialog dialog = new Dialog(MainActivity.this);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.note_add_alert);

           // headtext = dialog.findViewById(R.id.alertheadET);
            contenttext = dialog.findViewById(R.id.alertcontentET);
            saveBTN = dialog.findViewById(R.id.alertaddBTN);
            cancelBTN = dialog.findViewById(R.id.alertcancelBTN);
            copyBTN=dialog.findViewById(R.id.alertCopyBTN);
            pasteBTN=dialog.findViewById(R.id.alertPasteBTN);
            dateTV=dialog.findViewById(R.id.alertDateTV);

            dateTV.setText(getDate());

            pasteBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    try {
                        contenttext.setText(contenttext.getText().toString()+clipboard.getPrimaryClip().getItemAt(0).getText());
                    } catch (Exception e) {
                        return;
                    }
                }
            });
            copyBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", contenttext.getText());
                    clipboard.setPrimaryClip(clip);
                }
            });

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
            //DateFormat.getDateInstance(DateFormat.SHORT).format(date);

            FirebaseAuth auth = FirebaseAuth.getInstance();
            Note note = new Note(noteId,contenttext.getText().toString(), getDate(), "warn", auth.getCurrentUser().getEmail().toString());

            mFirebaseDatabase.child(noteId).setValue(note);
            return true;


        }

    public String getDate(){
        Date date = new Date();
        CharSequence s = android.text.format.DateFormat.format("MM-dd-yyyy", date.getTime());

        return s.toString();
    }
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
