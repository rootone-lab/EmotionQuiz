package com.rootonelabs.vicky.emotionquiz;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;



import static java.lang.Math.random;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private Button option1, option2, option3, option4;
    String imageSrc="", ans= "";
    ArrayList<HashMap<String, String>> questionList;
    int correctOption,i = 0;

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("question.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public void createMap() {

        try{
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray m_jArry = obj.getJSONArray("questions");
            questionList = new ArrayList<>();
            HashMap<String, String> info;

            for (int i = 0; i < 5; i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("image"));
                String image = jo_inside.getString("image");
                String answer = jo_inside.getString("answer");

                //Add your values in your `ArrayList` as below:
                info = new HashMap<String, String>();
                info.put("image", image);
                info.put("answer",answer);

                questionList.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void chooseAnswer(View view) throws InterruptedException {
        Log.i("TAG", "BUTTON"+ view.getTag() + "PRESSED");
        int id = view.getId();
        Button buttonPressed = (Button)findViewById(id);

        if(view.getTag().equals(Integer.toString(correctOption-1))){
            //buttonPressed.setButtonColor(getResources().getColor(R.color.fbutton_color_nephritis));
            Toast.makeText(this, "Correct Answer! :)", Toast.LENGTH_SHORT).show();
        }else{
            //buttonPressed.setButtonColor(getResources().getColor(R.color.fbutton_color_nephritis));
            Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT).show();
        }

        sleep(2000);

        getQuestion();
        setOptions();

    }

    void getQuestion()
    {
        if(i<questionList.size()) {
            HashMap<String, String> obj = new HashMap<String, String>();
            obj = questionList.get(i);
            ans="";
            int imgId = getResources().getIdentifier(obj.get("image"), "drawable", getPackageName());
            image.setImageDrawable(getDrawable(imgId));
            ans = ans + obj.get("answer");
            i++;
        }else {
            Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView)findViewById(R.id.image);
        option1 = (Button)findViewById(R.id.option1);
        option2 = (Button)findViewById(R.id.option2);
        option3 = (Button)findViewById(R.id.option3);
        option4 = (Button)findViewById(R.id.option4);

        createMap();
        getQuestion();
        setOptions();



    }



    private void setOptions() {

        String[] emotions = {"angry", "excited", "disgust", "happy", "sad"};
        ArrayList<String>answer = new ArrayList<>();

        int correctOption = ((int)Math.random()*4)+1;

        if(correctOption==1){
            option1.setText(ans);
        }
        else if(correctOption==2){
            option2.setText(ans);
        }
        else if(correctOption==3){
            option3.setText(ans);
        }
        else if(correctOption==4){
            option4.setText(ans);
        }

        for(int i=1;i<=4;i++){

            if(i==correctOption)
            {
                answer.add(ans);
            }
            else
            {
                answer.add(emotions[(correctOption+i)%emotions.length]);
            }

        }

        option1.setText(answer.get(0));
        option2.setText(answer.get(1));
        option3.setText(answer.get(2));
        option4.setText(answer.get(3));
    }
}
