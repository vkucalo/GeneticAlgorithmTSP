package com.tsp;

/**
 * Created by Viktor on 12.5.2017..
 */
public class City {
    int x ;
    int y;
    int cuco = 2;
    public City(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "x = " + x + " " + "y = " + y;
    }
    public double izracunajUdaljenost(City c2) {
        return Math.sqrt((this.x - c2.x) * (this.x - c2.x) + (this.y - c2.y) * (this.y - c2.y));
    }
}