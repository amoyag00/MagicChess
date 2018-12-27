# MagicChess
Code used to develop a voice controlled chess, similar to Harry Potter's chess.
Library used to perform the communications with arduino using java: https://github.com/HirdayGupta/Java-Arduino-Communication-Library

# Getting Started
 ## Raspberry Pi3 configuration 
 In order to get the bluetooth server working in the raspberry follow the instructions explained by levifuksz here https://blog.iamlevi.net/2017/05/control-raspberry-pi-android-bluetooth/
 If you want to change the name of your bluetooth device you must created a file called /etc/machine-info that should have the following content:
`PRETTY_HOSTNAME=device-name`
Depending on which OS you have chosen you may have to modify the bluetooth configuration file, located in
`/etc/bluetooth/main.conf`
Uncomment `DiscoverableTimeout = 0` and `PairableTimeout = 0` if you want your raspberry bluetooth to be always visible and pairable.

