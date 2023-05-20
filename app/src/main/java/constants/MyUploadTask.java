package constants;

import android.os.AsyncTask;

import java.io.File;

public class MyUploadTask extends AsyncTask<Void, Void, Boolean> {
    File file;
    String url;
    public MyUploadTask(File f, String url){
        this.file = f;
        this.url = url;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //call upload video method from here
        return false;
    }

}
