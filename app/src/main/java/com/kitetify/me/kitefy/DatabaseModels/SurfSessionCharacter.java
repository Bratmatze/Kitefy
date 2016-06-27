package com.kitetify.me.kitefy.DatabaseModels;


public class SurfSessionCharacter {

    private int _funID;
    private String _name;

    public SurfSessionCharacter() {}

    public SurfSessionCharacter(String _name) {
        this._name = _name;
    }

    // ----- SETTER --------

    public int get_funID() {
        return _funID;
    }

    public String get_name() {
        return _name;
    }


    // ----- GETTER --------


    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_funID(int _funID) {
        this._funID = _funID;
    }
}
