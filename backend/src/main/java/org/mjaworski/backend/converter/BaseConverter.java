package org.mjaworski.backend.converter;

import org.dozer.DozerBeanMapper;

import java.util.Arrays;

public abstract class BaseConverter {
    protected static DozerBeanMapper mapper;

    private static void configureMapper(String... mappingFileUrls) {
        mapper.setMappingFiles(Arrays.asList(mappingFileUrls));
    }

    static {
        mapper = new DozerBeanMapper();
        configureMapper("org/mjaworski/backend/dozer-mapping.xml");
    }
}