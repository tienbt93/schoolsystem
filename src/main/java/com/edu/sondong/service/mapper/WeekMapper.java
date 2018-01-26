package com.edu.sondong.service.mapper;

import com.edu.sondong.domain.*;
import com.edu.sondong.service.dto.WeekDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Week and its DTO WeekDTO.
 */
@Mapper(componentModel = "spring", uses = {SemesterMapper.class})
public interface WeekMapper extends EntityMapper<WeekDTO, Week> {

    @Mapping(source = "semester.id", target = "semesterId")
    WeekDTO toDto(Week week);

    @Mapping(source = "semesterId", target = "semester")
    @Mapping(target = "lessons", ignore = true)
    Week toEntity(WeekDTO weekDTO);

    default Week fromId(Long id) {
        if (id == null) {
            return null;
        }
        Week week = new Week();
        week.setId(id);
        return week;
    }
}
