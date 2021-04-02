# Cloud Wolf:
#       This checks the integrity of the portal, if failed
#       portal will be killed.

execute if entity @s[tag=vilgax] unless predicate bentenmobmod:vilgax_portal_x run kill @s
