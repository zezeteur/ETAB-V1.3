package ci.digitalacademy.monetab.controller;

import ci.digitalacademy.monetab.models.Student;
import ci.digitalacademy.monetab.models.Teacher;
import ci.digitalacademy.monetab.models.User;
import ci.digitalacademy.monetab.services.StudentService;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
@Slf4j
public class StudentsController {

    public final StudentService studentService;

    @GetMapping
    public String showStudentsList(Model model){
        log.debug("Request to show students list");
        List<StudentDTO> students = studentService.findAll();
        model.addAttribute("students" , students);

        return "students/list";
    }

    @PostMapping
    public String saveStudent(StudentDTO studentDTO){
        log.debug("Request to save student :{}", studentDTO);
        studentService.save(studentDTO);

        return "redirect:/students";
    }

    @GetMapping("/{id}")
    public String showUpdateStudentForms(Model model, @PathVariable Long id){
        log.debug("Request to show update student forms");
        Optional<StudentDTO> student = studentService.findOne(id);

        if (student.isPresent()){
            model.addAttribute("student" , student.get());
            return "students/forms";
        } else {
            return "redirect:/students";
        }
    }

    @GetMapping("/add")
    public String showAddStudentForms(Model model){
        log.debug("Request to show add student forms");
        model.addAttribute("student", new StudentDTO());
        return "students/forms";
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        try {
            studentService.delete(id); // Appelle la méthode de suppression dans le service
            return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
