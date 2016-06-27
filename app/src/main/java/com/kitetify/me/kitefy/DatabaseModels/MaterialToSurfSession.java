package com.kitetify.me.kitefy.DatabaseModels;


public class MaterialToSurfSession {

    private int _sID;
    private int _mID;

    public MaterialToSurfSession() {}

    public MaterialToSurfSession(int _sID) {
        this._sID = _sID;
    }


    //  GETTER // SETTER

    public int get_sID() {
        return _sID;
    }

    public void set_sID(int _sID) {
        this._sID = _sID;
    }

    public int get_mID() {
        return _mID;
    }

    public void set_mID(int _mID) {
        this._mID = _mID;
    }
}
