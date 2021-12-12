package com.github.skrethel.simple.config.retrofit;

import com.github.skrethel.simple.config.retrofit.constraint.DefaultSchemaConstraintValidator;
import com.github.skrethel.simple.config.retrofit.exception.GeneratorException;
import com.github.skrethel.simple.config.retrofit.exception.RetrofitException;
import com.github.skrethel.simple.config.retrofit.exception.ValidatorException;
import com.github.skrethel.simple.config.retrofit.io.ConfigFileSource;
import com.github.skrethel.simple.config.retrofit.io.ConfigFileWriter;
import com.github.skrethel.simple.config.retrofit.io.ConfigWriter;
import com.github.skrethel.simple.config.retrofit.io.SchemaFileSource;
import com.github.skrethel.simple.config.retrofit.io.SchemaFileWriter;
import com.github.skrethel.simple.config.retrofit.workers.Generator;
import com.github.skrethel.simple.config.retrofit.workers.Retrofitter;
import com.github.skrethel.simple.config.retrofit.workers.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.ByteArrayOutputStream;


public class SimpleConfigRetrofit {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConfigRetrofit.class);

	@Option(name = "-r", usage = "retrofit mode", forbids = {"-m", "-k", "-v"}, depends = {"-s", "-c", "-o"})
	private boolean retrofitMode = false;

	@Option(name = "-m", usage = "generate schema mode", forbids = {"-r", "-k", "-v"}, depends = {"-c", "-o"})
	private boolean generateSchemaMode = false;

	@Option(name = "-k", usage = "generate config mode", forbids = {"-r", "-m", "-v"}, depends = {"-s", "-o"})
	private boolean generateConfigMode = false;

	@Option(name = "-v", usage = "validate config mode", forbids = {"-r", "-m", "-k"}, depends = {"-s", "-c"})
	private boolean validateMode = false;

	@Option(name = "-s", usage = "schema file", metaVar = "path to schema file")
	private String schemaFile;

	@Option(name = "-c", usage = "config file", metaVar = "path to config file")
	private String configFile;

	@Option(name = "-o", usage = "output file - depending on mode either schema or config file", metaVar = "path to output file")
	private String outputFile;

	@Option(name = "-d", usage = "verbose output")
	private boolean verbose = false;

	@Option(name = "-h", usage = "help", help = true, aliases = "--help")
	private boolean help = false;

	public static void main(String[] args) {
		int status = new SimpleConfigRetrofit().run(args);
		System.exit(status);
	}

	public int run(String[] args) {
		LogConfig.defaultConfig();
		CmdLineParser parser = new CmdLineParser(this);
		try {
			parser.parseArgument(args);
			if (verbose) {
				LogConfig.verboseConfig();
				LOGGER.debug("Enabling verbose mode");
			}
			if (help) {
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				parser.printUsage(outputStream);
				LOGGER.info("Help: \n" + outputStream.toString());
			}
			if (retrofitMode) {
				Retrofitter retrofitter = new Retrofitter();
				ConfigFileSource configSource = new ConfigFileSource(configFile);
				SchemaFileSource schemaSource = new SchemaFileSource(schemaFile);
				ConfigWriter configWriter = new ConfigFileWriter(outputFile);
				retrofitter.retrofit(configSource, schemaSource, configWriter, new DefaultSchemaConstraintValidator());
			} else if (generateConfigMode) {
				Generator generator = new Generator();
				SchemaFileSource schemaSource = new SchemaFileSource(schemaFile);
				ConfigWriter configWriter = new ConfigFileWriter(outputFile);
				generator.generate(schemaSource, configWriter);
			} else if (generateSchemaMode) {
				Generator generator = new Generator();
				ConfigFileSource configSource = new ConfigFileSource(configFile);
				SchemaFileWriter schemaWriter = new SchemaFileWriter(outputFile);
				generator.generate(configSource, schemaWriter);
			} else if (validateMode) {
				Validator validator = new Validator();
				ConfigFileSource configSource = new ConfigFileSource(configFile);
				SchemaFileSource schemaSource = new SchemaFileSource(schemaFile);
				validator.validate(configSource, schemaSource, new DefaultSchemaConstraintValidator());
			} else {
				LOGGER.error("Please specify valid mode");
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				parser.printUsage(outputStream);
				LOGGER.error("Help: \n" + outputStream.toString());
				return 3;
			}
			return 0;
		} catch (CmdLineException e) {
			LOGGER.error(e.getMessage());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			parser.printUsage(outputStream);
			LOGGER.error("Help: \n" + outputStream.toString());
			return 1;
		} catch (ValidatorException | GeneratorException | RetrofitException e) {
			LOGGER.error(e.getMessage(), e);
			return 2;
		}
	}
}
