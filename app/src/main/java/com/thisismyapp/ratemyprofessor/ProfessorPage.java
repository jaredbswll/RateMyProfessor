package com.thisismyapp.ratemyprofessor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfessorPage extends AppCompatActivity {

    private String[] profNameArray;
    private String[] profRatingArray;       //String arrays to hold professors values retrieved from strings.xml file
    private String[] profClassArray;

    private String[] profCommentsArray;

    private String profSearch;              //Variable to be used later the name of the professor that the user searched for
    private Database database;     //Place where data will be stored for professors in the meantime
    private ArrayList<Professor> tempDatabase;
    private Professor currentProfessor;       //current Professor being shown on the page

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;  //Used to get the info back from the commentPage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_page);


        //dataBase of professors exists in the ActivityMain class. Here is where we initialize that database
        database = MainActivity.getDatabase();
        if(database == null){
            database = new Database(findViewById(R.id.main_layout));
            MainActivity.setDatabase(database);
        }
        tempDatabase = database.database;

        //Initializing arrays (getting them from strings.xml file)
        profClassArray = getResources().getStringArray(R.array. array_professor_class);
        profRatingArray = getResources().getStringArray(R.array. array_professor_rating);
        profNameArray = getResources().getStringArray(R.array. array_professor);

        profCommentsArray = getResources().getStringArray(R.array.array_professor_comments);

        //This will work:

        //RANDOM VALUE FOR profSearch TO TEST:
        profSearch = this.getIntent().getStringExtra("professor");

        //Search to get professors correct index to be used by each array
        int index = 0;
        for (int i = 0; i < profNameArray.length; i++){
            if (profSearch.equals(profNameArray[i])){
                index = i;
//                currentProfessor = new Professor(profNameArray[i], profRatingArray[i], profClassArray[i]);
                currentProfessor = tempDatabase.get(i);
//                currentProfessor.addComment("Dom", "4", "CSE 341", "My first comment");
//                currentProfessor.addComment("Dominic", "7", "CSE 234", "My second comment");
//                tempDatabase.add(currentProfessor);
//                //tempDatabase.add(new Professor(profNameArray[i], profRatingArray[i], profClassArray[i]));
            }
//            else {
//                tempDatabase.add(new Professor(profNameArray[i], profRatingArray[i], profClassArray[i]));
//            }
        }

        /*Get values to update the UI with:
        String name = profNameArray[index];
        String rating = profRatingArray[index];
        String profClass = profClassArray[index];

        String profComments = profCommentsArray[0];

        String[] temp = profComments.split("\n");

        //Making professor object(do not know if this is needed):
        Professor newProf = new Professor(name, rating, profClass); */

        //Update the UI:
        TextView tv1 = (TextView) findViewById(R.id.professor_name);
        tv1.setText(currentProfessor.getName());
        TextView tv2 = (TextView) findViewById(R.id.professor_rating);
        tv2.setText(currentProfessor.getRating());
        TextView tv3 = (TextView) findViewById(R.id.professor_class);
        tv3.setText(currentProfessor.getProfClass());

        final ProfessorPage currentPage = this;
        final Professor currentProf = this.currentProfessor;

        ///////////////////////////////////////////////////////////
        //Send user to comment page if button is clicked to leave a comment
        Button leaveComment = (Button) findViewById(R.id.leave_comment_btn);
        leaveComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(currentPage, CommentPage.class);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
                //Intent i = new Intent(ProfessorPage.this, CommentPage.class);
                //Bundle b = new Bundle();
                //b.putSerializable("profPage", currentPage);
                //b.putSerializable("prof", currentProf);
                //i.putExtras(b);
                //startActivity(i);
            }
        });

        addComment();

        /////////////////////////////////////////////////////////////




        //Underlines Comments Label
        TextView commentLabel = findViewById(R.id.prof_page_comments_label);
        commentLabel.setPaintFlags(commentLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

    }


    /*
    * Description: This is a special function that is called when the user submits their comment it gets all
    *               of the info from the boxes and makes a comment with them
    *  Input: (Information from text boxes on professor page)
    *  Output: Add comment to professor then add the comment to the professors page
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                // Get String data from Intent
                String usersName = data.getStringExtra("userName");
                String usersRating= data.getStringExtra("userRating");
                String usersComment = data.getStringExtra("comment");
                String usersHpwRating = data.getStringExtra("hpw");

                //Add that comment info to the professors object:
                currentProfessor.addComment(usersName, usersRating, currentProfessor.getProfClass(), usersComment, usersHpwRating);
                this.addComment();
            }
        }
    }


    /*
     * Function to add comment to the professor page when the submit button is pressed
     *
     * input: (String usersName, String usersRating, String date, String usersComment)
     * output: Updates the display by adding a comment with the various info
     */

    public void addComment() {

        //Closes the keyboard whenever this function is called
        //The purpose is to close the keyboard when the user submits their comment
        //InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        //ADDING COMMENTS DYNAMICALLY:
        deleteComments();


        int count = 0;
        for (Comments c : currentProfessor.getComments()) {
            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = inflater.inflate(R.layout.comment_frame, null);
            LinearLayout mainLayout = (LinearLayout) findViewById(R.id.user_comments);

            //Adding comment to the view:
            mainLayout.addView(myView);
            //Setting values of the comment to whatever is passed in:
            TextView usersName = (TextView) findViewById(R.id.users_name2);
            usersName.setId(count);
            count++;
            usersName.setText("NAME:  " + c.getName());

            TextView usersRating = (TextView) findViewById(R.id.users_rating2);
            usersRating.setId(count);
            count++;
            usersRating.setText("RATING:  " + c.getRating());

            TextView currentDate = (TextView) findViewById(R.id.date2);
            currentDate.setId(count);
            count++;
            currentDate.setText("CLASS TAKEN:  " + c.getClassTaken());

            TextView usersHoursPerWeek = (TextView) findViewById(R.id.users_hours_per_week2);
            usersHoursPerWeek.setId(count);
            count++;
            usersHoursPerWeek.setText("HOURS PER WEEK:  " + c.getHpw());

            TextView usersComment = (TextView) findViewById(R.id.users_comment2);
            usersComment.setId(count);
            count++;
            usersComment.setText(c.getComment());


        }

        getAvgHoursPerWeek();
    }

    /*
     * Description: Loops through the comments and displays the average hours per week
     *
     * Input: None (just uses professor instance variable)
     * Output: Updates the hour per week label with correct average of all comments
     */
    public void getAvgHoursPerWeek(){
        int hpwSum = 0;
        int count = 0;
        for (Comments c : currentProfessor.getComments()) {
            int rating = Integer.parseInt(c.getHpw());
            hpwSum += rating;
            count++;
        }
        TextView profHoursPerWeek = (TextView) findViewById(R.id.prof_hours_per_week);
        if(count == 0){
            //Do Nothing
            //This catches divide by zero issue when first loading page
        } else {
            int avgHPW = hpwSum / count;
            String hpwLabel = String.valueOf(avgHPW);
            profHoursPerWeek.setText("Hours Per Week (Average): " + hpwLabel);
        }
    }

    private void deleteComments(){
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.comment_frame, null);
        LinearLayout userComments = (LinearLayout) findViewById(R.id.user_comments);

        userComments.removeAllViews();
    }

}
