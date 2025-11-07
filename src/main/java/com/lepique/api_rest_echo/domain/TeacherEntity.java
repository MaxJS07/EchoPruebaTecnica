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
@Table(name = "TBTEACHER")
public class TeacherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TBTEACHER")
    @SequenceGenerator(name = "SEQ_TBTEACHER", sequenceName = "SEQ_TBTEACHER", allocationSize = 1)
    @Column(name = "TEACHERID")
    private Long teacherId;

    @Column(name = "TEACHERNAME")
    private String teacherName;

    @Column(name = "TEACHERLASTNAME")
    private String teacherLastName;

    @Column(name = "TEACHEREMAIL")
    private String teacherEmail;

    //RELATIONS WITH OTHER TABLES

    //Classes
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ClassEntity> teacherClasses = new ArrayList<>();
}
