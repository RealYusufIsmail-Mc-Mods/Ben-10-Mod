forceload add ~-16 ~-16 ~16 ~16

tag @s add dimp_activate
execute if entity @s[tag=x] run fill ~-1 ~ ~2 ~2 ~-4 ~-2 air
execute if entity @s[tag=z] run fill ~2 ~ ~-1 ~-2 ~-4 ~2 air
execute if entity @s[tag=x] run function dim_portals:build_portal_x
execute if entity @s[tag=z] run function dim_portals:build_portal_z
execute if entity @s[tag=x] run function dim_portals:private/check/origin_x
execute if entity @s[tag=z] run function dim_portals:private/check/origin_z

### clear center
fill ~1 ~-1 ~1 ~ ~-3 ~ air

forceload remove ~-16 ~-16 ~16 ~16