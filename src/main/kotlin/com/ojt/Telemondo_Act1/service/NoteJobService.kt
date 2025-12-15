package com.ojt.Telemondo_Act1.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.luigivismara.shortuuid.ShortUuid
import com.ojt.Telemondo_Act1.dto.PostNoteDTO
import com.ojt.Telemondo_Act1.dto.PutNoteDTO
import com.ojt.Telemondo_Act1.job.CreateNoteJob
import com.ojt.Telemondo_Act1.job.DeleteNoteJob
import com.ojt.Telemondo_Act1.job.GetNoteJob
import com.ojt.Telemondo_Act1.job.UpdateNoteJob
import com.ojt.Telemondo_Act1.repo.NoteRepository
import org.quartz.*
import org.quartz.DateBuilder.futureDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class NoteJobService(
    @Autowired private val scheduler: Scheduler,
    @Autowired private val noteRepo: NoteRepository,
) {

    //    fun triggerCrudJob(entityId: Long, operation: String) {
    //        val job =
    //            JobBuilder.newJob(NoteJob::class.java)
    //                .withIdentity("CrudJob-$entityId-$operation")
    //                .usingJobData("entityId", entityId.toString())
    //                .usingJobData("operation", operation)
    //                .build()
    //
    //        val trigger =
    //            TriggerBuilder.newTrigger()
    //                .withIdentity("CrudTrigger-$entityId-$operation")
    //                .startNow()
    //                .build()
    //
    //        scheduler.scheduleJob(job, trigger)
    //    }
    //
    //    fun scheduleRecurringJob(intervalSeconds: Int) {
    //        val jobKey = JobKey.jobKey("RecurringJob")
    //        if (!scheduler.checkExists(jobKey)) {
    //            val job = JobBuilder.newJob(NoteJob::class.java).withIdentity(jobKey).build()
    //
    //            val trigger =
    //                TriggerBuilder.newTrigger()
    //                    .withIdentity("RecurringTrigger")
    //                    .startNow()
    //                    .withSchedule(
    //                        SimpleScheduleBuilder.simpleSchedule()
    //                            .withIntervalInSeconds(intervalSeconds)
    //                            .repeatForever()
    //                    )
    //                    .build()
    //
    //            scheduler.scheduleJob(job, trigger)
    //        }
    //    }

    private fun stopJob(jobName: String, jobGroup: String) {
        val triggerKey = TriggerKey.triggerKey(jobName, jobGroup)
        if (scheduler.checkExists(triggerKey)) {
            scheduler.unscheduleJob(triggerKey)
            println("Job $jobName in group $jobGroup has been unscheduled")
        } else {
            println("Trigger $jobName not found")
        }
    }

    private fun deleteJob(jobName: String, jobGroup: String) {
        val jobKey = JobKey.jobKey(jobName, jobGroup)
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey)
            println("Job $jobName deleted from group $jobGroup")
        } else {
            println("Job $jobName does not exist")
        }
    }

    private fun stopAllJobs() {
        scheduler.clear() // Unschedules all triggers
        println("All jobs and triggers have been cleared")
    }

    fun getNoteCountDelayed(delay: Int) {
        val jobName = "getNoteCount-job"
        val jobGroup = "NOTES"

        val jobDetail =
            JobBuilder.newJob(GetNoteJob::class.java).withIdentity(jobName, jobGroup).build()

        val trigger =
            TriggerBuilder.newTrigger()
                .withIdentity("${jobName}-trigger", jobGroup)
                .forJob(jobDetail)
                .withSchedule(
                    SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(delay)
                        .repeatForever()
                )
                .startAt(futureDate(delay, DateBuilder.IntervalUnit.SECOND))
                .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }

    fun deleteGetNoteCount() {
        deleteJob("getNoteCount-job", "NOTES")
    }

    private val objectMapper = jacksonObjectMapper()

    // TODO: Design a format / general function to shorten boilerplate
    fun createNoteDelayed(body: PostNoteDTO) {
        if (body.user.isEmpty()) {
            throw Exception("User is empty")
        }

        val jobName = "createNote-job"
        val jobGroup = "NOTES"

        val jobDetail =
            JobBuilder.newJob(CreateNoteJob::class.java)
                .withIdentity(jobName, jobGroup)
                .usingJobData("body", objectMapper.writeValueAsString(body))
                .build()

        val trigger =
            TriggerBuilder.newTrigger()
                .withIdentity("${jobName}-trigger", jobGroup)
                .forJob(jobDetail)
                .startAt(futureDate(body.delay ?: 5, DateBuilder.IntervalUnit.SECOND))
                .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }

    fun updateNoteDelayed(body: PutNoteDTO) {
        // verify later
        val verify =
            noteRepo.findByIdOrNull(ShortUuid.decode(body.id))
                ?: throw Exception("Note does not exist")

        val jobName = "updateNote-job"
        val jobGroup = "NOTES"

        val jobDetail =
            JobBuilder.newJob(UpdateNoteJob::class.java)
                .withIdentity(jobName, jobGroup)
                .usingJobData("body", objectMapper.writeValueAsString(body))
                .build()

        val trigger =
            TriggerBuilder.newTrigger()
                .withIdentity("${jobName}-trigger", jobGroup)
                .forJob(jobDetail)
                .startAt(futureDate(body.delay ?: 5, DateBuilder.IntervalUnit.SECOND))
                .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }

    fun deleteNoteDelayed(id: String, delay: Int) {
        val jobName = "deleteNote-job"
        val jobGroup = "NOTES"

        val jobDetail =
            JobBuilder.newJob(DeleteNoteJob::class.java)
                .withIdentity(jobName, jobGroup)
                .usingJobData("id", id)
                .build()

        val trigger =
            TriggerBuilder.newTrigger()
                .withIdentity("${jobName}-trigger", jobGroup)
                .forJob(jobDetail)
                .startAt(futureDate(delay, DateBuilder.IntervalUnit.SECOND))
                .build()

        scheduler.scheduleJob(jobDetail, trigger)
    }
}
