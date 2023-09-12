package org.example.move;

import org.python.jline.internal.Nullable;

public class Move {
    private String from;
    private String to;
    @Nullable
    private String captured;

    public void setCaptured(@Nullable String captured){ this.captured = captured; }

    public void setFrom(String from){
        this.from = from;
    }
    public void setTo(String to){
        this.to = to;
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


