# Sprint Planning
## Sprint 1
- Deadline: 2020-3-8
- Manager: Yuting Zhang

```mermaid
gantt

section Mingxiang Dai
Server+Client.basic                 : done,      1_1, 2020-03-02, 2d
World.basic                         : done,    	 1_2, after 1_1, 2d
Server+Client.oneToOneConnection    : done,    1_3, after 1_2, 2d

section Yuting Zhang
Territory.basic                     : done,      2_1, 2020-03-02, 2d
Player.basic                        : done,      2_2, after 2_1, 1d
Territory.addSomeMethods            : done,    2_3, after 2_2, 3d

section Yingcong Fang
Dice                   				 : done  ,    3_1, 2020-03-02, 1d
Game.frame             				 : done,    3_2, after 3_1, 3d
Order.basic + Game.MoveRelatedupdate            : done,    3_3, after 3_2, 2d
    
```

## Sprint 2

- Deadline: 2020-3-14
- Manager: Yingcong Fang
```mermaid
gantt

section Mingxiang Dai
InputHelper              			    : done,    1_1, 2020-03-09, 1d
Comunicator + multiThread.initial       : done,    1_2, after 1_1, 2d
Client.handleDifferentNumbersPlayer     : done,    1_3, after 1_2, 2d

section Yuting Zhang
Player.sendOrderOut         			: done,   2_1, 2020-03-09, 2d
World.checkHasPathBetween			    : done,   2_2, after 2_1, 2d
OrderList.getOneRoundOrder	  			: done, 	2_3, after 2_2, 1d


section Yingcong Fang
Game.AttckRelatedUpdate                 : done,    3_1, 2020-03-09, 2d
Game.hasWinner                          : done,    3_2, after 3_1,  1d
Order.getOneOrder					    : done,      3_3, after 3_2,  2d   
```


## Sprint 3

- Deadline: 2020-3-21
- Manager: Mingxiang Dai

```mermaid
gantt

section Mingxiang Dai
FetchValidOrder       : done,    1_1, 2020-03-15, 1d
Multithread.communication   			: done,    1_2, after 1_1, 2d
playerInitializer 						: done, 1_3, after 1_2, 2d

section Yuting Zhang
Territory.BFS                           : done,     2_1, 2020-03-16, 1d
WorldInitialHelper              	    : done,     2_2, after 2_1, 2d
Territory.checkConnection               : done,     2_3, after 2_2, 2d

section Yingcong Fang
Game.FindByName                		     : done,    3_1, 2020-03-15, 2d
displayMessagesInServerandClient         : done,    3_2, after 3_1, 2d
World.addOneUnitPerRound + checkAttack	 : done, 	3_3, after 3_2, 2d
    
```