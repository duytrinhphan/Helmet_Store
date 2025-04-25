package com.example.demo.infrastructure.adapter;

public interface DtoToEntity<ENTITY> {
    ENTITY dtoToEntity(ENTITY e);
}
