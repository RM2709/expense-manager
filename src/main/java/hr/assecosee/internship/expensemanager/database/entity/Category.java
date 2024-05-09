package hr.assecosee.internship.expensemanager.database.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "CATEGORY_ID", nullable = false)
    private Integer categoryId;
    @Basic
    @Column(name = "NAME", nullable = false, length = -1)
    private String name;
    @Basic
    @Column(name = "DESCRIPTION", nullable = true, length = -1)
    private String description;
    @OneToMany(mappedBy = "categoryByCategoryId")
    private Collection<Expense> expensesByCategoryId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId) && Objects.equals(name, category.name) && Objects.equals(description, category.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, name, description);
    }

    public Collection<Expense> getExpensesByCategoryId() {
        return expensesByCategoryId;
    }

    public void setExpensesByCategoryId(Collection<Expense> expensesByCategoryId) {
        this.expensesByCategoryId = expensesByCategoryId;
    }
}
