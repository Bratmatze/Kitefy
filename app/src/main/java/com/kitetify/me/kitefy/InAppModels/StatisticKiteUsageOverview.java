package com.kitetify.me.kitefy.InAppModels;

import java.math.BigDecimal;

/**
 * Diese Objekt entspricht, einer Tabllenzeile in der Statistiktabelle
 *
 *
 */
public class StatisticKiteUsageOverview {

    private String     _kite_names;
    private Integer    _number_of_usages;
    private double     _hours_of_usages;
    private double     _avg_hours_of_sessions;
    private double     _avg_windspeed;

    public StatisticKiteUsageOverview(String kite_names,
                                      Integer number_of_usages,
                                      double hours_of_usages,
                                      double avg_hours_of_sessions,
                                      double avg_windspeed) {

        _kite_names            = kite_names;
        _number_of_usages      = number_of_usages;
        _hours_of_usages       = hours_of_usages;
        _avg_hours_of_sessions = avg_hours_of_sessions;
        _avg_windspeed         = avg_windspeed;


    }


    public StatisticKiteUsageOverview() {

    }


    // ------------ setter -------------

    public void set_avg_hours_of_sessions(double _avg_hours_of_sessions) {
        this._avg_hours_of_sessions = _avg_hours_of_sessions;
    }

    public void set_avg_windspeed(double _avg_windspeed) {
        this._avg_windspeed = _avg_windspeed;
    }

    public void set_hours_of_usages(double _hours_of_usages) {
        this._hours_of_usages = _hours_of_usages;
    }

    public void set_kite_names(String _kite_names) {
        this._kite_names = _kite_names;
    }

    public void set_number_of_usages(Integer _number_of_usages) {
        this._number_of_usages = _number_of_usages;
    }


    // ------------ getter -------------


    public double get_avg_hours_of_sessions() {
        return _avg_hours_of_sessions;
    }

    public double get_avg_windspeed() {
        return _avg_windspeed;
    }

    public double get_hours_of_usages() {
        return _hours_of_usages;
    }

    public String get_kite_names() {
        return _kite_names;
    }

    public Integer get_number_of_usages() {
        return _number_of_usages;
    }
}
