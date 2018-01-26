package com.edu.sondong.service.mapper;

import com.edu.sondong.domain.*;
import com.edu.sondong.service.dto.TeacherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Teacher and its DTO TeacherDTO.
 */
@Mapper(componentModel = "spring", uses = {SchoolMapper.class})
public interface TeacherMapper extends EntityMapper<TeacherDTO, Teacher> {

    @Mapping(source = "school.id", target = "schoolId")
    TeacherDTO toDto(Teacher teacher);

    @Mapping(source = "schoolId", target = "school")
    @Mapping(target = "lessons", ignore = true)
    Teacher toEntity(TeacherDTO teacherDTO);

    default Teacher fromId(Long id) {
        if (id == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(id);
        return teacher;
    }
}
