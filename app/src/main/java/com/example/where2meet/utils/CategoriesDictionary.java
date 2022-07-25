package com.example.where2meet.utils;

import java.util.Dictionary;
import java.util.Hashtable;

public class CategoriesDictionary {
    public Dictionary<String, String> getCategoriesDictionary() {
        return categoriesDictionary;
    }
    public Dictionary<String, String> categoriesDictionary;
    public CategoriesDictionary(){
        categoriesDictionary = new Hashtable<String, String>();
        categoriesDictionary.put("Arts and Entertainment","10000");
        categoriesDictionary.put("Business and Professional Services", "11000");
        categoriesDictionary.put("Community and Government","12000");
        categoriesDictionary.put("Dining and Drinking","13000");
        categoriesDictionary.put("Event","14000");
        categoriesDictionary.put("Health and Medicine","15000");
        categoriesDictionary.put("Landmarks and Outdoors","16000");
        categoriesDictionary.put("Retail","17000");
        categoriesDictionary.put("Sports and Recreation","18000");
        categoriesDictionary.put("Travel and Transportation","19000");
    }

}
