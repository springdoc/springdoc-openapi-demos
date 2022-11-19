package org.springdoc.demo.app4;

import java.util.Arrays;

import org.springdoc.demo.app4.AppNativeConfiguration.AppNativeRuntimeHints;
import org.springdoc.demo.app4.coffee.CoffeeService;
import org.springdoc.demo.app4.employee.EmployeeRepository;

import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

/**
 * @author bnasslahsen
 */
@Configuration
@ImportRuntimeHints(AppNativeRuntimeHints.class)
public class AppNativeConfiguration {

	static Class[] applicationClasses = { org.springdoc.demo.app4.user.User[].class,
			org.springdoc.demo.app4.employee.Employee[].class,
			org.springdoc.demo.app4.coffee.Coffee[].class,
			org.springdoc.demo.app4.coffee.CoffeeOrder[].class,
			EmployeeRepository.class,
			CoffeeService.class
	};

	static class AppNativeRuntimeHints implements RuntimeHintsRegistrar {

		@Override
		public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
			Arrays.stream(applicationClasses).forEach(applicationClass ->
					hints.reflection().registerType(applicationClass,
							(hint) -> hint.withMembers(MemberCategory.DECLARED_FIELDS,
									MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
									MemberCategory.INVOKE_DECLARED_METHODS)));
		}
	}

}
