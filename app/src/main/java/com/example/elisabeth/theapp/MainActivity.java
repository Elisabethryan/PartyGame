package com.example.elisabeth.theapp;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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
    }
/*
        class FileHandling extends MainActivity{
            public ArrayList<String> records;

            public FileHandling() {
                records = new ArrayList<String>();

            };

            private void addToRecords(String[] arrayOfWords){
                for(int i=0;i<arrayOfWords.length;i++){
                    records.add(arrayOfWords[i]);
                }
            };

            private List<String> WriteFile(Context context, String filename, String filecontents){

                byte[] filebytes = filecontents.getBytes();
                FileOutputStream outputStream;

                try {
                    outputStream = context.getApplicationContext().openFileOutput(filename, context.MODE_PRIVATE);
                    outputStream.write(filebytes);
                    outputStream.close();
                } catch (Exception e) {
                    Log.d("myTag", "catch");

                    e.printStackTrace();
                }
                    return null;
            }

            private String[] ReadFile(Context context, String filename){

                FileInputStream inputStream;
                byte[] b = new byte[60];

                try {
                    inputStream = context.getApplicationContext().openFileInput(filename);
                    inputStream.read(b);
                    String s = new String(b, "UTF-8").trim();
                    String[] arrayOfWords = s.split("\\s+");
                    inputStream.close();
                    addToRecords(arrayOfWords);
                    return arrayOfWords;
                } catch (Exception e) {
                    e.printStackTrace();
                    return new String[1];
                }
            }
//gör varje deck till ett eget objekt?
        }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private List<String> WriteFile(Context context, String filename, String filecontents){
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
        return null;
    }

    private String[] ReadFile(Context context, String filename){
        //initialize
        FileInputStream inputStream;
        byte[] b = new byte[60];

        try {
            //read in to one string
            inputStream = context.getApplicationContext().openFileInput(filename);
            inputStream.read(b);
            //split the string into seperate words by spaces
            String s = new String(b, "UTF-8").trim();
            Log.d("string trimmed", s);
            String[] arrayOfWords = s.split("\\s+"); // split("-", 150)
            Log.d("string split", arrayOfWords[1]);
            //close
            inputStream.close();
            return arrayOfWords;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    void startGame1(View view){
        WriteFile(this, "myfile", "banan rostade-mackor word hey man banana-split");
        Log.d("tag", "före playgame");
        playGame(1);
    }

    void playGame(int i){
        //read in string[] and convert to arraylist
       // String[] arrayOfWords = ReadFile(this, "root" + i);
        String[] arrayOfWords = ReadFile(this, "myfile");
        int l = arrayOfWords.length;
        List<String> finalArray = new ArrayList<String>(Arrays.asList(arrayOfWords));
        ArrayList<String> list = new ArrayList<String>(11);
        for (String s : arrayOfWords) {
            list.add(s);
        }
        //start next activity (game) with the list
        Intent intent = new Intent(this, gameActivity.class);
        intent.putStringArrayListExtra("mylist", list);
        startActivity(intent);
    }
/*
    public void goToGame(View view) {
        FileHandling filehandler = new FileHandling();
        String fileContents = "Alla möjliga namn jag kan komma på omg detta går okej";
        //filehandler.WriteFile(this, "root", fileContents);
        //get list
        String[] words = filehandler.ReadFile(this, "root");
        //convert to arraylist
        List<String> wordList = new ArrayList<String>(Arrays.asList(words));

        //start next activity
        Intent intent = new Intent(this, gameActivity.class);
        intent.putStringArrayListExtra("mylist", filehandler.records);
        startActivity(intent);
    };

*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
