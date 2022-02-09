package ua.bondar.course.bondarsite.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondar.course.bondarsite.model.FeedBack;

import java.util.List;

public interface FeedBackRepositary extends JpaRepository<FeedBack, Long> {
    void deleteById(Long id);
}
