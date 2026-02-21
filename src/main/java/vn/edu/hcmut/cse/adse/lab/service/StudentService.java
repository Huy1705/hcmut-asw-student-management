package vn.edu.hcmut.cse.adse.lab.service;

import org.springframework.stereotype.Service;

import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repository;

    public List<Student> getAll() {
        return repository.findAll();
    }

    public Student getById(String id) {
        return repository.findById(id).orElse(null);
    }

    public List<Student> searchByName(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword);
    }

    // Thêm hàm Lưu (dùng cho cả Thêm mới và Cập nhật)
    public void save(Student student) {
        repository.save(student);
    }

    // Thêm hàm Xóa theo ID
    public void delete(String id) {
        repository.deleteById(id);
    }
    public Page<Student> getAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }
    public Page<Student> searchByName(String keyword, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(keyword, pageable);
    }

}