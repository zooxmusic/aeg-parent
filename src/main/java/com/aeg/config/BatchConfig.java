package com.aeg.config;

import com.aeg.ims.batch.tasklet.FtpGetRemoteFilesTasklet;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

import java.io.File;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    @Qualifier("jobRepository")
    private JobRepository jobRepository;

    @Autowired
    @Qualifier("ftpSessionFactory")
    private SessionFactory ftpSessionFactory;


    @Bean
    @Scope(value="step")
    public FtpGetRemoteFilesTasklet myFtpGetRemoteFilesTasklet()
    {
        FtpGetRemoteFilesTasklet  ftpTasklet = new FtpGetRemoteFilesTasklet();
        ftpTasklet.setRetryIfNotFound(true);
        ftpTasklet.setDownloadFileAttempts(3);
        ftpTasklet.setRetryIntervalMilliseconds(10000);
        ftpTasklet.setFileNamePattern("README");
        //ftpTasklet.setFileNamePattern("TestFile");
        ftpTasklet.setRemoteDirectory("/");
        ftpTasklet.setLocalDirectory(new File(System.getProperty("java.io.tmpdir")));
        ftpTasklet.setSessionFactory(ftpSessionFactory);

        return ftpTasklet;
    }

    @Bean
    public SessionFactory ftpSessionFactory()
    {
        DefaultFtpSessionFactory ftpSessionFactory = new DefaultFtpSessionFactory();
        ftpSessionFactory.setHost("ftp.gnu.org");
        ftpSessionFactory.setClientMode(0);
        ftpSessionFactory.setFileType(0);
        ftpSessionFactory.setPort(21);
        ftpSessionFactory.setUsername("anonymous");
        ftpSessionFactory.setPassword("anonymous");

        return ftpSessionFactory;
    }

    @Bean
    public SimpleJobLauncher jobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        return jobLauncher;
    }

}
