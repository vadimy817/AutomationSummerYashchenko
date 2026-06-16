package ua.ukma.service;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Набір швидких тестів (Fast Suite)")
@SelectClasses(PasswordValidatorTest.class)
@IncludeTags("fast")
public class FastTestSuite {
}
