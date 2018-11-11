package com.thisismyapp.ratemyprofessor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitListener implements View.OnClickListener {

    private ProfessorPage profPage;
    private Professor prof;

    public SubmitListener(ProfessorPage pp, Professor p){
        profPage = pp;
        prof = p;
    }

    @Override
    public void onClick(View v) {
        //Getting references to TextViews
        TextView userBox = (TextView) profPage.findViewById(R.id.user_name_input);
        TextView ratingBox = (TextView) profPage.findViewById(R.id.user_rating_input);
        TextView commentBox = (TextView) profPage.findViewById(R.id.commentBar);
        TextView hoursPerWeekBox = (TextView) profPage.findViewById(R.id.user_hours_per_week);

        //Getting the professors information for adding the hours per week label to their information
        TextView profHoursPerWeekBox = profPage.findViewById(R.id.prof_hours_per_week);

        //Getting the strings the user types into those text views
        String user = userBox.getText().toString();
        String rating = ratingBox.getText().toString();
        String comment = commentBox.getText().toString();
        String hpw = hoursPerWeekBox.getText().toString();

        // TODO - Clean up logic, check for conditions to be false then make up pop up box for each, have else at end that does normal stuff
        // TODO - Also need to add hours per week to the users comments

        //Checks if all input boxes have something in them
        if (checkBoxes(user, rating, comment, hpw)){
            int numRating = Integer.parseInt(rating);
            int hoursPerWeekRating = Integer.parseInt(hpw);
            int commentCharCount = comment.length();            //Used to get the character count of the comment
            //Actually checks if the user enters a number that is 1 - 10 using function from professor page and characterCount
            if ( checkHoursPerWeek(hoursPerWeekRating) == true && profPage.checkUserRating(numRating) == true && commentCharCount <= 200) {
                userBox.setText("");
                ratingBox.setText("");
                commentBox.setText("");
                profHoursPerWeekBox.setText("Hours Per Week: " + hpw);
                prof.addComment(user, rating, prof.getProfClass(), comment);
                profPage.addComment();
            } else {
                createPopUp("Check Rating/Comment", "Please double check that you input a number between 1 and 10 for the professors rating, input a number between 1 and 40 for the hours per week box and your comment is 200 characters or less.");
            }
        } else {
            createPopUp("Check Text Fields", "Please make sure no text fields are left empty.");
        }

    }


    /*
    * Description: This function returns true if the hours per week they typed in is between 1 and 40, false otherwise
    *
    * Input: (int the number of hours per week)
    * Output: true/false
    *
     */
    public boolean checkHoursPerWeek(int userInput){
        if (userInput >= 1 && userInput <= 40){
            return true;
        } else {
            return false;
        }
    }

    /*
    * Description: This function takes in a title string, and a message string and creates a pop-up box that
    *               will display the pop up with the specified title and message
    * Input: (String titleMessage, String actualMessage)
    * Output: Displays message with the specified title and message when this function is called
     */
    public void createPopUp(String title, String message){
        //Alert Dialog creates the popup to tell user to double check their input
        AlertDialog.Builder prompt = new AlertDialog.Builder(profPage);
        prompt.setCancelable(true);
        prompt.setTitle(title);
        prompt.setMessage(message);
        prompt.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        prompt.show();
    }

    /*
    * Description: This function returns true if all input boxes have input in them, false otherwise
    *
    * Input: (String usersName, String usersRating, String usersComment)
    * Output: True or False
     */
    public boolean checkBoxes(String usersName, String usersRating, String usersComment, String hoursPerWeek){
        if (usersName.equals("") || usersRating.equals("") || usersComment.equals("") || hoursPerWeek.equals("")){
            return false;
        } else {
            return true;
        }
    }
}
