package com.quinn.hunter.plugin

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.TransformOutputProvider
import com.google.common.io.Files
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

    public void weaveByteCode(DirectoryInput directoryInput){
        String srcPath = directoryInput.file.absolutePath;
        File dir = new File(srcPath)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                if (isWeavableClass(filePath)) {
                    weaveFile(filePath)
                }
            }
        }
    }

    private void getDestPath() {
        File dest = outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes, directoryInput.scopes,
                Format.DIRECTORY)
    }
    private void weaveFile(String inputFilePath){
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFilePath);
            ClassReader classReader = new ClassReader(fileInputStream);
            ClassWriter classWriter = new TimingClassWriter(urlClassLoader, ClassWriter.COMPUTE_FRAMES);
            ClassAdapter classAdapter = new ClassAdapter(classWriter);
            classReader.accept(classAdapter, ClassReader.EXPAND_FRAMES);
            FileOutputStream fos = new FileOutputStream(inputFilePath);
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
