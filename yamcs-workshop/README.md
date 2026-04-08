# AcubeSAT YAMCS Instance Workshop

This repository holds the source code of the YAMCS application used for the Spacecraft Operations Workshop. 

The primary **Mission's Database** is stored at YAMCS server, in which **Telemetry** data can be archived and then processed using the YAMCS web interface. The operator can also send **Telecommands** that are stored at the YAMCS database.

YAMCS includes a web interface which provides quick access and control over many of its features. The web interface runs on port 8090 and integrates with the security system of YAMCS.


## Running YAMCS

[Here](https://yamcs.org/getting-started) you can find prerequisites, basic commands and information to get things started with YAMCS.

To simply start the main YAMCS instance, run:

    mvn yamcs:run

View the YAMCS web interface by visiting http://localhost:8090

## Telemetry

To start pushing CCSDS packets into YAMCS, run the included Python script:

    python simulator.py

This script opens the packets.raw file and sends packets at 1 Hz over TCP to YAMCS. To see information regarding the incoming packets and updates of the values of the parameters go to the "Packets" and "Parameters" pages, in the Telemetry section, on YAMCS web interface. 

## Telecommanding

This project defines a few example Telecommands. Telecommands can be sent through the "Commanding" section on YAMCS web interface.

## Data-Links

In yamcs.Workshop.yaml file (located in yamcs-workshop/src/main/yamcs/etc), two Data-Links are implemented sending and receiving data in different ports. Every time a packet gets sent or received, the count of the respective data-link is increased.

* Telemetry Data-Link 
    * "Spacecraft", receiving data through port 10015

* Telecommanding Data-Link
    * "Virtual Antenna", sending data at port 10025

Packets are sent from the Operator to the Spacecraft through the **Spacecraft** Data-Link. Packets, originated from the Spacecraft, are received by the Operator through the Virtual Antenna Data-Link.





