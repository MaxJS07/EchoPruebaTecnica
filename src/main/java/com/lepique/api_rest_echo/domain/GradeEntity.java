package com.lepique.api_rest_echo.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @ToString @EqualsAndHashCode
@Table(name = "TBGRADE")
public class GradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBGRADE")
    @SequenceGenerator(name = "SEQ_TBGRADE", sequenceName = "SEQ_TBGRADE", allocationSize = 1)
    @Column(name = "GRADEID")
    private Long gradeId;

    @Column(name = "GRADENAME")
    private String gradeName;

    //RELATIONS WITH ANOTHER TABLES

    //Students
    @OneToMany(mappedBy = "grade", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StudentEntity> StudentsGrades = new ArrayList<>();

}
