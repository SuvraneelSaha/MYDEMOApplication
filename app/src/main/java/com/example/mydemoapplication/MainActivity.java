package com.example.mydemoapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Instance Variables ;
    EditText name , contact , dob ;
    Button insert , update , delete , view ;

    DBHelper DB ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Aggin", "This is a debug message");
        Log.d("Aggin","DEMO TEST");

        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);

        insert=findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);

        DB=new DBHelper(this);

        // this method setOnClickListener acts when the button is clicked
        // and this new View.OnClickListener is the method defination on what needs to be done when the
        // button is clicked ;
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                // this getText is coming from the EditText Object class
                String contactText = contact.getText().toString();
                String dobText = dob.getText().toString();

                Boolean checkInsertData = DB.insertUserData(nameText,contactText,dobText);

                if(checkInsertData==true){
                    Toast.makeText(MainActivity.this,"New Entry is Inserted",Toast.LENGTH_LONG).show();
                    name.setText("");
                    contact.setText("");
                    dob.setText("");
                }
                else {
                    Toast.makeText(MainActivity.this,"New Entry is NOT Inserted",Toast.LENGTH_LONG).show();
                }
            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();
                // this getText is coming from the EditText Object class
                String contactText = contact.getText().toString();
                String dobText = dob.getText().toString();

                Boolean checkUpdateData = DB.updateUserData(nameText,contactText,dobText);

                if(checkUpdateData==true){
                    Toast.makeText(MainActivity.this,"Entry Updated",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(MainActivity.this,"No Entry Updated",Toast.LENGTH_LONG).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText = name.getText().toString();


                Boolean checkDeleteData = DB.deleteUserData(nameText);

                if(checkDeleteData==true){
                    Toast.makeText(MainActivity.this,"Entry Deleted",Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(MainActivity.this,"Entry NOT DELETED ",Toast.LENGTH_LONG).show();
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getData();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this,"No Entry Exists",Toast.LENGTH_LONG).show();
                    return;
                }else{
                    StringBuffer buffer = new StringBuffer();
                    while (res.moveToNext()){
                        buffer.append("Name : "+res.getString(0) +"\n");
                        buffer.append("Contact : "+res.getString(1) +"\n");
                        buffer.append("DOB : "+res.getString(2) +"\n");
                        buffer.append("\n");
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("USER Entries");
                    builder.setMessage(buffer.toString());
                    builder.show();

                }



            }
        });
    }
}