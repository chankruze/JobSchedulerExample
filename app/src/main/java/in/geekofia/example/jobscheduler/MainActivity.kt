package `in`.geekofia.example.jobscheduler

import `in`.geekofia.example.jobscheduler.databinding.ActivityMainBinding
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Logger.addLogAdapter(AndroidLogAdapter())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnScheduleJob.setOnClickListener {
                scheduleJob()
            }
            btnCancelJob.setOnClickListener {
                cancelJob()
            }
        }

    }

    private fun scheduleJob() {
        val componentName = ComponentName(this, ExampleJobService::class.java)
        val jobInfo = JobInfo.Builder(123, componentName)
            .setRequiresCharging(false)
            .setPersisted(true)
            .setPeriodic(15 * 60 * 1000)
            .build()

        val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val res = jobScheduler.schedule(jobInfo)

        if (res == JobScheduler.RESULT_SUCCESS)
            Logger.d("Job scheduled")
        else
            Logger.d("Job scheduling failed")
    }

    private fun cancelJob() {
        val jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        jobScheduler.cancel(123)
        Logger.d("Job cancelled")
    }
}