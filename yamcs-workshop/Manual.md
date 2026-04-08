# AcubeSAT Workshop Services Manual 
This document holds some definitions regarding the parameters as well as the Telemetry and Telecommand packets used in the Spacecraft Operations' Workshop. 

## Definitions 

``` 
A Telemetry packet is referred to as TM
```

```
A Telecommand packet is referred to as TC
```

Each packet belongs to a specific **Service** (reflected by the first digit of the identification of the packet, eg. TM[3,1] is a Telemtry packet of Service [01]). All packets of the same Service concern a specific operation of the mission and are related with one another. This means that a Telemtry packet of a Service sent by the Spacecraft can be the "answer" to a Telecommand packet of the same Service sent by the Operator.

## Parameters

The following are the parameters used in the workshop mission:

1. **AngleX** : 
> The Angle X of the satellite taking as reference point the exact position of the ground station on Earth. The appropriate position of the satellite (detrmined by the angles parameters) permits the charging of the battery. When the battery level is low the satellite needs to be rotated in order to get the max output from the solar panels.
2. **AngleY**:
> The Angle Y of the satellite taking as reference point the exact position of the ground station on Earth. The appropriate position of the satellite (detrmined by the angles parameters) permits the charging of the battery. When the battery level is low the satellite needs to be rotated in order to get the max output from the solar panels.
3. **BatteryLevel**: 
>The charging level of the battery used to power the satellite. 
4. **BatteryChargingSpeed**:
> The speed that the battery used to power the satellite is charging
5. **TemperatureLevel**:
> The level (value) of the satellites temperature. The temperature needs to be at specific levels in order for the satellite to be able to change mode. 
6. **TemperatureChangingSpeed**:
> The speed that the satellite's temperature is changing. Default is 1 grade every 60 seconds.
7. **SolarPanelOutput**:
> This parameter gives information regarding the solar panel output which are used to power the satellite when the battery level falls below the 25%.
8. **HeaterPercentage**:
> The percentage of the heaters usage. The heaters have different levels of usage determining the respective impact they are going to have on the temperature. If their use percentage is at 100% then the temperature rises by 2 grades every 60 seconds. The heaters' percentage must be set to a specific value in order to rise and then maintain the temperature to a specific value. In this case the Temperature changing speed needs to be taken into consideration. The heaters can only be turned on in Nominal mode.
9. **TimeInScienceMode**:
> This parameter holds the total time in seconds that the satellite is in Science Mode. It counts time starting from the first entry into Scince mode, after a transition from the Nominal mode. The satellite needs to be at least 60 seconds in Science mode in order to be able to execute a photo capture of the Payload container and finish the operation.
10. **PhotoCaptured**:
> This is a boolean parameter (True/False) revealing whether the capture of the photo of the Payload container was successful or not. A photo can be captured only in Science mode.

## Modes
### Safe Mode
>The main goal of Safe mode is to ensure the survival of the spacecraft minimizing the power consumption. The Spacecraft gets in this mode when a critical condition occurs and it can't function properly. One such condition is the drop of the **battery level below the 25%**. In this case the Spacecraft enters the Safe Mode. 

>In order to **transit** from Safe to Nominal mode the Operator has to check the battery voltage level and if the errors that previously lead to entering into Safe mode are resolved he sends the appropriate Telecommand to enter the Nominal Mode.
### Nominal Mode 
>The main purpose of the Nominal mode is to conduct the nominal mission operations such as the generation of reports of the Spacecraft's parameters, the communication between the Ground Station and the Spacecraft. The Sapcecraft can enter this mode only if the battery level is greater than the 25%.

>The spacecraft stays in this mode and performs the aforementioned nominal operations until the Operators send
a TC to enter the Science mode. An exit to Safe mode can also be triggered in a contingency situation (battery level < 25%).

### Science Mode
>The Science mode constitutes the final Operational mode and comprises the parameters for the Experimental Procedure. It is accessible only via TC transitioning from Nominal mode by a twofold conditional check, **Temperature** & **Battery Voltage**. To enter the Science mode from the Nominal mode the Temperature needs to be between 36 and 38 degrees Celsius and the battery level greater than the 25%. 

