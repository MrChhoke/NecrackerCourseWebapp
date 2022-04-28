package ua.bondar.course.bondarsite.repo;

import org.springframework.data.repository.CrudRepository;
import ua.bondar.course.bondarsite.model.UserOfShop;


public interface UserRepo extends CrudRepository<UserOfShop, Long> {
    UserOfShop findByUsername(String name);
    Boolean existsUserOfShopsByUsername(String name);
}
