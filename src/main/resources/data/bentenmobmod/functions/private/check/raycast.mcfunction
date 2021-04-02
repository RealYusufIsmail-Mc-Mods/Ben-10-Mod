scoreboard players remove .range dim_portals 1
execute if block ~ ~ ~ #dim_portals:portal_activator run function dim_portals:private/check/portal
execute if score .range dim_portals matches 1.. unless block ~ ~ ~ #dim_portals:portal_activator positioned ^ ^ ^0.25 run function dim_portals:private/check/raycast