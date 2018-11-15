package com.thisismyapp.ratemyprofessor;
import java.util.ArrayList;

/*
- Professor class to use to make a "professor object" using the values in the strings.xml file
 */
public class Professor {

    private String name;
    private String rating;
    private String profClass;
    private ArrayList<Comments> comments;

    //Professor constructor
    public Professor(String profName, String profRating, String pClass){
        this.name = profName;
        this.rating = profRating;
        this.profClass = pClass;
        comments = new ArrayList<Comments>();
    }

    //Getters to get names: (no setters for now since values won't be updated)
    public String getName(){
        return this.name;
    }

    public String getRating(){
        return this.rating;
    }

    public String getProfClass(){
        return this.profClass;
    }

    public ArrayList<Comments> getComments(){ return this.comments; }

    public void addComment(String name, String rating, String classTaken, String comment, int hoursPerWeek){
        comments.add(new Comments(name, rating, classTaken, comment, hoursPerWeek));
    }

    public void addComment(Comments comment) { comments.add(comment); }

}
