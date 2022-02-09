package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This creates a recycler view of all the available vehicles for booking
 * It also has buttons that help the user navigate to other activities
 *
 * @author Vico Lau
 * @version 0.1
 */

public class ProfileActivity extends AppCompatActivity {

    RecyclerView recView;
    private FirebaseFirestore firestore;
    ArrayList<String> nameInfo = new ArrayList<>();
    ArrayList<String> statusInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        recView = findViewById(R.id.recView);
        firestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String myUserEmail = intent.getExtras().getString("currUser");

        System.out.println("first " + myUserEmail);

        VehicleRecyclerViewAdapter myAdapter = new VehicleRecyclerViewAdapter(nameInfo, statusInfo
                , this, myUserEmail);

        recView.setAdapter(myAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));

        updateRecView();
    }

    /**
     * creates a rec view of all the available vehicles
     */

    public void updateRecView()
    {
        firestore.collection("everything").document("vehicles")
                .collection("cars").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            List<DocumentSnapshot> ds = task.getResult().getDocuments();

                            for(DocumentSnapshot doc : ds)
                            {
                                Map<String, Object> docData = doc.getData();

                                Boolean openOrNot = (Boolean) docData.get("open");

                                if(openOrNot)
                                {

                                    String currModel = (String) docData.get("model");
                                    nameInfo.add(currModel);

                                    int currCapacity = doc.getLong("capacity").intValue();
                                    int currRating = doc.getLong("rating").intValue();

                                    statusInfo.add("Capacity: " + currCapacity + " Rating: "
                                            + currRating);
                                }
                            }

                            VehicleRecyclerViewAdapter a = (VehicleRecyclerViewAdapter)
                                    recView.getAdapter();
                            a.changeInfo(nameInfo, statusInfo);
                            a.notifyDataSetChanged();
                        }
                    }
                });

    }

    public void updateUI(View v)
    {
        Intent intent = getIntent();
        String myUserEmail = intent.getExtras().getString("currUser");

        System.out.println(myUserEmail);

        Intent newIntent = new Intent(this, VehicleProfileActivity.class);
        newIntent.putExtra("currUser", myUserEmail);
        startActivity(newIntent);
    }

    public void goToOpenClose(View v)
    {
        Intent intent = getIntent();
        String myUserEmail = intent.getExtras().getString("currUser");

        Intent newIntent = new Intent(this, OpenCloseActivity.class);
        newIntent.putExtra("currUser", myUserEmail);
        startActivity(newIntent);
    }

    public void goToRemoveBooking(View v)
    {
        Intent intent = getIntent();
        String myUserEmail = intent.getExtras().getString("currUser");

        Intent newIntent = new Intent(this, RemoveBookingActivity.class);
        newIntent.putExtra("currUser", myUserEmail);
        startActivity(newIntent);
    }
}