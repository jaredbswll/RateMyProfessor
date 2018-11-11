package com.thisismyapp.ratemyprofessor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_page);

        //This line makes it so the keyboard isn't automatically launched when opening the app
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


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

        ///////////////////////////////////////////////////////////
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new SubmitListener(this, this.currentProfessor));

        addComment();

        /////////////////////////////////////////////////////////////

        //Call character counter for the comment box:
        commentCharCounter();

    }


    /*
    * Description: Counts the characters that the user types into the comment box (since the max allowed is 200 characters)
    *
    * Input: None
    * Output: Updates the "Character Counter" label under the comment box in real time
     */
    public void commentCharCounter(){
        //Getting text view
        final TextView charCountLabel = (TextView) findViewById(R.id.char_count);
        TextWatcher mTextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //This sets a textview to the current length
                charCountLabel.setText("Character Count: " + String.valueOf(s.length()) + "/200");
            }

            public void afterTextChanged(Editable s) {
            }
        };
        //Add listener to the comment box:
        EditText commentBox = (EditText) findViewById(R.id.commentBar);
        commentBox.addTextChangedListener(mTextEditorWatcher);
    }


    /*
    * Function to check that the inputted users rating is within the valid range
    *
    * Input: (Int Users Rating)
    * Output: True or False depending on if the users is from 1 - 10
    */
    public boolean checkUserRating(int rating){
        if (rating >= 1 && rating <= 10){
            return true;
        } else {
            return false;
        }
    }


    /*
     * Function to add comment to the professor page when the submit button is pressed
     *
     * input: (String usersName, String usersRating, String date, String usersComment)
     * output: Updates the display by adding a comment with the various info
     */

    public void addComment() {
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
            TextView usersComment = (TextView) findViewById(R.id.users_comment2);
            usersComment.setId(count);
            count++;
            usersComment.setText(c.getComment());

        }

    }

    private void deleteComments(){
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.comment_frame, null);
        LinearLayout userComments = (LinearLayout) findViewById(R.id.user_comments);

        userComments.removeAllViews();
    }

}
