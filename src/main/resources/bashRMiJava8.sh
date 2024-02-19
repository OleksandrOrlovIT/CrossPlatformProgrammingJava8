#!/bin/bash

#Path to main directory from your parent directory
cd

javac java/orlov641p/khai/edu/com/controller/lab6idl/*.java java/orlov641p/khai/edu/com/model/*.java java/orlov641p/khai/edu/com/service/*.java

orbd -ORBInitialPort 1050&
temp=$!

cd /home/kazutaka/IdeaProjects/CrossPlatformProgrammingJava8/src/main/java

java orlov641p.khai.edu.com.controller.lab6idl.MainServerIDL -ORBInitialPort 1050 -ORBInitialHost localhost &

sleep 2 # Wait for the server to start

java orlov641p.khai.edu.com.controller.lab6idl.ClientIDL -ORBInitialPort 1050 -ORBInitialHost localhost &
java orlov641p.khai.edu.com.controller.lab6idl.OrderIDL -ORBInitialPort 1050 -ORBInitialHost localhost &
java orlov641p.khai.edu.com.controller.lab6idl.FlightIDL -ORBInitialPort 1050 -ORBInitialHost localhost &
java orlov641p.khai.edu.com.controller.lab6idl.TicketIDL -ORBInitialPort 1050 -ORBInitialHost localhost &

# You can add more clients or services if needed

read -p "Press Enter to kill the processes..."

# Kill all the Java processes
pkill -f 'java orlov641p.khai.edu.com.controller.lab6idl'

kill $temp
cd /home/kazutaka/IdeaProjects/CrossPlatformProgrammingJava8/src/main
find . -name "*.class" -type f -delete