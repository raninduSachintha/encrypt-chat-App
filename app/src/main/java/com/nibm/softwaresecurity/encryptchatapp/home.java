package com.nibm.softwaresecurity.encryptchatapp;

import static com.nibm.softwaresecurity.encryptchatapp.cryptography.generateKey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.SecretKey;

public class home extends AppCompatActivity {

    Button btnSendMsg;
    EditText etMsg;
    TextView user;
    ListView Discussion;
    ArrayList<String> listConversation = new ArrayList<String>();
    ArrayAdapter arrayAdpt;
    String user_msg_key, cipher, planeText , svMsg;
    private DatabaseReference dbr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        String loggeduser;
        Intent in = getIntent();
        loggeduser = in.getStringExtra("user");

        btnSendMsg = (Button) findViewById(R.id.btnSendMsg);
        etMsg = (EditText) findViewById(R.id.etMessage);
        user = findViewById(R.id.user);
        Discussion = (ListView) findViewById(R.id.Conversation);
        arrayAdpt = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listConversation);
        Discussion.setAdapter(arrayAdpt);
        dbr = FirebaseDatabase.getInstance().getReference();

        user.setText(loggeduser);

        cryptography cry = new cryptography();



        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    SecretKey secretKey=generateKey("2b7e151628aed2a6abf71589");
                    cipher =cry.encryptMsg(etMsg.getText().toString(),secretKey);
                }catch(Exception e){
                    e.printStackTrace();
                }

                Map<String, Object> map = new HashMap<String, Object>();
                user_msg_key = dbr.push().getKey();
                dbr.updateChildren(map);

                DatabaseReference dbr2 = dbr.child(user_msg_key);


                String timeStamp = new SimpleDateFormat("yy/MM/dd  HH:mm").format(Calendar.getInstance().getTime());

                svMsg = user.getText().toString() + "\n" + cipher + "\n" + timeStamp;
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("msg", svMsg );
                dbr2.updateChildren(map1);
                etMsg.setText("");
            }
        });
        dbr.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("onChildAdded", "onChildAdded");
                updateConversation(dataSnapshot);

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i("onChildChanged", "onChildChanged");
                updateConversation(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void updateConversation(DataSnapshot dataSnapshot){
        String msg, conversation , chiperText , user , time;
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            msg = (String) ((DataSnapshot)i.next()).getValue();

            String[] lines = msg.split("\n");

            user = lines[0];
            chiperText =lines[1];
            time= lines[2];

            cryptography cry = new cryptography();
            try{
                SecretKey secretKey=generateKey("2b7e151628aed2a6abf71589");
                planeText=cry.decryptMsg(chiperText,secretKey);
            }catch(Exception e){
                e.printStackTrace();
            }

            String resultMsg = "\uD83D\uDE4E    " +user + ":\n\uD83D\uDCAC    " + planeText + "\n\uD83D\uDD57    " + time + "\n";


            conversation = resultMsg;
            //Log.i("my", msg);
            arrayAdpt.insert(conversation, arrayAdpt.getCount());
            arrayAdpt.notifyDataSetChanged();
        }
    }
}