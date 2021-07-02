package com.example.toserver;

public class DbObject implements Comparable<DbObject>{

    private int ID;
    private int value;
    private String content;

    public DbObject(int val, String cont){
        this.value = val;
        this.content = cont;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(DbObject o) {

        int compareValue = ((DbObject)o).getValue();

        return this.value-compareValue;
    }
}
