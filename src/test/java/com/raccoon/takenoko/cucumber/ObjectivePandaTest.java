package com.raccoon.takenoko.cucumber;


import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com/raccoon/takenoko/cucumber/ObjectivePanda.feature")
public class ObjectivePandaTest {
}
