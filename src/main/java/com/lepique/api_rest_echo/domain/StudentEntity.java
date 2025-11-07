package com.lepique.api_rest_echo.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @EqualsAndHashCode @ToString
@Table(name = "TBSTUDENT")
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBSTUDENT")
    @SequenceGenerator(name = "SEQ_TBSTUDENT", sequenceName = "SEQ_TBSTUDENT", allocationSize = 1)
    @Column(name = "STUDENTID")
    private Long studentId;

    @Column(name = "STUDENTNAME")
    private String studentName;

    @Column(name = "STUDENTLASTNAME")
    private String studentLastName;

    @Column(name = "STUDENTAGE")
    private Long studentAge;

    @ManyToOne
    @JoinColumn(name = "GRADEID", referencedColumnName = "GRADEID")
    private GradeEntity grade;
}
