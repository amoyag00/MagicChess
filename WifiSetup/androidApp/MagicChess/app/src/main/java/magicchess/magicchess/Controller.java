package magicchess.magicchess;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

public class Controller {

    private ArrayList<BluetoothDevice> pairedDevices;
    private BluetoothDevice selectedDevice;
    private static Controller instance;
    BluetoothSocket socket;

    public static Controller getInstance(){
        if(instance==null){
            instance=new Controller();
        }
        return instance;
    }


    public void setBTSocket(BluetoothSocket socket){
        this.socket=socket;
    }

    public BluetoothSocket getSocket() {
        return socket;
    }

    public void setDevicesList(ArrayList<BluetoothDevice> pairedDevices){
        this.pairedDevices=pairedDevices;
    }

    public void selectDevice(int id){
        this.selectedDevice=pairedDevices.get(id);
        Log.i("meme: ",String.valueOf(id));
    }

    public BluetoothDevice getDevice(){
        return this.selectedDevice;
    }
}
