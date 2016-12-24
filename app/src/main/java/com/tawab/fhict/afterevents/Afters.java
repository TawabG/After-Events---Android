package com.tawab.fhict.afterevents;

public class Afters {

    int _afterid;
    String _afterNaam;
    String _afterSoort;
    double _lat;
    double _lon;

    public Afters() {

    }

    public Afters(int id, String name, String soort, double lat, double lon){
        this._afterid = id;
        this._afterNaam = name;
        this._afterSoort = soort;
        this._lat = lat;
        this._lon = lon;
    }



    public int get_afterid() {
        return _afterid;
    }

    public void set_afterid(int _afterid) {
        this._afterid = _afterid;
    }

    public String get_afterNaam() {
        return _afterNaam;
    }

    public void set_afterNaam(String _afterNaam) {
        this._afterNaam = _afterNaam;
    }

    public String get_afterSoort() {
        return _afterSoort;
    }

    public void set_afterSoort(String _afterSoort) {
        this._afterSoort = _afterSoort;
    }

    public double get_lat() {
        return _lat;
    }

    public void set_lat(double _lat) {
        this._lat = _lat;
    }

    public double get_lon() {
        return _lon;
    }

    public void set_lon(double _lon) {
        this._lon = _lon;
    }








}
