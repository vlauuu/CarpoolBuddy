package com.example.carpoolbuddy;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * It allows the see what cars are available for booking through a recycler view
 * and they can view more about it by clicking into it
 *
 * @author Vico Lau
 * @version 0.1
 */

public class VehicleRecyclerViewAdapter extends RecyclerView.Adapter <VehicleViewHolder> {

    ArrayList<String> nameData;
    ArrayList<String> statusData;
    Context screen;
    String emailString;

    public VehicleRecyclerViewAdapter(ArrayList<String> itemNames,
                                      ArrayList<String> statusOutput,
                                      Context screen,
                                      String myUserEmail) {

        nameData = itemNames;
        statusData = statusOutput;

        this.screen = screen;

        emailString = myUserEmail;
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_row_view,
                parent, false);

        VehicleViewHolder holder = new VehicleViewHolder(myView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position)
    {
        /*set name and STATUS*/
        holder.nameText.setText(nameData.get(position));
        holder.statusText.setText(statusData.get(position));

        holder.returnLayout().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String modelString = holder.getNameText();

                System.out.println("the model string received:" + modelString);
                updateUI(modelString);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameData.size();
    }

    public void changeInfo(ArrayList<String> nameData, ArrayList<String> statusData)
    {
        this.nameData = nameData;
        this.statusData = statusData;
    }

    public void updateUI(String modelString)
    {
        Intent intent = new Intent(this.screen, VehiclesInfoActivity.class);
        intent.putExtra("currModel", modelString);
        intent.putExtra("currUser", emailString);

        this.screen.startActivity(intent);
    }

}
