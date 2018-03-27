package dev.eder.padilla.firebasejobdispacher;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by ederpadilla on 26/03/18.
 */

public class MyService extends JobService {
    final String TAG = MyService.class.getSimpleName();
    private BackGroundClass mBackGroundClass;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(final JobParameters job) {
        Log.e(TAG,"on Start JOB");
        mBackGroundClass = new BackGroundClass(){
            @Override
            protected void onPostExecute(String s) {
                Toast.makeText(getApplicationContext(),"Message from background task "+s,Toast.LENGTH_SHORT).show();
                jobFinished(job,true);
            }
        };
        mBackGroundClass.execute();
        return true;//if you run youre service in backgound its better to return true
    }



    @Override
    public boolean onStopJob(JobParameters job) {
        Log.e(TAG,"on Stop JOB");
        return true;
    }
    public static class BackGroundClass extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {
            return "Hello from do in background";
        }
    }
}
