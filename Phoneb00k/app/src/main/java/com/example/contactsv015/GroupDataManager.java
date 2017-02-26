package com.example.contactsv015;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Александр on 24.02.2017.
 */

public class GroupDataManager {


    private static String GROUPS_FILE="groups";
    private static Gson gson = new Gson();

    public static List<GroupData> loadGroups(Context c){
        SharedPreferences prefs = c.getApplicationContext().getSharedPreferences(GROUPS_FILE,0);

        Set<String> groupSet = prefs.getStringSet("groups",new TreeSet<String>());

        return deSerializeGroups(groupSet);

    }

    public static List<GroupData> deSerializeGroups(Set<String> groupSet){
        ArrayList<GroupData> extracted = new ArrayList<GroupData>(groupSet.size());
        for(String s:groupSet){
            GroupData nextData = gson.fromJson(s,GroupData.class);
            extracted.add(nextData);
        }
        return extracted;
    }

    public static Set<String> serializeGroups (List<GroupData> groupDatas) {
        Set<String> groupSter= new TreeSet<String>();
        for (GroupData gd:groupDatas){
            String sgd =gson.toJson(gd);
            groupSter.add(sgd);
        }
        return groupSter;
    }

    public static void saveGroups (List<GroupData> groupDatas,Context c){

        SharedPreferences prefs= c.getApplicationContext().getSharedPreferences(GROUPS_FILE,0);

        prefs.edit().putStringSet("groups",serializeGroups(groupDatas)).apply();
    }


}
