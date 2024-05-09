package hr.assecosee.internship.expensemanager.database.entity;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "USERS", schema = "public", catalog = "user")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "USER_ID", nullable = false)
    private Integer userId;
    @Basic
    @Column(name = "FIRST_NAME", nullable = false, length = -1)
    private String firstName;
    @Basic
    @Column(name = "LAST_NAME", nullable = false, length = -1)
    private String lastName;
    @Basic
    @Column(name = "EMAIL", nullable = true, length = -1)
    private String email;
    @OneToMany(mappedBy = "usersByUserId")
    private Collection<Expense> expensesByUserId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, email);
    }

    public Collection<Expense> getExpensesByUserId() {
        return expensesByUserId;
    }

    public void setExpensesByUserId(Collection<Expense> expensesByUserId) {
        this.expensesByUserId = expensesByUserId;
    }
}
