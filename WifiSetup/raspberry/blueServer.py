#!/usr/bin/python

import logging
import logging.handlers
import argparse
import sys
import os
import time
import subprocess

from bluetooth import *

# Code modified from levifuksz original python bluetooth server https://github.com/levifuksz/raspibt
# Main loop
def main():
    chessPassword="magicchess"
    correctPassword=0
    # Setup logging
    #setup_logging()

    # We need to wait until Bluetooth init is done
    time.sleep(5)

    # Make device visible
    os.system("hciconfig hci0 piscan")

    # Create a new server socket using RFCOMM protocol
    server_sock = BluetoothSocket(RFCOMM)
    # Bind to any port
    server_sock.bind(("", PORT_ANY))
    # Start listening
    server_sock.listen(1)

    # Get the port the server socket is listening
    port = server_sock.getsockname()[1]

    # The service UUID to advertise
    uuid = "7be1fcb3-5776-42fb-91fd-2ee7b5bbb86d"

    # Start advertising the service
    advertise_service(server_sock, "RaspiBtSrv",
                       service_id=uuid,
                       service_classes=[uuid, SERIAL_PORT_CLASS],
                       profiles=[SERIAL_PORT_PROFILE])

    # These are the operations the service supports
    # Feel free to add more
    operations = ["ping", "example"]

    # Main Bluetooth server loop
    client_sock = None

    

    while True:
        try:
            print "Waiting for connection on RFCOMM channel %d" % port
            # This will block until we get a new connection
            client_sock, client_info = server_sock.accept()

            print "Accepted connection from ", client_info

            # Read the data sent by the client
            data = client_sock.recv(1024)
            if len(data) == 0:
                break
            data = data.rstrip()
            print "Received [%s]" % data

            # Read the chess Password
            if data == chessPassword:
                response = "ok"
                client_sock.send(response)
            elif data == "ping":
                response = "msg:Pong"
                client_sock.send(response)
                client_sock.close()
            elif data == "example":
                response = "msg:This is an example"
                client_sock.send(response)
                client_sock.close()
            else:
                response="wrong password"
                client_sock.send(response)
                client_sock.close()
                continue
                
            
            # Read the SSID and password of the Wi-fi network
            data = client_sock.recv(1024)
            data = data.rstrip()
            ssid = data.split("&")[0]
            password = data.split("&")[1]
            registerNetwork(ssid,password)
            response="ok"
            # Setup wi-fi  connection via script
            client_sock.send(response)
            print "Sent back [%s]" % response
            client_sock.close()

        except IOError:
            pass

        except KeyboardInterrupt:

            if client_sock is not None:
                client_sock.close()

            server_sock.close()

            print "Server going down"
            break

def registerNetwork(ssid, password):
    print ssid
    print password
    number=subprocess.check_output(["wpa_cli","add_network"])
    number=number.split("\n")[1]
    print number
    print subprocess.check_output(['wpa_cli','set_network', number, 'ssid', '"'+ssid+'"'])
    print subprocess.check_output(['wpa_cli','set_network', number, 'psk', '"'+password+'"'])
    print subprocess.check_output(["wpa_cli","enable_network", number])
    print subprocess.check_output(["wpa_cli","save","config"]);
    print subprocess.check_output(["wpa_cli","-i", "wlan0", "reconfigure"]);
main()

