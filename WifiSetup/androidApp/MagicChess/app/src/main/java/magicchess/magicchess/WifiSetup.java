package magicchess.magicchess;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WifiSetup extends AppCompatActivity {
    private OutputStream outputStream;
    private InputStream inputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_setup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.connectionOK)).setVisibility(View.INVISIBLE);

    }

    public void setupWifi(View view) {
        String ssid= ((TextView)findViewById(R.id.ssid)).getText().toString();
        String wifiPassword= ((TextView)findViewById(R.id.wifiPassword)).getText().toString();
        String message=ssid + "&" +wifiPassword;
        BluetoothSocket socket = null;
        String response="";
        int length=0;
        byte[] buffer = new byte[1024];

        try {
            socket = Controller.getInstance().getSocket();
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            outputStream.write(message.getBytes());
            //read response
            length = inputStream.read(buffer);
            response = new String(buffer, 0, length);
            if(response.equals("ok")){
                ((TextView) findViewById(R.id.connectionOK)).setVisibility(View.VISIBLE);
            }else{
                ((TextView) findViewById(R.id.connectionOK)).setVisibility(View.INVISIBLE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
