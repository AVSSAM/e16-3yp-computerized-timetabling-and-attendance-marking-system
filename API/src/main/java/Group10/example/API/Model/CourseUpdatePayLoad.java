package Group10.example.API.Model;

import java.util.List;

public class CourseUpdatePayLoad {
    private String courseNumber;
    private String courseName;
    private int semester;
    private int days;

    private List<Schedule> timeTable;
    private List<Log> courseLog;

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getSemester() {
        return semester;
    }

    public int getDays() {
        return days;
    }

    public List<Schedule> getTimeTable() {
        return timeTable;
    }

    public List<Log> getCourseLog() {
        return courseLog;
    }
}
