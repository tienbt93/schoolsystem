package com.edu.sondong.web.rest;

import com.edu.sondong.SchoolsystemApp;

import com.edu.sondong.domain.School;
import com.edu.sondong.repository.SchoolRepository;
import com.edu.sondong.service.dto.SchoolDTO;
import com.edu.sondong.service.mapper.SchoolMapper;
import com.edu.sondong.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.edu.sondong.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SchoolResource REST controller.
 *
 * @see SchoolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SchoolsystemApp.class)
public class SchoolResourceIntTest {

    private static final String DEFAULT_SCHOOL_NAM = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_NAM = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSchoolMockMvc;

    private School school;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchoolResource schoolResource = new SchoolResource(schoolRepository, schoolMapper);
        this.restSchoolMockMvc = MockMvcBuilders.standaloneSetup(schoolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createEntity(EntityManager em) {
        School school = new School()
            .schoolNam(DEFAULT_SCHOOL_NAM)
            .schoolAddress(DEFAULT_SCHOOL_ADDRESS)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .email(DEFAULT_EMAIL);
        return school;
    }

    @Before
    public void initTest() {
        school = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getSchoolNam()).isEqualTo(DEFAULT_SCHOOL_NAM);
        assertThat(testSchool.getSchoolAddress()).isEqualTo(DEFAULT_SCHOOL_ADDRESS);
        assertThat(testSchool.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSchool.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createSchoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School with an existing ID
        school.setId(1L);
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolNam").value(hasItem(DEFAULT_SCHOOL_NAM.toString())))
            .andExpect(jsonPath("$.[*].schoolAddress").value(hasItem(DEFAULT_SCHOOL_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.schoolNam").value(DEFAULT_SCHOOL_NAM.toString()))
            .andExpect(jsonPath("$.schoolAddress").value(DEFAULT_SCHOOL_ADDRESS.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        School updatedSchool = schoolRepository.findOne(school.getId());
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool
            .schoolNam(UPDATED_SCHOOL_NAM)
            .schoolAddress(UPDATED_SCHOOL_ADDRESS)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .email(UPDATED_EMAIL);
        SchoolDTO schoolDTO = schoolMapper.toDto(updatedSchool);

        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getSchoolNam()).isEqualTo(UPDATED_SCHOOL_NAM);
        assertThat(testSchool.getSchoolAddress()).isEqualTo(UPDATED_SCHOOL_ADDRESS);
        assertThat(testSchool.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSchool.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Create the School
        SchoolDTO schoolDTO = schoolMapper.toDto(school);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolDTO)))
            .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);
        int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Get the school
        restSchoolMockMvc.perform(delete("/api/schools/{id}", school.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(School.class);
        School school1 = new School();
        school1.setId(1L);
        School school2 = new School();
        school2.setId(school1.getId());
        assertThat(school1).isEqualTo(school2);
        school2.setId(2L);
        assertThat(school1).isNotEqualTo(school2);
        school1.setId(null);
        assertThat(school1).isNotEqualTo(school2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolDTO.class);
        SchoolDTO schoolDTO1 = new SchoolDTO();
        schoolDTO1.setId(1L);
        SchoolDTO schoolDTO2 = new SchoolDTO();
        assertThat(schoolDTO1).isNotEqualTo(schoolDTO2);
        schoolDTO2.setId(schoolDTO1.getId());
        assertThat(schoolDTO1).isEqualTo(schoolDTO2);
        schoolDTO2.setId(2L);
        assertThat(schoolDTO1).isNotEqualTo(schoolDTO2);
        schoolDTO1.setId(null);
        assertThat(schoolDTO1).isNotEqualTo(schoolDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(schoolMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(schoolMapper.fromId(null)).isNull();
    }
}
