# Cloud Wolf's Dimension Portals! w/ Demos from Vesaucy

scoreboard objectives add dim_portals dummy
forceload add 100 112
setblock 100 0 112 bedrock
setblock 100 1 112 bedrock
setblock 100 1 113 bedrock
setblock 100 1 111 bedrock
setblock 101 1 112 bedrock
setblock 99 1 112 bedrock
setblock 100 2 112 bedrock

function dim_portals:score_create

tellraw @a[gamemode=creative] ["",{"text":"Cloud Wolf","color":"#62D0FF","clickEvent":{"action":"open_url","value":"https://www.youtube.com/channel/UCZnBqVITQ0dloqUU0fGxY3g"}},{"text":"'s Dimension Portals Pack Installed!","color":"#7FFF8E"},{"text":"\n"},{"text":"With Demo Dimensions from: ","color":"#69D9A6"},{"text":"VeSaucy","color":"#FFCE69"},{"text":"\n\n"},{"text":"[ Click Here ]","color":"red","clickEvent":{"action":"run_command","value":"function dim_portals:private/demo"}},{"text":" to read how to try the demo deminsions!","color":"#5BFF8B"},{"text":"\n"},{"text":"[ Click Here ]","color":"red","clickEvent":{"action":"open_url","value":"https://www.youtube.com/watch?v=lBhi4IV9BN4"}},{"text":" For a Tutorial on how to add your own dimensions!","color":"#5BFF8B"},{"text":"\n\n"},{"text":"(You may have to exit and reload world before dimensions work)","color":"#FFCE69"}]