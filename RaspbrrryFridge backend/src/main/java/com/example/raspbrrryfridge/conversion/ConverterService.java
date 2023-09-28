package com.example.raspbrrryfridge.conversion;

public interface ConverterService<DTO, Entity> {
    Entity convertToEntity(DTO dto, Entity entity);
}
