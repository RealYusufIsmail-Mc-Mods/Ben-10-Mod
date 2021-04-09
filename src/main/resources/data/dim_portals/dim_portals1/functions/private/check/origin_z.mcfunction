scoreboard players set .check dim_portals 0
scoreboard players set .failed dim_portals 0

function dim_portals:activation
execute unless entity @s[tag=dimp_activate] run scoreboard players set .failed dim_portals 1
execute if entity @s[tag=dimp_activate] unless entity @e[tag=portal,dx=0] run summon item_frame ~ ~ ~ {Facing:0b,Tags:["portal","z","init"],Invulnerable:1b,Invisible:1b,Fixed:1b,Item:{}}
tag @s remove dimp_activate

execute as @e[tag=portal,tag=init] run function dim_portals:setup_type

scoreboard players set .invalid dim_portals 0
### prevent alt portals in alt dimensions ###
execute unless predicate dim_portals:overworld run scoreboard players set .invalid dim_portals 1
execute as @e[tag=portal,tag=init] run function dim_portals:dimension_override
execute if score .invalid dim_portals matches 1 run scoreboard players set .failed dim_portals 1
execute if score .invalid dim_portals matches 1 run kill @e[tag=portal,tag=init]

tag @e[tag=portal] remove init