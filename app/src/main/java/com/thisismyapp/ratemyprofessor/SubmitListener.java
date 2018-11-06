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
        TextView userBox = (TextView) profPage.findViewById(R.id.user_name_input);
        TextView ratingBox = (TextView) profPage.findViewById(R.id.user_rating_input);
        TextView commentBox = (TextView) profPage.findViewById(R.id.commentBar);

        String user = userBox.getText().toString();
        String rating = ratingBox.getText().toString();
        String comment = commentBox.getText().toString();

        //Alert Dialog creates the popup to tell user to double check their input
        AlertDialog.Builder prompt = new AlertDialog.Builder(profPage);
        prompt.setCancelable(true);
        prompt.setTitle("Check Rating");
        prompt.setMessage("Please double check that you add a number between 1 and 10 for the professors rating");
        prompt.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        //Actually checks if the user enters a number that is 1 - 10 using function from professor page
        int numRating = Integer.parseInt(rating);
        if (profPage.checkUserRating(numRating) == true) {
            userBox.setText("");
            ratingBox.setText("");
            commentBox.setText("");
            prof.addComment(user, rating, prof.getProfClass(), comment);
            profPage.addComment();
        } else {
            prompt.show();
        }
    }
}
