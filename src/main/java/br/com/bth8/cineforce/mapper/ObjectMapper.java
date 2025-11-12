package br.com.bth8.cineforce.mapper;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ObjectMapper {

    private final ModelMapper mapper;

    public ObjectMapper() {
        this.mapper = new ModelMapper();
    }

    public <O,D> D parseObject(O origin, Class<D> destination) {
        return this.mapper.map(origin, destination);
    }

    public <O,D> List<D> parseListObjects(List<O> origin, Class<D> destintion) {

        List<D> destinationList = new ArrayList<>();

        for (Object o: origin) {
            destinationList.add(parseObject(o, destintion));
        }

        return destinationList;
    }
}
