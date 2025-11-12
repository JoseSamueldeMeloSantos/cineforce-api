package br.com.bth8.cineforce.serialization.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class YamlJacksonHttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    protected YamlJacksonHttpMessageConverter() {
        super(
                new YAMLMapper()
                        .setSerializationInclusion(JsonInclude.Include.NON_NULL),
                MediaType.parseMediaType("application/yaml")
        );
    }
}
