package com.lolita.plugin.asm

import com.lolita.annotations.ParameterDebug
import com.lolita.annotations.TimingDebug
import javassist.CtClass
import javassist.CtConstructor
import javassist.CtMethod
import javassist.Modifier;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by Quinn on 09/07/2017.
 */

public class ASMUtils {

    public static void weaveByteCode(String classDir){

        File dir = new File(classDir)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                println "filePath = " + filePath
                if (isWeavableClass(filePath)) {
                    try {
                        FileInputStream is = null;
                        is = new FileInputStream(filePath);
                        ClassReader cr = new ClassReader(is);
                        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                        ClassAdapter classAdapter = new ClassAdapter(cw);
                        cr.accept(classAdapter, ClassReader.EXPAND_FRAMES);
                        FileOutputStream fos = new FileOutputStream(filePath);
                        fos.write(cw.toByteArray());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    public static boolean isWeavableClass(String filePath){
        return filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('R.class') && !filePath.contains("BuildConfig.class");
    }


}
