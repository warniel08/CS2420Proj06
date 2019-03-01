/*
 *   Warner Nielsen
 *   2/27/19
 *   Proj06
 *   CS 2420
 *   Garth Sorenson
 * */

/*
 *   The City class stores the name of a city. It has a getter
 *   and setter for the name data field. It also has a compareTo()
 *   method used to compare two different cities.
 * */

package NielWarnProj06;

public class City implements Comparable<City> {
    private String name_; // name data field to identify city

    // City constructor
    public City() {
        name_ = "";
    }

    // getter for city name
    public String getCityName() { return name_; }

    // setter for city name
    public void setCityName(String name) {
        name_ = name;
    }

    // compareTo override to compare one city against another
    @Override
    public int compareTo(City destination) {
        return (this.name_).compareTo(destination.name_);
    }
}
