package com.thisismyapp.ratemyprofessor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        String r = "3.0/10";
        String date = "12/12/2018";
        String c = "This class was alright, i enjoyed most parts of it but this is really just a test";
        addComment(n, r, date, c);

    }


    /*
    * Function to add comment to the professor page when the submit button is pressed
    * input: (String usersName, String usersRating, String date, String usersComment)
    * output: Updates the display by adding a comment with the various info
    */
    public void addComment(String name, String rating, String date, String comment){
        //ADDING COMMENTS DYNAMICALLY:

        //TODO: Figure out a way to add another comment with different values

        TextView usersName = (TextView) findViewById(R.id.users_name);
        usersName.setText(name);

        TextView usersRating = (TextView) findViewById(R.id.users_rating);
        usersRating.setText(rating);

        TextView currentDate = (TextView) findViewById(R.id.date);
        currentDate.setText(date);

        TextView usersComment = (TextView) findViewById(R.id.users_comment);
        usersComment.setText(comment);


/*      ****Doesn't work fully yet****
        //Getting the correct layouts for the comment section
        LinearLayout commentLayout = findViewById(R.id.user_comments);
        LinearLayout usersInfo = findViewById(R.id.user_comment_info);
        LinearLayout usersCommentNameRatingDate = findViewById(R.id.user_comment_nrd);

        //add userInfo layout to comment layout:
        usersInfo.setOrientation(LinearLayout.HORIZONTAL);
        usersInfo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        commentLayout.addView(usersInfo);

        //Add users avatar to userInfo with constraints (look at professor_page.xml for constraints):
        ImageView userAvatar = (ImageView) findViewById(R.id.user_avatar);
        userAvatar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        usersInfo.addView(userAvatar);

        //Add the layout for the users name, rating and date:
        usersCommentNameRatingDate.setOrientation(LinearLayout.VERTICAL);
        usersCommentNameRatingDate.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        usersInfo.addView(usersCommentNameRatingDate);


        //Add the users info in the three text boxes next to the users avatar:
        TextView usersName = (TextView) findViewById(R.id.users_name);
        usersName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        usersName.setText(name);

        TextView usersRating = (TextView) findViewById(R.id.users_rating);
        usersRating.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        usersRating.setText(rating);

        TextView currentDate = (TextView) findViewById(R.id.date);
        usersRating.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        currentDate.setText(date);

        //Add the users comment to the end of the view:
        TextView usersComment = (TextView) findViewById(R.id.users_comment);
        usersComment.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        usersComment.setText(comment);
*/



    }

}
