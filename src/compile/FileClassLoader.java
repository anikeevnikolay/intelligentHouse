/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author nian0715
 */
public class FileClassLoader extends URLClassLoader {
    public FileClassLoader() {
        this(new URL[]{});
    }

    public FileClassLoader(URL[] urls) {
        this(urls, null);
    }

    public FileClassLoader(ClassLoader parent) {
        this(new URL[]{}, parent);
    }

    public FileClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    public Class<?> createClass(File file) throws IOException, ClassNotFoundException {
        if (file == null)
            throw new ClassNotFoundException("null");
        if (!file.exists())
            throw new ClassNotFoundException(file.toString());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            int read = fis.read(bytes);
            if (read != bytes.length) {
                return null;
            }
            return createClass(bytes);
        } finally {
            fis.close();
        }
    }

    public Class<?> createClass(byte[] bytes) {
        Class<?> clazz = defineClass(null, bytes, 0, bytes.length);
        resolveClass(clazz);
        return clazz;
    }
}
