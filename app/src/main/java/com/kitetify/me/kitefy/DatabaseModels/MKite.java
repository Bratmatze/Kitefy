package com.kitetify.me.kitefy.DatabaseModels;

public class MKite extends Material {

    private int _kID;
    private int _lines;
    private float _size;
    private float _windForm;
    private float _windTill;
    private String _shape;
    private String _text;
    private Material _material;

    public MKite() {}

    public MKite(int _lines, String _shape, float _size, String _text, float _windForm, float _windTill) {
        this._lines = _lines;
        this._shape = _shape;
        this._size = _size;
        this._text = _text;
        this._windForm = _windForm;
        this._windTill = _windTill;
    }

    // ------------------- GETTER -----------------------
    public int get_kID() {
        return _kID;
    }

    public int get_lines() {
        return _lines;
    }

    public String get_shape() {
        return _shape;
    }

    public float get_size() {
        return _size;
    }

    public String get_text() {
        return _text;
    }

    public float get_windForm() {
        return _windForm;
    }

    public float get_windTill() {
        return _windTill;
    }

    public Material get_material() {
        return _material;
    }

    // -------------- SETTER ---------------------
    public void set_lines(int _lines) {
        this._lines = _lines;
    }

    public void set_shape(String _shape) {
        this._shape = _shape;
    }

    public void set_size(float _size) {
        this._size = _size;
    }

    public void set_text(String _text) {
        this._text = _text;
    }

    public void set_windForm(float _windForm) {
        this._windForm = _windForm;
    }

    public void set_windTill(float _windTill) {
        this._windTill = _windTill;
    }

    public void set_material(Material _material) {
        this._material = _material;
    }

    public void set_kID(int _kID) {
        this._kID = _kID;
    }
}
