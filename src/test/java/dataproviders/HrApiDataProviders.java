package dataproviders;

import org.testng.annotations.DataProvider;

public class HrApiDataProviders {

    @DataProvider(name = "createEmployeeData")
    public Object[][] createEmployeeData(){
        return new Object[][]{
                {"Patel", 12345},
                {"J", 1}
        };
    }

    @DataProvider(name = "createEmployeeInvalidData")
    public Object[][] createEmployeeInvalidData(){
        return new Object[][]{
                {"", 12345, "All employee fields are required"},
                {"!@#$%", 12345, "All employee fields are required"},
                {"123eds", 12345, "All employee fields are required"},
                {"fieunvuedfnvuervneuvbneruyvbeuyrvbuyebvyuebvuyerbvuye", 12345, "All employee fields are required"},
                {"John", -123, "All employee fields are required"},
                {"John", 0, "All employee fields are required"},
                {"John", 1000000, "All employee fields are required"}
        };
    }

}
