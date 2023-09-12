package org.example.results;

public class Results {

    private int CR;
    private int staticBoard;


    public Results() {
        this.CR = 2;
        this.staticBoard = 2;
    }

    public void setCR(int CR){
        this.CR = CR;
    }

    public int getCR(){ return CR; }

    public void setStaticBoard(int staticBoard){
        this.staticBoard = staticBoard;
    }

}
