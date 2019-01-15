package com.hunter.library.timing.impl;

import android.util.Log;

import com.hunter.library.timing.IBlockHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by quinn on 12/09/2018
 * Rank all block methods
 */
public class RankingBlockHandler implements IBlockHandler {

    private final String TAG = "RankingBlockHandler";
    private String newline = System.getProperty("line.separator");
    private String doubleNewline = newline + newline;
    private ConcurrentHashMap<String, ArrayList<Integer>> methodBlockDetails = new ConcurrentHashMap<>();
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final int threshold;

    public RankingBlockHandler(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void timingMethod(final String method, final int mills) {
        if(mills < threshold()) return;
        Log.i(TAG, method + " costs " + mills);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> costTimeList = methodBlockDetails.get(method);
                if(costTimeList == null) {
                    costTimeList = new ArrayList<>();
                }
                costTimeList.add(mills);
                methodBlockDetails.put(method, costTimeList);
            }
        });
    }

    @Override
    public String dump() {
        String rankingContent = getRankingDetail(20);
        String summary = "Total count of found block method is " + methodBlockDetails.size() + " (Over " + threshold() + "ms)";
        Log.i(TAG, summary);
        Log.i(TAG, rankingContent);
        return summary + rankingContent;
    }

    private String getRankingDetail(int count) {
        //sort the block detail
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
        StringBuilder result = new StringBuilder();
        result.append(doubleNewline).append("------Average Block-Time Ranking----").append("Top " + toppestCount).append("----");
        result.append(newline);
        for(String key: method_block_average_map.keySet()){
            if(index++ < toppestCount) {
                result.append(key + ": " + formatFloat(method_block_average_map.get(key)) + "ms");
                result.append("(Count : " +method_block_count_map.get(key) + ")");
                result.append(newline);
            }else {
                break;
            }
        }
        toppestCount = Math.min(count, method_block_count_map.keySet().size());
        index = 0;
        result.append("------Block Count Ranking----").append("Top " + toppestCount).append("----");
        result.append(newline);
        for(String key: method_block_count_map.keySet()){
            if(index++ < toppestCount) {
                result.append(key + ": " + method_block_count_map.get(key) + "");
                result.append("(Avg : " + formatFloat(method_block_average_map.get(key)) + "ms)");
                result.append(newline);
            }else {
                break;
            }
        }
        result.append(newline);
        return result.toString();
    }

    private Float formatFloat(Float val) {
        return (float)(Math.round(val*100))/100;
    }

    /**
     * sort hashmap by value
     */
    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map ) {
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

    @Override
    public void clear() {
        methodBlockDetails.clear();
    }

    @Override
    public int threshold() {
        return this.threshold;
    }
}
