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


        //Logic for handling errors with the user input boxes.
        //Checks that all boxes are filled out first then checks each box and if the input is invalid the corresponding pop-up is
        //displayed, if all checks pass then normal comment function is executed
        if (checkBoxes(user, rating, comment, hpw) ==  true){
            int numRating = Integer.parseInt(rating);
            int hoursPerWeekRating = Integer.parseInt(hpw);
            int commentCharCount = comment.length();            //Used to get the character count of the comment
            if(checkHoursPerWeek(hoursPerWeekRating) == false){
                createPopUp("Check Hours Per Week", "Please make sure the hours per week entered is between 1 and 40.");
            } else if (checkUserRating(numRating) == false){
                createPopUp("Check Rating", "Please make sure the professors rating entered is between 1 and 10.");
            } else if(commentCharCount >= 200){
                createPopUp("Check Comment", "Please make sure your comment is 200 characters or less");
            } else {
                userBox.setText("");
                ratingBox.setText("");
                commentBox.setText("");
                hoursPerWeekBox.setText("");
                prof.addComment(user, rating, prof.getProfClass(), comment, hoursPerWeekRating);
                profPage.addComment();
            }
        } else {
            createPopUp("Check Text Fields", "Please make sure no text fields are left empty.");
        }
    }


    /*
     * Description: This function checks that the inputted users rating is within the valid range
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
