package com.lambdaschool.school.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "Instructor", description = "The entity/table for Instructors")
@Entity
@Table(name = "instructor")
public class Instructor
{
    @ApiModelProperty(name = "instructid", value = "Primary key for Instructors table", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long instructid;

    @ApiModelProperty(name = "instructname", value = "Instructor name", required = true, example = "John Doe")
    private String instructname;

    @ApiModelProperty(name = "courses", value = "List of courses...foreign key for Courses table", required = true, example = "History 101, Chemistry")
    @OneToMany(mappedBy = "instructor")
    @JsonIgnoreProperties("instructors")
    private List<Course> courses = new ArrayList<>();

    public Instructor()
    {
    }

    public Instructor(String instructname)
    {
        this.instructname = instructname;
    }

    public long getInstructid()
    {
        return instructid;
    }

    public void setInstructid(long instructid)
    {
        this.instructid = instructid;
    }

    public String getInstructname()
    {
        return instructname;
    }

    public void setInstructname(String instructname)
    {
        this.instructname = instructname;
    }

    public List<Course> getCourses()
    {
        return courses;
    }

    public void setCourses(List<Course> courses)
    {
        this.courses = courses;
    }
}
