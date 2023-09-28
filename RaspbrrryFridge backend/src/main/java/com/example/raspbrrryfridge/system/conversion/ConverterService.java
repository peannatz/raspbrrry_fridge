package com.example.raspbrrryfridge.system.conversion;

public interface ConverterService<DTO, Entity> {
    Entity convertToEntity(DTO dto, Entity entity);
}
