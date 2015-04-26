package mmbuw.com.brokenproject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;


public class BrokenActivity extends Activity {
    public static String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    private EditText auntEdit;
    private Button auntButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broken);
        auntEdit = (EditText)findViewById(R.id.edittext);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.broken, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void brokenFunction(View view) {
        //I was once, perhaps, possibly a functioning function
        try {
            auntButton = (Button) findViewById(R.id.button);
            if (auntEdit.getText().toString().equals("Timmy")) {
                System.out.println("Timmy fixed a bug!");
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error accrued!", Toast.LENGTH_SHORT).show();
        }


        System.out.println("If this appears in your console, you fixed a bug.");
        Intent intent = new Intent(this,AnotherBrokenActivity.class);
        String message = "This string will be passed to the new activity";
        intent.putExtra("EXTRA_MESSAGE", message);
        startActivity(intent);
    }

}
