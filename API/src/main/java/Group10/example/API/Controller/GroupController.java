package Group10.example.API.Controller;

import Group10.example.API.Model.*;
import Group10.example.API.Repository.CourseRepository;
import Group10.example.API.Repository.GroupRepository;
import Group10.example.API.Repository.LecturerRepository;
import Group10.example.API.Repository.StudentRepository;
import Group10.example.API.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @Autowired
    StudentRepository studentRepo;

    @Autowired
    CourseRepository courseRepo;

    @Autowired
    LecturerRepository lecRepo;

    @Autowired
    GroupRepository groupRepo;

    @Autowired
    public GroupController(GroupService groupService){
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public HashMap<String,String> createGroup(@Valid @RequestBody Group group){

        
        HashMap<String, String> map = new HashMap<>();
        if(groupRepo.findBygroupName(group.getGroupName())!=null){
            map.put("msg","Group Name is already exists");
            return map;
        }
        groupService.saveGroup(group);
        map.put("msg","successfully created group");
        map.put("group_id",group.getGroupID());
        return map;
    }

    @PostMapping("add/lecturers")
    public HashMap<String, String> addLec(@RequestBody GroupPayLoad lecturers){
        HashMap<String, String> map ;
        List<String> lecList = lecturers.getIdList();
        String groupID = lecturers.getGroupId();
        map  = groupService.addLectures(lecList,groupID);
        return map;

    }

   @GetMapping(value="/all/lecturers/{group_id}")
    public List<Lecturer> getLecturers(@PathVariable("group_id") String groupId){
       Optional<Group> group =groupService.findGroupByID(groupId);
       List<Lecturer> lecs = new ArrayList<Lecturer>();
       Set<String> lecID ;

       if(group.isPresent()){
           lecID = group.get().getLecList();
           for(String a:lecID){

               Optional<Lecturer> lec = lecRepo.findById(a);
               lec.ifPresent(c->lecs.add(c));

           }

       }

       return lecs;
    }


    @PostMapping(value="/remove/lecturers")
    public HashMap<String,Object> removeLec(@RequestBody GroupPayLoad lecturers){

        HashMap<String, Object> map;
        String groupId = lecturers.getGroupId();
        List<String> lecList = lecturers.getIdList();
        map = groupService.removeLecFromGroup(lecList,groupId);
        return map;


    }

    @PostMapping("add/students")
    public HashMap<String, Object> addStudents(@RequestBody GroupPayLoad students){
        HashMap<String, Object> map ;
        List<String> studentList = students.getIdList();
        String groupID = students.getGroupId();
        map  = groupService.addStudents(studentList,groupID);
        return map;

    }

    @RequestMapping(value="/all/students")
    public HashMap<String, Object>   getStudents(@RequestBody GroupPayLoad groupId){
        HashMap<String, Object> map = new HashMap<>();
        Group group =groupService.findGroupByName(groupId.getGroupName());
        List<String> students = new ArrayList<>();
        Set<String> stuID ;

        if(group==null){

            map.put("msg","group not found");
            return map;

        }
        stuID = group.getStudentList();
        for(String a:stuID){

            Student student = studentRepo.findByuserName(a);
            if(student!=null){

                students.add(student.getUserName());
                
            }

        }

        map.put("students",students);
        return map;
    }


    @PostMapping(value="/remove/students")
    public HashMap<String,Object> removeStudent(@RequestBody GroupPayLoad students){

        HashMap<String, Object> map ;
        String groupId = students.getGroupName();
        List<String> studentList = students.getIdList();
        map = groupService.removeStudentFromGroup(studentList,groupId);
        return map;


    }

    @PostMapping("add/courses")
    public HashMap<String, Object> addCourse(@RequestBody GroupPayLoad course){
        HashMap<String, Object> map ;
        List<String> courseList = course.getIdList();
        String groupID = course.getGroupId();
        map  = groupService.addCourse(courseList,groupID);
        return map;

    }

    @GetMapping(value="/all/courses/{group_id}")
    public List<Course> getCourses(@PathVariable("group_id") String groupId){
        Optional<Group> group =groupService.findGroupByID(groupId);
        List<Course> courses = new ArrayList<Course>();
        Set<String> courseID ;

        if(group.isPresent()){
           courseID = group.get().getCourseList();
            for(String a:courseID){

                Optional<Course> course = courseRepo.findById(a);
                course.ifPresent(c->courses.add(c));

            }

        }

        return courses;
    }


    @PostMapping(value="/remove/courses")
    public HashMap<String,Object> removeCourse(@RequestBody GroupPayLoad course){

        HashMap<String, Object> map ;
        String groupId = course.getGroupId();
        List<String> courseList = course.getIdList();
        map = groupService.removeCourseFromGroup(courseList,groupId);
        return map;


    }

    @GetMapping(value="/all")
    public List<String> allGroups() {
        Collection<Group> groups=groupService.getAll();
        List<String> groupNames=new ArrayList<>();
        for(Group group : groups){
            groupNames.add(group.getGroupName());
        }
        return groupNames;
    }

    @PostMapping(value = "/delete")
    public Group deleteGroup(@RequestBody GroupPayLoad groupName) {
        Group grp= groupService.findGroupByName(groupName.getGroupName());
        String grpId = grp.getGroupID();
        groupService.deleteGroup(grpId);
        return grp;

    }


}
