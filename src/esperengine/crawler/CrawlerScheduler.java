package esperengine.crawler;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
public class CrawlerScheduler
{
//Quartz configuration
	Scheduler scheduler;
	int jobCounter = 0;
	Set<Trigger> triggerSet = new TreeSet<Trigger>(); //using a set to contain all the triggers for the only job
	String jobName, jobGroupName;
	JobDetail job;
	public CrawlerScheduler(String jobName, String jobGroupName) throws SchedulerException
	{
		scheduler = StdSchedulerFactory.getDefaultScheduler();
		this.jobName = jobName;
		this.jobGroupName = jobGroupName;
	}

	public void setJobDetail(Class myJob, Map<String, Object> map) throws SchedulerException
	{
		JobDataMap jMap = new JobDataMap();
		for (String key : map.keySet()) {
			jMap.put(key, map.get(key));
		}
		job = newJob(myJob)
			.withIdentity(jobName, jobGroupName)
			.usingJobData(jMap)
			.build();
	}

	public void setTrigger(String triggerName, String triggerGroup, String condition)
	{
		Trigger trigger1 = newTrigger()
			.withIdentity(triggerName, triggerGroup)
			.withSchedule(cronSchedule(condition))
			.forJob(jobName, jobGroupName)
			.build();
		triggerSet.add(trigger1);
	}

	public void start() throws SchedulerException
	{
		System.out.println("RUNNING!!!");
		scheduler.scheduleJob(job, triggerSet, false);
		scheduler.start();
	}

	public void shutdown() throws SchedulerException
	{
		scheduler.shutdown(true);
	}
}