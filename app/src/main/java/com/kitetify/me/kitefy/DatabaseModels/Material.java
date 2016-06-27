package com.kitetify.me.kitefy.DatabaseModels;

import java.util.Date;

public class Material {

    private int _mID;
    private String _name;
    private String _type;
    private int _year;
    private float _price;
    private float _sellPrice;
    private String _condition;
    private Date _buyDate;
    private Date _sellDate;
    private int _sellFlag;
    private int _uID;

    public Material() {
        this._sellDate = new Date();
        //this._sellPrice = get_sellPrice();
        this._sellFlag = 0;
    }

    public Material(Date _buyDate, String _condition, String _name, float _price, String _type, int _uID, int _year) {
        this._buyDate = _buyDate;
        this._condition = _condition;
        this._name = _name;
        this._price = _price;
        this._type = _type;
        this._uID = _uID;
        this._year = _year;
        this._sellDate = new Date();
        //this._sellPrice = get_sellPrice();
        this._sellFlag = 0;
    }

    // --------------- GETTER -------------------

    public Date get_buyDate() {
        return _buyDate;
    }

    public String get_condition() {
        return _condition;
    }

    public int get_mID() {
        return _mID;
    }

    public String get_name() {
        return _name;
    }

    public float get_price() {
        return _price;
    }

    public String get_type() {
        return _type;
    }

    public int get_uID() {
        return _uID;
    }

    public int get_year() {
        return _year;
    }

    public Date get_sellDate() {
        return _sellDate;
    }

    public float get_sellPrice() {
        return _sellPrice;
    }

    public int get_sellFlag() {
        return _sellFlag;
    }
    // --------------- SETTER -------------------


    public void set_buyDate(Date _buyDate) {
        this._buyDate = _buyDate;
    }

    public void set_condition(String _condition) {
        this._condition = _condition;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_price(float _price) {
        this._price = _price;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public void set_year(int _year) {
        this._year = _year;
    }

    public void set_mID(int _mID) {
        this._mID = _mID;
    }

    public void set_uID(int _uID) {
        this._uID = _uID;
    }

    public void set_sellDate(Date _sellDate) {
        this._sellDate = _sellDate;
    }

    public void set_sellPrice(float _sellPrice) {
        this._sellPrice = _sellPrice;
    }

    public void set_sellFlag(int _sellFlag) {
        this._sellFlag = _sellFlag;
    }
}

