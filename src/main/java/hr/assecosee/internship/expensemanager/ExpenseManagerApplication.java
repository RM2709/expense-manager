package hr.assecosee.internship.expensemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ExpenseManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseManagerApplication.class, args);
	}

}
