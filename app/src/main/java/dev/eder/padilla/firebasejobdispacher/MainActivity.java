package dev.eder.padilla.firebasejobdispacher;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

public class MainActivity extends AppCompatActivity{

    private static String TAG = MainActivity.class.getSimpleName();
    private FirebaseJobDispatcher mJobDispatcher;
    Button mBuStartJob,
           mBuStopJob;

    final int startJobEmoji = 0x1F680;

    final int stopJobEmoji = 0x270B;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBuStartJob = findViewById(R.id.button_start_job);
        mBuStopJob = findViewById(R.id.button_stop_job);
        mBuStartJob.setText(getString(R.string.start_job)+getEmojiByUnicode(startJobEmoji));
        mBuStopJob.setText(getString(R.string.stop_job)+getEmojiByUnicode(stopJobEmoji));
        mJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));


    }
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

    public void startJob(View view){
        Job job = mJobDispatcher.newJobBuilder().
                setService(MyService.class).
                setLifetime(Lifetime.FOREVER).
                setRecurring(true).
                setTag(TAG).
                setTrigger(Trigger.executionWindow(0,10)).
                setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL).
                setReplaceCurrent(false).
                setConstraints(Constraint.ON_ANY_NETWORK).
                build();
        mJobDispatcher.mustSchedule(job);
        Toast.makeText(getApplicationContext(),"Job Scheduled..",Toast.LENGTH_SHORT).show();

    }

    public void stopJob(View view){
        mJobDispatcher.cancel(TAG);
        Toast.makeText(getApplicationContext(),"Job Cancelled..",Toast.LENGTH_SHORT).show();
    }


}
