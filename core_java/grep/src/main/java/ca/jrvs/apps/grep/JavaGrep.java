package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface JavaGrep {
/**
 *  Top level search workflow
 * @throws IOException
 */
void process() throws IOException;

/**
 * Traverse a given directory and return all files
 * @param rootDir input directory
 * @return files under rootDir
 */
List<File> listFiles(String rootDir);

/**
 * Read a file and return all the lines
 *
 *
 * @param inputFile file to be read
 * @return lines
 * @throws IllegalArgumentException
 */
List<String> readLines(File inputFile);

/**
 * check if a line contains the regex pattern (passed by user)
 * @param line the input string
 * @return true if the string matches
 */
boolean containsPattern(String line);

/**
 * Write result lines to a file
 * @param lines lines to write to the file
 * @throws IOException if the write failed
 */
void writeToFile(List<String> lines) throws IOException;

String getRootPath();

void setRootPath (String rootPath);

String getRegex();

void setRegex(String regex);

String getOutFile();

void setOutFile(String outFile);
}