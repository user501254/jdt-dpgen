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

@CommandLine.Command(name = "state", version = "jdt-dpgen 0.1",
		description = "Generates State behavioral design pattern. " +
					  "Allow an object to alter its behavior when its internal state changes. The object will " +
					  "appear to change its class",
		mixinStandardHelpOptions = true,
		showDefaultValues = true,
		sortOptions = false
)
public class StateQ extends DesignPatternQ {

	private static final Logger log = LoggerFactory.getLogger(StateQ.class);
	private static final String PACKAGE_NAME = "com.gof.behavioral.state";

	@CommandLine.ArgGroup(exclusive = false)
	InputGroup inputGroup;

	@CommandLine.Option(order = Integer.MIN_VALUE, required = true, arity = "1", names = {"-p", "--packageName"},
			defaultValue = PACKAGE_NAME)
	String packageName;

	public static void main(String[] args) {
		System.setProperty("picocli.usage.width", "auto");
		int exitCode = new CommandLine(new StateQ()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public DpArrayList<DpSource> call() throws Exception {
		String stateName = InputGroup.stateName;
		List<String> concreteStateNames = Arrays.stream(InputGroup.concreteStateNames.split(","))
				.distinct().collect(Collectors.toList());
		String contextName = InputGroup.contextName;

		// TODO: Add input validations

		log.info("Generating representation for design pattern sources.");
		return new StateGen(packageName, stateName, concreteStateNames, contextName).invoke();

	}

	static class InputGroup implements ArgGroup {

		private static final String STATE_NAME = "State";
		private static final String CONCRETE_STATE_NAMES = "ConcreteState1,ConcreteState2,ConcreteState3";
		private static final String CONTEXT_NAME = "Context";


		@CommandLine.Parameters(index = "0", paramLabel = "StateName",
				description = "The State defines an interface for encapsulating the behavior associated with a " +
							  "particular state of the Context.",
				defaultValue = STATE_NAME)
		static String stateName = STATE_NAME;

		@CommandLine.Parameters(index = "1", paramLabel = "ConcreteStateNames",
				description = "The ConcreteState implements a behavior associated with the state of the Context.",
				defaultValue = CONCRETE_STATE_NAMES)
		static String concreteStateNames = CONCRETE_STATE_NAMES;

		@CommandLine.Parameters(index = "2", paramLabel = "ContextName",
				description = "The Context class maintains an instance of a ConcreteState subclass which defines " +
							  "the current state.",
				defaultValue = CONTEXT_NAME)
		static String contextName = CONTEXT_NAME;

		private InputGroup() {
		}
	}
}