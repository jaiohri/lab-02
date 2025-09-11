package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);


        // All the buttons in the app and the input field for adding a city
        Button addCityButton = findViewById(R.id.addCityButton);
        Button deleteCityButton = findViewById(R.id.deleteCityButton);
        Button confirmButton = findViewById(R.id.confirm_button);
        EditText inputField = findViewById(R.id.input_Field);

        // Add a click listener to toggle visibility for input field and confirm button.
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputField.getVisibility()==View.VISIBLE && confirmButton.getVisibility()==View.VISIBLE) {
                    inputField.setText("");
                    inputField.setVisibility(View.GONE);
                    confirmButton.setVisibility(View.GONE);
                }
                else {
                    inputField.setText("");
                    inputField.setVisibility(View.VISIBLE);
                    confirmButton.setVisibility(View.VISIBLE);
                }
            }
        });


        // Add listener on confirm button and submit the entered city to add it list of cities.
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredCity = inputField.getText().toString().trim();

                if (!enteredCity.isEmpty()){
                    // Add the city
                    dataList.add(enteredCity);
                    cityAdapter.notifyDataSetChanged();

                    // Scroll smoothly to the added city in the list
                    cityList.smoothScrollToPosition(dataList.size()-1);

                    // Hide on submit
                    inputField.setText("");
                    inputField.setVisibility(View.GONE);
                    confirmButton.setVisibility(View.GONE);
                    selectedPosition = -1;

                    // Hide keyboard on submit
                    android.view.inputmethod.InputMethodManager imm =
                            (android.view.inputmethod.InputMethodManager) getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(inputField.getWindowToken(), 0);
                    }
                }
            }
        });

        // Get the position of the clicked/selected city in the dataList
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
        });

        // Delete the selected city
        deleteCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPosition != -1){
                    dataList.remove(selectedPosition);
                    cityAdapter.notifyDataSetChanged();
                    selectedPosition = -1;
                }
            }
        });
    }
}