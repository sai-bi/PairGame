package com.example.pair.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Button;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ArrayList<Integer> imgIdentifier;
    private int defaultBgID;
    private int matchBgID;
    private ArrayList<Button> buttons;
    private int lastClickedIndex;
    private Button startBtn;
    private boolean gameStart;
    private int operationCount;
    private int matchCount = 0;
    private TextView operationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultBgID = R.drawable.defaultbg;
        matchBgID = R.drawable.match;
        startBtn = (Button)findViewById(R.id.start);
        gameStart = false;
        lastClickedIndex = -1;
        operationCount = 0;
        matchCount = 0;
        operationView = (TextView)findViewById(R.id.operation);
        operationView.setText("Total operations: " + Integer.toString(operationCount));


        buttons = new ArrayList<Button>();
        imgIdentifier = new ArrayList<Integer>();


        for(int i = 0;i < 16;i++){
            String btnName = "button" + Integer.toString(i+1);
            int id = getResources().getIdentifier(btnName,"id",getPackageName());

            Button btn = (Button)(findViewById(id));
            btn.setBackgroundResource(R.drawable.defaultbg);
            buttons.add(btn);
        }

        final ArrayList<Integer> numList = new ArrayList<Integer>();
        for(int i = 0;i < 8;i++){
            numList.add(i);
            numList.add(i);
        }
        Collections.shuffle(numList);

        for(int i = 0;i < 8;i++){
            String imgName = "bird" + Integer.toString(i+1);
            int imgID = getResources().getIdentifier(imgName,"drawable",getPackageName());
            imgIdentifier.add(imgID);
        }

        final AlertDialog.Builder reminder = new AlertDialog.Builder(this);
        reminder.setMessage("Press the START button to start!");
        reminder.setTitle("Reminder");
        reminder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog.Builder success = new AlertDialog.Builder(this);
        success.setMessage("All pairs are found!");
        success.setTitle("Congratulations!");
        success.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startBtn.getText() == "start"){
                    startBtn.setText("restart");
                    gameStart = true;
                }
                else{
                    startBtn.setText("start");
                    lastClickedIndex = -1;
                    Collections.shuffle(numList);
                    operationCount = 0;
                    operationView.setText("Total operations: " + Integer.toString(operationCount));
                    gameStart = false;
                    for(int i = 0;i < 16;i++){
                        buttons.get(i).setBackgroundResource(defaultBgID);
                        buttons.get(i).setClickable(true);
                    }
                }
            }
        });

        for(int i = 1;i < 17;i++){

            final int k = i;
            buttons.get(i-1).setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    if(!gameStart){
                        reminder.show();
                        return;
                    }
                    operationCount++;
                    operationView.setText("Total operations: " + Integer.toString(operationCount));

                    int currClickedIndex = k - 1;

                    if (lastClickedIndex == currClickedIndex) {
                        buttons.get(currClickedIndex).setBackgroundResource(defaultBgID);
                        lastClickedIndex = -1;
                    } else {
                        //btnClicked.set(currClickedIndex, Boolean.TRUE);
                        if (lastClickedIndex == -1) {
                            buttons.get(currClickedIndex).setBackgroundResource(imgIdentifier.get(numList.get(currClickedIndex)));
                            lastClickedIndex = currClickedIndex;
                        }else {
                            if (numList.get(lastClickedIndex).equals(numList.get(currClickedIndex))) {

                                buttons.get(currClickedIndex).setClickable(false);
                                buttons.get(lastClickedIndex).setClickable(false);
                                buttons.get(currClickedIndex).setBackgroundResource(matchBgID);
                                buttons.get(lastClickedIndex).setBackgroundResource(matchBgID);
                                lastClickedIndex = -1;
                                matchCount++;
                                if(matchCount == 8){
                                    success.show();
                                }
                            } else {
                                buttons.get(lastClickedIndex).setBackgroundResource(defaultBgID);
                                buttons.get(currClickedIndex).setBackgroundResource(imgIdentifier.get(numList.get(currClickedIndex)));
                                lastClickedIndex = currClickedIndex;

                            }
                        }
                    }


                }
            });
        }





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
