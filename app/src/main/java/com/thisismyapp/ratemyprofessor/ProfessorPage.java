package com.thisismyapp.ratemyprofessor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ProfessorPage extends AppCompatActivity {

    private String[] profNameArray;
    private String[] profRatingArray;       //String arrays to hold professors values retrieved from strings.xml file
    private String[] profClassArray;

    private String profSearch;              //Variable to be used later the name of the professor that the user searched for

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_page);

        //Initializing arrays (getting them from strings.xml file)
        profClassArray = getResources().getStringArray(R.array. array_professor_class);
        profRatingArray = getResources().getStringArray(R.array. array_professor_rating);
        profNameArray = getResources().getStringArray(R.array. array_professor);

        //This will work:

        //RANDOM VALUE FOR profSearch TO TEST:
        profSearch = "He, Xin";

        //Search to get professors correct index to be used by each array
        int index = 0;
        for (int i = 0; i < profNameArray.length; i++){
            if (profSearch.equals(profNameArray[i])){
                index = i;
            }
        }

        //Get values to update the UI with:
        String name = profNameArray[index];
        String rating = profRatingArray[index];
        String profClass = profClassArray[index];

        //Making professor object(do not know if this is needed):
        Professor newProf = new Professor(name, rating, profClass);

        //Update the UI:
        TextView tv1 = (TextView) findViewById(R.id.professor_name);
        tv1.setText(name);
        TextView tv2 = (TextView) findViewById(R.id.professor_rating);
        tv2.setText(rating);
        TextView tv3 = (TextView) findViewById(R.id.professor_class);
        tv3.setText(profClass);

        //TODO: Test add comment functionality.
        String n = "Omero Paris";
        String r = "7.0";
        String date = "12/12/2018";
        String c = "This class was alright, I enjoyed most parts of it but this is really just a test";
        addComment(n, r, date, c);

        String n2 = "Omega";
        String r2 = "3.0";
        String date2 = "10/30/2018";
        String c2 = "This class was stupid I am going to play fortnite now...";
        addComment(n2, r2, date2, c2);

/*        String n3 = "Zoey";
        String r3 = "5.0";
        String date3 = "11/11/2018";
        String c3 = "This is potientially the worst skin in fortnite do NOT buy it, it can cause you to spew out toxicity and anyone you kill with this will instantly hate you!";
        addComment(n3, r3, date3, c3);
*/

    }


    /*
    * Function to add comment to the professor page when the submit button is pressed
    * input: (String usersName, String usersRating, String date, String usersComment)
    * output: Updates the display by adding a comment with the various info
    */
    public void addComment(String name, String rating, String date, String comment){


        //Actually adding view:
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.comment_frame,null);
        LinearLayout mainLayout = (LinearLayout) findViewById(R.id.main_layout);


        //Adding comment to the view:
        mainLayout.addView(myView);

        //Setting values of the comment to whatever is passed in:
        TextView usersName = (TextView) findViewById(R.id.users_name2);
        usersName.setText(name);
        TextView usersRating = (TextView) findViewById(R.id.users_rating2);
        usersRating.setText(rating);
        TextView currentDate = (TextView) findViewById(R.id.date2);
        currentDate.setText(date);
        TextView usersComment = (TextView) findViewById(R.id.users_comment2);
        usersComment.setText(comment);


        //THIS SECTION WAS ME TRYING TO MAKE THE "FRAME" FROM CODE, USELESS NOW BUT MIGHT BE GOOD TO KEEP:
/*
        //Making comment frame dynamically:
        //Outer most Linear Layout
        LinearLayout commentFrame = new LinearLayout(this);
        commentFrame.setOrientation(LinearLayout.VERTICAL);
        commentFrame.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        //Linear Layout for image, name, rating, date
        LinearLayout userInfo = new LinearLayout(this);
        userInfo.setOrientation(LinearLayout.VERTICAL);
        userInfo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        //Image View for users avatar (might need to change what you set the view to):
        ImageView userAvatar = new ImageView(this);
        userAvatar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        userAvatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background));


        //Linear Layout for the users actual comment (users, name, rating and date)
        LinearLayout usersNRD = new LinearLayout(this);
        usersNRD.setOrientation(LinearLayout.VERTICAL);
        usersNRD.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        //Text views to go into layout above
        TextView usersName = new TextView(this);
        usersName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        usersName.setText("TEST NAME");

        TextView usersRating = new TextView(this);
        usersRating.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        usersRating.setText("9.0");

        TextView currentDate = new TextView(this);
        currentDate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        currentDate.setText("10/30/18");

        //Final thing to create is the textView for the users actual comment:
        TextView usersActualComment = new TextView(this);
        usersActualComment.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        usersActualComment.setText("If you see this text I have actually got this to work and this is an example of what it should do");




        //Users avatar being difficult
        //Adding views together to get the view as it is on screen
        commentFrame.addView(userInfo);
        //userInfo.addView(userAvatar);
        userInfo.addView(usersNRD);
        usersNRD.addView(usersName);
        usersNRD.addView(usersRating);
        usersNRD.addView(currentDate);
        commentFrame.addView(usersActualComment);
*/
    }

}
