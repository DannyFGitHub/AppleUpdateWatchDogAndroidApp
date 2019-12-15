package com.touchmediaproductions.android.appleupdatewatchdog;

import WebParser.RecordOfSoftUpdate;
import WebParser.SoftwareUpdateChecker;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(50);
        setSupportActionBar(toolbar);

        setTitle("Apple Updates");

        runFillList();


    }

    public void runFillList(){
        Thread thread = new Thread(() -> {
            try  {
                LinkedList<RecordOfSoftUpdate> linkedList = SoftwareUpdateChecker.getSoftUpdateRecords();


                LinkedList<LinearLayout> listOfRecordView = new LinkedList<>();


                for (RecordOfSoftUpdate recordOfSoftwareUpdate : linkedList) {

                    LinearLayout updateRecord = new LinearLayout(getApplicationContext());

                    TextView dateOfUpdate = new TextView(getApplicationContext());
                    dateOfUpdate.setText(recordOfSoftwareUpdate.getReleaseDate());
                    dateOfUpdate.setTextColor(Color.GRAY);
                    dateOfUpdate.setTypeface(Typeface.MONOSPACE);

                    TextView nameOfUpdate = new TextView(getApplicationContext());
                    nameOfUpdate.setText(recordOfSoftwareUpdate.getName());
                    nameOfUpdate.setTextColor(Color.BLUE);
                    nameOfUpdate.setTypeface(Typeface.DEFAULT_BOLD);

                    TextView availability = new TextView(getApplicationContext());
                    availability.setText(recordOfSoftwareUpdate.getDeviceAvailability());
                    availability.setTextColor(Color.DKGRAY);
                    availability.setTypeface(Typeface.SERIF);

                    TextView tags = new TextView(getApplicationContext());

                    String tagString = "";
                    for (String tag:recordOfSoftwareUpdate.getDeviceCategoryTags()) {
                        if (tagString.isEmpty())
                            tagString += tag;
                        else
                            tagString += ", " + tag;
                    }

                    tags.setText(tagString);
                    tags.setTextColor(Color.LTGRAY);
                    tags.setTypeface(Typeface.SANS_SERIF);

                    updateRecord.addView(dateOfUpdate);
                    updateRecord.addView(nameOfUpdate);
                    updateRecord.addView(availability);
                    updateRecord.addView(tags);

                    updateRecord.setPadding(10,10,10,10);
                    updateRecord.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    updateRecord.setOrientation(LinearLayout.VERTICAL);

                    if(recordOfSoftwareUpdate.getUrlLink() != null) {
                        updateRecord.setOnClickListener(v -> {
                            Thread openBrowser = new Thread(() -> {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(recordOfSoftwareUpdate.getUrlLink().toString()));
                                startActivity(browserIntent);
                            });
                            openBrowser.start();
                        });
                    }

                    listOfRecordView.add(updateRecord);
                }

                ArrayAdapter<LinearLayout> stringArrayAdapter = new ArrayAdapter<LinearLayout>(getApplicationContext(), android.R.layout.simple_list_item_1, listOfRecordView){
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent){
                        return listOfRecordView.get(position);
                    }
                };

                runOnUiThread(() -> updateListView(stringArrayAdapter));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void updateListView(ArrayAdapter<LinearLayout> stringArrayAdapter){
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeToRefresh);

        ListView lv = findViewById(R.id.listViewer);
        lv.setAdapter(stringArrayAdapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            lv.setEmptyView(new View(this));
            lv.setAdapter(stringArrayAdapter);
            swipeRefreshLayout.setRefreshing(false);
        });



    }

}
