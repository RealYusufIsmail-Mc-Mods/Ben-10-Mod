scoreboard players set .check dim_portals 0
 function dim_portals:score_combine
execute if score .check dim_portals matches 1.. at @s run function dim_portals:private/check/to_frame
 function dim_portals:score_reset

scoreboard players add @s[tag=dim_warp] dim_portals 1
execute if score @s dim_portals matches 1.. run scoreboard players reset @s[tag=!dim_warp] dim_portals
execute if score @s dim_portals matches 80..100 at @s run function dim_portals:private/portal/warp
tag @s remove dim_warp