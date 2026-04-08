# AcubeSAT YAMCS Training Instance



## Introduction

This repository is the codebase for the training instance of YAMCS. Before you start your training read some [General Information](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/YAMCS:-General-Information) about the role of YAMCS in the AcubeSAT project.

You also need to be familiar with git, in order to handle changes and additions to the project. [Here](https://gitlab.com/groups/acubesat/ops/-/wikis/General-Git-Training) is a Git General Training that will help you.

Finally, [here](https://gitlab.com/acubesat/ops/yamcs-instance/-/wikis/0.-Introduction-and-Contents) you can find the YAMCS Developer Guide, which contains detailed information and guidance for developing a project using YAMCS.

## Overview of the Training

The purpose of this training is to familiarize you with YAMCS software. There is a [List](https://gitlab.com/acubesat/ops/yamcs-training/-/wikis/New-member-YAMCS-Training-Tasks) containing all training tasks and training instructions. The tasks, as mentioned, need to be done by the order they are defined.

If using VS Code , make sure you have installed the following addons: 
- XML Language Support by Red Hat
- Some kind of formatter such as Prettier

These will help you for syntax highlighting, error detection , code formatting  and will generally make your life easier.


## Definitions for **yamcs-training** instance

### Running YAMCS

[Here](https://yamcs.org/getting-started) you can find prerequisites, basic commands and information to get things started with YAMCS.

To simply start the main YAMCS instance, run:

    mvn yamcs:run

View the YAMCS web interface by visiting http://localhost:8090

### Testing

In order to send packets to YAMCS, run the included Python script:

    python simulator.py

This script reads the testdata.ccsds file and sends packets at 1 Hz over UDP to YAMCS. To see information regarding the incoming packets and updates of the values of the parameters go to the "Packets" and "Parameters" pages, in the Telemetry section, on YAMCS web interface.

If there is a need to send specific packets, then [ecss-services](https://gitlab.com/acubesat/obc/ecss-services) is required. To install, run:

    git clone https://gitlab.com/acubesat/obc/ecss-services.git --recurse-submodules

After modifying `main.cpp`, the generated packets will be sent to port 10015 that YAMCS listens to.

### Communication Protocol

Currently the communication is established with UDP in the yamcs-training project. But during the training you will be asked to change it to TCP. 

### Mission Database

The Mission Database describes the telemetry and commands that are processed by YAMCS. It tells YAMCS how to decode packets or how to encode telecommands.

The .xml files (located in yamcs-instance/src/main/yamcs/mdb) contain all the information regarding the parameters, the containers and the commands used in AcubeSAT YAMCS Instance.

- The dt.xml file contains all **ParameterTypes** for Telemetry and **ArgumentTypes** for Telecommanding.

- The rest of the .xml files are used to define parameters, containers and commands for the mission. The .xml file in which a paremeter or container or command is defined, reflects its use. More specifically:
  - **pus.xml**: contains parameters, containers and commands, which implement the principal services that offer great control over reading/writing parameters from the Ground Station.
  - **xtce.xml**: contains the ADCS and OBC parameters that will be used during the Environmental Campaign.
