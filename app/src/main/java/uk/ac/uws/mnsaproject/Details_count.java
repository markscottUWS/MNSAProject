package uk.ac.uws.mnsaproject;




import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Details_count extends Activity {

    final static String urlGoogleChart
            = "http://chart.apis.google.com/chart";
    final static String urlp3Api
            = "?cht=p3&chs=400x150&chl=A|B|C&chd=t:";

    EditText inputA, inputB, inputC;
    Button generate;
    ImageView pieChart;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details_count);
        inputA = (EditText)findViewById(R.id.adata);
        inputB = (EditText)findViewById(R.id.bdata);
        inputC = (EditText)findViewById(R.id.cdata);
        generate = (Button)findViewById(R.id.generate);
        pieChart = (ImageView)findViewById(R.id.pie);
        generate.setOnClickListener(generateOnClickListener);
    }

    Button.OnClickListener generateOnClickListener
            = new Button.OnClickListener(){

        @Override
        public void onClick(View arg0) {
            // TODO Auto-generated method stub
            String A = inputA.getText().toString();
            String B = inputB.getText().toString();
            String C = inputC.getText().toString();
            String urlRqs3DPie = urlGoogleChart
                    + urlp3Api
                    + A + "," + B + "," + C;

            Bitmap bm3DPie = loadChart(urlRqs3DPie);
            if(bm3DPie == null){
                Toast.makeText(Details_count.this,
                        "Problem in loading 3D Pie Chart",
                        Toast.LENGTH_LONG).show();
            }else{
                pieChart.setImageBitmap(bm3DPie);
            }
        }};

    private Bitmap loadChart(String urlRqs){
        Bitmap bm = null;
        InputStream inputStream = null;

        try {
            inputStream = OpenHttpConnection(urlRqs);
            bm = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bm;
    }

    private InputStream OpenHttpConnection(String strURL) throws IOException{
        InputStream is = null;
        URL url = new URL(strURL);
        URLConnection urlConnection = url.openConnection();

        try{
            HttpURLConnection httpConn = (HttpURLConnection)urlConnection;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                is = httpConn.getInputStream();
            }
        }catch (Exception ex){
        }

        return is;
    }
}
