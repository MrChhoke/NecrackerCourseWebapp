package ua.bondar.course.bondarsite.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bondar.course.bondarsite.model.message.FeedBack;

public interface FeedBackRepositary extends JpaRepository<FeedBack, Long> {
    void deleteById(Long id);
}
