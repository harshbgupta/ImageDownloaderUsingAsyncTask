package corporation.hell.imagedownloaderusingasynctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button download = (Button) findViewById(R.id.button);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dont Forget to write permission in manifest file
                new ImageDownloder(MainActivity.this).execute("https://s3-us-west-1.amazonaws.com/powr/defaults/image-slider2.jpg");
            }
        });
    }
}
