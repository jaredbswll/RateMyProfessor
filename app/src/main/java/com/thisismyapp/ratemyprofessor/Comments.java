package com.thisismyapp.ratemyprofessor;

public class Comments {

    private String name;
    private String rating;
    private String classTaken;
    private String comment;
    private int hpw;

    //Comment constructor
    public Comments(String profName, String profRating, String takenClass, String userComment, int hoursPerWeek){
        this.name = profName;
        this.rating = profRating;
        this.classTaken = takenClass;
        this.comment  = userComment;
        this.hpw = hoursPerWeek;
    }

    //Getters:
    public String getName() {
        return this.name;
    }

    public String getRating(){
        return this.rating;
    }

    public String getClassTaken() {
        return this.classTaken;
    }

    public String getComment(){
        return this.comment;
    }

    public int getHpw(){
        return this.hpw;
    }
}
