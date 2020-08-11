# RISK version 1
- Yuting Zhang
- Yingcong Fang
- Mingxiang Dai
## Prerequisites
- Gradle 6.0 or above
- Java 1.8 or above
## Configure

1. Change port  
Go to `shared/configuration/port.txt` and change the port. The default port is 12345.
2. Change host  
Go to `shared/configuration/host.txt` and change the host. The default host is "vcm-12521.vm.duke.edu".
 
## Play the game
### 1. Run the server
#### 1.1 Start server  
Go to the root directory of project then run `gradle run-server`
#### 1.2 Choose the number of players
After starting the server, it will let you choose how many players will be in the game. Choose a integer between 2 and 5.
#### 1.3 Wait for player to connect server
No farther action is needed in server end.
### 2. Join the game
#### 2.1 Start client
Go to the root directory of project then run `gradle run-client`
#### 2.2 Choose your name
After starting the client, it will let you choose your name. Type the name you want.
### 3. Gaming!
After all players inputs their name, the server will sent map to all players and the game will start.

**<font color="red">Note:</font> each player should have her/his unique name.**
## For developers
Design report and sprint plan report are avaliable in `design_report.md` and `sprint_plan.md`



