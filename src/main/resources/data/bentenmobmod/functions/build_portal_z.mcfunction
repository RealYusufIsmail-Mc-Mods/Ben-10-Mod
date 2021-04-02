# Cloud Wolf:
#       This is to build a portal of specified block type.
#       Copy the 2 lines and change block to your own.
#       (Note the coords r different from build_portal_x)

execute if entity @s[tag=glowstone] run fill ~2 ~-4 ~-1 ~-2 ~-4 ~2 glowstone
execute if entity @s[tag=glowstone] run fill ~ ~ ~-1 ~ ~-4 ~2 glowstone
execute if entity @s[tag=mossy] run fill ~2 ~-4 ~-1 ~-2 ~-4 ~2 mossy_cobblestone
execute if entity @s[tag=mossy] run fill ~ ~ ~-1 ~ ~-4 ~2 mossy_cobblestone