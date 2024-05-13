package hr.assecosee.internship.expensemanager.database.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Expense {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "expense_id", nullable = false)
    private Integer expenseId;
    @Basic
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Integer userId;
    @Basic
    @Column(name = "category_id", nullable = false, insertable = false, updatable = false)
    private Integer categoryId;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "time", nullable = false)
    private Object time;
    @Basic
    @Column(name = "amount", nullable = false, precision = 0)
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User usersByUserId;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return Objects.equals(expenseId, expense.expenseId) && Objects.equals(userId, expense.userId) && Objects.equals(categoryId, expense.categoryId) && Objects.equals(description, expense.description) && Objects.equals(time, expense.time) && Objects.equals(amount, expense.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseId, userId, categoryId, description, time, amount);
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