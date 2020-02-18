package com.atoz_develop.spms.vo;

public class Student {
    protected String studentNo;
    protected String department;
    protected String studentName;
    protected int grade;
    protected String gender;
    protected int age;
    protected String phoneNumber;
    protected String address;
    protected String password;

    public String getStudentNo() {
        return studentNo;
    }

    public Student setStudentNo(String studentNo) {
        this.studentNo = studentNo;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public Student setDepartment(String department) {
        this.department = department;
        return this;
    }

    public String getStudentName() {
        return studentName;
    }

    public Student setStudentName(String studentName) {
        this.studentName = studentName;
        return this;
    }

    public int getGrade() {
        return grade;
    }

    public Student setGrade(int grade) {
        this.grade = grade;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public Student setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Student setAge(int age) {
        this.age = age;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Student setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Student setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Student setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return studentNo + ", " + studentName + ", " + department + ", " + grade + ", " + gender + ", " + age + ", " + phoneNumber + ", " +
                address + ", " + password;
    }
}
