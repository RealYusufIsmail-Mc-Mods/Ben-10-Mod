# prep the dimension area
execute positioned ~ ~2 ~ run tag @e[type=item_frame,tag=portal,sort=nearest,limit=1] add target_portal
execute at @e[type=item_frame,tag=target_portal,limit=1] run summon minecraft:area_effect_cloud ~ ~ ~ {Tags:["portal_checker"]}
data modify entity @e[type=area_effect_cloud,tag=portal_checker,limit=1] Tags append from entity @e[type=item_frame,tag=target_portal,limit=1] Tags[]
tag @e remove target_portal
execute as @e[type=area_effect_cloud,tag=portal_checker,limit=1] at @s in overworld run tp @s ~ ~ ~

execute as @e[type=area_effect_cloud,tag=portal_checker] at @s run function dim_portals:private/portal/find_parent