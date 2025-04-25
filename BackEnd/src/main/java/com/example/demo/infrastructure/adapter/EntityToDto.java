package com.example.demo.infrastructure.adapter;

public interface EntityToDto<Entity, DTO> {
    public DTO changeToDto(Entity entity);
}