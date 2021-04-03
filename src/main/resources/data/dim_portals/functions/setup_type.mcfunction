# Cloud Wolf:
#       Used to tie a tag to the type of portal it is.
#       Can also alter the portal face (with RP on)

execute if block ~ ~ ~ glowstone run tag @s add glowstone
execute if block ~ ~ ~ glowstone run data modify entity @s Item set value {id:"minecraft:flint_and_steel",Count:1b,tag:{CustomModelData:1}}
execute if block ~ ~ ~ mossy_cobblestone run tag @s add mossy
execute if block ~ ~ ~ mossy_cobblestone run data modify entity @s Item set value {id:"minecraft:flint_and_steel",Count:1b,tag:{CustomModelData:2}}
ecute if block ~ ~ ~ vilgax_block run tag @s add vilgax
execute if block ~ ~ ~ vilgax_block run data modify entity @s Item set value {id:"minecraft:flint_and_steel",Count:1b,tag:{CustomModelData:3}}