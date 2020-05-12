package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class JavaGrepLambdaImp extends JavaGrepImp {
  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

  public static void main(String[] args) {
    if (args.length != 3 ) {
      throw new IllegalArgumentException("USAGE: Java regex rootPath outFile");
    }

    JavaGrepLambdaImp JavaGrepLambdaImp = new JavaGrepLambdaImp();
    JavaGrepLambdaImp.setRegex(args[0]);
    JavaGrepLambdaImp.setRootPath(args[1]);
    JavaGrepLambdaImp.setOutFile(args[2]);

    try {
      JavaGrepLambdaImp.process();
    }catch (Exception ex){
      JavaGrepLambdaImp.logger.error(ex.getMessage());
    }
  }

  /**
   * Traverse a given directory and return all files
   *
   * @param rootDir input directory
   * @return files under rootDir
   */
  @Override
  public List<File> listFiles(String rootDir) {
    List<File> res = null;
    try {
      res = Files.find(Paths.get(rootDir),
          Integer.MAX_VALUE,
          (filePath,fileAttr)-> fileAttr.isRegularFile())
          .map(Path::toFile)
          .collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return res;
  }

  /**
   * Read a file and parse the file into individual individual lines
   *
   * @param inputFile file to be read
   * @return lines return each line individually
   * @throws IllegalArgumentException
   */
  @Override
  public List<String> readLines(File inputFile) throws IllegalArgumentException {
    List<String> res = null;
    try {
      res =  Files.lines(Paths.get(String.valueOf(inputFile))).collect(Collectors.toList());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return res;
  }
}
