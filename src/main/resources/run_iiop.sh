#!/bin/bash

#Path in your file system to java directory.
#(You should init CP with your path in terminal)
CP=

cd $CP

javac orlov641p/khai/edu/com/model/*.java orlov641p/khai/edu/com/service/*.java

javac orlov641p/khai/edu/com/controller/lab5iiop/ServerInterface.java

javac orlov641p/khai/edu/com/controller/lab5iiop/ServerImpl.java

rmic -iiop orlov641p.khai.edu.com.controller.lab5iiop.ServerImpl

javac orlov641p/khai/edu/com/controller/lab5iiop/ServerIIOP.java

javac orlov641p/khai/edu/com/controller/lab5iiop/ClientIIOP.java

javac orlov641p/khai/edu/com/controller/lab5iiop/FlightIIOP.java

javac orlov641p/khai/edu/com/controller/lab5iiop/OrderIIOP.java

javac orlov641p/khai/edu/com/controller/lab5iiop/TicketIIOP.java

# Start the ORB daemon
orbd -ORBInitialPort 1050 &
temp=$!

# Run ServerIIOP in the background
java -cp $CP -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory \
     -Djava.naming.provider.url=iiop://localhost:1050 \
     orlov641p.khai.edu.com.controller.lab5iiop.ServerIIOP &


# Run ClientIIOP in a new terminal
gnome-terminal -- bash -c "java -cp $CP -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory \
     -Djava.naming.provider.url=iiop://localhost:1050 \
     orlov641p.khai.edu.com.controller.lab5iiop.ClientIIOP; exec bash"

# Run FlightIIOP, OrderIIOP, TicketIIOP in new terminals
gnome-terminal -- bash -c "java -cp $CP -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory \
     -Djava.naming.provider.url=iiop://localhost:1050 \
     orlov641p.khai.edu.com.controller.lab5iiop.FlightIIOP; exec bash"

gnome-terminal -- bash -c "java -cp $CP -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory \
     -Djava.naming.provider.url=iiop://localhost:1050 \
     orlov641p.khai.edu.com.controller.lab5iiop.OrderIIOP; exec bash"

gnome-terminal -- bash -c "java -cp $CP -Djava.naming.factory.initial=com.sun.jndi.cosnaming.CNCtxFactory \
     -Djava.naming.provider.url=iiop://localhost:1050 \
     orlov641p.khai.edu.com.controller.lab5iiop.TicketIIOP; exec bash"


read -p "Press Enter to terminate the applications..."

# Kill the ORBD process
kill $temp

# Clean up compiled classes
cd /home/kazutaka/IdeaProjects/CrossPlatformProgrammingJava8/src/main
find . -name "*.class" -type f -delete