package com.example.pair.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ArrayList<Integer> imgIdentifier;
    private ArrayList<Boolean> btnClicked;
    private int defaultBgID;
    private ArrayList<Button> buttons;
    private int lastClickedIndex;
    // private int currClickedIndex;
    private ArrayList<Boolean> clickable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        defaultBgID = R.drawable.defaultbg;
        lastClickedIndex = -1;

        buttons = new ArrayList<Button>();
        imgIdentifier = new ArrayList<Integer>();
        btnClicked = new ArrayList<Boolean>();
        clickable = new ArrayList<Boolean>();

        for(int i = 1;i < 17;i++){
            String btnName = "button" + Integer.toString(i);
            int id = getResources().getIdentifier(btnName,"id",getPackageName());

            Button btn = (Button)(findViewById(id));
            btn.setBackgroundResource(R.drawable.defaultbg);
            buttons.add(btn);
            btnClicked.add(Boolean.FALSE);
            clickable.add(Boolean.TRUE);
        }

        final ArrayList<Integer> numList = new ArrayList<Integer>();
        for(int i = 1;i < 9;i++){
            numList.add(i);
            numList.add(i);
        }
        Collections.shuffle(numList);

        for(int i = 0;i < numList.size();i++){
            String imgName = "bird" + Integer.toString(numList.get(i));
            int imgID = getResources().getIdentifier(imgName,"drawable",getPackageName());
            imgIdentifier.add(imgID);
        }


        for(int i = 1;i < 17;i++){

            final int k = i;
            buttons.get(i-1).setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {

                    int currClickedIndex = k - 1;
                    if (!clickable.get(currClickedIndex)) {
                        return;
                    }

                    // buttons.get(currClickedIndex).setBackgroundResource(imgIdentifier.get(numList.get(currClickedIndex)));
                    // buttons.get(currClickedIndex).setBackgroundResource(imgIdentifier.get(numList.get(currClickedIndex)));

                    if (btnClicked.get(currClickedIndex)) {
                        buttons.get(currClickedIndex).setBackgroundResource(defaultBgID);
                        btnClicked.set(currClickedIndex, Boolean.FALSE);
                        lastClickedIndex = -1;
                    } else {
                        btnClicked.set(currClickedIndex, Boolean.TRUE);
                        if (lastClickedIndex == -1) {
                            buttons.get(currClickedIndex).setBackgroundResource(imgIdentifier.get(numList.get(currClickedIndex)));
                            lastClickedIndex = currClickedIndex;
                        }else {
                            if (numList.get(lastClickedIndex).equals(numList.get(currClickedIndex))) {
                                clickable.set(lastClickedIndex, Boolean.FALSE);
                                clickable.set(currClickedIndex, Boolean.FALSE);
                                buttons.get(currClickedIndex).setBackgroundResource(imgIdentifier.get(numList.get(currClickedIndex)));
                                buttons.get(lastClickedIndex).setBackgroundResource(imgIdentifier.get(numList.get(lastClickedIndex)));
                                lastClickedIndex = -1;
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
