package com.kitetify.me.kitefy.DatabaseModels;


public class MaterialPlus {

    private int _mTypeID;
    private int _mID;
    private int _mPlusID;

    public MaterialPlus() {}

    public MaterialPlus(int _mID, int _mPlusID, int _mTypeID) {
        this._mID = _mID;
        this._mPlusID = _mPlusID;
        this._mTypeID = _mTypeID;
    }

    // --------- GETTER --------------

    public int get_mID() {
        return _mID;
    }

    public int get_mPlusID() {
        return _mPlusID;
    }

    public int get_mTypeID() {
        return _mTypeID;
    }


    // --------- SETTER --------------


    public void set_mID(int _mID) {
        this._mID = _mID;
    }

    public void set_mPlusID(int _mPlusID) {
        this._mPlusID = _mPlusID;
    }

    public void set_mTypeID(int _mTypeID) {
        this._mTypeID = _mTypeID;
    }
}
