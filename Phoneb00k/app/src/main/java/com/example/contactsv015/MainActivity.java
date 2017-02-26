package com.example.contactsv015;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button deleteBtn;
    Button btnGroup;
    Button Button03;
    Button Button01;
    TextView textView4;
    ListView listView;
    int i=1;

    ArrayList<String> myArrayList = new ArrayList<String>();
    SharedPreferences contactPrefs;
   // int i=0;
    ArrayAdapter<String> adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGroup = (Button) findViewById(R.id.btnGroup);
        Button03 = (Button) findViewById(R.id.button03);
        Button01 = (Button) findViewById(R.id.button01);
        listView = (ListView) findViewById(R.id.listview);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        btnGroup.setOnClickListener(this);
        Button03.setOnClickListener(this);
        Button01.setEnabled(false);
        Button01.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
        contactPrefs = getApplicationContext().getSharedPreferences("contacts",0);
        //contactPrefs.edit().putInt("d",1).apply();
        //contactPrefs.getString()



        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myArrayList);
        listView.setAdapter(adapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            String contact = (String)parent.getItemAtPosition(position);
                MyAlertDialogFragment.newInstance(R.string.alert_dialog_title,contact).show(getSupportFragmentManager(),MyAlertDialogFragment.class.getSimpleName());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button03:
                Intent intent = new Intent(this, Main2Activity.class);
                startActivityForResult(intent, 1);

                //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myArrayList);
                //listView.setAdapter(adapter1);

                break;

            case R.id.deleteBtn:

                switch (i){
                    case 0:
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Удаление не активно!", Toast.LENGTH_SHORT);
                        toast.show();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String contact = (String)parent.getItemAtPosition(position);
                            MyAlertDialogFragment.newInstance(R.string.alert_dialog_title,contact).show(getSupportFragmentManager(),MyAlertDialogFragment.class.getSimpleName());
                            i=1;
                        }
                    });
                        break;
                    case 1:
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                "Удаление активно!", Toast.LENGTH_SHORT);
                        toast1.show();
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                String data;
                                data = (String)parent.getItemAtPosition(position);
                                myArrayList.remove(data);
                                listView.setAdapter(adapter1);
                                i=0;
                            }
                        });
                        break;
                }

                break;
            case R.id.btnGroup:
                Intent intent1 = new Intent(this,groupActivity.class);

               // intent1.putExtra("param",1);
                startActivity(intent1);
                break;

            default:
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter1.clear();
        adapter1.addAll( contactPrefs.getStringSet("contacts", new LinkedHashSet<String>()));
        adapter1.notifyDataSetChanged();


    }

    @Override
    protected void onStop() {
        super.onStop();
        contactPrefs.edit().putStringSet("contacts",new LinkedHashSet<String>(myArrayList)).apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String nnames = data.getStringExtra("name");
       // myArrayList.add(nnames);
        myArrayList.add(nnames);
        contactPrefs.edit().putStringSet("contacts",new LinkedHashSet<String>(myArrayList)).apply();
        adapter1.notifyDataSetChanged();



        //++i;
    }

   public void deleteContact(){
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               String data;
               data = (String)parent.getItemAtPosition(position);
               myArrayList.remove(data);
               listView.setAdapter(adapter1);
               adapter1.notifyDataSetChanged();

           }
       });

   }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int title, String contact) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            //args.putSerializable("groups",  );
            args.putInt("title", title);
            args.putString("contact",contact);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");
            final String contact = getArguments().getString("contact");
            final List<GroupData> groups = GroupDataManager.loadGroups(getActivity());
            ArrayAdapter<GroupData> adapter = new ArrayAdapter<GroupData>(getActivity(),android.R.layout.simple_list_item_1,groups);

            AlertDialog dlg =  new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                   // .setN
                    .setNegativeButton(R.string.alert_dialog_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    //(Mai)getActivity().deleteC
                                    dialog.dismiss();
                                }
                            }
                    )

                    .setAdapter(adapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            GroupData gd =groups.get(which);
                            gd.getContacts().add(contact);
                            GroupDataManager.saveGroups(groups,getActivity());
                            dialog.dismiss();
                        }
                    })
                    .create();

            return dlg;
        }
    }

}