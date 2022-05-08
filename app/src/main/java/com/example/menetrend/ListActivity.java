package com.example.menetrend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    FirebaseFirestore fstore;
    RecyclerView rview;
    private ArrayList<Menetrend> mItemsData;
    private CollectionReference mItems;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        fstore = FirebaseFirestore.getInstance();
        rview = findViewById(R.id.r_view);
        fstore.collection("menetrend")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.println(Log.ASSERT,"d", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.println(Log.ASSERT,"d", "sikertelen!");

                        }
                    }
                });

    }
    private void queryData() {
        mItemsData.clear();
        mItems.limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Menetrend item = document.toObject(Menetrend.class);
                item.setId(document.getId());
                mItemsData.add(item);
            }
        });
    }

    public void addAct(View view) {
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }
}