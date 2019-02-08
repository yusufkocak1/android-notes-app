package com.yusuf.yube.mynotes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yusuf.yube.mynotes.Contact.Note;
import com.yusuf.yube.mynotes.OflineMode.OfflineMode;
import com.yusuf.yube.mynotes.alert.DeleteAlert;
import com.yusuf.yube.mynotes.alert.UpdateAlert;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //left navigation
    NavigationView navigationView;
    View headerLayout;
    TextView navHeaderName;
    TextView navHeaderEmail;
    ImageView navHeaderAvatar;

    //main
    private Button exitBTN;
    private FloatingActionButton fab;
    private ProgressDialog progDailog;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference userref;
    private String name = "";
    private String email = "";
    //recyclerView
    private RecyclerView recyclerView;
    private ArrayList<Note> noteList = new ArrayList<>();
    private noteAdapter adapter = new noteAdapter(this, noteList);
    private int gender;

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

        if (!checkInternet()) {
            Intent offlineIntent = new Intent(MainActivity.this, OfflineMode.class);
            startActivity(offlineIntent);
            finish();
        }
        getDesignElement();

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("notes");
        userref = database.getReference("users");
        setEmail(mAuth.getCurrentUser().getEmail());

        userListener();
        noteListener();
        recView();


    }

    private void navHeader() {
        navHeaderName.setText(name);
        navHeaderEmail.setText(email);
        switch (gender){
            case 1:
                navHeaderAvatar.setBackgroundResource(R.drawable.man);
                break;
            case 0:
                navHeaderAvatar.setBackgroundResource(R.drawable.woman);
                break;
             default:
                 navHeaderAvatar.setBackgroundResource(R.drawable.logo);
        }
    }

    private void recView() {
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                //Values are passing to activity & to fragment as well
                new UpdateAlert().showdialog(noteList.get(position), MainActivity.this);
            }
            @Override
            public void onLongClick(View view, int position) {
                new DeleteAlert().showdialog(noteList.get(position).getId(), MainActivity.this);
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
                        fab.show();
                    }
                }, 1000);
            }
        });
    }

    private void noteListener() {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("email").getValue().toString().equals(mAuth.getCurrentUser().getEmail())) {
                        noteList.add(new Note(ds.getKey(), ds.child("content").getValue(String.class), ds.child("date").getValue(String.class), ds.child("type").getValue(String.class), ds.child("email").getValue(String.class)));
                        progDailog.dismiss();
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });


    }

    private void userListener() {
        userref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()
                ) {
                    name=mAuth.getCurrentUser().getEmail();
                    if (ds.child("email").getValue().toString().equals(mAuth.getCurrentUser().getEmail())) {
                        name = ds.child("name").getValue().toString();
                        gender=Integer.parseInt(ds.child("gender").getValue().toString());
                        navHeader();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected boolean checkInternet() { //interneti kontrol eden method
        // TODO Auto-generated method stub
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    private void getDesignElement() {
        //new not Fab
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        //start loading progress Dialog

        progDailog = ProgressDialog.show(this, "Loading", "Please wait...", true);
        progDailog.setCancelable(false);

        //logout Button in navbar
        exitBTN = findViewById(R.id.logoutBtn);
        exitBTN.setOnClickListener(this);

        //navigation
        navigationView = findViewById(R.id.nav_view);
        headerLayout = navigationView.getHeaderView(0);
        navHeaderName = headerLayout.findViewById(R.id.navName);
        navHeaderEmail=headerLayout.findViewById(R.id.navEmail);
        navHeaderAvatar=headerLayout.findViewById(R.id.navAvatar);


        //recyclerView
        recyclerView = (RecyclerView) findViewById(R.id.note_recyc);
    }

    @Override
    public void onClick(View v) {
        navHeaderName.setText("" + v.getId());
        switch (v.getId()) {
            case R.id.fab:
                fab(v);
                break;
            case R.id.logoutBtn:
                exitBTN();
                break;

        }
    }

    //logout buton
    private void exitBTN() {
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //fab onclick
    private void fab(View v) {
        addDialog dialog = new addDialog();
        if (dialog.showdialog(getEmail(), v)) {

        }
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


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
            copyBTN = dialog.findViewById(R.id.alertCopyBTN);
            pasteBTN = dialog.findViewById(R.id.alertPasteBTN);
            dateTV = dialog.findViewById(R.id.alertDateTV);

            dateTV.setText(getDate());

            pasteBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    try {
                        contenttext.setText(contenttext.getText().toString() + clipboard.getPrimaryClip().getItemAt(0).getText());
                        Snackbar.make(view, "Pasted!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
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
                    Snackbar.make(view, "Copied!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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
            Note note = new Note(noteId, String.valueOf(contenttext.getText()), getDate(), "warn", auth.getCurrentUser().getEmail());

            mFirebaseDatabase.child(noteId).setValue(note);
            return true;


        }

        public String getDate() {
            Date date = new Date();
            CharSequence s = android.text.format.DateFormat.format("MM-dd-yyyy", date.getTime());

            return s.toString();
        }
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
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
