package hr.assecosee.internship.expensemanager.database.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Expense {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "EXPENSE_ID", nullable = false)
    private Integer expenseId;
    @Basic
    @Column(name = "USER_ID", nullable = false, insertable = false, updatable = false)
    private Integer userId;
    @Basic
    @Column(name = "CATEGORY_ID", nullable = false, insertable = false, updatable = false)
    private Integer categoryId;
    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "TIME", nullable = false)
    private Object time;
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private User usersByUserId;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID", nullable = false)
    private Category categoryByCategoryId;

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(expenseId, expense.expenseId) && Objects.equals(userId, expense.userId) && Objects.equals(categoryId, expense.categoryId) && Objects.equals(description, expense.description) && Objects.equals(time, expense.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, userId, categoryId, description, time);
    }

    public User getUsersByUserId() {
        return usersByUserId;
    }

    public void setUsersByUserId(User usersByUserId) {
        this.usersByUserId = usersByUserId;
    }

    public Category getCategoryByCategoryId() {
        return categoryByCategoryId;
    }

    public void setCategoryByCategoryId(Category categoryByCategoryId) {
        this.categoryByCategoryId = categoryByCategoryId;
    }
}
