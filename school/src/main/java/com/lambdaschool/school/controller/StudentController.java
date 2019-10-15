package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // Please note there is no way to add students to course yet!


    @ApiOperation(value = "List all enrolled students", response = Student.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student found.",
            response = Student.class),
            @ApiResponse(code = 404, message = "Student not found")})
    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudentsPageable(@PageableDefault(page=0, size=3)Pageable pageable) {
        List<Student> myStudents = studentService.findAllPageable(pageable);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    @ApiOperation(value = "Return one student by their ID#", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student found.",
            response = Student.class),
            @ApiResponse(code = 404, message = "Student not found")})
    @GetMapping(value = "/Student/{StudentId}",
            produces = {"application/json"})
    public ResponseEntity<?> getStudentById(
//            ApiParam(value = "Student ID#", required = true, example = "1")
            @PathVariable Long StudentId) {
        Student r = studentService.findStudentById(StudentId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }


    @ApiOperation(value = "Search for a student by their name", response = Student.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student found.",
            response = Student.class),
            @ApiResponse(code = 404, message = "Student not found")})
    @GetMapping(value = "/student/namelike/{name}",
            produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(
//            ApiParam(value = "Student ID#", required = true, example = "1")
            @PathVariable String name) {
        List<Student> myStudents = studentService.findStudentByNameLike(name);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    @ApiOperation(value = "Add a new student", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student added to the database."),
            @ApiResponse(code = 500, message = "Something went wrong and the student was not added.")})
    @PostMapping(value = "/Student",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(@Valid
                                           @RequestBody
                                                   Student newStudent) throws URISyntaxException {
        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Studentid}").buildAndExpand(newStudent.getStudid()).toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update a student by their ID #", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student updated in the database."),
            @ApiResponse(code = 500, message = "Something went wrong and the student was not updated.")})
    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(
            @RequestBody
                    Student updateStudent,
            @PathVariable
                    long Studentid) {
        studentService.update(updateStudent, Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Delete a student", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Student removed from the database."),
            @ApiResponse(code = 500, message = "Something went wrong and the student was not deleted.")})
    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(
//            ApiParam(value = "Student ID#", required = true, example = "1")
            @PathVariable
                    long Studentid) {
        studentService.delete(Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
