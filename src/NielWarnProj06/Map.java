/*
*   Warner Nielsen
*   2/27/19
*   Proj06
*   CS 2420
*   Garth Sorenson
* */

/*
*   The Map class is used to determine if a given city has a flight
*   that is adjacent to it. It has an isPath() method to determine if
*   given a particular city if there is a route to the destination city.
*   There is a method readFlightMap() that takes in two different file
*   names, one for cities and one for flights. The cities are stored
*   in an ArrayList to be used throughout the class. They are eventually
*   stored in the cityAdjListArr for the adjacent cities that determine
*   existing flight paths.
* */

package NielWarnProj06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Map {
    private ArrayList<City> citiesArray_; // to keep track of list of cities
    private ArrayList<City> isVisitedList_; // to keep track of if city is visited
    private List<LinkedList<City>> cityAdjListArr_; // to store adjacent cities as array of linked lists

    // Map Default Constructor creates empty flight map
    public Map() {
        citiesArray_ = new ArrayList<>();
        isVisitedList_ = new ArrayList<>();
        cityAdjListArr_ = new ArrayList<>();
    }

    // Reads flight information into the flight map
    public void readFLightMap(String cityFileName, String flightFileName) {
        try {
            // New scanners for each file
            Scanner cityFileInput = new Scanner(new File(cityFileName));
            Scanner flightFileInput = new Scanner(new File(flightFileName));

            /*
            *   While loop that runs while city file has a next line.
            *   It creates a new City object so that it can assign
            *   a name from the text file and store it in the citiesArray.
            * */
            while (cityFileInput.hasNextLine()) {
                City c = new City();
                c.setCityName(cityFileInput.nextLine());
                citiesArray_.add(c);
            }
            cityFileInput.close();

            /*
            *   While loop that runs while flight file has a next line.
            *   It creates two new cities; one called origin and the
            *   other called destination as well as a new Linked List
            *   called cityLinkedList. Each city will be added to the
            *   LinkedList and then added to the cityAdjacentListArray.
            *   The flight text file has a city separated with a , and
            *   a \t char. The useDelimiter method is used in the flight
            *   File so those chars are taken out and not saved in the
            *   adjacent city array.
            * */
            while (flightFileInput.hasNextLine()) {
                String cities;
                City originCity = new City();
                City destCity = new City();
                LinkedList<City> cityLinkedList = new LinkedList<>();
                String[] flightArray;

                // Assign nextLine from file to cities string
                cities = flightFileInput.nextLine();
                // Split cities string on , and \t chars, into two cities and store in array
                flightArray = cities.split(",\t");

                // set the city name of first index, store in originCity object
                originCity.setCityName(flightArray[0]);
                // add originCity to cityLinkedList
                cityLinkedList.add(originCity);
                // set the city name of second index, store in destinationCity object
                destCity.setCityName(flightArray[1]);
                // add destCity to the cityLinkedList
                cityLinkedList.add(destCity);

                // store the linkedlist of cities in the cityAdjacency list array
                cityAdjListArr_.add(cityLinkedList);
            }
            flightFileInput.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    // Marks a city as visited
    public void markVisited(City aCity) {
        isVisitedList_.add(aCity);
    }

    // Clears marks on all cities
    public void unvisitAll() {
        isVisitedList_.clear();
    }

    // Determines whether a city was visited
    public boolean isVisited(City aCity) {
        return isVisitedList_.contains(aCity);
    }

    // Check if city is on the cities list
    public boolean isOnCitiesList(City city) {
        for (City c:citiesArray_) {
            if (c.compareTo(city) == 0) {
                return true;
            }
        }
        return false;
    }

    /*
    *   Returns the next unvisited city, if any, that
    *   is adjacent to a given city. Returns null if no
    *   unvisited adjacent city was found.
    * */
    public City getNextCity(City fromCity) {
        City nextCity;
        try {
            for (int i = 0; i < cityAdjListArr_.size(); i++) {
                /*
                *   Condition checks that the first city in each adjacency list is
                *   equal to the fromCity AND if a city is not visited, it gets
                *   the adjacent city from the adjacency array
                * */
                if (cityAdjListArr_.get(i).get(0).compareTo(fromCity) == 0 && !isVisited(cityAdjListArr_.get(i).get(1))) {
                    nextCity = cityAdjListArr_.get(i).get(1);
                    return nextCity;
                }
            }
            return null;
        } catch (NoSuchElementException e) {
            System.out.println(e);
            throw e;
        }
    }

    /*
    *   Determines whether a sequence of flights between
    *   two cities exists.
    * */
    public boolean isPath(City originCity,
                          City destinationCity) {
// ---------------------------------------------------
// Determines whether a sequence of flights between two cities
// exists. Nonrecursive stack version.
// Precondition: originCity and destinationCity are the origin
// and destination cities, respectively.
// Postcondition: Returns true if a sequence of flights exists
// from originCity to destinationCity, otherwise returns
// false. Cities visited during the search are marked as
// visited in the flight map.
// Implementation notes: Uses a stack for the cities of a
// potential path. Calls unvisitAll, markVisited, and
// getNextCity.
// ---------------------------------------------------
        Stack stack = new Stack();

        City topCity, nextCity;
        unvisitAll();  // clear marks on all cities

        // push origin city onto stack, mark it visited
        stack.push(originCity);
        markVisited(originCity);

        topCity = (City)(stack.peek());
//        System.out.println("TopCity; " + topCity.getCityName());
//        System.out.println("StackEmpty; " + stack.isEmpty());
        while (!stack.isEmpty() &&
                (topCity.compareTo(destinationCity) != 0)) {
            // loop invariant: stack contains a directed path
            // from the origin city at the bottom of the stack
            // to the city at the top of the stack

            // find an unvisited city adjacent to the city on
            // the top of the stack
            nextCity = getNextCity(topCity);

            if (nextCity == null) {
                stack.pop();  // no city found; backtrack
            }
            else {                  // visit city
                stack.push(nextCity);
                markVisited(nextCity);
                topCity = (City)stack.peek();
            }  // end if
        }  // end while
        if (stack.isEmpty()) {
            return false;  // no path exists
        }
        else {
            return true;   // path exists
        }  // end if
    }  // end isPath
}
