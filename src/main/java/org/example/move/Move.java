package org.example.move;

import org.python.jline.internal.Nullable;

public class Move {
    private String piece;
    private String from;
    private String to;
    @Nullable
    private String captured;

    public void setPiece(String piece){
        this.piece = piece;
    }
    public void setCaptured(@Nullable String captured){ this.captured = captured; }

    public void setFrom(String from){
        this.from = from;
    }
    public void setTo(String to){
        this.to = to;
    }
    public String getPiece(){
        return piece;
    }
    @Nullable
    public String getCaptured() {return captured; }

    public String getTo(){
        return to;
    }

    public String getfrom(){
        return from;
    }

}


