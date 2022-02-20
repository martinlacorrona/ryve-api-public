package com.martinlacorrona.ryve.api.utils;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapperExtended {
    public static <S, T> List<T> mapList(List<S> source, Class<T> targetClass, ModelMapper modelMapper) {
        return source
                .stream()
                .parallel()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
