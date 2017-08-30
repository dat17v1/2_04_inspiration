package dk.kea.clbo.studentsapp.controllers;

import dk.kea.clbo.studentsapp.models.entities.Student;
import dk.kea.clbo.studentsapp.models.repositories.IStudentRepository;
import dk.kea.clbo.studentsapp.models.repositories.StudentInMemory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class StudentController {

    // List<Student> students = new ArrayList<>();
    private IStudentRepository studentrepository = new StudentInMemory();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("std", studentrepository.readAll());
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("student", new Student());
        return "create";

    }

    @PostMapping("/create")
    public String create(@ModelAttribute Student stu) {
        // generate random studentID
        UUID uuid = UUID.randomUUID();
        stu.setStudentId(uuid.toString());

        studentrepository.create(stu);
        return "redirect:/";
        //return new ModelAndView("redirect:/");
    }

    @GetMapping("/details")
    public String details(@RequestParam("id") String id, Model model) {
        model.addAttribute("stu", studentrepository.read(id));
        return "details";//details(model);
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") String id, Model model) {
        model.addAttribute("stu", studentrepository.read(id));
        //model.addAttribute("student", new Student());
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute Student stu, Model model){
        studentrepository.delete(stu);
        return "index";
    }

    @GetMapping("/enroll")
    public String enroll(@RequestParam("id") String id, Model model) {
        // TODO: Get list of Courses
        // model.addAttribute("courses", courserepository.readAll());
        model.addAttribute("stu", studentrepository.read(id));
        return "enroll"; // enroll.html is similar to create student
    }
}
