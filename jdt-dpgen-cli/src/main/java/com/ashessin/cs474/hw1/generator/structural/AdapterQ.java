package com.ashessin.cs474.hw1.generator.structural;

import com.ashessin.cs474.hw1.generator.ArgGroup;
import com.ashessin.cs474.hw1.generator.DesignPatternQ;
import com.ashessin.cs474.hw1.generator.DpArrayList;
import com.ashessin.cs474.hw1.generator.DpSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

@CommandLine.Command(name = "adapter", version = "jdt-dpgen 0.1",
		description = "Generates Adapter structural design pattern. " +
					  "Convert the interface of a class into another interface clients expect. Adapter lets classes " +
					  "work together that couldn't otherwise because of incompatible interfaces.",
		mixinStandardHelpOptions = true,
		showDefaultValues = true,
		sortOptions = false
)
public class AdapterQ extends DesignPatternQ {

	private static final Logger log = LoggerFactory.getLogger(AdapterQ.class);
	private static final String PACKAGE_NAME = "com.gof.structural.adapter";

	@CommandLine.ArgGroup(exclusive = false)
	InputGroup inputGroup;

	@CommandLine.Option(order = Integer.MIN_VALUE, required = true, arity = "1", names = {"-p", "--packageName"},
			defaultValue = PACKAGE_NAME)
	String packageName;

	public static void main(String[] args) {
		System.setProperty("picocli.usage.width", "auto");
		int exitCode = new CommandLine(new AdapterQ()).execute(args);
		System.exit(exitCode);
	}

	@Override
	public DpArrayList<DpSource> call() throws Exception {
		String adapterName = InputGroup.adapterName;
		String adapteeName = InputGroup.adapteeName;
		String targetName = InputGroup.targetName;

		// TODO: Add input validations

		log.info("Generating representation for design pattern sources.");
		return new AdapterGen(packageName, adapterName, adapteeName, targetName).invoke();

	}

	static class InputGroup implements ArgGroup {

		private static final String ADAPTER_NAME = "Adapter";
		private static final String ADAPTEE_NAME = "Adaptee";
		private static final String TARGET_NAME = "Target";

		@CommandLine.Parameters(index = "0", paramLabel = "AdapterName",
				description = "The Adapter class adapts interface Adaptee to the Target.",
				defaultValue = ADAPTER_NAME)
		static String adapterName = ADAPTER_NAME;

		@CommandLine.Parameters(index = "1", paramLabel = "AdapteeName",
				description = "The Adaptee class defines an existing interface where adaption will be applied.",
				defaultValue = ADAPTEE_NAME)
		static String adapteeName = ADAPTEE_NAME;

		@CommandLine.Parameters(index = "2", paramLabel = "TargetName",
				description = "The target interface defines the domain-specific interface used by the Client.",
				defaultValue = TARGET_NAME)
		static String targetName = TARGET_NAME;

		private InputGroup() {
		}
	}
}
