package com.ACG.sayari.catagory;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ACG.sayari.MySayari;
import com.ACG.sayari.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Happy extends AppCompatActivity implements View.OnClickListener {
    List<String> sayarilist;

    DatabaseReference databaseReference;
    MySayari mySayari;
    int position=0;
    TextView sayari_text;
    Button pre,next,copy,share;
    ProgressDialog dialog;
    TextView count_text;
    RelativeLayout relativeLayout;
    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common);
        sayari_text=findViewById(R.id.sayari_text);
        pre=findViewById(R.id.pre);
        next=findViewById(R.id.next);
        copy=findViewById(R.id.copy);
        share=findViewById(R.id.share);
        count_text=findViewById(R.id.count_text);
        relativeLayout=findViewById(R.id.relative_layout);


        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        share.setOnClickListener(this);
        copy.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("wait");

        dialog.setCancelable(false);

        dialog.show();

        databaseReference= FirebaseDatabase.getInstance().getReference("happy");
        mySayari=new MySayari();
        sayarilist=new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    mySayari = ds.getValue(MySayari.class);
                    dialog.dismiss();
                    if (mySayari != null) {
                        sayarilist.add(mySayari.getS());
                    }

                }
                sayari_text.setText(sayarilist.get(position));
                count_text.setText(position+"/"+sayarilist.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"error occurred",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.pre:

                prev();
                break;

            case
                    R.id.next:
                next();
                break;
            case R.id.copy:
                copy();
                break;
            case R.id.share:
                share();
                break;


        }

    }

    private void prev() {
        if (position > 0) {

            position = (position - 1) % sayarilist.size();
            sayari_text.setText(sayarilist.get(position));
            count_text.setText(position + "/" + sayarilist.size());

        }


    }


    protected void next(){


        position=(position+1)%sayarilist.size();
        sayari_text.setText(sayarilist.get(position));
        count_text.setText(position + "/" + sayarilist.size());
    }

    private void copy(){
        ClipboardManager clipboardManager= (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text",sayari_text.getText());
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(getApplicationContext(),"copied",Toast.LENGTH_SHORT).show();


    }

    private void share(){
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,sayari_text.getText());
        startActivity(Intent.createChooser(intent,"share by"));
    }
}
