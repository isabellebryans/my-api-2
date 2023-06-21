package org.example.results;

public class Results {

    private int CR;
    private int boardStatus;


    public Results() {
        this.CR = 2;
        this.boardStatus = 2;
    }

    public void setCR(int CR){
        this.CR = CR;
    }

    public int getCR(){ return CR; }

    public void setBoardStatus(int boardStatus){
        this.boardStatus = boardStatus;
    }

}
