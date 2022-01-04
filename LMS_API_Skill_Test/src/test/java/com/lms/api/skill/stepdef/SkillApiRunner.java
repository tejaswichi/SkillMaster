package com.lms.api.skill.stepdef;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
			features = {"src/test/resources/Feature/"},
			glue = {"com.lms.api.skill.stepdef"},
			//tags="@check",
			monochrome = true,
			plugin = {"pretty","html:target/HtmlReports/report.html",
					"json:target/JSONReports/report.json",
			"junit:target/JunitReports/report.xml"}
			)

public class SkillApiRunner {

}
