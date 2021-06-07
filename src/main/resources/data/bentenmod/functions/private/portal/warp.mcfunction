#say warp
scoreboard players set @s dim_portals 1000000

tag @s add this
execute if predicate dim_portals:overworld run function dim_portals:private/portal/to_dimension
execute unless predicate dim_portals:overworld run function dim_portals:private/portal/to_overworld
tag @s remove this