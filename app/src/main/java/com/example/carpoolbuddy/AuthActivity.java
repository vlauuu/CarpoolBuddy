package com.example.carpoolbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CheckedInputStream;

/**
 * This is the home page for the user where they can sign up and sign in
 *
 * @author Vico Lau
 * @version 0.1
 */

public class AuthActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String selected;
    private Spinner sUserType;

    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emailField = findViewById(R.id.editTextEmailAddress);
        passwordField = findViewById(R.id.editTextPassword);

        ArrayList<String> allEmails = new ArrayList<>();

        Spinner spinner = findViewById(R.id.userTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**
     * This method takes in a View
     * It checks whether the email entered and password is in the database
     * Then it allows the user to login
     *
     * @param v
     */

    public void signIn(View v)
    {
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        firestore.collection("everything").document("all users")
                .collection("users").get().addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
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
                        String currPassword = (String) docData.get("password");

                        if(currEmail.equals(emailString))
                        {
                            if(currPassword.equals(passwordString))
                            {
                                updateUI(currEmail);
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * This method takes in a View
     * It checks whether the email entered is in the CIS community
     * Then it checks the spinner to determine what the user type is
     * Then it adds the user to the database with the appropriate information
     *
     * @param v
     */

    public void signUp(View v)
    {
        String emailString = emailField.getText().toString();
        String passwordString = passwordField.getText().toString();

        if(emailString.contains("cis.edu.hk"))
        {
            String[] splitEmail = emailString.split("[@._]");

            String currUid = splitEmail[0];
            List<String> splitUid = new ArrayList<String>();
            Matcher match = Pattern.compile("[0-9]+|[a-z]+|[A-Z]+").matcher(currUid);
            while (match.find())
            {
                splitUid.add(match.group());
            }

            String currName = splitUid.get(0);
            ArrayList<String> nullList = new ArrayList<>();
            Double nullD = 0.0;
            
            User currUser = null;

            Spinner spinner = (Spinner)findViewById(R.id.userTypeSpinner);
            String text = spinner.getSelectedItem().toString();

            if(text.equals("Student"))
            {
                String currUid2 = splitUid.get(1);

                currUser = new Student(currUid, currName, emailString, "student"
                        , nullD, passwordString, nullList, currUid2, nullList);

                firestore.collection("everything").document("all users")
                        .collection("users").document(emailString).set(currUser);
            }

            if(text.equals("Teacher"))
            {
                currUser = new Teacher(currUid, currName, emailString, "teacher"
                    , nullD, passwordString, nullList, "");

                firestore.collection("everything").document("all users")
                        .collection("users").document(emailString).set(currUser);
            }

            if(text.equals("Parent"))
            {
                currUser = new Parent(currUid, currName, emailString, "parent"
                        , nullD, passwordString, nullList, nullList);

                firestore.collection("everything").document("all users")
                        .collection("users").document(emailString).set(currUser);
            }

            if(text.equals("Alumni"))
            {
                String currUid2 = splitUid.get(1);

                currUser = new Alumni(currUid, currName, emailString, "alumni"
                        , nullD, passwordString, nullList, currUid2);

                firestore.collection("everything").document("all users")
                        .collection("users").document(emailString).set(currUser);
            }

            Toast.makeText(this, "User Added", Toast.LENGTH_SHORT).show();

            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(emailString);
        }
        else
        {
            Toast.makeText(this, "Email Invalid", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUI(String myUserEmail)
    {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("currUser", myUserEmail);
        startActivity(intent);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView,
                               View view,
                               int i,
                               long l) {

        String text = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

