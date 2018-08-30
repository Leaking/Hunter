package com.quinn.hunter.plugin

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
/**
 * Created by Quinn on 09/07/2017.
 */

public class BytecodeWeaver {

    private URLClassLoader urlClassLoader;

    public BytecodeWeaver(URLClassLoader urlClassLoader) {
        this.urlClassLoader = urlClassLoader
    }

    public void weaveByteCode(String classDir){
        File dir = new File(classDir)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                if (isWeavableClass(filePath)) {
                    weaveFile(filePath)
                }
            }
        }
    }

    private void weaveFile( String filePath){
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ClassReader classReader = new ClassReader(fileInputStream);
            ClassWriter classWriter = new TimingClassWriter(urlClassLoader, ClassWriter.COMPUTE_FRAMES);
            ClassAdapter classAdapter = new ClassAdapter(classWriter);
            classReader.accept(classAdapter, ClassReader.EXPAND_FRAMES);
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(classWriter.toByteArray());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isWeavableClass(String filePath){
        return filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('R.class') && !filePath.contains("BuildConfig.class");
    }


}
