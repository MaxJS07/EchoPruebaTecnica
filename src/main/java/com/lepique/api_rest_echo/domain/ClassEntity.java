package com.lepique.api_rest_echo.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @EqualsAndHashCode
@Table(name = "TBCLASSES")
public class ClassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBCLASSES")
    @SequenceGenerator(name = "SEQ_TBCLASSES", sequenceName = "SEQ_TBCLASSES", allocationSize = 1)
    @Column(name = "CLASSID")
    private Long classId;

    @ManyToOne
    @JoinColumn(name = "TEACHERID", referencedColumnName = "TEACHERID")
    private TeacherEntity teacher;

    @ManyToOne
    @JoinColumn(name = "COURSEID", referencedColumnName = "COURSEID")
    private CourseEntity course;

    @ManyToOne
    @JoinColumn(name = "GRADEID", referencedColumnName = "GRADEID")
    private GradeEntity grade;
}
