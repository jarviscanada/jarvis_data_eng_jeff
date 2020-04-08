package ca.jrvs.apps.grep;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JavaGrepImp implements JavaGrep {
  final Logger logger = LoggerFactory.getLogger(JavaGrep.class);
  private String regex;
  private String rootPath;
  private String outFile;

  public static void main(String[] args) {
    if (args.length != 3 ) {
      throw new IllegalArgumentException("USAGE: Java regex rootPath outFile");
    }
    JavaGrepImp JavaGrepImp = new JavaGrepImp();
    JavaGrepImp.setRegex(args[0]);
    JavaGrepImp.setRootPath(args[1]);
    JavaGrepImp.setOutFile(args[2]);

    try {
      JavaGrepImp.process();
    }catch (Exception ex){
      JavaGrepImp.logger.error(ex.getMessage());
    }
  }
  /**
   * Top level search workflow
   *
   * @throws IOException
   */
  @Override
  public void process() throws IOException {
    // initialize a List of string to store all the matched lines
    List<String> matchedLines = new ArrayList<>();
    try {
      //initialize and store all the files
      for (File file : listFiles(getRootPath())) {
        // for each file go through parse the file and read each line
        for (String line : readLines(file)) {
          // for each line if that line matches the pattern store it in matchedLines
          if (containsPattern(line)) {
            matchedLines.add(line);
          }
        }
      }
    } catch(NullPointerException e){
      e.printStackTrace();
    }
    writeToFile(matchedLines);
  }

  /**
   * Traverse a given directory and return all files
   *
   * @param rootDir input directory
   * @return files under rootDir
   */
  @Override
  public List<File> listFiles(String rootDir) {
    //initialize return list
    List<File> res = new ArrayList<>();
    //Open rootDir and get the contents of rootDir
    File directory = new File(rootDir);
    File[] directoryContents = directory.listFiles();
    //if Directory is empty return null
    if (directoryContents == null) {
      return null;
    }
    // recursively go through the directory and get all the files
    for (File file : directoryContents) {
      if (file.isDirectory()) {
        List<File> innerDirectory = listFiles(file.getName());
        if (innerDirectory != null) {
          res.addAll(innerDirectory);
        }
      } else {
        res.add(file);
      }
    }
    return res;
  }

  /**
   * Read a file and return all the lines
   *
   * @param inputFile file to be read
   * @return lines
   * @throws IllegalArgumentException (I'm not sure what throws the error)
   */
  @Override
  public List<String> readLines(File inputFile) {
    List<String> res = new ArrayList<>();
    // use the scanner method to read each line
    Scanner scanner = null;
    try {
      scanner = new Scanner(inputFile);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    //check if the file is null
    if (scanner != null) {
      scanner.useDelimiter("\\n");
      while (scanner.hasNext()) {
        res.add(scanner.next());
      }
    }
    return res;
  }

  /**
   * check if a line contains the regex pattern (passed by user)
   *
   * @param line the input string
   * @return true if the string matches
   */
  @Override
  public boolean containsPattern(String line) {
    return line.matches(getRegex());
  }

  /**
   * Write result lines to a file
   *
   * @param lines lines to write to the file
   * @throws IOException if the write failed
   */
  @Override
  public void writeToFile(List<String> lines) throws IOException {
    FileWriter FileWrite = new FileWriter(getOutFile());
    for (String line : lines) {
      FileWrite.write(line);
    }
    FileWrite.close();
  }

  @Override
  public String getRegex() {
    return regex;
  }

  @Override
  public String getRootPath() {
    return rootPath;
  }

  @Override
  public String getOutFile() {
    return outFile;
  }

  @Override
  public void setRegex(String regex) {
    this.regex = regex;
  }

  @Override
  public void setRootPath(String rootPath) {
    this.rootPath = rootPath;
  }

  @Override
  public void setOutFile(String outFile) {
    this.outFile = outFile;
  }
}
