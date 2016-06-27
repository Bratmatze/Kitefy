package com.kitetify.me.kitefy.DatabaseModels;


public class Forecast {
    private Integer _fID;
    private String _name;

    public Forecast() {}

    public Forecast(Integer _fID, String _name) {
        this._fID = _fID;
        this._name = _name;
    }

    public Integer get_fID() {
        return _fID;
    }

    public void set_fID(Integer _fID) {
        this._fID = _fID;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
