package magicchess.magicchess;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class BluetoothDevices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_devices);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayList<BluetoothDevice> devicesList=new ArrayList<BluetoothDevice>();
        devicesList.addAll(pairedDevices);
        final Controller controller =Controller.getInstance();
        controller.setDevicesList(devicesList);
        List<String> s = new ArrayList<String>();

        LinearLayout ll = (LinearLayout)findViewById(R.id.pairedDevices);
        LinearLayout.LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        TextView title=new TextView(this);
        title.setText("Dispositivos emparejados");
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.parseColor("#000000"));
        title.setTextSize(20);
        lp.setMargins(0,20,0,20);
        ll.addView(title,lp);
        int id=0;
        for(BluetoothDevice bt : pairedDevices){
            s.add(bt.getName());
            Log.i("chess",bt.getName());
            Button pairedDevice=new Button(this);
            pairedDevice.setId(id++);
            pairedDevice.setBackgroundColor(Color.parseColor("#ffffff"));
            pairedDevice.setText(bt.getName());
            lp.gravity=Gravity.CENTER;
            pairedDevice.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bluetooth, 0, 0, 0);

            pairedDevice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(BluetoothDevices.this, ChessPassword.class);
                    controller.selectDevice(v.getId());
                    startActivity(myIntent);
                }
            });

            ll.addView(pairedDevice, lp);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bluetooth_devices, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
