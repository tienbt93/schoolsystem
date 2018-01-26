package com.edu.sondong.service.mapper;

import com.edu.sondong.domain.*;
import com.edu.sondong.service.dto.RoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Room and its DTO RoomDTO.
 */
@Mapper(componentModel = "spring", uses = {SchoolMapper.class})
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {

    @Mapping(source = "school.id", target = "schoolId")
    RoomDTO toDto(Room room);

    @Mapping(source = "schoolId", target = "school")
    @Mapping(target = "lessons", ignore = true)
    Room toEntity(RoomDTO roomDTO);

    default Room fromId(Long id) {
        if (id == null) {
            return null;
        }
        Room room = new Room();
        room.setId(id);
        return room;
    }
}
