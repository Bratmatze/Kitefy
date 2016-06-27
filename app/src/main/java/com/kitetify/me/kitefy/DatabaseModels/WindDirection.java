package com.kitetify.me.kitefy.DatabaseModels;


public class WindDirection {

    private int _windID;
    private String _direction;
    private double _degree;
    private String _name;

    public WindDirection() {}

    public WindDirection(double _degree, String _direction, String _name) {
        this._degree = _degree;
        this._direction = _direction;
        this._name = _name;
    }

    // ----- GETTER --------

    public double get_degree() {
        return _degree;
    }

    public String get_direction() {
        return _direction;
    }

    public String get_name() {
        return _name;
    }

    public int get_windID() {
        return _windID;
    }


    // ----- SETTER --------


    public void set_degree(double _degree) {
        this._degree = _degree;
    }

    public void set_direction(String _direction) {
        this._direction = _direction;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_windID(int _windID) {
        this._windID = _windID;
    }
}
