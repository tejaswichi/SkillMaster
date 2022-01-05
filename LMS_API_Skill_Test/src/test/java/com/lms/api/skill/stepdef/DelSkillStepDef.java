package com.lms.api.skill.stepdef;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.Assert;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

import java.io.IOException;

public class DelSkillStepDef extends TestBase{

	RequestSpecification requestSpec ;
	Response response;
	String path;
	String sheetDelete;

	
	DataTable dataTable;
	Scenario scenario;
	
	
	@Before
	public void initializeDataTable(Scenario scenario) throws Exception {
	this.scenario=scenario;
	sheetDelete=LoadProperties().getProperty("sheetDelete");	  
	dataTable =new DataTable("./src/test/resources/excel/testdata1.xls");
	dataTable.createConnection(sheetDelete);
	
	}
	
	public void requestSpecificationDelete() {
		requestSpec.header("Content-Type", "application/json");
		requestSpec.log();
		response = requestSpec.when().delete(path);
	}
	
	@Given("User is on DELETE method with endpoint")
	public void user_is_on_delete_method_with_endpoint() throws IOException {
		RestAssured.baseURI=LoadProperties().getProperty("base_uri");
		requestSpec=RestAssured.given().auth().preemptive().basic(LoadProperties().getProperty("username"),
				    LoadProperties().getProperty("password"));
		String skill_id=dataTable.getDataFromExcel(scenario.getName(),"Skill_id"); 
		System.out.println("SkillId is : " +skill_id);
		path=LoadProperties().getProperty("endpointDelete") + skill_id;
		System.out.println("Path for Delete is "+ path);
		
	}

	@When("User sends request with existing skill_id")
	public void user_sends_request_with_existing_skill_id() throws IOException {
		//requestSpecificationDelete();
		String skill_id=dataTable.getDataFromExcel(scenario.getName(),"Skill_id");
		String getPath =LoadProperties().getProperty("endpoint") + skill_id;
		
		// get the skill object for the skill id
		response = requestSpec.when().get(getPath);
		String responseBody = response.asPrettyString();

		// if skill object is exist for given skill id , go directly to delete
		if (response.statusCode() == 200 && responseBody.contains(skill_id)) {		
			requestSpecificationDelete();
		}
		else {  
			
			// post - create a new skill object using post and get newly created skill id
			String postPath=LoadProperties().getProperty("endpointPost");
			String body =  "{\"skill_name\": \"DataScience\"}";			
			requestSpec.body(body);
			requestSpec.header("Content-Type", "application/json");
			response = requestSpec.when().post(postPath);			
			String newSkillId = response.jsonPath().getString("skill_id");
			
			// delete the newly created skill id
			requestSpec.body("");
			path=LoadProperties().getProperty("endpointDelete") + newSkillId;			
			requestSpecificationDelete();
		}	
		
	}

	@Then("User should be able to delete the existing skill_id")
	public void user_should_be_able_to_delete_the_existing_skill_id() throws IOException {
		String expStatusCode = dataTable.getDataFromExcel(scenario.getName(), "StatusCode");
		String responseMessage = dataTable.getDataFromExcel(scenario.getName(), "Message");
		
		String responseBody = response.asPrettyString();
		System.out.println("Actual Response Status code=>  " + response.statusCode() + "  Expected Response Status code=>  " + expStatusCode);
		System.out.println("Response Body is =>  " + responseBody);
		assertEquals(Integer.parseInt(expStatusCode),response.statusCode());
		System.out.println("Response Message =>  " + responseMessage);
		
	}

	@When("User sends request with non-existing skill_id")
	public void user_sends_request_with_non_existing_skill_id() {
		requestSpecificationDelete();
	}

	
	@When("User sends request with blank skill_id")
	public void user_sends_request_with_blank_skill_id() {
		requestSpecificationDelete();
	}
	
	@When("User sends the request with alphanumeric skill Id")
	public void user_sends_the_request_with_alphanumeric_skill_id() {
		requestSpecificationDelete();
	}
	
	
	@When("User enter the Skill_id as decimal")
	public void user_enter_the_skill_id_as_decimal() {
		requestSpecificationDelete();
	}
	@Then("User should recieve an error status code")
	public void user_should_recieve_an_error_status_code() throws IOException {
		String expStatusCode = dataTable.getDataFromExcel(scenario.getName(), "StatusCode");
		String responseMessage = dataTable.getDataFromExcel(scenario.getName(), "Message");
		System.out.println("Actual Response Status code=>  " + response.statusCode() + "  Expected Response Status code=>  " + expStatusCode);
		String responseBody = response.asPrettyString();
		System.out.println("Response Body is =>  " + responseBody);
		assertEquals(Integer.parseInt(expStatusCode),response.statusCode());
		System.out.println("Response Message =>  " + responseMessage);

	}
}
