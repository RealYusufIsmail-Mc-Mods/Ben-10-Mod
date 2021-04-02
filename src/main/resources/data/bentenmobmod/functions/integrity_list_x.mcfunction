# Cloud Wolf:
#       This checks the integrity of the portal, if failed
#       portal will be killed.

execute if entity @s[tag=glowstone] unless predicate bentenmobmod:glowstone_portal_x run kill @s
execute if entity @s[tag=mossy] unless predicate bentenmobmod:mossy_portal_x run kill @s
execute if entity @s[tag=vilgax] unless predicate bentenmobmod:vilgax_portal_x run kill @s
