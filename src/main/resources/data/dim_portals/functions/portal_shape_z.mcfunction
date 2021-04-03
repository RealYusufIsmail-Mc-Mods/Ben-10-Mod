# Cloud Wolf:
#       Used to check if there is a portal at this location

execute if block ~ ~ ~ glowstone if predicate dim_portals:glowstone_portal_z run function dim_portals:private/check/origin_z
execute if block ~ ~ ~ vilgax if predicate dim_portals:vilgax_portal_z run function dim_portals:private/check/origin_z
