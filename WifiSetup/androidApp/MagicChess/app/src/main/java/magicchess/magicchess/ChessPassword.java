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
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChessPassword extends AppCompatActivity {
    private EditText password;
    private OutputStream outputStream;
    private InputStream inputStream;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chess_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        password=(EditText)findViewById(R.id.textPassword);
        TextView wrongPassword = (TextView) findViewById(R.id.wrongPassword);
        wrongPassword.setVisibility(View.INVISIBLE);

    }


    public void sendPassword(View view) {
        Controller controller=Controller.getInstance();
        BluetoothDevice device=controller.getDevice();
        ParcelUuid [] uuids = device.getUuids();
        BluetoothSocket socket = null;
        String response="";
        int length=0;
        byte[] buffer = new byte[1024];
        TextView wrongPassword = (TextView) findViewById(R.id.wrongPassword);

        try {
            socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
            controller.setBTSocket(socket);
            socket.connect();
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            outputStream.write(this.password.getText().toString().getBytes());
            //read response
            length = inputStream.read(buffer);
            response = new String(buffer, 0, length);
            if(response.equals("ok")){
                Intent myIntent = new Intent( ChessPassword.this, WifiSetup.class);
                startActivity(myIntent);
                wrongPassword.setVisibility(View.INVISIBLE);
            }else{
                wrongPassword.setVisibility(View.VISIBLE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
