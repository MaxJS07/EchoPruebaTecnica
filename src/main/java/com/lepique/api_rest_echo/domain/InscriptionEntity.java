package com.lepique.api_rest_echo.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString @EqualsAndHashCode
@Table(name = "TBINSCRIPTION")
public class InscriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBINSCRIPTION")
    @SequenceGenerator(name = "SEQ_TBINSCRIPTION", sequenceName = "SEQ_TBINSCRIPTION", allocationSize = 1)
    @Column(name = "INSCRIPTIONID")
    private Long inscriptionId;

    @ManyToOne
    @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "COURSEID", referencedColumnName = "COURSEID")
    private CourseEntity course;
}
