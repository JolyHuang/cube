package com.sharingif.cube.api;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * 扫描api服务
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/2 下午2:15
 */
public class ApiClassPathScan {

    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private String basePackage;

    public ApiClassPathScan(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<Class> findCandidateComponents() {
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + basePackage.replace(".", "/") + '/' + DEFAULT_RESOURCE_PATTERN;

        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        try {
            resources = pathMatchingResourcePatternResolver.getResources(packageSearchPath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read basePackage: "+ packageSearchPath, e);
        }
        if(resources == null) {
            return null;
        }
        List<Class> classList = new ArrayList<Class>(resources.length);
        CachingMetadataReaderFactory cachingMetadataReaderFactory = new CachingMetadataReaderFactory();
        for (Resource resource : resources) {
            try {
                MetadataReader metadataReader = cachingMetadataReaderFactory.getMetadataReader(resource);
                classList.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
            } catch (Exception e) {
                throw new RuntimeException("Failed to read candidate component class: "+ resource, e);
            }
        }

        return filter(classList);
    }

    protected List<Class> filter(List<Class> classList) {
        List<Class> newClassList = new ArrayList<Class>(classList.size());
        for(Class cla : classList) {
            Annotation requestMapping = cla.getAnnotation(RequestMapping.class);
            if(requestMapping != null) {
                newClassList.add(cla);
            }
        }

        return newClassList;
    }

}
