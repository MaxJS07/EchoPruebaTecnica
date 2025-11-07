package com.lepique.api_rest_echo.service;

import com.lepique.api_rest_echo.domain.CourseEntity;
import com.lepique.api_rest_echo.domain.GradeEntity;
import com.lepique.api_rest_echo.domain.ScoreEntity;
import com.lepique.api_rest_echo.domain.StudentEntity;
import com.lepique.api_rest_echo.model.dto.ScoreDTO;
import com.lepique.api_rest_echo.repository.CourseRepository;
import com.lepique.api_rest_echo.repository.ScoreRepository;
import com.lepique.api_rest_echo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {

    //Repos
    @Autowired
    private ScoreRepository repo;

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    StudentRepository studentRepo;

    /**
     * RETURNS ALL THE SCORES OF ALL THE STUDENTS
     * @return
     */
    public List<ScoreDTO> GetAllScores(){
        List<ScoreEntity> scores = repo.findAll();
        return scores.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * RETURNS ALL THE SCORES FROM ALL THE COURSES OF A SPECIFIC STUDENT
     * @param idStudent
     * @return
     */
    public List<ScoreDTO> GetScoresByStudent(Long idStudent){
        List<ScoreEntity> scores = repo.scoresByStudent(idStudent);
        return scores.stream()
                .map(this::fromEntityToDto)
                .collect(Collectors.toList());
    }

    /**
     * INSERTS THE SCORE OF A STUDENT FROM A COURSE, AND RETURNS THE INSERTED DATA
     * @param score
     * @return
     */
    public ScoreDTO insertScore(ScoreDTO score){
        if(score.getScore() == null){
            throw new IllegalArgumentException("An atribute is empty.");
        }
        else{
            //De no venir vacíos pasamos a insertar
            try{
                ScoreEntity scoreEntity = fromDtoToEntity(score);
                ScoreEntity savedScore = repo.save(scoreEntity);

                return fromEntityToDto(savedScore);
            }
            catch (Exception e){
                throw new RuntimeException("There was an error trying to insert the score: " + e.getMessage());
            }
        }

    }

    /**
     * UPDATES THE SCORE OF A STUDENT FROM A COURSE, AND RETURNS THE UPDATED DATA
     * @param scoreId
     * @param score
     * @return
     */
    public ScoreDTO updateScore(Long scoreId, ScoreDTO score){

        //verificamos existencia
        ScoreEntity scoreExis = repo.findById(scoreId)
                .orElseThrow(() -> new RuntimeException("The score that was pretended to update wasn't found."));

        try{
            scoreExis.setScore(score.getScore());

            if(score.getStudentId() != null){
                StudentEntity student = studentRepo.findById(score.getStudentId())
                        .orElseThrow(() -> new IllegalArgumentException("The student with the given ID wasn't found."));
                scoreExis.setStudent(student);
            }
            else{
                scoreExis.setStudent(null);
            }

            if(score.getCourseId() != null){
                CourseEntity course = courseRepo.findById(score.getCourseId())
                        .orElseThrow(()-> new IllegalArgumentException("The course with the given ID wasn't found."));
                scoreExis.setCourse(course);
            }

            ScoreEntity scoreUpdated = repo.save(scoreExis);

            return fromEntityToDto(scoreUpdated);
        }
        catch (Exception e){
            throw new RuntimeException("There was an error trying to update the student: " + e.getMessage());
        }
    }


    //CONVERSION METHODS
    private ScoreDTO fromEntityToDto(ScoreEntity entity){

        ScoreDTO dto = new ScoreDTO();

        dto.setScoreId(entity.getScoreId());
        dto.setScore(entity.getScore());

        if(entity.getStudent() != null){
            dto.setStudentId(entity.getStudent().getStudentId());
            dto.setStudentName(entity.getStudent().getStudentName() + " " + entity.getStudent().getStudentLastName());
            dto.setStudentGrade(entity.getStudent().getGrade().getGradeName());
        }
        else{
            dto.setStudentId(null);
            dto.setStudentName("Without student");
            dto.setStudentGrade("Without grade");
        }

        if(entity.getCourse() != null){
            dto.setCourseId(entity.getCourse().getCourseId());
            dto.setCourseName(entity.getCourse().getCourseName());
        }
        else{
            dto.setCourseId(null);
            dto.setCourseName("without course");
        }

        return dto;
    }

    private ScoreEntity fromDtoToEntity(ScoreDTO dto){

        ScoreEntity entity = new ScoreEntity();

        entity.setScore(dto.getScore());

        if(dto.getStudentId() != null){
            StudentEntity student = studentRepo.findById(dto.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("The student with the given ID wasn't found."));
            entity.setStudent(student);
        }

        if(dto.getCourseId() != null){
            CourseEntity course = courseRepo.findById(dto.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("The course with the given ID wasn't found"));
            entity.setCourse(course);
        }

        return entity;
    }
}
