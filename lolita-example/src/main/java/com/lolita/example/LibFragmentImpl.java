package com.lolita.example;

import android.view.View;

import com.lolita.annotations.TimingDebug;

/**
 * Created by quinn on 6/14/17.
 */

@TimingDebug
public class LibFragmentImpl extends LibFragment{

    public LibFragmentImpl() {
        super();
    }

    @Override
    protected View onCreateView() {
        return null;
    }

    @Override
    public void initDataSource() {

    }

    @Override
    public void onBindEvent(boolean b) {

    }

    @Override
    public int refreshData() {
        return 0;
    }

    @Override
    public void render(int i) {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public boolean isAnimationRunning() {
        return false;
    }
}
