package com.kitetify.me.kitefy.DatabaseModels;




public class WindCharacter {


    private int _wcID;
    private String _name;

    public WindCharacter() {    }

    public WindCharacter(String _name) {
        this._name = _name;
    }

    // ----- GETTER --------

    public int get_wcID() {
        return _wcID;
    }

    public String get_name() {
        return _name;
    }


    // ----- SETTER --------


    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_wcID(int _wcID) {
        this._wcID = _wcID;
    }
}
