# Cloud Wolf:
#       This is to enable portals in alternate dimensions,
#       if not filled out your portal WILL NOT WORK.
#       This is to prevent having a portal to dimension B
#       inside dimension A. (location ambiguity)

execute if entity @s[tag=glowstone] if predicate dim_portals:glowstone_dimension run scoreboard players set .invalid dim_portals 0
execute if entity @s[tag=mossy] if predicate dim_portals:mossy_dimension run scoreboard players set .invalid dim_portals 0
execute if entity @s[tag=vilgax] if predicate bentenmobmod:vilgax_dimension run scoreboard players set .invalid dim_portals 0