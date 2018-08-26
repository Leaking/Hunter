package com.hunter.library;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by quinn on 27/06/2017.
 */

public class BlockManager {


    private static HashMap<String, ArrayList<Integer>> methodBlockDetails = new HashMap<>();

    public static void addMethodBlockDetail(String method, int mills) {
        ArrayList<Integer> costTimeList = methodBlockDetails.get(method);
        if(costTimeList == null) {
            costTimeList = new ArrayList<Integer>();
        }
        costTimeList.add(mills);
        methodBlockDetails.put(method, costTimeList);
    }

    public static String dump(int count) {
        StringBuilder stringBuilder = new StringBuilder("");
        HashMap<String, Float> method_block_average_map = new HashMap<>();
        HashMap<String, Integer> method_block_count_map = new HashMap<>();
        for(String key: methodBlockDetails.keySet()) {
            method_block_average_map.put(key, average(methodBlockDetails.get(key)));
            method_block_count_map.put(key, methodBlockDetails.get(key).size());
        }
        method_block_average_map = (HashMap<String, Float>) sortByValue(method_block_average_map);
        method_block_count_map = (HashMap<String, Integer>) sortByValue(method_block_count_map);
        int toppestCount = Math.min(count, method_block_average_map.keySet().size());
        int index = 0;
        stringBuilder.append("\n----平均值排行 top " + toppestCount + "----\n");
        for(String key: method_block_average_map.keySet()){
            if(index++ < toppestCount) {
                stringBuilder.append(key + ": " + formatFloat(method_block_average_map.get(key)));
                stringBuilder.append("(次数：" +method_block_count_map.get(key) + ")");
                stringBuilder.append("\n");
            }else {
                break;
            }
        }
        toppestCount = Math.min(count, method_block_count_map.keySet().size());
        index = 0;
        stringBuilder.append("\n----卡顿次数排行 top " + toppestCount + "----\n");
        for(String key: method_block_count_map.keySet()){
            if(index++ < toppestCount) {
                stringBuilder.append(key + ": " + method_block_count_map.get(key));
                stringBuilder.append("(平均：" + formatFloat(method_block_average_map.get(key)) + ")");
                stringBuilder.append("\n");
            }else {
                break;
            }
        }
        return stringBuilder.toString();
    }

    private static Float formatFloat(Float val) {
        return (float)(Math.round(val*100))/100;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    private static float average(ArrayList<Integer> costTimeList) {
        if(costTimeList == null || costTimeList.size() == 0) {
            return 0 ;
        }
        int sum = 0;
        for(int val: costTimeList) {
            sum = sum + val;
        }
        return (float)sum / (float)costTimeList.size();
    }


}
