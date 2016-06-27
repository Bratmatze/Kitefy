package com.kitetify.me.kitefy.DatabaseModels;



public class MaterialType {

    private int _mTypeID;
    private String _name;


    public MaterialType() {}

    public MaterialType(String _name) {
        this._name = _name;
    }


    // --------- GETTER --------------

    public String get_name() {
        return _name;
    }

    public int get_mTypeID() {
        return _mTypeID;
    }


    // --------- SETTER --------------


    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_mTypeID(int _mTypeID) {
        this._mTypeID = _mTypeID;
    }
}
