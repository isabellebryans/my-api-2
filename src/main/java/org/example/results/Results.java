package org.example.results;

public class Results {

    private int TS;
    private int CR;
    private int TT;
    private int boardStatus;
    private int status;

    public Results() {
        this.TS = 2;
        this.CR = 2;
        this.TT = 2;
        this.boardStatus = 2;
        this.status = 2;
    }

    public int getTT(){
        return TT;
    }
    public void setTS(int TS){
        this.TS = TS;
    }

    public void setCR(int CR){
        this.CR = CR;
    }

    public void setTT(int TT){
        this.TT = TT;
    }

    public void setBoardStatus(int boardStatus){
        this.boardStatus = boardStatus;
    }

    public void setStatus(int status){
        this.status = status;
    }
}
