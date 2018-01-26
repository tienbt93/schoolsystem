package com.edu.sondong.service.mapper;

import com.edu.sondong.domain.*;
import com.edu.sondong.service.dto.ClassSchoolDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ClassSchool and its DTO ClassSchoolDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClassSchoolMapper extends EntityMapper<ClassSchoolDTO, ClassSchool> {


    @Mapping(target = "lessons", ignore = true)
    ClassSchool toEntity(ClassSchoolDTO classSchoolDTO);

    default ClassSchool fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassSchool classSchool = new ClassSchool();
        classSchool.setId(id);
        return classSchool;
    }
}
