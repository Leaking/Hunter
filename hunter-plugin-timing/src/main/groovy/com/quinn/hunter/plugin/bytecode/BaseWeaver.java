package com.quinn.hunter.plugin.bytecode;

import java.io.File;

/**
 * Created by quinn on 06/09/2018
 */
public interface BaseWeaver {

    /**
     * Weave all classes of inputJar, and save the result to the outputJar
     */
    public void weaveJar(File inputJar, File outputJar);

    /**
     * Weave single class, and save the result to outputFile
     */
    public void weaveSingleClassToFile(File inputFile, File outputFile);

    /**
     * Check a certain file is weavable
     */
    public boolean isWeavableClass(String filePath);

}

