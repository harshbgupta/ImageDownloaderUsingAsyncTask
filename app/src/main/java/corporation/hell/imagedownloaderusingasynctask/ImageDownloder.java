package corporation.hell.imagedownloaderusingasynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Harsh on 05-11-2016.
 */
public class ImageDownloder extends AsyncTask<String, Integer,String> {

    private static final String TAG = "ImageDownloder-->";
    ProgressDialog progressDialog;
    String fileName = null;
    ImageView image;
    Context context;

    public ImageDownloder(Context context) {
        this.context = context;
    }

    /***
     *
     */
    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog((context));
        progressDialog.setTitle("Download in Progress...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    /***
     * for downloading Image
     * @param params path of Image
     * @return Output as String
     */
    @Override
    protected String doInBackground(String... params) {
        String path = params[0];
        int fileLength = 0;
        try {
            URL url = new URL(path);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            fileLength = urlConnection.getContentLength();
            File newFolder = new File("sdcard/hell_corporation");
            if(!(newFolder.exists())){
                newFolder.mkdir();
            }
            fileName = System.currentTimeMillis()+"";
            File inputFile = new File(newFolder,fileName+".jpeg");
            InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
            byte[] buffer  = new byte [1024];
            long total = 0;
            int count ;
            OutputStream outputStream = new FileOutputStream(inputFile);
            while ((count = inputStream.read(buffer)) != -1) {
                total += count;
                outputStream.write(buffer,0, count);
                int progress = (int)total*100/fileLength;
                publishProgress(progress);
            }
            inputStream.close();
            outputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Download Complete.";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        progressDialog.hide();
        Toast.makeText(context,result,Toast.LENGTH_LONG).show();

        //Scanning image for Gallery Manually
        MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {

            @Override
            public void onScanCompleted(String path, Uri uri) {
                // TODO Auto-generated method stub

            }
        });
    }
}
