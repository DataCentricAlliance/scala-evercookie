Evercookie Scala Spray backend

=====

Spray is a suite of scala libraries for building and consuming RESTful web services on top of Akka: lightweight, asynchronous, non-blocking, actor-based, testable
Evercookie is a Javascript API that produces extremely persistent cookies in a browser. It is written in JavaScript and additionally uses a SWF (Flash) object for the Local Shared Objects and, originally, PHPs for the server-side generation of cached PNGs and ETags.

This backend port original PHP scripts to Spray

You need to install [sbt 0.13.8](http://www.scala-sbt.org/download.html) to build this project

And now you can start to use it by many ways:

* Install it to your server as deb package (or even rpm, docker or windows package, [see here](http://www.scala-sbt.org/sbt-native-packager/formats/)):
    
    After execution `sbt clean debian:packageBin` you can find deb file in `target/` folder
    
    Now you can install it in your debian/ubuntu environment with `dpkg -i <deb>`
    
    After that you can edit properties (bind address, port, evercookie paths, cookie names, etc) in `/etc/scala-evercookie/application.conf`
* Run it as runnable jar
    
    After execution `sbt clean assembly` you can find jar file in `target/scala-<scala-version>/` folder
    
    Run it with `java -jar scala-evercookie.jar`!
* Run it from sbt:
    
    Just execute `sbt clean run`
* Run it using [JSVC](http://commons.apache.org/proper/commons-daemon/jsvc.html):
     
     `net.facetz.evercookie.Runner` class implements also `org.apache.commons.daemon.Daemon`. Run it as you want!
     
 Before run service you need create log directory by yourself `mkdir /var/log/scala-evercookie/` 
 and give all rights: `chmod 777 /var/log/scala-evercookie/` 