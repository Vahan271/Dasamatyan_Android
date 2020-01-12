package com.example.vahan.dasamatyan;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DasaranAvelacnelActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText dasaran;

    private Button avelacnel;

    private DBHelper dbHelper;

    private final String LOG_TAG = "myLogs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasaran_avelacnel);
        dbHelper = new DBHelper(this);
        dasaran = findViewById(R.id.dasaran);
        avelacnel = findViewById(R.id.avelacnelDasaran);
        avelacnel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final String dasaranString = this.dasaran.getText().toString();
        final ContentValues cv = new ContentValues();
        cv.put("dasaran", dasaranString);
        long rowID = db.insert("dasaran", null, cv);
        Log.d(LOG_TAG, "row inserted, in dasaran. ID = " + rowID);
        finish();
    }
}
