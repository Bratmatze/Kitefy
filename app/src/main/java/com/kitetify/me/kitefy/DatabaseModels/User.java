package com.kitetify.me.kitefy.DatabaseModels;


public class User {

    private int _uID;
    private String _name;

    public User() {}

    public User(String _name) {
        this._name = _name;
    }



    // GETTER // SETTER


    public void set_uID(int _uID) {
        this._uID = _uID;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_uID() {
        return _uID;
    }
}
