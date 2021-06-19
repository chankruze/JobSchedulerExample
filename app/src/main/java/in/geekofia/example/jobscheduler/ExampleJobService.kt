package `in`.geekofia.example.jobscheduler

import android.app.job.JobParameters
import android.app.job.JobService
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class ExampleJobService : JobService() {
    private var jobCancelled = false

    override fun onStartJob(params: JobParameters?): Boolean {
        Logger.d("Job started")
        doBackgroundWork(params)
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Logger.d("Job cancelled before completion")
        jobCancelled = true
        return false
    }

    private fun doBackgroundWork(params: JobParameters?) {
        Thread(Runnable {
            for (i in 0..9) {
                Logger.d("run: $i")
                if (jobCancelled) {
                    return@Runnable
                }
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            Logger.d("Job finished")
            jobFinished(params, false)
        }).start()
    }
}