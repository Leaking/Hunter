package com.quinn.hunter.transform.asm;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by quinn on 06/09/2018
 */
public interface IWeaver {

    /**
     * Check a certain file is weavable
     * @param filePath class路径
     * @return 是否需要修改字节码
     */
    public boolean isWeavableClass(String filePath) throws IOException;

    /**
     * Weave single class to byte array
     * @param inputStream
     */
    public byte[] weaveSingleClassToByteArray(InputStream inputStream) throws IOException;


}

