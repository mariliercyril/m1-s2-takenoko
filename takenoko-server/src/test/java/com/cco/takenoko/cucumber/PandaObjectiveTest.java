package com.cco.takenoko.cucumber;


import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;

import cucumber.api.junit.Cucumber;

/**
 * The {@code PandaObjectiveTest} class allows JUnit to run the Cucumber tests of panda objective.
 * 
 * @author obenazzouz
 * @author cmarilier
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", plugin = "pretty")
public class PandaObjectiveTest {}
