package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.String;

/**
 * The user is able to see information after clicking into it in the recycler view
 * It allows the user to book a vehicle
 *
 * @author Vico Lau
 * @version 0.1
 */

public class VehiclesInfoActivity extends AppCompatActivity
{
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicles_info);

        Intent intent = getIntent();
        String myUserEmail = intent.getExtras().getString("currUser");
        String currModel = intent.getExtras().getString("currModel");

        System.out.println(currModel);

        TextView modelView = (TextView)findViewById(R.id.modelTextView);
        TextView ratingView = (TextView)findViewById(R.id.ratingTextView);
        TextView capacityView = (TextView)findViewById(R.id.capacityTextView);

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("everything").document("vehicles")
                .collection("cars").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            List<DocumentSnapshot> ds = task.getResult().getDocuments();

                            for(DocumentSnapshot doc : ds)
                            {
                                Map<String, Object> docData = doc.getData();

                                String thisModel = (String) docData.get("model");

                                if (thisModel.equals(currModel))
                                {
                                    int thisCapacity = doc.getLong("capacity").intValue();
                                    int thisRating = doc.getLong("rating").intValue();

                                    String thisRatingS = String.valueOf(thisRating);
                                    String thisCapacityS = String.valueOf(thisCapacity);

                                    modelView.setText(thisModel);
                                    ratingView.setText("Rating: " + thisRatingS);
                                    capacityView.setText("Capacity: " + thisCapacityS);
                                }
                            }
                        }
                    }
                });

    }

    /**
     * this method takes in a View
     * it allows the user to book and reserve the vehicle
     * it adds them to the reservedUIDs of the car
     *
     * @param v
     */

    public void addVehicle(View v)
    {
        System.out.println("onclick works");

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("everything").document("vehicles")
                .collection("cars").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            System.out.println("attempts to find car");

                            List<DocumentSnapshot> ds = task.getResult().getDocuments();

                            Intent intent = getIntent();
                            String myUserEmail = intent.getExtras().getString("currUser");
                            String currModel = intent.getExtras().getString("currModel");

                            System.out.println(myUserEmail + " in vehicle info");
                            System.out.println(ds);

                            for(DocumentSnapshot doc : ds)
                            {
                                Map<String, Object> docData = doc.getData();

                                String thisModel = (String) docData.get("model");

                                ArrayList<String> allReservedUids = new ArrayList<>();

                                for (java.lang.String currID : (ArrayList<String>) docData
                                        .get("reservedUids"))
                                {

                                    String IDString = currID.toString();

                                    allReservedUids.add(IDString);
                                }

                                System.out.println(docData.get("reservedUids"));

                                if(thisModel.equals(currModel))
                                {
                                    System.out.println("Checks if the vehicle is open");
                                    System.out.println("before adding " + myUserEmail);

                                    allReservedUids.add(myUserEmail);

                                    System.out.println("After adding user: " + allReservedUids);

                                    int thisCapacity = doc.getLong("capacity").intValue();

                                    if(allReservedUids.size() == thisCapacity){
                                        firestore.collection("everything")
                                        .document("vehicles")
                                        .collection("cars").document(currModel)
                                        .update("open", false);
                                    }

                                    firestore.collection("everything")
                                    .document("vehicles")
                                    .collection("cars").document(currModel)
                                    .update("reservedUids", allReservedUids);

                                    System.out.println("adds user to list");
                        }
                    }
                }
            }
        });

        Toast.makeText(this, "Car Booked", Toast.LENGTH_SHORT).show();
    }

}