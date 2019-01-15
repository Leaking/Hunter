package com.hunter.library.timing.impl;

import android.util.Log;

import com.hunter.library.timing.IBlockHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by quinn on 12/09/2018
 * Reduce block trace
 */
public class StacktraceBlockHandler implements IBlockHandler {

    private final String TAG = "StacktraceBlockImpl";
    private String newline = System.getProperty("line.separator");
    private String doubleNewline = newline + newline;
    private List<StacktraceBlockHandler.BlockTrace> blockTraces = Collections.synchronizedList(new ArrayList<StacktraceBlockHandler.BlockTrace>());
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final int threshold;

    public StacktraceBlockHandler(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void timingMethod(final String method, final int mills) {
        if(mills < threshold()) return;
        Log.i(TAG, method + " costs " + mills);
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        final StackTraceElement currMethod = stackTraceElements[2];
        final StackTraceElement callMethod = stackTraceElements[3];
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                buildStacktrace(mills, currMethod.getClassName() + "." + currMethod.getMethodName(), callMethod.getClassName() + "." + callMethod.getMethodName());
            }
        });

    }

    private void buildStacktrace(int currMills, String currMethod, String callMethod){
        boolean flag = false;
        for(StacktraceBlockHandler.BlockTrace blockTrace : blockTraces) {
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
            StacktraceBlockHandler.BlockTrace blockTrace = new StacktraceBlockHandler.BlockTrace();
            blockTrace.methods.add(currMethod);
            blockTrace.mills.add(currMills);
            blockTrace.methods.add(callMethod);
            blockTrace.mills.add(-1);
            blockTraces.add(blockTrace);
        }
    }

    @Override
    public String dump() {
        String stackTraceContent = getBlockStackTrace();
        logHugeContent(TAG, stackTraceContent);
        return stackTraceContent;
    }

    private void logHugeContent(String tag, String msg) {
        int segmentSize = 3 * 1024;
        int length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.i(tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.i(tag, logContent);
            }
            Log.i(tag, msg);// 打印剩余日志
        }
    }

    protected String getBlockStackTrace() {
        List<StacktraceBlockHandler.BlockTrace> copyBlockTraces = new ArrayList<>(blockTraces);
        Collections.sort(copyBlockTraces, new Comparator<StacktraceBlockHandler.BlockTrace>() {
            @Override
            public int compare(StacktraceBlockHandler.BlockTrace o1, StacktraceBlockHandler.BlockTrace o2) {
                if(o1.traceCostedTime > o2.traceCostedTime) return -1;
                else if(o1.traceCostedTime < o2.traceCostedTime) return 1;
                else return 0;
            }
        });
        StringBuilder result = new StringBuilder();
        result.append(doubleNewline).append("----BlockStackTrace----Total ").append(copyBlockTraces.size()).append("----");

        for(StacktraceBlockHandler.BlockTrace blockTrace : copyBlockTraces) {
            result.append(newline).append("Block StackTrace ").append(copyBlockTraces.indexOf(blockTrace)).append(newline);
            result.append(blockTrace.toString()).append(newline);
        }
        return result.toString();
    }

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
    public void clear() {
        blockTraces.clear();
    }

    @Override
    public int threshold() {
        return threshold;
    }
}
