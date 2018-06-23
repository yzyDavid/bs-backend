package bs.services;

import bs.entities.LogEntity;
import bs.entities.UserEntity;
import bs.repositories.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * @author yzy
 */
@Service
public class LogService {
    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public LogEntity getLogEntityOfTodayByUser(UserEntity user) {
        String email = user.getEmail();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        Date date = today.getTime();

        if (logRepository.existsByEmailAndDate(email, date)) {
            return logRepository.getByEmailAndDate(email, date);
        }
        LogEntity log = new LogEntity();
        log.setEmail(email);
        log.setDate(date);
        log.setReachTarget(false);
        log.setStudyCount(0);
        logRepository.save(log);
        return log;
    }

    public long getCheckedDaysByUser(UserEntity user) {
        String email = user.getEmail();
        return logRepository.getAllByEmail(email).size();
    }
}
