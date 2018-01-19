/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compile;

import helpers.PropertiesHelper;
import helpers.PropertyConsts;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author nian0715
 */
public class ClassLoadHelper {

    public static Class loadClassByName(String name) throws ClassNotFoundException, IOException {
        Class cl = null;
        Properties pr = PropertiesHelper.getProperty(PropertyConsts.COMPILE_PROPERTIES);
        try {
            cl = loadClassReq(new File(pr.getProperty("class_path")), name);
            if (cl != null)
                return cl;
        } catch (Exception | Error e){}
        cl = Class.forName(name);
        return cl;
    }

    private static Class loadClassReq(File f, String name) throws ClassNotFoundException {
        try {
            if (f.getName().toLowerCase().endsWith(".class")) {
                FileClassLoader loader = new FileClassLoader();
                Class cl = loader.createClass(f);
                if (name.equals(cl.getName())) {
                    return cl;
                }
            }
        } catch (Exception | Error e) {
        }
        if (f.listFiles() != null) {
            for (File f1 : f.listFiles()) {
                try {
                    Class cl = loadClassReq(f1, name);
                    if (cl != null) {
                        return cl;
                    }
                } catch (Exception | Error e) {
                }
            }
        }
        throw new ClassNotFoundException(name);
    }
}
