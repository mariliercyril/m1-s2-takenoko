$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Login/LoginTest.feature");
formatter.feature({
  "line": 1,
  "name": "User Login",
  "description": "User should be able to login using valid credentials",
  "id": "user-login",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "line": 4,
  "name": "Testing login with valid credentials",
  "description": "",
  "id": "user-login;testing-login-with-valid-credentials",
  "type": "scenario_outline",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 5,
  "name": "I open login page",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I enter username as \"\u003cusername\u003e\" and password as \"\u003cpassword\u003e\"",
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "I submit login page",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "I redirect to user home page",
  "keyword": "Then "
});
formatter.examples({
  "line": 10,
  "name": "",
  "description": "",
  "id": "user-login;testing-login-with-valid-credentials;",
  "rows": [
    {
      "cells": [
        "username",
        "password"
      ],
      "line": 11,
      "id": "user-login;testing-login-with-valid-credentials;;1"
    },
    {
      "cells": [
        "oualid",
        "benazzouz"
      ],
      "line": 12,
      "id": "user-login;testing-login-with-valid-credentials;;2"
    },
    {
      "cells": [
        "admin",
        "admin"
      ],
      "line": 13,
      "id": "user-login;testing-login-with-valid-credentials;;3"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 290632,
  "status": "passed"
});
formatter.scenario({
  "line": 12,
  "name": "Testing login with valid credentials",
  "description": "",
  "id": "user-login;testing-login-with-valid-credentials;;2",
  "type": "scenario",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 5,
  "name": "I open login page",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I enter username as \"oualid\" and password as \"benazzouz\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "I submit login page",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "I redirect to user home page",
  "keyword": "Then "
});
formatter.match({
  "location": "stepDefinition.i_open_login_page()"
});
formatter.result({
  "duration": 256674423,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "oualid",
      "offset": 21
    },
    {
      "val": "benazzouz",
      "offset": 46
    }
  ],
  "location": "stepDefinition.i_enter_username_as_and_password_as(String,String)"
});
formatter.result({
  "duration": 2593079,
  "status": "passed"
});
formatter.match({
  "location": "stepDefinition.i_submit_login_page()"
});
formatter.result({
  "duration": 122039,
  "status": "passed"
});
formatter.match({
  "location": "stepDefinition.i_redirect_to_user_home_page()"
});
formatter.result({
  "duration": 127040,
  "status": "passed"
});
formatter.after({
  "duration": 132501,
  "status": "passed"
});
formatter.before({
  "duration": 134150,
  "status": "passed"
});
formatter.scenario({
  "line": 13,
  "name": "Testing login with valid credentials",
  "description": "",
  "id": "user-login;testing-login-with-valid-credentials;;3",
  "type": "scenario",
  "keyword": "Scenario Outline"
});
formatter.step({
  "line": 5,
  "name": "I open login page",
  "keyword": "Given "
});
formatter.step({
  "line": 6,
  "name": "I enter username as \"admin\" and password as \"admin\"",
  "matchedColumns": [
    0,
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 7,
  "name": "I submit login page",
  "keyword": "And "
});
formatter.step({
  "line": 8,
  "name": "I redirect to user home page",
  "keyword": "Then "
});
formatter.match({
  "location": "stepDefinition.i_open_login_page()"
});
formatter.result({
  "duration": 212890,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "admin",
      "offset": 21
    },
    {
      "val": "admin",
      "offset": 45
    }
  ],
  "location": "stepDefinition.i_enter_username_as_and_password_as(String,String)"
});
formatter.result({
  "duration": 240371,
  "status": "passed"
});
formatter.match({
  "location": "stepDefinition.i_submit_login_page()"
});
formatter.result({
  "duration": 83439,
  "status": "passed"
});
formatter.match({
  "location": "stepDefinition.i_redirect_to_user_home_page()"
});
formatter.result({
  "duration": 122914,
  "status": "passed"
});
formatter.after({
  "duration": 119194,
  "status": "passed"
});
formatter.before({
  "duration": 180543,
  "status": "passed"
});
formatter.scenario({
  "line": 15,
  "name": "Testing login with invalid credentials",
  "description": "",
  "id": "user-login;testing-login-with-invalid-credentials",
  "type": "scenario",
  "keyword": "Scenario"
});
formatter.step({
  "line": 16,
  "name": "I open login page",
  "keyword": "Given "
});
formatter.step({
  "line": 17,
  "name": "I enter username as \"invalid\" and password as \"invalid\"",
  "keyword": "When "
});
formatter.step({
  "line": 18,
  "name": "I submit login page",
  "keyword": "And "
});
formatter.step({
  "line": 19,
  "name": "I am on login page",
  "keyword": "Then "
});
formatter.match({
  "location": "stepDefinition.i_open_login_page()"
});
formatter.result({
  "duration": 136137,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "invalid",
      "offset": 21
    },
    {
      "val": "invalid",
      "offset": 47
    }
  ],
  "location": "stepDefinition.i_enter_username_as_and_password_as(String,String)"
});
formatter.result({
  "duration": 197742,
  "status": "passed"
});
formatter.match({
  "location": "stepDefinition.i_submit_login_page()"
});
formatter.result({
  "duration": 101040,
  "status": "passed"
});
formatter.match({
  "location": "stepDefinition.i_am_on_login_page()"
});
formatter.result({
  "duration": 110725,
  "status": "passed"
});
formatter.after({
  "duration": 68808,
  "status": "passed"
});
});