package pro.operations;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pro.dao.Book;
import pro.readers.ConfigReader;
import pro.writers.ConfigWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private ConfigReader configReader;

	@Autowired
	private ConfigWriter configWriter;


	Logger log = LoggerFactory.getLogger(BatchConfig.class);

	@Bean
	public Step step1() throws MalformedURLException {
		log.trace("Performing Step 1");
		return stepBuilderFactory.get("Step 1").<Book, Book>chunk(2).reader(configReader.readerXml())
				.writer(configWriter.xml2dbjdbcWriter()).build();
	}

	@Bean
	public Job job() throws MalformedURLException {
		log.trace("Starting the Job....");
		return jobBuilderFactory.get("Job 1").incrementer(new RunIdIncrementer()).start(step1()).build();
	}

}
