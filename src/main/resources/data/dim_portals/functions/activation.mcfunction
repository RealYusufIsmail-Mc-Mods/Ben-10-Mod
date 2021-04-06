# Cloud Wolf:
#       This is a list of ways portals can activate based on
#       the block type versus the input score method.

execute if score @s dimp_bucket matches 1.. if block ~ ~ ~ glowstone run tag @s add dimp_activate
execute if score @s dimp_flsteel matches 1.. if block ~ ~ ~ emerald_block run tag @s add dimp_activate