package com.github.phuonghuynh.compressor;

import org.apache.commons.lang.reflect.FieldUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.junit.Before;
import org.junit.Test;

public class CompressorTest {

   private Compressor compressor = new Compressor();

   @Before
   public void before() throws IllegalAccessException, NoSuchFieldException, SecurityException {
      compressor = new Compressor();
      FieldUtils.writeField(FieldUtils.getField(Compressor.class, "workingDirectory", true), compressor,
            "src/test/resources", true);
      FieldUtils.writeField(FieldUtils.getField(Compressor.class, "outputDirectory", true), compressor,
            "src/test/generate-resources", true);
      FieldUtils.writeField(FieldUtils.getField(Compressor.class, "includeSubDirectories", true), compressor,
            Boolean.TRUE, true);
      FieldUtils.writeField(FieldUtils.getField(Compressor.class, "outputExt", true), compressor,
            "gz", true);
   }

   @Test
   public void testCompress() throws MojoExecutionException, MojoFailureException {
      compressor.execute();
   }
}
