package com.lepique.api_rest_echo.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @EqualsAndHashCode
@Table(name = "TBSCORE")
public class ScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBSCORE")
    @SequenceGenerator(name = "SEQ_TBSCORE", sequenceName = "SEQ_TBSCORE", allocationSize = 1)
    @Column(name = "SCOREID")
    private Long scoreId;

    @ManyToOne
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "COURSEID", referencedColumnName = "COURSEID")
    private CourseEntity course;

    @Column(name = "SCORE")
    private Float score;
}
