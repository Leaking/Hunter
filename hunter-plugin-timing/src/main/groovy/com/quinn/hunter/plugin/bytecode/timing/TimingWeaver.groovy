package com.quinn.hunter.plugin.bytecode.timing

import com.android.SdkConstants
import com.quinn.hunter.plugin.bytecode.ExtendClassWriter
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

public class TimingWeaver {

    private final Logging logger = Logging.getLogger("TimingWeaver");
    private static final FileTime ZERO = FileTime.fromMillis(0);
    private URLClassLoader urlClassLoader;

    public static final String PLUGIN_LIBRARY = "com/hunter/library";

    public TimingWeaver(URLClassLoader urlClassLoader) {
        this.urlClassLoader = urlClassLoader
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

    public void weaveSingleClassToFile(File inputFile, File outputFile){
        try {
            InputStream inputStream = new FileInputStream(inputFile);
            byte[] bytes = weaveSingleClassToByteArray(inputStream);
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(bytes);
            fos.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] weaveSingleClassToByteArray(InputStream inputStream) {
        ClassReader classReader = new ClassReader(inputStream);
        ClassWriter classWriter = new ExtendClassWriter(urlClassLoader, ClassWriter.COMPUTE_MAXS);
        TimingClassAdapter classAdapter = new TimingClassAdapter(classWriter);
        classReader.accept(classAdapter, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    public boolean isWeavableClass(String filePath){
        return filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('R.class') && !filePath.contains("BuildConfig.class");
    }


}
