package com.hunter.library.timing.impl;

import android.os.Environment;
import android.util.Log;

import com.hunter.library.timing.IBlockManger;

import java.io.File;
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
 * Created by quinn on 27/06/2017.
 */

public class BlockManagerImpl implements IBlockManger {

    private final String TAG = "BlockManagerImpl";

    private String newline = System.getProperty("line.separator");
    private String twonewline = newline + newline;

    public static final int THRESHOLD = 100;

    /**
     * Store costed time of methods, one method may be invoked more than one time
     * 1(method) TO N(cost-times)
     */
    private ConcurrentHashMap<String, ArrayList<Integer>> methodBlockDetails = new ConcurrentHashMap<>();

    private List<BlockTrace> blockTraces = Collections.synchronizedList(new ArrayList<BlockTrace>());

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private File blockLog = new File(Environment.getExternalStorageDirectory() + File.separator + "blocktrace.log");

    private class BlockTrace {
        //Inner method is ahead of Outter method
        ArrayList<String> methods = new ArrayList<>();
        ArrayList<Integer> mills = new ArrayList<>();
        int traceCostedTime = -1;

        BlockTrace(){
        }

        @Override
        public String toString() {
            int size = methods.size();
            if(size <= 0) return "BlockTrace length is " + size;
            StringBuilder stringBuilder = new StringBuilder("");
            for(int i = 0; i < size; i++) {
                if(i != size -1) {
                    stringBuilder.append(methods.get(i)).append(" costed ").append(mills.get(i)).append("ms").append("\n");
                } else {
                    stringBuilder.append(methods.get(i)).append(" is root");
                }
            }
            return stringBuilder.toString();
        }
    }

    @Override
    public void timingMethod(final String method, final int mills) {
        if(mills < THRESHOLD) return;
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        final StackTraceElement currMethod = stackTraceElements[1];
        final StackTraceElement callMethod = stackTraceElements[2];

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> costTimeList = methodBlockDetails.get(method);
                if(costTimeList == null) {
                    costTimeList = new ArrayList<>();
                }
                costTimeList.add(mills);
                methodBlockDetails.put(method, costTimeList);
                appendTraceElement(mills, currMethod.getClassName() + "." + currMethod.getMethodName(), callMethod.getClassName() + "." + callMethod.getMethodName());
            }
        });

    }

    private void appendTraceElement(int currMills, String currMethod, String callMethod){
        boolean flag = false;
        for(BlockTrace blockTrace : blockTraces) {
            String method = blockTrace.methods.get(blockTrace.methods.size() - 1);
            int mills = blockTrace.mills.get(blockTrace.mills.size() - 1);
            // call method
            if(currMethod.equals(method) && mills == -1) {
                blockTrace.mills.set(blockTrace.mills.size() - 1, currMills);
                if(blockTrace.traceCostedTime < currMills) blockTrace.traceCostedTime = currMills;
                blockTrace.methods.add(callMethod);
                blockTrace.mills.add(-1);
                flag = true;
            }
        }
        if(!flag) {
            BlockTrace blockTrace = new BlockTrace();
            blockTrace.methods.add(currMethod);
            blockTrace.mills.add(currMills);
            blockTrace.methods.add(callMethod);
            blockTrace.mills.add(-1);
            blockTraces.add(blockTrace);
        }
    }

    private String dumpBlockStackTrace() {
        List<BlockTrace> copyBlockTraces = new ArrayList<>(blockTraces);
        Collections.sort(copyBlockTraces, new Comparator<BlockTrace>() {
            @Override
            public int compare(BlockTrace o1, BlockTrace o2) {
                if(o1.traceCostedTime > o2.traceCostedTime) return -1;
                else if(o1.traceCostedTime < o2.traceCostedTime) return 1;
                else return 0;
            }
        });
        StringBuilder result = new StringBuilder();
        result.append(twonewline).append("----dumpBlockStackTrace----").append(copyBlockTraces.size());

        for(BlockTrace blockTrace : copyBlockTraces) {
            result.append(newline).append("Block StackTrace ").append(copyBlockTraces.indexOf(blockTrace)).append(newline);
            result.append(blockTrace.toString()).append(newline);
        }
        return result.toString();
    }

    /**
     * 应用层可以自己拿数据做自定义的分析
     */
    public ConcurrentHashMap<String, ArrayList<Integer>> getMethodBlockDetails() {
        return methodBlockDetails;
    }

    @Override
    public void dump() {
        Log.i(TAG, "dump " + blockLog.length());
        String topContent = dump(20);
        String stackTraceContent = dumpBlockStackTrace();
        Log.i(TAG, topContent);
        Log.i(TAG, stackTraceContent);

//        try {
//            if(blockLog.exists()) FileUtils.forceDelete(blockLog);
//            if(!blockLog.exists()) blockLog.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            FileUtils.writeStringToFile(blockLog, topContent, true);
//            FileUtils.writeStringToFile(blockLog, stackTraceContent, true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 一种默认的分析方式，dump出平均值排行，以及重现次数排行
     */
    private String dump(int count) {
        StringBuilder result = new StringBuilder();
        result.append(newline);
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
        result.append(twonewline).append("----平均值排行 Top ").append(toppestCount).append("----");
        result.append(newline);
        for(String key: method_block_average_map.keySet()){
            if(index++ < toppestCount) {
                result.append(key + ": " + formatFloat(method_block_average_map.get(key)) + "ms");
                result.append("(次数: " +method_block_count_map.get(key) + ")");
                result.append(newline);
            }else {
                break;
            }
        }
        toppestCount = Math.min(count, method_block_count_map.keySet().size());
        index = 0;
        result.append(newline).append("----卡顿次数排行 top ").append(toppestCount).append("----");
        result.append(newline);
        for(String key: method_block_count_map.keySet()){
            if(index++ < toppestCount) {
                result.append(key + ": " + method_block_count_map.get(key) + "次");
                result.append("(平均耗时: " + formatFloat(method_block_average_map.get(key)) + "ms)");
                result.append(newline);
            }else {
                break;
            }
        }
        return result.toString();
    }

    /**
     * 小数点保留两位
     */
    private Float formatFloat(Float val) {
        return (float)(Math.round(val*100))/100;
    }

    /**
     * 按照 value值对 hashmap进行排序
     */
    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
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

    /**
     * 求平均值
     */
    private float average(ArrayList<Integer> costTimeList) {
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
