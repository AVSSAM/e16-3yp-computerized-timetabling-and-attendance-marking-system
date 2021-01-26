package Group10.example.API.Service;

import Group10.example.API.Model.Course;
import Group10.example.API.Model.Group;
import Group10.example.API.Model.Student;
import Group10.example.API.Model.StudentPayload;
import Group10.example.API.Repository.CourseRepository;
import Group10.example.API.Repository.GroupRepository;
import Group10.example.API.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

@Service
public class StudentService {

    @Autowired
    StudentRepository stuRepo;

    @Autowired
    MailService mailService;

    @Autowired
    GroupRepository groupRepo;

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    AttendanceService attService;

    public Student addStudent(Student student){
       return stuRepo.save(student);
    }

    public HashMap<String, Object> deleteStudent(String id){

        HashMap<String,Object> map = new HashMap<>();
        Optional<Student> student = stuRepo.findById(id);
        HashSet<String> groups;
        HashSet<String> students;

        if(student.isPresent()){
            groups = student.get().getGroupSet();
            for(String groupID : groups ){
                Optional<Group> group = groupRepo.findById(groupID);
                if(group.isPresent()){
                    students = group.get().getStudentList();
                    students.remove(id);
                    //save group again
                    group.ifPresent(g->groupRepo.save(g));

                }

            }
        }

        attService.deleteByStudentId(id);
        stuRepo.deleteById(id);
        map.put("msg","successfully deleted");
        return map;

    }

    public HashMap<String,Object> updateUserName(Student student){
        HashMap<String,Object> map = new HashMap<>();
        Optional<Student> stu = stuRepo.findById(student.getStudentID());
        stu.ifPresent(s->stu.get().setUserName(student.getUserName()) );
        stu.ifPresent(s->stuRepo.save(s));
        map.put("msg","user name successfully updated");
        return map;
    }

    public HashMap<String,Object> updatePassword(Student student){
        HashMap<String,Object> map = new HashMap<>();
        Optional<Student> stu = stuRepo.findById(student.getStudentID());
        stu.ifPresent(s->stu.get().setUserName(student.getUserName()) );
        stu.ifPresent(s->stuRepo.save(s));
        map.put("msg","user name successfully updated");
        return map;
    }

    public HashMap<String,Object> updateStudent(StudentPayload student){
        HashMap<String,Object> map = new HashMap<>();
        Optional<Student> stu = stuRepo.findById(student.getId());

        if(stu.isPresent() && student.getUserName()!=null) {
            stu.ifPresent(s -> stu.get().setUserName(student.getUserName()));
        }
        if(stu.isPresent() && student.getPassword()!=null) {
            stu.ifPresent(s -> stu.get().setPassword(student.getPassword()));
        }
        stu.ifPresent(s->stuRepo.save(s));
        map.put("msg","user name successfully updated");
        return map;
    }
    
    public String passGenerate(){

        // create a string of uppercase and lowercase characters and numbers
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

        // create random string builder
        StringBuilder sb = new StringBuilder();

        // create an object of Random class
        Random random = new Random();

        // specify length of random string
        int length = 10;

        for(int i = 0; i < length; i++) {

            // generate random index number
            int index = random.nextInt(alphaNumeric.length());

            // get character specified by index
            // from the string
            char randomChar = alphaNumeric.charAt(index);

            // append the character to string builder
            sb.append(randomChar);
        }

        String randomString = sb.toString();

        return randomString;
    } 

    public void sendMail(String to,String body,String sub){
        mailService.sendMail(to, body, sub);
    }

}
