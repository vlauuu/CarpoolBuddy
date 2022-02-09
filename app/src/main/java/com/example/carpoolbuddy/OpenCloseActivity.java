package com.example.carpoolbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

/**
 * This open and closes a vehicle when the user wishes to
 *
 * @author Vico Lau
 * @version 0.1
 */

public class OpenCloseActivity extends AppCompatActivity
{

    private EditText carModelField;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_close);

        firestore = FirebaseFirestore.getInstance();

        carModelField = findViewById(R.id.carModelEditText);
    }

    /**
     * This method takes in a View
     * It checks whether the email owns the car
     * Then it opens the car which is done by changing "open" of the vehicle to true
     *
     * @param v
     */

    public void openCar(View v)
    {

        Intent intent = getIntent();
        String myUserEmail = intent.getExtras().getString("currUser");

        String currCarModel = carModelField.getText().toString();

        firestore.collection("everything").document("vehicles")
                .collection("cars").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    List<DocumentSnapshot> ds = task.getResult().getDocuments();

                    System.out.println(ds);

                    for(DocumentSnapshot doc : ds)
                    {

                        Map<String, Object> docData = doc.getData();

                        String owner = (String) docData.get("owner");
                        Boolean currStatus = (Boolean) docData.get("open");

                        if(owner.equals(myUserEmail))
                        {
                            if(!currStatus)
                            {
                                firestore.collection("everything")
                                        .document("vehicles")
                                        .collection("cars").document(currCarModel)
                                        .update("open", true);
                                System.out.println("opened car");
                            }
                        }
                    }
                }
            }
        });

        Toast.makeText(this, "Car Opened", Toast.LENGTH_SHORT).show();
    }

    /**
     * This method takes in a View
     * It checks whether the email owns the car
     * Then it closes the car which is done by changing "open" of the vehicle to false
     *
     * @param v
     */

    public void closeCar(View v)
    {

        System.out.println("onclick works");

        Intent intent = getIntent();
        String myUserEmail = intent.getExtras().getString("currUser");

        String currCarModel = carModelField.getText().toString();

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
                        System.out.println(ds);

                        Map<String, Object> docData = doc.getData();

                        String owner = (String) docData.get("owner");
                        Boolean currStatus = (Boolean) docData.get("open");

                        if(owner.equals(myUserEmail))
                        {
                            if(currStatus)
                            {
                                firestore.collection("everything")
                                        .document("vehicles")
                                        .collection("cars").document(currCarModel)
                                        .update("open", false);

                                System.out.println("closed car");
                            }
                        }
                    }
                }
            }
        });

        Toast.makeText(this, "Car Closed", Toast.LENGTH_SHORT).show();
    }
}