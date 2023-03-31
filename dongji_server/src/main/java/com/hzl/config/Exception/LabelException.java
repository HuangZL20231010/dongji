package com.hzl.config.Exception;

public class LabelException extends Exception{
    int length;

    public LabelException(String message,int length) {
        super(message);
        this.length=length;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public int getLength(){
        return length;
    }

}
