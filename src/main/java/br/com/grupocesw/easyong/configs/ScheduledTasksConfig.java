package br.com.grupocesw.easyong.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableScheduling
public class ScheduledTasksConfig {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasksConfig.class);

    /*
        Spring cron expression for every day 00:00 am
        Cron Pattern: Second, minute, hour, day of month, month, day(s) of week
    */
    @Scheduled(cron = "0 0 0 * * *", zone = "America/Sao_Paulo")
    @CacheEvict(value = {"ngos", "faqs", "socialCauses", "cities"}, allEntries = true)
    public void clearCacheSchedule() {
        log.info("Clean Cache {}", LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}
