package android.example.gameboy20;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.Math;

public class MainActivity extends AppCompatActivity
{
    //chance  --> red|blue
    //i       --> counter for reseting via the gold button
    //rw      --> stores the id of red player
    //bw      --> stores the id of blue player
    String chance="";
    int i=0;
    public String rw="";
    public String bw="";

    //initiated when blue moves
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void moveb(int num)
    {
        // if blue's chance
        if(chance.equals("") || chance.equals("blue"))
        {

            Button bt = findViewById(R.id.B);
            bt.setBackgroundColor(Color.parseColor("#004697"));

            //alternate chance
            chance="red";
            int temp;
            View currentr;

            //if blue has started
            if (bw.length() != 0)
            {
                // cc is the current step; using bw to compute the new step
                String cc;
                cc = bw;

                //updating bw
                temp = 0;
                char[] chars = bw.toCharArray();

                for (int i = 0; i < chars.length; i++)
                {
                    if (chars[i] != 'm' && chars[i] != 'a')
                        temp = temp * 10 + Character.getNumericValue(chars[i]);

                }
                temp = temp + num;

                //common block found
                if(temp>16)
                {
                    bw = "m" + temp;
                }
                //special path
                else
                {
                    bw = "m" + temp + "a";
                }

                // if invalid
                if (temp > 47)
                {
                    //don't make a move
                    bw = cc;
                    Button redTemp= findViewById(R.id.B);
                    redTemp.setText(String.valueOf(num));
                }
                //if valid; make a move ahead
                else
                {
                    //Always changing the past step back to original
                    reset(cc);

                    //if blue is lands on a red block, eliminating red
                    if (bw.equals(rw))
                    {
                        reset(rw);
                        temp = getResources().getIdentifier(rw, "id", getPackageName());
                        currentr = findViewById(temp);
                        currentr.setBackgroundColor(Color.parseColor("#FF8A8A"));

                    }

                    //if blue lands on gold, won
                    if (bw.equals("m47"))
                    {
                        won("Congratulations, Player Blue");
                    }

                    //else keep on moving
                    else
                    {
                        String a;
                        a = bw;
                        //dark_grey step  --> reset the game to original
                        if (a.equals("m7") || a.equals("m12") || a.equals("m16") || a.equals("m28") || a.equals("m34") || a.equals("m39") || a.equals("m41") || a.equals("m46") || a.equals("m7a") || a.equals("m12a") || a.equals("m16a"))
                        {
                            regameb();

                            // change the text of the button
                            Button redTemp= findViewById(R.id.B);
                            redTemp.setText("OOPS");
                            redTemp= findViewById(R.id.R);
                            redTemp.setText("YIPPEE");

                        }
                        //green --> take a move
                        else if (a.equals("m17") || a.equals("m22") || a.equals("m44") || a.equals("m35"))
                        {
                            chance="blue";

                            temp = getResources().getIdentifier(bw, "id", getPackageName());
                            currentr = findViewById(temp);
                            currentr.setBackgroundColor(Color.parseColor("#74B5FF"));

                            // change the text of the button
                            Button redTemp= findViewById(R.id.B);
                            redTemp.setText("GOOD");
                            redTemp= findViewById(R.id.R);
                            redTemp.setText("SORRY");
                        }
                        //simple step --> make a move (change the color of next stop)
                        else
                        {
                            temp = getResources().getIdentifier(bw, "id", getPackageName());
                            currentr = findViewById(temp);
                            currentr.setBackgroundColor(Color.parseColor("#74B5FF"));

                            // change the text of the button
                            Button redTemp= findViewById(R.id.B);
                            redTemp.setText(String.valueOf(num));
                        }
                    }
                }
            }

            // if red has just started --> directly do the stuff
            else {
                bw = "m" + num+ "a" ;
                change(bw,"#74B5FF");

                // change the text of the button
                Button redTemp= findViewById(R.id.B);
                redTemp.setText(String.valueOf(num));

            }


        }

        // if red's chance --> tell him to wait his turn
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "-- AIN'T YOUR CHANCE NOW --" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //initiated when red moves
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void mover(int num)
    {
        // if red's chance
        if(chance.equals("") || chance.equals("red"))
        {
            Button bt = findViewById(R.id.R);
            bt.setBackgroundColor(Color.parseColor("#B10000"));

            int temp;
            View currentr;
            chance="blue";
            //if red has started
            if (rw.length() != 0)
            {
                String cc;
                cc = rw;

                //  use the rw variable to select only the value part and update rw
                temp = 0;
                char[] chars = rw.toCharArray();

                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] != 'm' && chars[i] != 'a')
                        temp = temp * 10 + Character.getNumericValue(chars[i]);

                }
                temp = temp + num;
                rw = "m" + temp;

                // if invalid
                if (temp > 47)
                {
                    rw = cc;
                    Button redTemp= findViewById(R.id.R);
                    redTemp.setText(String.valueOf(num));
                }
                else
                    {
                    //change the thing back to normal
                    reset(cc);


                    if (rw.equals(bw))
                    {
                        reset(bw);
                        temp = getResources().getIdentifier(bw, "id", getPackageName());
                        currentr = findViewById(temp);
                        currentr.setBackgroundColor(Color.parseColor("#74B5FF"));
                    }


                    if (rw.equals("m47"))
                    {
                        won("Congratulations, Player Red");
                    }
                    else
                        {
                            String a;
                            a = rw;
                            if (a.equals("m7") || a.equals("m12") || a.equals("m16") || a.equals("m28") || a.equals("m34") || a.equals("m39") || a.equals("m41") || a.equals("m46") || a.equals("m7a") || a.equals("m12a") || a.equals("m16a"))
                            {
                                regamer();

                                // change the text of the button
                                Button redTemp= findViewById(R.id.R);
                                redTemp.setText("OOPS");
                                redTemp= findViewById(R.id.B);
                                redTemp.setText("YIPPEE");

                            }
                            else if (a.equals("m17") || a.equals("m22") || a.equals("m44") || a.equals("m35"))
                            {
                                chance="red";

                                temp = getResources().getIdentifier(rw, "id", getPackageName());
                                currentr = findViewById(temp);
                                currentr.setBackgroundColor(Color.parseColor("#FF8A8A"));

                                // change the text of the button
                                Button redTemp= findViewById(R.id.R);
                                redTemp.setText("GOOD");
                                redTemp= findViewById(R.id.B);
                                redTemp.setText("SORRY");

                            }
                            else
                                {
                                    temp = getResources().getIdentifier(rw, "id", getPackageName());
                                    currentr = findViewById(temp);
                                    currentr.setBackgroundColor(Color.parseColor("#FF8A8A"));

                                    // change the text of the button
                                    Button redTemp= findViewById(R.id.R);
                                    redTemp.setText(String.valueOf(num));
                                }
                        }
                }
            }

