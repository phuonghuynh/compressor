package com.github.phuonghuynh.compressor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

/**
 * Compress files
 * 
 * @author phuonghqh
 * */
@Mojo(name = "compress")
public class Compressor extends AbstractMojo {

   @Parameter(property = "workingDirectory", required = true)
   private String workingDirectory;

   /**
    * Not specified means #workingDirectory
    * */
   @Parameter(property = "outputDirectory", required = false)
   private String outputDirectory;

   /**
    * A csv string represent filter extensions. Not specified means all files
    * */
   @Parameter(property = "filterExt")
   private String filterExt;

   @Parameter(property = "includeSubDirectories", defaultValue = "true")
   private boolean includeSubDirectories;

   /**
    * A string represent output file extension
    * */
   @Parameter(property = "outputExt", defaultValue = "gz")
   private String outputExt;

   /**
    * Main processing
    * 
    * @throws {@link MojoExecutionException}
    * @throws {@link MojoFailureException}
    * @return
    * */
   public void execute() throws MojoExecutionException, MojoFailureException {
      try {
         File workingDir = new File(workingDirectory);
         workingDirectory = workingDir.getCanonicalPath();
         if (StringUtils.isEmpty(outputDirectory)) {
            outputDirectory = workingDirectory;
            getLog().info(
                  String.format("[outputDirectory] not specified => then set it to [workingDirecotry=%s]",
                        workingDirectory));
         }

         Collection<File> files = FileUtils.listFiles(workingDir, filterExt != null ? filterExt.replaceAll(" ", "")
               .split(",") : null, includeSubDirectories);
         for (File file : files) {
            String fullpath = file.getCanonicalPath();
            getLog().info(String.format("Processing file %s", fullpath));
			if(System.getProperty("os.name").contains("Windows")) {
				getLog().info("OS is Windows-based, altering path strings to use Java-compatible path separators");
				workingDirectory = workingDirectory.replace("\\", "/");
				fullpath = fullpath.replace("\\", "/");
			}
			
            File zFile = new File(outputDirectory + "/" + fullpath.replaceFirst(workingDirectory, "")
                  + "." + outputExt);
            zFile.getParentFile().mkdirs();
            GzipCompressorOutputStream gzip = new GzipCompressorOutputStream(new FileOutputStream(zFile));
            gzip.write(FileUtils.readFileToByteArray(file));
            getLog().info(String.format("Compressing to %s", zFile.getAbsolutePath()));
            gzip.close();
         }
      }
      catch (IOException e) {
         throw new MojoExecutionException(String.format(
               "Error processing, params : \n [workingDirectory = %s] \n  [outputDirectory = %s] ", workingDirectory,
               outputDirectory), e);
      }
   }
}
