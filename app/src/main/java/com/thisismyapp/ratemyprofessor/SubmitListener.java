package com.thisismyapp.ratemyprofessor;

import android.view.View;
import android.widget.TextView;

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

        userBox.setText("");
        ratingBox.setText("");
        commentBox.setText("");

        prof.addComment(user, rating, prof.getProfClass(), comment);
        profPage.addComment();

    }
}
