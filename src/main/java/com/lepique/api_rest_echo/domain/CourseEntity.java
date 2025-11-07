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
@Table(name = "TBCOURSE")
public class CourseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBCOURSE")
    @SequenceGenerator(name = "SEQ_TBCOURSE",sequenceName = "SEQ_TBCOURSE", allocationSize = 1)
    @Column(name = "COURSEID")
    private Long courseId;

    @Column(name = "COURSENAME")
    private String courseName;


    //RELATIONS WITH OTHER TABLES

    //Inscriptions
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InscriptionEntity> coursesInscriptions = new ArrayList<>();

    //Scores
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScoreEntity> courseScores = new ArrayList<>();

    //Classes
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClassEntity> courseClasses = new ArrayList<>();
}
