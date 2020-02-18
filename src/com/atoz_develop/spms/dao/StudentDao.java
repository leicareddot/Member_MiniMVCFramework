package com.atoz_develop.spms.dao;

import com.atoz_develop.spms.vo.Student;

import java.sql.SQLException;
import java.util.List;

public interface StudentDao {
    /**
     * 학생 목록 조회
     *
     * @return 학생 목록
     * @throws SQLException
     */
    List<Student> selectList() throws SQLException;

    /**
     * 신규 학생 등록
     *
     * @param student
     * @return
     * @throws SQLException
     */
    int insert(Student student) throws SQLException;

    /**
     * 학생 삭제
     *
     * @param studentNo
     * @return
     * @throws SQLException
     */
    int delete(String studentNo) throws SQLException;

    /**
     * 학생 상세 정보 조회
     *
     * @param studentNo
     * @return
     * @throws SQLException
     */
    Student selectOne(String studentNo) throws SQLException;

    /**
     * 학생 정보 수정
     *
     * @param student
     * @return
     * @throws SQLException
     */
    int update(Student student) throws SQLException;

    /**
     * 로그인 처리를 위한 Student 리턴
     * @param studentNo
     * @param password
     * @return
     * @throws SQLException
     */
    Student exist(String studentNo, String password) throws SQLException;
}
