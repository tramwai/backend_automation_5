package stepDef.database_step_def;

import io.cucumber.java.en.*;
import org.testng.Assert;
import utils.DBUtil;

import java.math.BigDecimal;

public class databaseStepDef {

    static String mainQuery;

    @Given("user is able to connect to database")
    public void user_is_able_to_connect_to_database() {
        DBUtil.createDBConnection();

    }
    @When("user send {string} to database")
    public void user_send_to_database(String query) {
        mainQuery = query;
        DBUtil.executeQuery(query);
    }
    @Then("Validate the {int}")
    public void validate_the(Integer expectedSalary) {
        // to understand data type of unknown value
        System.out.println("data type is: " + DBUtil.getCellValue(mainQuery).getClass());

        Assert.assertEquals(DBUtil.getCellValue(mainQuery), new BigDecimal(expectedSalary));
    }
}