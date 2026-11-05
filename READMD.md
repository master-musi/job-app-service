# The Job Applications App
A lightweight Spring Boot application designed to track job applications, 
deployed on a Google Cloud Platform (GCP) Compute Engine Free Tier instance (e2-micro).

##  Quick Start / Deployment Guide
### Prerequisites
Java 21 installed on the target machine.

An H2 file-based database located in a ./data/ directory.

1. Build the Application (Locally)
   Package your Spring Boot project into an executable JAR file:

```Bash
mvn clean package
```
This generates the JAR file inside your target/ directory (e.g., job-app-service-1.0.0-SNAPSHOT.jar).

2. Upload to the Server
   Upload your .jar file and your H2 database file (jobappdata.mv.db) to your server:

Place the database file in a ./data subdirectory relative to where you run the application (e.g., ~/data/jobappdata.mv.db).

## Application Management Commands

### Start the Application
Run the application in the background using nohup so it continues running after you close your SSH terminal session:

```Bash
nohup java -jar job-app-service-1.0.0-SNAPSHOT.jar > app.log 2>&1 &
```
### Stop the Application
Find and terminate the running Java process:

```Bash
kill $(pgrep -f job-app-service)
```
## Logging & Maintenance

### Maintenance
```aiignore
sudo apt update && sudo apt upgrade -y
```

### Checking Logs
View real-time logs:

```Bash
tail -f app.log
```
View the last 50 lines of logs:

```Bash
tail -n 50 app.log
```
Managing Log Growth
To prevent app.log from continuously growing and filling up your disk space:

Use Linux logrotate (System-Level)
If you prefer handling it via the Linux OS, you can configure a logrotate rule so the system 
automatically compresses and deletes old logs.

Create a logrotate configuration file for your app:

```Bash
sudo nano /etc/logrotate.d/job-app
```
Paste the following configuration (assuming your log is saved in your home directory or a specific folder):

```Plaintext
/home/chealex761/app.log {
size 10M
rotate 5
compress
missingok
notifempty
copytruncate
}
```
Save and exit (Ctrl+O, Enter, Ctrl+X).

What this does: Every time logrotate runs (usually daily via cron), 
it checks if app.log is over 10 MB. If it is, it rotates it, 
keeps the last 5 copies compressed, and truncates the main log file.

### Control the Application

1. Control the size of the logs
```aiignore
chealex761@job-app:~$ ls -alh
total 54M
drwx------ 5 chealex761 chealex761 4.0K Nov  5 21:15 .
drwxr-xr-x 3 root       root       4.0K Nov  5 19:09 ..
-rw------- 1 chealex761 chealex761   73 Nov  5 19:46 .bash_history
-rw-r--r-- 1 chealex761 chealex761  220 Jul 12 16:58 .bash_logout
-rw-r--r-- 1 chealex761 chealex761 3.5K Jul 12 16:58 .bashrc
drwx------ 3 chealex761 chealex761 4.0K Nov  5 21:15 .local
-rw-r--r-- 1 chealex761 chealex761  807 Jul 12 16:58 .profile
drwx------ 2 chealex761 chealex761 4.0K Nov  5 19:31 .ssh
-rw-rw-r-- 1 chealex761 chealex761 8.9K Nov  5 20:48 app.log
drwxrwxr-x 2 chealex761 chealex761 4.0K Nov  5 20:45 data
-rw-rw-r-- 1 chealex761 chealex761  54M Nov  5 20:36 job-app-service-1.0.0-SNAPSHOT.jar
```

2. Control the memory
```aiignore
chealex761@job-app:~$ free -m
               total        used        free      shared  buff/cache   available
Mem:             964         733         105           0         261         231
Swap:              0           0           0
chealex761@job-app:~$ pwd
```

3. Control the logs
```aiignore
tail -f app.log
```

4. Testing the log rotation
```aiignore
chealex761@job-app:~$ sudo logrotate -fv /etc/logrotate.d/job-app
reading config file /etc/logrotate.d/job-app
Creating stub state file: /var/lib/logrotate/status
acquired lock on state file /var/lib/logrotate/status
Reading state from file: /var/lib/logrotate/status
Allocating hash table for state file, size 64 entries

Handling 1 logs

rotating pattern: /home/chealex761/app.log forced from command line empty log files are not rotated, (5 rotations), old logs are removed
considering log /home/chealex761/app.log
Creating new state
  Now: 2026-11-05 21:53
  Last rotated at 2026-11-05 21:00
  log needs rotating
rotating log /home/chealex761/app.log, log->rotateCount is 5
dateext suffix '-20261105'
glob pattern '-[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]'
renaming /home/chealex761/app.log.5.gz to /home/chealex761/app.log.6.gz (rotatecount 5, logstart 1, i 5), 
old log /home/chealex761/app.log.5.gz does not exist
renaming /home/chealex761/app.log.4.gz to /home/chealex761/app.log.5.gz (rotatecount 5, logstart 1, i 4), 
old log /home/chealex761/app.log.4.gz does not exist
renaming /home/chealex761/app.log.3.gz to /home/chealex761/app.log.4.gz (rotatecount 5, logstart 1, i 3), 
old log /home/chealex761/app.log.3.gz does not exist
renaming /home/chealex761/app.log.2.gz to /home/chealex761/app.log.3.gz (rotatecount 5, logstart 1, i 2), 
old log /home/chealex761/app.log.2.gz does not exist
renaming /home/chealex761/app.log.1.gz to /home/chealex761/app.log.2.gz (rotatecount 5, logstart 1, i 1), 
old log /home/chealex761/app.log.1.gz does not exist
log /home/chealex761/app.log.6.gz doesn't exist -- won't try to dispose of it
copying /home/chealex761/app.log to /home/chealex761/app.log.1
truncating /home/chealex761/app.log
compressing log with: /bin/gzip
chealex761@job-app:~$ 
chealex761@job-app:~$ 
chealex761@job-app:~$ 
chealex761@job-app:~$ ls -alh
total 54M
drwx------ 5 chealex761 chealex761 4.0K Nov  5 21:53 .
drwxr-xr-x 3 root       root       4.0K Nov  5 19:09 ..
-rw------- 1 chealex761 chealex761   73 Nov  5 19:46 .bash_history
-rw-r--r-- 1 chealex761 chealex761  220 Jul 12 16:58 .bash_logout
-rw-r--r-- 1 chealex761 chealex761 3.5K Jul 12 16:58 .bashrc
drwx------ 3 chealex761 chealex761 4.0K Nov  5 21:15 .local
-rw-r--r-- 1 chealex761 chealex761  807 Jul 12 16:58 .profile
drwx------ 2 chealex761 chealex761 4.0K Nov  5 19:31 .ssh
-rw-rw-r-- 1 chealex761 chealex761    0 Nov  5 21:53 app.log
-rw-rw-r-- 1 chealex761 chealex761 3.6K Nov  5 21:31 app.log.1.gz
drwxrwxr-x 2 chealex761 chealex761 4.0K Nov  5 20:45 data
-rw-rw-r-- 1 chealex761 chealex761  54M Nov  5 20:36 job-app-service-1.0.0-SNAPSHOT.jar
chealex761@job-app:~$ 
```

## Accessing the App
Once running, the application can be accessed via:

Web UI: http://<YOUR_VM_EXTERNAL_IP>:8082/applications

H2 Console: http://<YOUR_VM_EXTERNAL_IP>:8082/h2-console (if enabled)