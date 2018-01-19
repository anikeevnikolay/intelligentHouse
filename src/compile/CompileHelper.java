/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compile;

import com.sun.org.apache.xalan.internal.xsltc.compiler.CompilerException;

import javax.tools.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author nian0715
 */
public class CompileHelper {

    public static void compileClass(String name, String code, String filePath) throws CompilerException, FileNotFoundException, IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        JavaFileObject file = new JavaSourceFromString(name, code);

        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        ArrayList<String> options = new ArrayList<>();
        options.add("-d");
        options.add(filePath);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, options, null, compilationUnits);

        boolean success = task.call();
        if (!diagnostics.getDiagnostics().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                sb.append(diagnostic.getCode());
                sb.append(diagnostic.getKind());
                sb.append(diagnostic.getPosition());
                sb.append(diagnostic.getStartPosition());
                sb.append(diagnostic.getEndPosition());
                sb.append(diagnostic.getSource());
                sb.append(diagnostic.getMessage(null));
            }
            throw new CompilerException(sb.toString());
        }
    }
}
