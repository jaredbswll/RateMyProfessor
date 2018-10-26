package com.thisismyapp.ratemyprofessor;

public class Comments {

    private String name;
    private String rating;
    private String classTaken;
    private String comment;

    //Comment constructor
    public Comments(String profName, String profRating, String takenClass, String userComment){
        this.name = profName;
        this.rating = profRating;
        this.classTaken = takenClass;
        this.comment  = userComment;
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
}
