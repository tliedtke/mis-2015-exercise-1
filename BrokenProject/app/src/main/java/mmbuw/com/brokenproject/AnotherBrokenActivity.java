package mmbuw.com.brokenproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.webkit.WebView;
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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

import mmbuw.com.brokenproject.R;

public class AnotherBrokenActivity extends Activity {
    private String message;
    private Activity view = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_broken);

        Intent intent = getIntent();
        message = intent.getStringExtra(BrokenActivity.EXTRA_MESSAGE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        //What happens here? What is this? It feels like this is wrong.
        //Maybe the weird programmer who wrote this forgot to do something?

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.another_broken, menu);
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

    public void fetchHTML(View view) throws IOException {

        EditText ext_url = (EditText) findViewById(R.id.txt_url);
        //According to the exercise, you will need to add a button and an EditText first.
        //Then, use this function to call your http requests
        //Following hints:
        //Android might not enjoy if you do Networking on the main thread, but who am I to judge?
        //An app might not be allowed to access the internet without the right (*hinthint*) permissions
        //Below, you find a staring point for your HTTP Requests - this code is in the wrong place and lacks the allowance to do what it wants
        //It will crash if you just un-comment it.


        GetHttpResponse getHttpResponse = new GetHttpResponse();
        getHttpResponse.execute(ext_url.getText().toString());

    /*

            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(new HttpGet("http://lmgtfy.com/?q=android+ansync+task"));
            StatusLine status = response.getStatusLine();
            if (status.getStatusCode() == HttpStatus.SC_OK) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                response.getEntity().writeTo(outStream);
                String responseAsString = outStream.toString();
                System.out.println("Response string: " + responseAsString);
            } else {
                //Well, this didn't work.
                response.getEntity().getContent().close();
                throw new IOException(status.getReasonPhrase());
            }

    * */


    }

    private class GetHttpResponse extends AsyncTask<String, Integer, String> {
        HttpResponse httpResponse = null;
        String responseAsString = null;
        String err = "";

        @Override
        protected String doInBackground(String... urls) {

            try {
                String url = urls[0];

               /* HttpURLConnection con =
                        (HttpURLConnection) new URL(url).openConnection();
                con.setRequestMethod("HEAD");
                if (!(con.getResponseCode()   == HttpURLConnection.HTTP_OK)) {
                    err = "url is not available!";
                    return "Err";
                }*/

                HttpClient client = new DefaultHttpClient();

                httpResponse = client.execute(new HttpGet(url));

                StatusLine status = httpResponse.getStatusLine();

                if (status.getStatusCode() == HttpStatus.SC_OK) {
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    httpResponse.getEntity().writeTo(outStream);
                    responseAsString = outStream.toString();

                } else {
                    //Well, this didn't work.
                    httpResponse.getEntity().getContent().close();
                    throw new IOException(status.getReasonPhrase());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                err = "wrong formed url!";
            } catch (UnknownHostException e) {
                e.printStackTrace();
                err = "Unknown Host!";
            } catch (IllegalStateException e) {
                e.printStackTrace();
                err = "IllegalStateException";
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                err = "IllegalArgumentException";
            } catch (Exception e) {
                e.printStackTrace();
                err = "Error accrued!";
            }

            return responseAsString;
        }

        @Override
        protected void onPostExecute(String result) {
            //textView.setText(result);
            // System.out.println("Response string: " + responseAsString);
            WebView browser = (WebView) findViewById(R.id.wv_content);
            browser.getSettings().setJavaScriptEnabled(true);

            browser.loadData(responseAsString, "text/html", "UTF-8");

            if (!err.equals("")) {
                Toast.makeText(view, err, Toast.LENGTH_SHORT).show();
            }


        }

    }
}
