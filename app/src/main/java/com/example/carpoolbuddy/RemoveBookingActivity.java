package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This allows the user ot cancel booking for a vehicle anytime
 *
 * @author Vico Lau
 * @version 0.1
 */

public class RemoveBookingActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;

    private EditText modelField;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_booking);

        firestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        String myUserEmail = intent.getExtras().getString("currUser");

        modelField = findViewById(R.id.removeCarEditText);
    }

    /**
     * This method takes in a View
     * it checks whether the user actually booked the vehicle
     * then it removes the user from the reservedUIDs list
     * it checks whether the car is available or not after cancellation
     *
     * @param V
     */

    public void removeBooking(View V){

        String carModel = modelField.getText().toString();

        firestore.collection("everything").document("vehicles")
                .collection("cars").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            System.out.println("attempts to find car");

                            List<DocumentSnapshot> ds = task.getResult().getDocuments();

                            Intent intent = getIntent();
                            String myUserEmail = intent.getExtras().getString("currUser");

                            for(DocumentSnapshot doc : ds){

                                Map<String, Object> docData = doc.getData();

                                String thisModel = (String) docData.get("model");

                                ArrayList<String> allReservedUids = new ArrayList<>();

                                for (java.lang.String currID : (ArrayList<String>) docData
                                        .get("reservedUids")){

                                    String IDString = currID.toString();

                                    allReservedUids.add(IDString);
                                }

                                System.out.println(docData.get("reservedUids"));

                                if(thisModel.equals(carModel)){

                                    allReservedUids.remove(myUserEmail);

                                    System.out.println("After adding user: " + allReservedUids);

                                    int thisCapacity = doc.getLong("capacity").intValue();

                                    if(allReservedUids.size() < thisCapacity){
                                        firestore.collection("everything")
                                                .document("vehicles")
                                                .collection("cars").document(thisModel)
                                                .update("open", true);
                                    }

                                    firestore.collection("everything")
                                            .document("vehicles")
                                            .collection("cars").document(thisModel)
                                            .update("reservedUids", allReservedUids);

                                }
                            }
                        }
                    }
                });
    }
}