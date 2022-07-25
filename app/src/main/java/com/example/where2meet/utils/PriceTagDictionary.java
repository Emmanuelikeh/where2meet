package com.example.where2meet.utils;

import java.util.Dictionary;
import java.util.Hashtable;

public class PriceTagDictionary {
    public Dictionary<String, String> getPriceTagDictionary() {
        return priceTagDictionary;
    }
    public Dictionary<String, String>  priceTagDictionary;
    public PriceTagDictionary(){
        priceTagDictionary = new Hashtable<>();
        priceTagDictionary.put("$","1");
        priceTagDictionary.put("$$", "2");
        priceTagDictionary.put("$$$", "4");
    }
}
