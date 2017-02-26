package com.example.contactsv015;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    EditText editText;
    Button button;
    EditText editText4;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        editText = (EditText) findViewById(R.id.editText);
        editText4 = (EditText) findViewById(R.id.editText4);

        button  = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
        temp="  ";
    }
//.toString
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("name",editText.getText().toString()+temp.toString()+editText4.getText().toString());

        setResult(RESULT_OK,intent);
        finish();

    }
}
