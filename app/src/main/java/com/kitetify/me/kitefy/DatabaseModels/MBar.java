package com.kitetify.me.kitefy.DatabaseModels;


public class MBar extends Material {

    private int    _bID;
    private int    _lines;
    private float  _length;
    private float  _width;
    private String _text;
    private Material _material;


    public MBar() {}

    public MBar(float _length, int _lines, float _width, String _text) {
        this._length = _length;
        this._lines = _lines;
        this._width = _width;
        this._text = _text;
    }

    public float get_length() {
        return _length;
    }

    public void set_length(float _length) {
        this._length = _length;
    }

    public int get_lines() {
        return _lines;
    }

    public void set_lines(int _lines) {
        this._lines = _lines;
    }

    public float get_width() {
        return _width;
    }

    public void set_width(float _width) {
        this._width = _width;
    }

    public String get_text() {
        return _text;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public int get_bID() {
        return _bID;
    }

    public Material get_material() {
        return _material;
    }

    public void set_material(Material _material) {
        this._material = _material;
    }

    public void set_bID(int _bID) {
        this._bID = _bID;
    }
}
