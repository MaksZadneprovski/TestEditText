package com.example.testedittext.db;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Converters {
    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        if (list == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    @TypeConverter
    public static ArrayList<String> toArrayList(String value) {
        if (value == null) {
            return null;
        }

        ArrayList<String> list = new ArrayList<>();
        String[] items = value.split(",");
        Collections.addAll(list, items);
        return list;
    }
}

