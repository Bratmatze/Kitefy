package com.kitetify.me.kitefy.DatabaseModels;


import java.util.Date;

public class SurfSession {


    private int _sID;
    private int _wID;
    private int _funID;
    private int _uID;
    private int _wcID;
    private float _windMin;
    private float _windMax;
    private float _windAvg;
    private float _forecast;
    private float _duration;
    private Date _date;
    private String _spot;

    public SurfSession() {}

    public SurfSession(float _duration,
                       float _forecast,
                       int _funID,
                       int _uID,
                       int _wcID,
                       int _wID,
                       float _windAvg,
                       float _windMax,
                       float _windMin,
                       Date _date,
                       String _spot) {

        this._duration = _duration;
        this._forecast = _forecast;
        this._funID = _funID;
        this._uID = _uID;
        this._wcID = _wcID;
        this._wID = _wID;
        this._windAvg = _windAvg;
        this._windMax = _windMax;
        this._windMin = _windMin;
        this._date = _date;
        this._spot = _spot;
    }

    // ----------------- GETTER -----------------

    public float get_duration() {
        return _duration;
    }

    public float get_forecast() {
        return _forecast;
    }

    public int get_funID() {
        return _funID;
    }

    public int get_sID() {
        return _sID;
    }

    public int get_uID() {
        return _uID;
    }

    public int get_wcID() {
        return _wcID;
    }

    public int get_wID() {
        return _wID;
    }

    public float get_windAvg() {
        return _windAvg;
    }

    public float get_windMax() {
        return _windMax;
    }

    public float get_windMin() {
        return _windMin;
    }

    public Date get_date() {
        return _date;
    }

    public String get_spot() {
        return _spot;
    }


    // ----------------- SETTER -----------------


    public void set_duration(float _duration) {
        this._duration = _duration;
    }

    public void set_forecast(float _forecast) {
        this._forecast = _forecast;
    }

    public void set_funID(int _funID) {
        this._funID = _funID;
    }

    public void set_uID(int _uID) {
        this._uID = _uID;
    }

    public void set_wcID(int _wcID) {
        this._wcID = _wcID;
    }

    public void set_wID(int _wID) {
        this._wID = _wID;
    }

    public void set_windAvg(float _windAvg) {
        this._windAvg = _windAvg;
    }

    public void set_windMax(float _windMax) {
        this._windMax = _windMax;
    }

    public void set_windMin(float _windMin) {
        this._windMin = _windMin;
    }

    public void setDate(Date _date) {
        this._date = _date;
    }

    public void set_spot(String _spot) {
        this._spot = _spot;
    }

    public void set_date(Date _date) {
        this._date = _date;
    }

    public void set_sID(int _sID) {
        this._sID = _sID;
    }
}
