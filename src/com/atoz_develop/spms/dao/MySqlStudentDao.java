package com.atoz_develop.spms.dao;

import com.atoz_develop.spms.vo.Student;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlStudentDao implements StudentDao {

    DataSource ds;

    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    /**
     * 학생 목록 조회
     *
     * @return 학생 목록
     * @throws SQLException
     */
    public List<Student> selectList() throws SQLException {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        List<Student> students = null;
        try {
            connection = ds.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(
                    "SELECT STUDENT_NO, DEPARTMENT, STUDENT_NAME, GRADE, GENDER, AGE, PHONE_NUMBER, ADDRESS " +
                            " FROM STUDENT " +
                            " WHERE STUDENT_NO != 'admin'" +
                            " ORDER BY STUDENT_NO"
            );

            students = new ArrayList<>();

            while (rs.next()) {
                students.add(new Student()
                        .setStudentNo(rs.getString("STUDENT_NO"))
                        .setDepartment(rs.getString("DEPARTMENT"))
                        .setStudentName(rs.getString("STUDENT_NAME"))
                        .setGrade(rs.getInt("GRADE"))
                        .setGender(rs.getString("GENDER"))
                        .setAge(rs.getInt("AGE"))
                        .setPhoneNumber(rs.getString("PHONE_NUMBER"))
                        .setAddress(rs.getString("ADDRESS"))
                );
            }

            return students;

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 신규 학생 등록
     *
     * @param student
     * @return
     * @throws SQLException
     */
    public int insert(Student student) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = ds.getConnection();
            pstmt = connection.prepareStatement(
                    "INSERT INTO STUDENT(STUDENT_NO, DEPARTMENT, STUDENT_NAME, GRADE, GENDER, AGE, PHONE_NUMBER, ADDRESS, PASSWORD)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );

            pstmt.setString(1, student.getStudentNo());
            pstmt.setString(2, student.getDepartment());
            pstmt.setString(3, student.getStudentName());
            pstmt.setInt(4, student.getGrade());
            pstmt.setString(5, student.getGender());
            pstmt.setInt(6, student.getAge());
            pstmt.setString(7, student.getPhoneNumber());
            pstmt.setString(8, student.getAddress());
            pstmt.setString(9, student.getPassword());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) { }
        }
    }

    /**
     * 학생 삭제
     *
     * @param studentNo
     * @return
     * @throws SQLException
     */
    public int delete(String studentNo) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = ds.getConnection();
            pstmt = connection.prepareStatement(
                    "DELETE FROM STUDENT WHERE STUDENT_NO = ?"
            );
            pstmt.setString(1, studentNo);

            return pstmt.executeUpdate();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 학생 상세 정보 조회
     *
     * @param studentNo
     * @return
     * @throws SQLException
     */
    public Student selectOne(String studentNo) throws SQLException {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            connection = ds.getConnection();
            stmt = connection.createStatement();
            rs = stmt.executeQuery(
                    "SELECT STUDENT_NO, DEPARTMENT, STUDENT_NAME, GRADE, GENDER, AGE, PHONE_NUMBER, ADDRESS" +
                            " FROM STUDENT" +
                            " WHERE STUDENT_NO = " + studentNo
            );
            rs.next();

            return new Student()
                    .setStudentNo(studentNo)
                    .setDepartment(rs.getString("DEPARTMENT"))
                    .setStudentName(rs.getString("STUDENT_NAME"))
                    .setGrade(rs.getInt("GRADE"))
                    .setGender(rs.getString("GENDER"))
                    .setAge(rs.getInt("AGE"))
                    .setPhoneNumber(rs.getString("PHONE_NUMBER"))
                    .setAddress(rs.getString("ADDRESS")
                    );

        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 학생 정보 수정
     *
     * @param student
     * @return
     * @throws SQLException
     */
    public int update(Student student) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = ds.getConnection();
            pstmt = connection.prepareStatement(
                    "UPDATE STUDENT SET DEPARTMENT = ?, STUDENT_NAME = ?, PHONE_NUMBER = ?, ADDRESS = ? WHERE STUDENT_NO = ?"
            );
            pstmt.setString(1, student.getDepartment());
            pstmt.setString(2, student.getStudentName());
            pstmt.setString(3, student.getPhoneNumber());
            pstmt.setString(4, student.getAddress());
            pstmt.setString(5, student.getStudentNo());
            return pstmt.executeUpdate();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 로그인 처리를 위한 Student 리턴
     * @param studentNo
     * @param password
     * @return
     * @throws SQLException
     */
    public Student exist(String studentNo, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // DB에서 학생 정보 조회
            connection = ds.getConnection();
            pstmt = connection.prepareStatement(
                    "SELECT STUDENT_NO, STUDENT_NAME FROM STUDENT" +
                            " WHERE STUDENT_NO = ? AND PASSWORD = ?"
            );
            pstmt.setString(1, studentNo);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            // 일치하는 학생이 있으면 Student에 학생 정보 담음
            if (rs.next()) {
                return new Student()
                        .setStudentNo(rs.getString("STUDENT_NO"))
                        .setStudentName(rs.getString("STUDENT_NAME"));
            } else return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (Exception e) { }
        }
    }
}