# Introduction

The grep application is a Java interpretation of the bash command grep. This grep app
takes in three arguments: `regex rootPath outFile`, then it looks at the contents of each file in the `rootPath` recursively and in each file, the grep application would go line by line and look for lines that match the `regex`, save the matched lines and write the saved lines to the `outFile`.

What was learned in this project  was how to operate the IntelliJ Idea IDE, as well as lambda functions in addition to
Java I/O.

# Usage
USAGE: regex rootPath outFile
`regex`: a special string used to compare patterns line by line for each file
`rootPath`: the root directory where the recursive search will begin matching files
`outFile`: the output file where the matching results would be saved to. The file will be created
assuming that this file does not exist prior to the app.

A simple example:

`#!\/bin\/bash` `Users/UserName/Documents` `outFile`

This command would look for any files that contain the line `#!/bin/bash` in any line of the files in Documents or its subdirectories, and then it would print the found results to outFile.

# Pseudocode

The Pseudocode for process would be
```
res = []
for file in ListFiles(rootDir)
   for line in file
       if (line contains regex):
           res.add(line);
return res;

```


# Performance Issue
The problem with the grep application is the fact that the main process stores all of the files
then opens one file and then opens one line. So for a `rootPath` that has too many files that takes
too much space it would cause the application to crash. The naive solution is to increase the
amount of ram that the system contains. However, if the program could somehow read and then release
the file that was scanned without waiting for the entire directory to be finished it could allow
the ram to hogged by unused items.

# Improvement
1. Make outFile optional, if not provided, print to standard output
2. Give an option to skip over certain file extensions (ie `.txt` or `.sh` files)
3. Improve the handling if too many files were read; if too many objects are read
proceed to just print whatever files are read instead of crashing
