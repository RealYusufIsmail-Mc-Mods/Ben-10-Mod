# Cloud Wolf:
#       This checks the integrity of the portal, if failed
#       portal will be killed.

execute if entity @s[tag=glowstone] unless predicate dim_portals:glowstone_portal_z run kill @s
execute if entity @s[tag=mossy] unless predicate dim_portals:mossy_portal_z run kill @s