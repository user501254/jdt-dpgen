package com.ashessin.cs474.hw1.generator.creational;

import com.ashessin.cs474.hw1.generator.ArgGroup;
import com.ashessin.cs474.hw1.generator.DesignPatternQ;
import com.ashessin.cs474.hw1.generator.DpArrayList;
import com.ashessin.cs474.hw1.generator.DpSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@CommandLine.Command(name = "prototype", version = "jdt-dpgen 0.1",
		description = "Generates Prototype creational design pattern. " +
					  "Specify the kinds of objects to create using a prototypical instance, and create new objects " +
					  "by copying this prototype.",
		mixinStandardHelpOptions = true,
		showDefaultValues = true,
		sortOptions = false
)
public class PrototypeQ extends DesignPatternQ {

	private static final Logger log = LoggerFactory.getLogger(PrototypeQ.class);
	private static final String PACKAGE_NAME = "com.gof.creational.prototype";

	@CommandLine.ArgGroup(exclusive = false)
	InputGroup inputGroup;

	@CommandLine.Option(order = Integer.MIN_VALUE, required = true, arity = "1", names = {"-p", "--packageName"},
			defaultValue = PACKAGE_NAME)
	String packageName;

	public static void main(String[] args) {
		System.setProperty("picocli.usage.width", "auto");
		int exitCode = new CommandLine(new PrototypeQ()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public DpArrayList<DpSource> call() throws Exception {
		String abstractPrototypeName = InputGroup.abstractPrototypeName;
		String concretePrototypeName = InputGroup.concretePrototypeName;
		List<String> properties = Arrays.stream(InputGroup.properties.split(";")).collect(Collectors.toList());

		// TODO: Add input validations

		LinkedHashMap<String, String> propertiesMap = new LinkedHashMap<>(2);
		properties.forEach(propertyString -> {
			String[] property = propertyString.split(",");
			String propertyName = property[1];
			String propertyType = property[0];
			propertiesMap.put(propertyName, propertyType);
		});

		log.info("Generating representation for design pattern sources.");
		return new PrototypeGen(packageName, abstractPrototypeName, concretePrototypeName, propertiesMap).invoke();

	}

	static class InputGroup implements ArgGroup {

		private static final String ABSTRACT_PROTOTYPE_NAME = "Prototype";
		private static final String CONCRETE_PROTOTYPE_NAME = "ConcretePrototype";
		private static final String PROPERTIES = "Object,property1;Object,property2";

		@CommandLine.Parameters(index = "0", paramLabel = "PrototypeName",
				description = "The Prototype interface defines the copy() method.",
				defaultValue = ABSTRACT_PROTOTYPE_NAME)
		static String abstractPrototypeName = ABSTRACT_PROTOTYPE_NAME;

		@CommandLine.Parameters(index = "1", paramLabel = "ConcretePrototypeName",
				description = "The ConcretePrototype implements a Prototype interface and has a copy constructor.",
				defaultValue = CONCRETE_PROTOTYPE_NAME)
		static String concretePrototypeName = CONCRETE_PROTOTYPE_NAME;

		@CommandLine.Parameters(index = "2", paramLabel = "ConcretePrototypeProperties",
				defaultValue = PROPERTIES)
		static String properties = PROPERTIES;

		private InputGroup() {
		}
	}
}
