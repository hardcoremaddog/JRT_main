package com.javarush.task.task29.task2909.human;

import java.util.*;

public class University {
    private String name;
    private int age;
    private List<Student> students = new ArrayList<>();

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List getStudents() {
        return students;
    }

    public void setStudents(List students) {
        this.students = students;
    }

    public University(List students) {
        this.students = students;
    }

    public Student getStudentWithAverageGrade(double value) {
        for (Student s : students) {
            if (s.getAverageGrade() == value) {
                return s;
            }
        }
        return null;
    }

    private Map<Student, Double> studentsMap = new HashMap<>();
    public void createStudentsMap() {
        for (Student s : students) {
            studentsMap.put(s, s.getAverageGrade());
        }
    }

    public Student getStudentWithMaxAverageGrade() {
        createStudentsMap();
        return Collections.max(studentsMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public Student getStudentWithMinAverageGrade() {
        createStudentsMap();
        return Collections.min(studentsMap.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public void expel(Student student) {
        students.remove(student);
    }
}