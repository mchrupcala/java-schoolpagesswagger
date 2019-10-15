package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "List all courses", response = Course.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Class list was found",
            response = Course.class),
            @ApiResponse(code = 404, message = "Class list was not found")})
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCoursesByPage(@PageableDefault(page = 0, size = 3)Pageable pageable)
    {
        ArrayList<Course> myCourses = courseService.findAllPageable(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }



    @ApiOperation(value = "List the number of students per class", response = Course.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Class list was found",
            response = Course.class),
            @ApiResponse(code = 404, message = "Class list was not found")})
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a course", response = Course.class)
    @ApiResponses(value = {@ApiResponse(code = 200,
            message = "Course deleted from the database.",
            response = Course.class),
            @ApiResponse(code = 500, message = "Something went wrong and the course was not deleted.")})
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(
            @ApiParam(value = "Course ID#", required = true, example = "1")
            @PathVariable long courseid)
    {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
