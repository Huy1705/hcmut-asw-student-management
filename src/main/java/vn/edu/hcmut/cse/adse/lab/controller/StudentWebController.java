package vn.edu.hcmut.cse.adse.lab.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; // Luu y: su dung @Controller, KHONG dung
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import vn.edu.hcmut.cse.adse.lab.entity.Student;
import vn.edu.hcmut.cse.adse.lab.service.StudentService;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentWebController {
@Autowired
private StudentService service;
// Route: GET http://localhost:8080/students
@GetMapping
    public String getAllStudents(
            @RequestParam(required = false) String keyword, 
            @RequestParam(defaultValue = "0") int page, 
            Model model) {
            
        Pageable pageable = PageRequest.of(page, 5);
        Page<Student> studentPage;

        System.out.println("Keyword nhận được: " + keyword);

        if (keyword != null && !keyword.isEmpty()) {
            studentPage = service.searchByName(keyword, pageable);
        } else {
            studentPage = service.getAllPaginated(pageable);
        }

        // Truyền dữ liệu sang View (Thymeleaf)
        model.addAttribute("dsSinhVien", studentPage.getContent()); 
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "students";
    }
    // 1.2 Xem chi tiết sinh viên
    @GetMapping("/{id}")
    public String viewStudent(@PathVariable String id, Model model) {
        Student student = service.getById(id);
        if (student == null) {
            return "redirect:/students"; 
        }
        model.addAttribute("student", student);
        return "student-detail";
    }

    // 1.3 Hiển thị Form Thêm mới
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "student-form";
    }

    // 1.3 Hiển thị Form Chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Student student = service.getById(id);
        model.addAttribute("student", student); 
        return "student-form";
    }

    // 1.3 Xử lý lưu dữ liệu (Thêm mới/Sửa)
    @PostMapping("/save")
    public String saveStudent(Student student) {
        service.save(student);
        return "redirect:/students"; 
    }

    // Xử lý Xóa sinh viên
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        service.delete(id);
        return "redirect:/students";
    }
}