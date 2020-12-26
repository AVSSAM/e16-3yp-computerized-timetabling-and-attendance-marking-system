package Group10.example.API.DAO;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Group10.example.API.Model.Lecturer;
import Group10.example.API.Repository.LecturerRepository;

@Component
public class LecturerDAO {
	
	@Autowired
	private LecturerRepository lecturerRepository;
	
	public Collection<Lecturer> getLectures() {
        return lecturerRepository.findAll();
    }

    public Lecturer addLecture( Lecturer lecturer) {
        return lecturerRepository.insert(lecturer);
    }

    public Optional<Lecturer> getLecturerById(String id) {
        return lecturerRepository.findById(id);
    }
    
    public Optional<Lecturer> findLecturerByUserName(String userName){
    	
    	return Optional.of(lecturerRepository.findByuserName(userName));
    }

}
