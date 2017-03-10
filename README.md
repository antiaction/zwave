antiaction-zwave
================

Java Z-Wave library

A small library communicate with a Z-Wave controller.

## Usage ##

Use as third party library.
Also working on a webapp as a user interface to a Z-Wave network.

## History ##

### version 0.2.0-SNAPSHOT ###

Started on a new version after I discovered the Z-Wave API documentation has been made public!

* MultiLevelSensor command class checked against specifications.
* SensorType class completely rewritten and updated with all the correct types and scales.
* ConfigurationValue moved to separate class.
* ThermostatSetpointValue moved to separate class.
* Renamed SensorBinary and SensorMultiLevel classes to BinarySensor and MultiLevelSensor classes.
* Added some more official contants so the implemented classes also have corresponding constants classes.
* BinarySwitch, BinarySensor and MultiLevelSwitch checked against specifications.
* Added Clock command
* Added Configuration command V1.
* Added Protection command V1.
* Added ThermostatSetpoint command.
* Basic command checked against specifications.
* Battery command checked against specifications.
* ManufacturerSpecific command V1 checked against specifications.
* Version command checked against specifications.
* WakeUp command checked against specifications.

### version 0.1.0-SNAPSHOT ###
Released: 2017-02-16

A simple implementation based on various unofficial information found on the net.
