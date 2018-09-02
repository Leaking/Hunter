package com.quinn.hunter.plugin

import com.android.SdkConstants
import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.TransformOutputProvider
import com.google.common.io.Files
import com.quinn.hunter.plugin.log.Logging
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

import java.nio.file.attribute.FileTime
import java.util.zip.CRC32
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream

/**
 * Created by Quinn on 09/07/2017.
 */

public class BytecodeWeaver {

    private final Logging logger = Logging.getLogger("BytecodeWeaver");
    private static final FileTime ZERO = FileTime.fromMillis(0);
    private URLClassLoader urlClassLoader;

    public static final String PLUGIN_LIBRARY = "com/hunter/library";

    public BytecodeWeaver(URLClassLoader urlClassLoader) {
        this.urlClassLoader = urlClassLoader
    }

    public void weaveDirectory(DirectoryInput directoryInput){
        String srcPath = directoryInput.file.absolutePath;
        File dir = new File(srcPath)
        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath
                if (isWeavableClass(filePath)) {
                    weaveSingleClassToFile(file)
                }
            }
        }
    }

    public void weaveJar(File inputJar, File outputJar){
        ZipFile inputZip = new ZipFile(inputJar);
        ZipOutputStream outputZip = new ZipOutputStream(new BufferedOutputStream(
                                java.nio.file.Files.newOutputStream(outputJar.toPath())))
        Enumeration<? extends ZipEntry> inEntries = inputZip.entries();
        while (inEntries.hasMoreElements()) {
            ZipEntry entry = inEntries.nextElement();

            InputStream originalFile =
                    new BufferedInputStream(inputZip.getInputStream(entry));
            ZipEntry outEntry = new ZipEntry(entry.getName());
            byte[] newEntryContent;
            if (!entry.getName().endsWith(SdkConstants.DOT_CLASS) || entry.getName().startsWith(PLUGIN_LIBRARY)) {
                newEntryContent = originalFile.bytes
            } else {
                newEntryContent = weaveSingleClassToByteArray(originalFile);
            }
            CRC32 crc32 = new CRC32();
            crc32.update(newEntryContent);
            outEntry.setCrc(crc32.getValue());
            outEntry.setMethod(ZipEntry.STORED);
            outEntry.setSize(newEntryContent.length);
            outEntry.setCompressedSize(newEntryContent.length);
            outEntry.setLastAccessTime(ZERO);
            outEntry.setLastModifiedTime(ZERO);
            outEntry.setCreationTime(ZERO);

            outputZip.putNextEntry(outEntry);
            outputZip.write(newEntryContent);
            outputZip.closeEntry();
        }
        outputZip.flush();
        outputZip.close();

    }

    private void getDestPath() {
        File dest = outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes, directoryInput.scopes,
                Format.DIRECTORY)
    }

    private void weaveSingleClassToFile(File file){
        try {
            byte[] bytes = weaveSingleClassToByteArray(new FileInputStream(file));
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] weaveSingleClassToByteArray(InputStream inputStream) {
        ClassReader classReader = new ClassReader(inputStream);
        ClassWriter classWriter = new TimingClassWriter(urlClassLoader, ClassWriter.COMPUTE_FRAMES);
        ClassAdapter classAdapter = new ClassAdapter(classWriter);
        classReader.accept(classAdapter, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }


    private boolean isWeavableClass(String filePath){
        return filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('R.class') && !filePath.contains("BuildConfig.class");
    }


}
