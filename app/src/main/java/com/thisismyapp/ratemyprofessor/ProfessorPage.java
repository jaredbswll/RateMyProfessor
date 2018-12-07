package com.thisismyapp.ratemyprofessor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProfessorPage extends AppCompatActivity {

    private String[] profNameArray;
    private String[] profRatingArray;       //String arrays to hold professors values retrieved from strings.xml file
    private String[] profClassArray;

    private String[] profCommentsArray;

    private String profSearch;              //Variable to be used later the name of the professor that the user searched for
    private Database database;     //Place where data will be stored for professors in the meantime
    private ArrayList<Professor> tempDatabase;
    private Professor currentProfessor;       //current Professor being shown on the page
    private ArrayList<String> classesList;
    private String[] testProfSearch;

    private FirebaseFirestore _firestore;

    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;  //Used to get the info back from the commentPage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_page);

        _firestore = FirebaseFirestore.getInstance();

        //dataBase of professors exists in the ActivityMain class. Here is where we initialize that database
        database = MainActivity.getDatabase();
        if(database == null){
            database = new Database();
            MainActivity.setDatabase(database);
        }
        //tempDatabase = database.database;

        //Initializing arrays (getting them from strings.xml file)
        profClassArray = getResources().getStringArray(R.array. array_professor_class);
        profRatingArray = getResources().getStringArray(R.array. array_professor_rating);
        profNameArray = getResources().getStringArray(R.array. array_professor);

        profCommentsArray = getResources().getStringArray(R.array.array_professor_comments);

        //This will work:

        //RANDOM VALUE FOR profSearch TO TEST:
        //profSearch = this.getIntent().getStringExtra("professor");
        testProfSearch = this.getIntent().getStringArrayExtra("professor");
        classesList = this.getIntent().getStringArrayListExtra("classesList");

        currentProfessor = new Professor(testProfSearch[0], testProfSearch[1], testProfSearch[2]);

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
                //This is used to call the commentPage and wait until submit is clicked to get the
                //data from that page
                intent.putStringArrayListExtra("classesList", classesList);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        //Deletes and adds all comments so they aren't duplicated each time page is opened

        String[] commentsList = this.getIntent().getStringArrayExtra("comments");
        int numComments = commentsList.length/6;
        int counter = 0;
        for (int i = 0; i < numComments; i++){
            String classTaken = commentsList[counter]; //classTaken
            String rating = commentsList[counter + 1]; //rating
            String comment = commentsList[counter + 2]; //comment
            String classDiff = commentsList[counter + 3]; //classDiff
            String user = commentsList[counter + 4]; //user
            String hpw = commentsList[counter + 5]; //hpw

            currentProfessor.addComment(user, rating, classTaken,  comment, hpw, classDiff);

            counter += 6;
        }

        addComment();

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
                final String usersName = data.getStringExtra("userName");
                final String usersRating= data.getStringExtra("userRating");
                final String usersComment = data.getStringExtra("comment");
                final String usersHpwRating = data.getStringExtra("hpw");
                final String usersClassTaken = data.getStringExtra("ct");
                final String usersClassDifficulty = data.getStringExtra("cd");

                //Add that comment info to the professors object:
                currentProfessor.addComment(usersName, usersRating, usersClassTaken, usersComment, usersHpwRating, usersClassDifficulty);

                _firestore.collection("professors").document(currentProfessor.getName()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            DocumentReference docRef = doc.getReference();

                            Map<String, Object> comment = new HashMap<String, Object>();
                            comment.put("classDifficulty", usersClassDifficulty);
                            comment.put("classTaken", usersClassTaken);
                            comment.put("hpw", Integer.parseInt(usersHpwRating));
                            comment.put("rating", Integer.parseInt(usersRating));
                            comment.put("comment", usersComment);
                            comment.put("user", usersName);

                            ArrayList<Map<String, Object>> commentsList = (ArrayList<Map<String, Object>>) doc.get("comments");
                            commentsList.add(comment);

                            docRef.update("comments", commentsList);

                            addComment();

                        }
                    }
                });

                //this.addComment();
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
        //ADDING COMMENTS DYNAMICALLY:
        deleteComments();

        //Gets the current date using users phone
        String date = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());

        int count = 0;
        float numCom = 0;
        float avgRate = 0;
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

            TextView currentDate = (TextView) findViewById(R.id.date2);
            currentDate.setId(count);
            count++;
            currentDate.setText("DATE:  " + date);

            TextView usersRating = (TextView) findViewById(R.id.users_rating2);
            usersRating.setId(count);
            count++;
            usersRating.setText("RATING:  " + c.getRating());

            avgRate += Integer.parseInt(c.getRating());

            TextView usersClassTaken = (TextView) findViewById(R.id.users_class_taken2);
            usersClassTaken.setId(count);
            count++;
            usersClassTaken.setText("CLASS TAKEN:  " + c.getClassTaken());

            TextView usersHoursPerWeek = (TextView) findViewById(R.id.users_hours_per_week2);
            usersHoursPerWeek.setId(count);
            count++;
            usersHoursPerWeek.setText("HOURS PER WEEK:  " + c.getHpw());

            TextView usersClassDifficulty = (TextView) findViewById(R.id.users_class_difficulty2);
            usersClassDifficulty.setId(count);
            count++;
            usersClassDifficulty.setText("CLASS DIFFICULTY:  " + c.getClassDiff());

            TextView usersComment = (TextView) findViewById(R.id.users_comment2);
            usersComment.setId(count);
            count++;
            usersComment.setText(c.getComment());

            //count+=1;
            numCom += 1.0;

        }

        TextView tv2 = (TextView) findViewById(R.id.professor_rating);
        if(numCom == 0){
            tv2.setText("N/A");
            _firestore.collection("professors").document(currentProfessor.getName()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot doc = task.getResult();
                                DocumentReference docRef = doc.getReference();
                                docRef.update("rating", 0);
                            }
                        }
                    });
        }
        else{
            float tempRate = avgRate/numCom;
            final int rating = Math.round(tempRate);
            tv2.setText(Integer.toString(rating));

            _firestore.collection("professors").document(currentProfessor.getName()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful()){
                                DocumentSnapshot doc = task.getResult();
                                DocumentReference docRef = doc.getReference();
                                docRef.update("rating", rating);
                            }
                        }
                    });
        }

    }


    private void deleteComments(){
        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myView = inflater.inflate(R.layout.comment_frame, null);
        LinearLayout userComments = (LinearLayout) findViewById(R.id.user_comments);

        userComments.removeAllViews();
    }

}