            // if red has just started
            else {
                rw = "m" + num;
                change(rw,"#FF8A8A");

                // change the text of the button
                Button redTemp= findViewById(R.id.R);
                redTemp.setText(String.valueOf(num));

            }


        }

        // if blues chance
        else
            {
            Toast toast = Toast.makeText(getApplicationContext(), "-- AIN'T YOUR CHANCE NOW --" , Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //onCreate, buttons handling
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.R);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v)
            {
                // make a random choice of dice
                int randomChoice;
                double temp;
                temp=Math.random()*6+1;
                randomChoice=(int)temp;



                //make the red color move randomChoice number of steps
                mover(randomChoice);
            }
        });

        Button btnn = (Button) findViewById(R.id.B);
        btnn.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {

                // make a random choice of dice
                int randomChoice;
                double temp;
                temp = Math.random() * 6 + 1;
                randomChoice = (int) temp;


                //make the blue color move randomChoice number of steps
                moveb(randomChoice);
            }

        });

        Button btnn1 = (Button) findViewById(R.id.m47);
        btnn1.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(View v) {
                if(i==0){
                    Toast.makeText(MainActivity.this, "ARE YOU SURE YOU WANT TO RESET", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "PRESS GOAL AGAIN TO RESET", Toast.LENGTH_SHORT).show();
                    i=1;
                }

                else{
                    i=0;
                    regamer();
                    regameb();
                    Toast ttoast = Toast.makeText(getApplicationContext(), "-xx- GAME RESET -xx-" , Toast.LENGTH_SHORT);
                    ttoast.show();
                }

            }

        });
        Toast toast = Toast.makeText(getApplicationContext(), "-xx- ANYONE CAN START THE GAME -xx-" , Toast.LENGTH_SHORT);
        toast.show();

    }

    //changes the color of view a, with color c
    //(c: hex code, a: contains the id)
    public void change(String a,String c)
    {
        int temp;
        View currentr;
        temp = getResources().getIdentifier(a, "id", getPackageName());
        currentr = findViewById(temp);
        currentr.setBackgroundColor(Color.parseColor(c));
    }

    //reset color with view a back to original
    public void reset(String a)
    {
        //nothing
        if (a.equals("")) { //change nothing
        }

        // dark_grey
        else if (a.equals("m7") || a.equals("m12") || a.equals("m16") || a.equals("m28") || a.equals("m34") || a.equals("m39") || a.equals("m41") || a.equals("m46") || a.equals("m7a") || a.equals("m12a") || a.equals("m16a"))
        { change(a,"#505050");}

        // green
        else if (a.equals("m17") || a.equals("m22") || a.equals("m44") || a.equals("m35"))
        { change(a,"#4fcf4f");}

        // gold
        else if (a.equals("m47"))
        { change(a,"#E9CA00");}

        //light grey
        else
        { change(a,"#ababab");}


    }

    //new game conditions for blue player
    public void regameb()
    {

        reset(bw);
        bw="";
        Button v1 =(Button) findViewById(R.id.B);
        v1.setText(" Blue ");
        v1.setBackgroundColor(Color.parseColor("#74B5FF"));

    }

    //new game conditions for red player
    public void regamer()
    {
        reset(rw);
        rw="";
        Button v =(Button) findViewById(R.id.R);
        v.setText(" Red ");
        v.setBackgroundColor(Color.parseColor("#FF8A8A"));

    }

    //condition of winning for a player
    public void won(String a)
    {
        regamer();
        regameb();
        //toast that player a has won
        Toast toast = Toast.makeText(getApplicationContext(), a , Toast.LENGTH_LONG);
        toast.show();

    }



    //MainActivity.java ends here --> :(
}



