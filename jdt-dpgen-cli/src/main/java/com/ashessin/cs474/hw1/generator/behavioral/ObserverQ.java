package com.ashessin.cs474.hw1.generator.behavioral;

import com.ashessin.cs474.hw1.generator.ArgGroup;
import com.ashessin.cs474.hw1.generator.DesignPatternQ;
import com.ashessin.cs474.hw1.generator.DpArrayList;
import com.ashessin.cs474.hw1.generator.DpSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@CommandLine.Command(name = "observer", version = "jdt-dpgen 0.1",
		description = "Generates Observer behavioral design pattern. " +
					  "Define a one-to-many dependency between objects so that when one object changes state, all " +
					  "its dependents are notified and updated automatically.",
		mixinStandardHelpOptions = true,
		showDefaultValues = true,
		sortOptions = false
)
public class ObserverQ extends DesignPatternQ {

	private static final Logger log = LoggerFactory.getLogger(ObserverQ.class);
	private static final String PACKAGE_NAME = "com.gof.behavioral.observer";

	@CommandLine.ArgGroup(exclusive = false)
	InputGroup inputGroup;

	@CommandLine.Option(order = Integer.MIN_VALUE, required = true, arity = "1", names = {"-p", "--packageName"},
			defaultValue = PACKAGE_NAME)
	String packageName;

	public static void main(String[] args) {
		System.setProperty("picocli.usage.width", "auto");
		int exitCode = new CommandLine(new ObserverQ()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public DpArrayList<DpSource> call() throws Exception {
		String observerName = InputGroup.observerName;
		List<String> concreteObserverNames = Arrays.stream(InputGroup.concreteObserverNames.split(","))
				.distinct().collect(Collectors.toList());
		String subjectName = InputGroup.subjectName;
		String concreteSubjectNames = InputGroup.concreteSubjectName;

		// TODO: Add input validations

		log.info("Generating representation for design pattern sources.");
		return new ObserverGen(packageName, observerName, concreteObserverNames,
				subjectName, concreteSubjectNames).invoke();
	}

	static class InputGroup implements ArgGroup {

		private static final String OBSERVER_NAME = "Observer";
		private static final String CONCRETE_OBSERVER_NAMES = "Observer1,Observer2";
		private static final String SUBJECT_NAME = "Subject";
		private static final String CONCRETE_SUBJECT_NAME = "ConcreteSubject";

		@CommandLine.Parameters(index = "2", paramLabel = "ObserverName",
				description = "The Observer defines an updating interface for objects which should be notified " +
							  "of changes in a subject.",
				defaultValue = OBSERVER_NAME)
		static String observerName = OBSERVER_NAME;

		@CommandLine.Parameters(index = "3", paramLabel = "ConcreteObserverNames",
				description = "The ConcreteObserver maintains a reference to a ConcreteSubject object, stores the " +
							  "state that should stay consistent with the subject's and implements the Observer " +
							  "updating interface to keep its state consistent with the subject's.",
				defaultValue = CONCRETE_OBSERVER_NAMES)
		static String concreteObserverNames = CONCRETE_OBSERVER_NAMES;

		@CommandLine.Parameters(index = "0", paramLabel = "SubjectName",
				description = "The Subject knows its observers.",
				defaultValue = SUBJECT_NAME)
		static String subjectName = SUBJECT_NAME;

		@CommandLine.Parameters(index = "1", paramLabel = "ConcreteSubjectNames",
				description = "The ConcreteSubject stores the state of interest to ConcreteObserver objects and " +
							  "sends notifications to its observers when its state changes.",
				defaultValue = CONCRETE_SUBJECT_NAME)
		static String concreteSubjectName = CONCRETE_SUBJECT_NAME;

		private InputGroup() {
		}
	}
}
