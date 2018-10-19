package com.thisismyapp.ratemyprofessor;

/*
- Professor class to use to make a "professor object" using the values in the strings.xml file
 */
public class Professor {

    private String name;
    private String rating;
    private String profClass;

    //Professor constructor
    public Professor(String profName, String profRating, String pClass){
        this.name = profName;
        this.rating = profRating;
        this.profClass = pClass;
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


}
