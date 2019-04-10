package com.example.elisabeth.theapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.PopupWindow;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> allLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  getSupportActionBar().hide();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "undermenyn", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });
        //The example-file "Uppsala" is written onCreate
        WriteFile(this, "1", "UKK Fyrisån Ångström Sopplunch Värmlands-nation Ekonom Jurist Socionom Studentportalen Studium Ladok Engelska-parken Ralph-Lauren");
        WriteFile(this, "2", "UKK Fyrisån Ångström Sopplunch Värmlands-nation Ekonom Jurist Socionom Studentportalen Studium Ladok Engelska-parken Ralph-Lauren");

        //define the button
        Button one = findViewById(R.id.button11);
        //set the onClick listener
        one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("onclick", "that was a short click");
                playGame1(v);
            }
        });
        //WARNING stupid ugly solution with repeat code, will be made dynamic
        Button two = findViewById(R.id.button12);
        one.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("onclick", "that was a short click");
                playGame2(v);
            }
        });
        //set the long click listener for first button
        one.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("onclick", "that was a long click");
                //start our options activity
                startActivity(new Intent(MainActivity.this, OptionsActivity.class));
                // TODO Auto-generated method stub
                //Create the pop up
                PopupWindow options = new PopupWindow();
                LayoutInflater layoutInflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_options,null);
                //display the pop up
                options.showAtLocation(customView, Gravity.CENTER, 0, 0);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //write file given name of file and desired contents, WARN overwrites!!
    private void WriteFile(Context context, String filename, String filecontents){
        //initialize
        byte[] filebytes = filecontents.getBytes();
        FileOutputStream outputStream;

        try {
            //write from filebytes to file
            outputStream = context.getApplicationContext().openFileOutput(filename, context.MODE_PRIVATE);
            outputStream.write(filebytes);
            outputStream.close();
        } catch (Exception e) {
            Log.d("myTag", "catch");
            e.printStackTrace();
        }
    }

    //read file given filename
    private String[] ReadFile(Context context, String filename){
        //initialize
        FileInputStream inputStream;
        byte[] b = new byte[1000];

        try {
            //read into one string
            inputStream = context.getApplicationContext().openFileInput(filename);
            inputStream.read(b);
            //split the string into separate words by spaces
            String s = new String(b, "UTF-8").trim();
            String[] arrayOfWords = s.split("\\s+"); // split("-", 150)
            //close
            inputStream.close();
            return arrayOfWords;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //play the game with the array connected to the button
    void playGame1(View view){
        //open file with the right name
        String[] arrayOfWords = ReadFile(this, "1");
        //play the game after changing the type
        launchGame(decodeArray(arrayOfWords));
    }
    //hardcoded for each button, THIS WILL BE FIXED
    void playGame2(View view){
        String[] arrayOfWords = ReadFile(this, "2");
        launchGame(decodeArray(arrayOfWords));
    }

    //convert our string[] from readFile to ArrayList<String> accepted for launching intent
    ArrayList<String> decodeArray(String[] arrayOfWords){
        int l = arrayOfWords.length;
        //make list of arrays
        List<String> finalArray = new ArrayList<String>(Arrays.asList(arrayOfWords));
        //convert to arraylist of strings
        ArrayList<String> list = new ArrayList<String>(l);
        for (String s : arrayOfWords) {
            list.add(s);
        }
        return list;
    }

    //launch the game with the array of choice
    void launchGame(ArrayList<String> list){
        Intent intent = new Intent(this, gameActivity.class);
        //add the list to our intent
        intent.putStringArrayListExtra("mylist", list);
        //play! (go to game activity)
        startActivity(intent);
    }

}
