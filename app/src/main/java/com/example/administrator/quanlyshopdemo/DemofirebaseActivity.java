package com.example.administrator.quanlyshopdemo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.quanlyshopdemo.doituong.sanpham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Administrator on 11/13/2016.
 */
public class DemofirebaseActivity extends Activity {

    DatabaseReference mData;
    TextView txtsp;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demofirebase);
        txtsp= (TextView)findViewById(R.id.txtsanpham11);
        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Hello, World!");


        mData = FirebaseDatabase.getInstance().getReference();
//        mData.child("Hoten").setValue("Trương Hữu Hiếu");

        sanpham sp =new sanpham("hahhah",10000,"hihihihi");
        mData.child("sanpham").setValue(sp);


        mData.child("sanpham1").setValue(sp, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if(databaseError== null)
                {
                    Toast.makeText(DemofirebaseActivity.this,"Lưu thành công",Toast.LENGTH_SHORT).show();

                }
                else
                {

                    Toast.makeText(DemofirebaseActivity.this,"Lưu thất bại",Toast.LENGTH_SHORT).show();

                }
            }
        });


        mData.child("Hoten").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtsp.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
