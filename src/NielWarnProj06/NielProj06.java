/*
 *   Warner Nielsen
 *   2/27/19
 *   Proj06
 *   CS 2420
 *   Garth Sorenson
 * */

/*
 *   The driver code will use the Map and City classes to read in
 *   a cities file and a flight file that will be given by the user.
 *   There is also a request file that contains an origin City and
 *   a destination City that will be stored in a cities ArrayList
 *   which will be used to compare with the cities from the Maps
 *   array.
 * */

package NielWarnProj06;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NielProj06 {
    public static void main(String[] args) {
        ArrayList<City> citiesArray; // ArrayList of Cities to store cities
        Map fMap = new Map(); // create new Map object
        String cities, flights, requests;
        City originCity; // new city object
        City destinationCity; // new city object

        // Intro to program
        System.out.println("Welcome to the Amazing Flight Map Request Checker!");
        System.out.println("\nThe program will ask you for a few different filenames " +
                        "\nto ensure the the program runs correctly. It will use" +
                        "\nthose files to extract information to determine if several" +
                        "\nflight requests are possible given the information from those files.");

        System.out.print("\nPlease enter the name of the file that contains the cities to" +
                        "\nbe used in this program: ");
        cities = getUserInput();

        System.out.print("Please enter the name of the file that contains the flights to" +
                "\nbe used in this program: ");
        flights = getUserInput();

        // Use readFlightMap to read files into the map object
        fMap.readFLightMap("src/NielWarnProj06/" + cities + ".txt", "src/NielWarnProj06/" + flights + ".txt");

        System.out.print("Please enter the name of the file that contains the flight requests to" +
                "\nbe used in this program: ");
        requests = getUserInput();

        // Use readRequestFile() method to store cities in citiesArray
        citiesArray = readRequestFile("src/NielWarnProj06/" + requests + ".txt");

        // iterator for while loop
        int i = 0;

        // loop on citiesArray size
        while (i < citiesArray.size()) {
            // Gets the 0th, 2nd, and 4th city from the array list
            originCity = citiesArray.get(i);
            // Gets the 1st, 3rd, and 5th city from the citiesArray list
            destinationCity = citiesArray.get(i+1);

            // Print request to fly from city to city
            System.out.println("\nRequest is to fly from " + originCity.getCityName() + " to " + destinationCity.getCityName() + ".");

            // If the 0th, 2nd, or 4th city from the citiesArray list is not in the map objects cities list
            if (!fMap.isOnCitiesList(originCity)) {
                // Print that it does not serve that city
                System.out.println("Sorry. HPAir does not serve " + originCity.getCityName());
            // Or if the 1st, 3rd, or 5th city from the citiesArray list is not in the map objects cities list
            } else if (!fMap.isOnCitiesList(destinationCity)) {
                // Print that it does not serve that city
                System.out.println("Sorry. HPAir does not serve " + destinationCity.getCityName());
            // Or if the two cities ARE in the flight Path
            } else if (fMap.isPath(originCity, destinationCity)) {
                // Print that it does fly from c to c2
                System.out.println("HPAir flies from " + originCity.getCityName() + " to " + destinationCity.getCityName() + ".");
            // Otherwise Print that it does not fly from c to c2
            } else {
                System.out.println("Sorry. HPAir does not fly from " + originCity.getCityName() + " to " + destinationCity.getCityName() + ".");
            }
            // incrementer increases by 2 so that it gets the next pair of cities from the citiesArray list inside driver
            i += 2;
        }
    }

    /*
    *   The readRequestFile() method takes the request file and stores the cities
    *   from the file into a citiesArrayList. The method returns the ArrayList of
    *   Cities so it can be used in the driver class.
    * */
    public static ArrayList<City> readRequestFile(String requestFileName) {
        ArrayList<City> citiesArrayList = new ArrayList<>(); // create citiesArrayList to store cities from file

        try {
            // new scanner for the request text file
            Scanner requestFileInput = new Scanner(new File(requestFileName));

            while (requestFileInput.hasNextLine()) {
                String cities;
                City originCity = new City();
                City destCity = new City();
                String[] flightArray;

                // assign the next line to the cities string
                cities = requestFileInput.nextLine();
                // split the cities string on a , and \t chars and store two cities in flightArray
                flightArray = cities.split(",\t");

                // set the city name of first index, store in originCity object
                originCity.setCityName(flightArray[0]);
                // add originCity to citiesArrayList
                citiesArrayList.add(originCity);
                // set the city name of second index, store in destinationCity object
                destCity.setCityName(flightArray[1]);
                // add destCity to the citiesArrayList
                citiesArrayList.add(destCity);
            }
            return citiesArrayList; // return the citiesArrayList

        } catch (FileNotFoundException e) {
            System.out.println(e);
            return null;
        }
    }

    // Method to get user input because it is asked several times
    public static String getUserInput() {
        String fileName;

        // user input for file names to be used
        Scanner userInput = new Scanner(System.in);
        fileName = userInput.next();

        return fileName;
    }
}