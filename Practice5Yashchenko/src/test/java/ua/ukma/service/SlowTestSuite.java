package ua.ukma.service;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Набір складних та динамічних тестів (Slow Suite)")
@SelectClasses(PasswordValidatorTest.class)
@IncludeTags("slow")
public class SlowTestSuite {
}
