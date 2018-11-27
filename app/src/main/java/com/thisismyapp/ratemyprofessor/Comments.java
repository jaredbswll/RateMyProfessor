package com.thisismyapp.ratemyprofessor;


public class Comments{

    private String name;
    private String rating;
    private String classTaken;
    private String comment;
    private String hpw;
    private String classDiff;

    //Comment constructor
    public Comments(String profName, String profRating, String takenClass, String userComment, String hoursPerWeek, String classDifficulty){
        this.name = profName;
        this.rating = profRating;
        this.classTaken = takenClass;
        this.comment  = userComment;
        this.hpw = hoursPerWeek;
        this.classDiff = classDifficulty;
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

    public String getHpw(){
        return this.hpw;
    }

    public String getClassDiff() {return this. classDiff;}
}
