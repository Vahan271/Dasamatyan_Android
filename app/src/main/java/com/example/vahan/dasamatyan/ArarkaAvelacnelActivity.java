package com.example.vahan.dasamatyan;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ArarkaAvelacnelActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText ararka;

    private Button avelacnel;

    private DBHelper dbHelper;

    final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ararka_avelacnel);
        dbHelper = new DBHelper(this);
        ararka = findViewById(R.id.ararka);
        avelacnel = findViewById(R.id.avelacnelArarka);
        avelacnel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final String ararkaString = this.ararka.getText().toString();
        final ContentValues cv = new ContentValues();
        cv.put("anun", ararkaString);
        long rowID = db.insert("ararka", null, cv);
        Log.d(LOG_TAG, "row inserted, in ararka. ID = " + rowID);
        finish();
    }

}
