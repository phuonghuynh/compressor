compressor
==========

Maven plugin used to compress resources, such as: JS, IMG, PNG, HTML...

#### Overview
Spring MVC can handling static resource by using `resourceChain` feature. Basically, the chain like bellow
```java
Request for Resource
      |
      | HTTP request
      v
Resolvers chain: FirstResolver, SecondResolver, ThirdResolver
(each resolver can return the resource or delegate to the next one)
      |
      | Resolved Resource
      v
Transformers chain: FirstTransformer, SecondTransformer
(each transformer can transform the resource or just pass it along without modification)
      |
      | Transformed Resource
      v
HTTP Response with Resource content
```

One of the best practise when doing speed up your website is compression the static resources (*.js, *.json, *.css, *.html...), see [Google Developer Speed Docs](https://developers.google.com/speed/docs/insights/EnableCompression).

This maven plugin is about do compression for your resources.

Given the resources...
```bash
└── modules
    ├── app.js
    ├── bubble
    │   ├── bubble.srv.js
    │   ├── bubble.tpl.html
    ├── collection
    │   ├── chart.ctrl.js
    │   ├── chart.tpl.html
```

By a simple configuration
```xml
<plugin>
   <groupId>com.github.phuonghuynh</groupId>
   <artifactId>compressor</artifactId>
   <version>1.0</version>
   <configuration>
      <workingDirectory>src/main/webapp/public</workingDirectory>
   </configuration>
   <executions>
      <execution>
         <phase>generate-resources</phase>
         <goals>
            <goal>compress</goal>
         </goals>
      </execution>
   </executions>
</plugin>
```

And the output is (`.gz` files)
```bash
└── modules
    ├── app.js
    ├── app.js.gz
    ├── bubble
    │   ├── bubble.srv.js
    │   ├── bubble.srv.js.gz
    │   ├── bubble.tpl.html
    │   └── bubble.tpl.html.gz
    ├── collection
    │   ├── chart.ctrl.js
    │   ├── chart.ctrl.js.gz
    │   ├── chart.tpl.html
    │   └── chart.tpl.html.gz
```

Using the Spring `GzipResourceResolver` help to finds variations of a resource with a ".gz" extension when HTTP clients support gzip compression and returns the zipped version of requested resource, otherwise the unzipped version is returned.

#### Configuration parameters:
- `workingDirectory` *(required)* : String - where the resources should be rea. *Ex: `src/main/webaap/assets`*
- `outputDirectory` *(optional)* : String - where the compressor should write to. Not specified means using `workingDirectory` for the `outputDirectory`.
- `filterExt` *(optional)*: CSV String - represent filter extensions. Not specified means all files. *Ex: `js,css,png`*
- `includeSubDirectories` *(optional)* : boolean - toogle the flag to looking in sub-directories or not, default is `true`
- `outputExt` *(optional)* : String - specify the extension of the output, default is `gz`

**Please fell free to contribute.**