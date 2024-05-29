package hr.assecosee.internship.expensemanager.database.repository;

import hr.assecosee.internship.expensemanager.database.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    List<Expense> findAllByUserId(Integer userId);

    List<Expense> findAllByCategoryId(Integer categoryId);

    List<Expense> findAllByTimeBetween(Date from, Date to);

    List<Expense> findAllByTimeBetweenAndUserId(Date from, Date to, Integer userId);
}