>Completion of the experimental procedure (capture of a photo of the Payload experiment) can only happen when the Spacecraft is at least a minute in Science mode and is followed by exit to Nominal mode. An exit command to Safe mode is triggered under contingency conditions (battery level < 25%).

## Telememtry and Telecommand Packets

The following are the Telemetry and Telecommand packets used. 

1. Service [**01**]:
    ```
    TC [1,1] perform an are-you-alive connection test :  

    We send this Telecommand only to investigate if the Spacecraft is alive and functional. 
    ```
    ```
     TM [1,2] are-you-alive connection test report :
    
    The reception of this Telemetry packets indicates that the Spacecraft is alive and functional.
    ```
    
2. Service [**02**]:
    ```
     TC [2,1] request report battery related parameter values:
    
    With this Telecommand we request the current values of the battery level and battery charging speed parameters from the Spacecraft.
    ```
    ```
     TM [2,2] battery related parameter value report:

    With this Tememetry packet we receive the current values of the battery level and battery charging speed parameters from the Spacecraft.
    ```
    ```
     TC [2,3] request report temperature related parameter values:

    With this Telecommand we request the current values of the temeperature level and temeperature changing speed parameters from the Spacecraft.
    ```
    ```
     TM [2,4] temperature related parameter value report:

    With this Tememetry packet we receive the current values of the temeperature level and temeperature changing speed parameters from the Spacecraft.
    ```
    ```
     TC [2,5] request report battery angle parameter values:
    
    With this Telecommand we request the current values of the angle x and y parameters from the Spacecraft.
    ```
    ```
     TM [2,6] angle related parameter value report:

    With this Tememetry packet we receive the current values of the angle x and y parameters from the Spacecraft.
    ```
    ```
     TC [2,7] request report solar-panel related parameter values:
    
    With this Telecommand we request the current value of the solar panel output parameter from the Spacecraft.
    ```
    ```
     TM [2,8] solar-panel related parameter value report:

    With this Tememetry packet we receive the current value of the solar panel output parameter from the Spacecraft.
    ```
3. Service [**03**]:
    ```
     TC [3,1] request report heater percentage parameter value:

    With this Telecommand we request the current value of the heater parameter (percentage) from the Spacecraft.
    ```
    ```
     TM [3,2] heater percentage parameter value report:

    With this Tememetry packet we receive the current value of the heater parameter (percentage) from the Spacecraft.
    ```
    ```
     TC [3,3] set heater percentage parameter value:

    With this Telecommand we set the value (percentage) of the heater parameter to a new one.
    ```
    ```
     TC [3,4] set angle parameter values:
    
    With this Telecommand we set the values of the angles parameter to new ones.
    ```
4. Service [**04**]:
    ```
     TC [4,1] capture photo request:

    With this Telecommand we request to capture a photo of the Payload. The capture can only be executed if the Spacecraft is in Science Mode.
    ```
    ```
     TM [4,2] captured photo report:
    
    With this Telemetry packet we get informed regarding the status of the capture of the photo. If the PhotoCaptured paramter is True that means that the photo has been captured and the Operation is complete.
    ```
    ```
     TC [4,3] request time in science mode report:
    
    With this Telecommand we request the time (in seconds) that the Spacecraft is in Science Mode.
    ```
    ```
     TM [4,4] time in science mode report:
    
    With this Telemetry packet we receive the total time (in seconds) that the Spacecraft is in Science Mode.
    ```
5. Service [**05**]:
    ```
     TC [5,1] request mode status report:

    With this Telecommand we request the status (nominal, safe, science) of the Spacecraft's mode.
    ```
    ```
     TM [5,2] mode status report:

    With this Telemetry packet we receive the status (nominal, safe, science) of the Spacecraft's current mode.
    ```
    ```
     TC [5,3] change mode:

    With this Telecommand we change the current mode of the Spacecraft to a new one.
    ```




