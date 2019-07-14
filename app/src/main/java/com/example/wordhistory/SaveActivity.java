package com.example.wordhistory;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SaveActivity extends Activity {

    private TextView head;
    private Button clear;
    private ListView list;

    private int n=0;
    private ArrayList<String>str = new ArrayList<>();
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        head = (TextView)findViewById(R.id.head);
        clear = (Button)findViewById(R.id.clear);
        list = (ListView)findViewById(R.id.list);
        sp = getSharedPreferences("save",Activity.MODE_PRIVATE);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (true){
                    if (n >= 0){
                        sp.edit().remove(String.valueOf(n)).apply();
                        n--;
                    }
                    else {
                        n++;
                        break;
                    }
                }
                finish();

            }
        });

        n = 0;
        while (true){
            if(!sp.getString(String.valueOf((n)),"").equals("")){
                str.add(sp.getString(String.valueOf((n)),""));
                n++;
            }
            else{
                break;
            }
        }

        list.setAdapter(new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,str));
        ((BaseAdapter)list.getAdapter()).notifyDataSetChanged();

    }
}
