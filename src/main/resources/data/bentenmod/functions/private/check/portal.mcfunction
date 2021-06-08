clone ~ ~ ~ ~ ~ ~ 100 1 112 replace move
scoreboard players set .range dim_portals 0
scoreboard players set .check dim_portals 1
scoreboard players set .failed dim_portals 1

### X basis Portals
execute if score .check dim_portals matches 1.. positioned ~ ~1 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~ ~ ~ run function dim_portals:portal_shape_x
execute if score .check dim_portals matches 1.. positioned ~ ~1 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~-1 ~ ~ run function dim_portals:portal_shape_x
execute if score .check dim_portals matches 1.. positioned ~ ~2 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~ ~ ~ run function dim_portals:portal_shape_x
execute if score .check dim_portals matches 1.. positioned ~ ~2 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~-1 ~ ~ run function dim_portals:portal_shape_x
execute if score .check dim_portals matches 1.. positioned ~ ~-1 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~ ~4 ~ run function dim_portals:portal_shape_x
execute if score .check dim_portals matches 1.. positioned ~ ~-1 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~-1 ~4 ~ run function dim_portals:portal_shape_x

### Z basis Portals
execute if score .check dim_portals matches 1.. positioned ~ ~1 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~ ~ ~ run function dim_portals:portal_shape_z
execute if score .check dim_portals matches 1.. positioned ~ ~1 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~ ~ ~-1 run function dim_portals:portal_shape_z
execute if score .check dim_portals matches 1.. positioned ~ ~2 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~ ~ ~ run function dim_portals:portal_shape_z
execute if score .check dim_portals matches 1.. positioned ~ ~2 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~ ~ ~-1 run function dim_portals:portal_shape_z
execute if score .check dim_portals matches 1.. positioned ~ ~-1 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~ ~4 ~ run function dim_portals:portal_shape_z
execute if score .check dim_portals matches 1.. positioned ~ ~-1 ~ if block ~ ~ ~ #dim_portals:portal_frames positioned ~ ~4 ~-1 run function dim_portals:portal_shape_z

execute if score .failed dim_portals matches 1 run clone 100 1 112 100 1 112 ~ ~ ~ replace move