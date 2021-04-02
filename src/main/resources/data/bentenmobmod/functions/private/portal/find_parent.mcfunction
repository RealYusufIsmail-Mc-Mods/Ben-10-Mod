#say searching
execute positioned ~ -64 ~ unless entity @e[type=item_frame,tag=portal,dy=384] at @s run function dim_portals:private/portal/new_portal
execute positioned ~ -64 ~ at @e[type=item_frame,tag=portal,dy=384,sort=nearest,limit=1] if entity @s[tag=x] positioned ~0.5 ~-2.5 ~ run tp @a[tag=this] ~ ~ ~
execute positioned ~ -64 ~ at @e[type=item_frame,tag=portal,dy=384,sort=nearest,limit=1] if entity @s[tag=z] positioned ~ ~-2.5 ~0.5 run tp @a[tag=this] ~ ~ ~
kill @s