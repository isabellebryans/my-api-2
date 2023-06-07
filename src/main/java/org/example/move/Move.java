package org.example.move;

public class Move {
    private String piece;
    private String from;
    private String to;

    public void setPiece(String piece){
        this.piece = piece;
    }

    public void setFrom(String from){
        this.from = from;
    }
    public void setTo(String to){
        this.to = to;
    }
    public String getPiece(){
        return piece;
    }

    public String getTo(){
        return to;
    }

    public String getfrom(){
        return from;
    }

}


