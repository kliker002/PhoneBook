package com.example.contactsv015;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class groupActivity extends AppCompatActivity implements View.OnClickListener {

    ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;


    ArrayList<GroupData> groups=new ArrayList<>();
    //Map<String,List<String>> topics;

    EditText toButton;
    Button toList;
    Button delBtnGroup;
    Button delBtnChild;
    int i=0;
    int j=0;
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        toButton = (EditText) findViewById(R.id.toButton);
        toList = (Button) findViewById(R.id.toList);
        delBtnGroup = (Button) findViewById(R.id.delBtnGroup);
        delBtnChild = (Button) findViewById(R.id.delBtnChild);

        expandableListView =(ExpandableListView) findViewById(R.id.expandableListView);
      //  fillData();
       // int param =getIntent().getIntExtra("param",2);
        listAdapter = new MyExListAdapter(this,groups);
        expandableListView.setAdapter(listAdapter);

        toList.setOnClickListener(this);
        delBtnGroup.setOnClickListener(this);
        delBtnChild.setOnClickListener(this);
      /*  expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String data;
                GroupData gd =groups.remove(groupPosition);
                GroupDataManager.saveGroups(groups,getApplication());
                expandableListView.setAdapter(listAdapter);

                return true;
            }
        });*/
      /*  expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data;
                GroupData gd =groups.remove(position);
                GroupDataManager.saveGroups(groups,getApplication());
                expandableListView.setAdapter(listAdapter);

            }
        });*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        groups.clear();
        groups.addAll(GroupDataManager.loadGroups(this));
        expandableListView.setAdapter(listAdapter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        GroupDataManager.saveGroups(groups,this);
    }
/*
    public  void fillData(){
        GroupData g = new GroupData("Java");
        g.getContacts().add("Super");
        g.getContacts().add("Encaps");
        g.getContacts().add("Methods");
        Gson gson = new Gson();
        String sss = gson.toJson(g);

        //        =  new ArrayList<String>();
        groups.add(g);
        //listAdapter

        List<String> java = new ArrayList<>();
        List<String> c = new ArrayList<>();
        java.add("Super");
        java.add("Encaps");
        java.add("Methods");

        c.add("Procedure");
        c.add("Pointers");
        c.add("Array");

//        topics.put(langs.get(0), java);
  //      topics.put(langs.get(1),c);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toList:
                String temp;
                temp = toButton.getText().toString();
                GroupData gd = new GroupData(temp);
                groups.add(gd);
                expandableListView.setAdapter(listAdapter);
                break;
            case R.id.delBtnChild:
                switch (k) {
                    case 1:
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                //GroupData gd = groups.remove(childPosition); - Deleted Groups.
                                // GroupData gd =groups.get(groupPosition).getContacts().remove(childPosition);  - Не работает.
                                GroupData gd = groups.get(groupPosition);//A это работает?
                                gd.getContacts().remove(childPosition);// ахаа, смешно!
                                GroupDataManager.saveGroups(groups,getApplication());
                                expandableListView.setAdapter(listAdapter);
                                k=0;
                                return true;
                            }
                        });
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                "Удаление child active", Toast.LENGTH_SHORT);
                        toast1.show();
                        k=0;
                        break;
                    case 0:
                        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                            @Override
                            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                                k=1;
                                return true;
                            }
                        });
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Удаление child not active", Toast.LENGTH_SHORT);
                        toast.show();
                        k=1;
                        break;
                    default:
                        break;
                }
                break;


             //   break;
            case R.id.delBtnGroup:
                switch (j){
                    case 0:

                        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                if (expandableListView.isGroupExpanded(groupPosition)) {
                                    expandableListView.collapseGroup(groupPosition);
                                }
                                else  expandableListView.expandGroup(groupPosition);
                                return true;
                            }
                        });
                        if(delBtnGroup.isActivated()){
                            j=1;
                        }
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Удаление не активно!", Toast.LENGTH_SHORT);
                        toast.show();
                        j=1;
                        break;
                    case 1:
                        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                String data;
                                GroupData gd =groups.remove(groupPosition);
                                GroupDataManager.saveGroups(groups,getApplication());
                                expandableListView.setAdapter(listAdapter);
                                j=0;
                                return true;
                            }
                        });
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                "Удаление active", Toast.LENGTH_SHORT);
                        toast1.show();
                        j=0;
                        break;
                    default:break;
                }

                break;

        }
    }
}