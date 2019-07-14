package com.example.wordhistory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String url;
    private TextView history;
    private EditText enterWord;
    private Button find;
    private Button save;

    private int n=0;
    private SharedPreferences sp;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        history = (TextView)findViewById(R.id.history);
        enterWord = (EditText)findViewById(R.id.enterWord);
        find = (Button)findViewById(R.id.find);
        save = (Button)findViewById(R.id.save);
        sp = getSharedPreferences("save", Activity.MODE_PRIVATE);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setClass(getApplicationContext(),SaveActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onStart() {

        super.onStart();
        n = 0;
        while (true){
            if (!sp.getString(String.valueOf((n)),"").equals("")){
                n++;
            }
            else{
                break;
            }
        }
    }

    private String dictionaryEntries() {
        final String language = "en-us";
        final String word = enterWord.getText().toString();
        final String fields = "etymologies";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }


    public void sendRequestOnClick(View v){
        DictionaryRequest request = new DictionaryRequest(this, history);
        url = dictionaryEntries();
        request.execute(url);

        if(!enterWord.getText().toString().equals("")){
            sp.edit().putString(String.valueOf((int) (n)),enterWord.getText().toString()).apply();//**commit()**;
            n++;
        }
        else {

        }
    }

}
