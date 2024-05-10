package hr.assecosee.internship.expensemanager.database.repository;

import hr.assecosee.internship.expensemanager.database.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

}
