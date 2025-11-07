package com.lepique.api_rest_echo.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @EqualsAndHashCode
@Table(name = "TBCOURSE")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBCOURSE")
    @SequenceGenerator(name = "SEQ_TBCOURSE",sequenceName = "SEQ_TBCOURSE", allocationSize = 1)
    @Column(name = "COURSEID")
    private Long courseId;

    @Column(name = "COURSENAME")
    private String courseName;
}
