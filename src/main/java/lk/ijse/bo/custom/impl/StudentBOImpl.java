package lk.ijse.bo.custom.impl;

import lk.ijse.DTO.StudentDTO;
import lk.ijse.bo.custom.StudentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StudentDAO;
import lk.ijse.entity.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBOImpl implements StudentBO<StudentDTO> {

    StudentDAO studentDAO = (StudentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.STUDENT);

    @Override
    public boolean addStudent(StudentDTO entity) throws IOException {
        Student student = new Student();
        student.setStudent_id(entity.getStudent_id());
        student.setName(entity.getName());
        student.setAddress(entity.getAddress());
        student.setContact(entity.getContact());
        student.setGender(entity.getGender());
        student.setDob(entity.getDob());


        return studentDAO.add(student);
    }

    @Override
    public String generateNewStudentID() throws IOException {
        return studentDAO.generateNewID();
    }

    @Override
    public List<StudentDTO> getAllStudents() throws IOException {
        List<Student> students = studentDAO.getAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();

        for (Student student : students) {

            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setStudent_id(student.getStudent_id());
            studentDTO.setName(student.getName());
            studentDTO.setAddress(student.getAddress());
            studentDTO.setContact(student.getContact());
            studentDTO.setGender(student.getGender());
            studentDTO.setDob(student.getDob());

            studentDTOS.add(studentDTO);
        }

        return studentDTOS;
    }

    @Override
    public boolean updateStudent(StudentDTO entity) throws IOException {
        Student student = new Student();
        student.setStudent_id(entity.getStudent_id());
        student.setName(entity.getName());
        student.setAddress(entity.getAddress());
        student.setContact(entity.getContact());
        student.setGender(entity.getGender());
        student.setDob(entity.getDob());
        return studentDAO.update(student);
    }

    @Override
    public boolean deleteStudent(String id) throws IOException {
            return studentDAO.delete(id);
    }

    @Override
    public List<Student> SearchSID(String sid) throws IOException {
        return studentDAO.SearchSID(sid);
    }

    @Override
    public Student serachbyIDS(String sid) throws SQLException, ClassNotFoundException, IOException {
        return studentDAO.searchById(sid);
    }
}
