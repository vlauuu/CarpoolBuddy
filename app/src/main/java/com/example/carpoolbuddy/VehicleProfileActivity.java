package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.util.Strings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * It allows the user to add their own vehicle to the data base after they fill in the
 * appropriate params
 *
 * @author Vico Lau
 * @version 0.1
 */

public class VehicleProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;

    private EditText ownerField;
    private EditText carModelField;
    private EditText capacityField;
    private EditText ratingField;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_profile);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        ownerField = findViewById(R.id.editTextOwner);
        carModelField = findViewById(R.id.editTextCarModel);
        capacityField = findViewById(R.id.editTextCapacity);
        ratingField = findViewById(R.id.editTextRating);

    }

    /**
     * This method takes in a View
     * it adds a vehicle to the database with the added params
     * and it adds it to the user's owned vehicle list
     *
     * @param v
     */

    public void addVehicle(View v)
    {
        String ownerEmail = ownerField.getText().toString();
        String carModel = carModelField.getText().toString();
        String capacityS = capacityField.getText().toString();
        String ratingS = ratingField.getText().toString();

        int capacity = Integer.parseInt(capacityS);
        int rating = Integer.parseInt(ratingS);

        ArrayList<String> reservedUids = new ArrayList<>();

        Car currCar = new Car(capacity, carModel,true, ownerEmail, rating, reservedUids);

        firestore.collection("everything").document("vehicles")
                .collection("cars").document(carModel).set(currCar);

        firestore.collection("everything").document("all users")
                .collection("users").get()
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

                        String currEmail = (String) docData.get("email");
                        ArrayList<String> currCarList = (ArrayList<String>) doc
                                .get("ownedVehicles");

                        if(currEmail.equals(ownerEmail))
                        {
                            currCarList.add(carModel);

                            firestore.collection("everything")
                                    .document("all users")
                                    .collection("users")
                                    .document(ownerEmail).update("ownedVehicles", currCarList);
                        }
                    }
                }
            }
        });

        Toast.makeText(this, "Car Added", Toast.LENGTH_SHORT).show();
    }
}