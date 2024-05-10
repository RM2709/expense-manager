package hr.assecosee.internship.expensemanager.database.repository;

import hr.assecosee.internship.expensemanager.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
