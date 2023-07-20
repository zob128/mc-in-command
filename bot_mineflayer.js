const Socks = require('socks').SocksClient
const Http = require('http')
const {ProxyAgent} = require('proxy-agent')
const { createBot } = require('mineflayer')
const { pathfinder, Movements, goals } = require('mineflayer-pathfinder')
const pvp = require('mineflayer-pvp').plugin
const armorManager = require('mineflayer-armor-manager')
const {Vec3} = require('./node_modules/vec3')
const {GoalFollow, GoalBlock, GoalPlaceBlock} = goals

const placeItem = require('./lib/placeItem')

const bot = createBot({
    // host: 'yq.lovemc.love',
    host: 'mc.dc233.com',
    username: 'bot_miner',
})

let digging = false

const mcData = require('minecraft-data')(bot.version)
bot.loadPlugin(pathfinder)
// Load collect block
bot.loadPlugin(require('mineflayer-collectblock').plugin)

bot.on('kicked', (reason, loggedIn)=>console.log('kicked:', reason, loggedIn))
bot.on('error', err=>console.log('error:', err));
bot.on('whisper', (username, message)=>{
    if (username != 'zob128') return
    console.log(`command[${username}]: ${message}`)
    usage(message)
})
bot.on('message', (jsonMsg, position)=>{
    let message = jsonMsg.toString()
    console.log(`[${position}]: ${message}`)
    if (!message.startsWith('[zob128')) return
    message = message.replace(/\[.*?\] /, '')
    usage(message)
})
/**
 * 
 * @param {String} message 
 */
function usage(message) {
    console.log(`command: ${message}`)
    let argument = message.split(' ');
    const order = argument.shift()
    if (order == 'chat') {
        let text = argument.join(' ')
        bot.chat(text)
    } else if (order == 'stay') {
        console.log('moving here')
        stayHere()
    } else if (order == 'stop') {
        console.log('stop moving')
        digging = false
        bot.pathfinder.setGoal(null)
        bot.collectBlock.movements = null
    } else if (order == 'follow') {
        console.log('follow player')
        followPlayer()
    } else if (order == 'sand') {
        console.log('collect sand')
        collectSand()
    } else if (order == 'wood') {
        console.log('collect wood')
        collectWood()
    } else if (order == 'hole') {
        let depth = Number.parseInt(argument.shift()) || undefined
        let footstep = argument.shift() == '0' ? false : true
        console.log('dig a hole')
        digging = true
        digAHole(depth, footstep)
    } else if (order == 'drop') {
        console.log('drop..')
        expunge()
    } else if (order == 'block') {
        console.log('view the block below..')
        console.log(bot.blockAt(bot.entity.position.offset(0, -1, 0)))
    } else if (order == 'ball') {
        console.log('build ball..')
        buildBall(5)
    }
}
//yq.lovemc.love
// bot.once('login', async()=>{
//     await bot.waitForTicks(20)
//     bot.chat('/l 147258369')
//     bot.activateEntity(bot.nearestEntity((entity)=>entity.type == 'player'))
// })

//mc.dc233.com
bot.once('login', async()=>{
    await bot.waitForTicks(20)
    bot.chat('/l 147258369')
    await bot.waitForTicks(10)
    bot.chat('/menu')
    bot.once('windowOpen', async(window)=>{
        // console.log('window:', window)
        await bot.clickWindow(13, 0, 0)
        await bot.waitForTicks(20)
        bot.acceptResourcePack()
    })
})


//stay here
function stayHere() {
    const player = bot.players['zob128']

    if (!player || !player.entity) {
        // bot.chat('I cant see zob128')
        return
    }
    // bot.chat('I can see zob128')
    const movements = new Movements(bot, mcData)

    // 取消使用方块
    // movements.scafoldingBlocks = []

    bot.pathfinder.setMovements(movements)

    const goal = new GoalFollow(player.entity, 1)
    bot.pathfinder.setGoal(goal)
}
function followPlayer() {
    const playerCI = bot.players['zob128']

    if (!playerCI || !playerCI.entity) {
        // console.log('I cant see zob128')
        return
    }
    // console.log('I can see zob128')
    const movements = new Movements(bot, mcData)

    // 取消使用方块
    // movements.scafoldingBlocks = []

    bot.pathfinder.setMovements(movements)

    const goal = new GoalFollow(playerCI.entity, 1)
    bot.pathfinder.setGoal(goal, true)
}


// bot.loadPlugin(pathfinder)
// function stayHere() {
//     const mcData = require('minecraft-data')(bot.version)
//     const movements = new Movements(bot, mcData)
//     movements.scafoldingBlocks = []
//     bot.pathfinder.setMovements(movements)

//     const emeraldBlock = bot.findBlock({
//         matching: mcData.blocksByName.emerald_block.id,
//         maxDistance: 32
//     })

//     if (!emeraldBlock) {
//         bot.chat('cant find emerald_block')
//         return
//     }
//     bot.chat('have found emerald_block')
//     // const [x, y, z] = [emeraldBlock.x, emeraldBlock.y+1, emeraldBlock.z]
//     const x = emeraldBlock.position.x
//     const y = emeraldBlock.position.y + 1
//     const z = emeraldBlock.position.z
//     const goal = new GoalBlock(x, y, z)
//     bot.pathfinder.setGoal(goal, true)
// }

async function collectSand(radius = 8, count = 8) {
    const shovel = bot.inventory.items().find(item => item.name.includes('shovel'))
    if (shovel) {
        await bot.equip(shovel, 'hand')
    }
    // Find a nearby grass block
    
    const mcData = require('minecraft-data')(bot.version)
    const blocks = bot.findBlocks({
        matching: mcData.blocksByName.sand.id,
        maxDistance: radius,
        count: count
    })

    const targets = [];
    for (let i = 0; i < blocks.length; i++) {
        targets.push(bot.blockAt(blocks[i]));
    }
    await bot.collectBlock.collect(targets, {
        // ignoreNoPath: true,
        count: count,
    });
}

async function collectWood(radius = 32, count = 256) {
    const shovel = bot.inventory.items().find(item => item.name.includes('shovel'))
    if (shovel) {
        await bot.equip(shovel, 'hand')
    }
    // Find a nearby grass block
    
    const mcData = require('minecraft-data')(bot.version)
    const blocks = bot.findBlocks({
        matching: mcData.blocksByName.oak_log.id,
        maxDistance: radius,
        count: count
    })

    const targets = [];
    for (let i = 0; i < blocks.length; i++) {
        targets.push(bot.blockAt(blocks[i]));
    }
    try {
        await bot.collectBlock.collect(targets, {
            // ignoreNoPath: true,
            count: count,
        });
    } catch (error) {
        console.error(error)
        await bot.collectBlock.collect(targets, {
            // ignoreNoPath: true,
            count: count,
        });
    }
    
}

async function digAHole(todepth = 11, footstep = true){
    let depth = bot.entity.position.y > todepth ? bot.entity.position.y - todepth : 0;
    let center_pos = bot.entity.position.clone()
    // const pickaxe = bot.inventory.items().find(item => item.name.includes('pickaxe'))
    // if (!pickaxe) {
    //     console.log('unfound pickaxe')
    //     return
    // }
    // await bot.equip(pickaxe, 'hand')
    const start_pos = bot.entity.position.offset(-3, -1, -3)
    for (let y = 0; y < depth; y++) {
        for (let x = 0; x < 7; x++) {
            for (let z = 0; z < 7; z++) {
                if (!digging) return
                if (x == 3 && z == 3) continue
                if (center_pos.x != bot.entity.position.x || center_pos.z != bot.entity.position.z) {
                    console.log('deviate position, stop work')
                    return
                }
                let block = bot.blockAt(start_pos.offset(x, -y, z))
                if (block?.material == 'mineable/pickaxe') {
                    const pickaxe = bot.inventory.items().find(item => item.name.includes('pickaxe'))
                    if (!pickaxe) {
                        console.log('unfound pickaxe')
                        return
                    }
                    await bot.equip(pickaxe, 'hand')
                } else if (block?.material == 'mineable/shovel') {
                    const shovel = bot.inventory.items().find(item => item.name.includes('shovel'))
                    if (shovel) {
                        await bot.equip(shovel, 'hand')
                    }
                } else if (block?.material == 'default') {
                    continue
                }
                await bot.dig(block, true)
                console.log(`x: ${x}, y: ${y}, z: ${z}`)
            }
        }
        await bot.dig(bot.blockAt(bot.entity.position.offset(0, -1, 0)), true)
        center_pos = bot.entity.position.clone()
        if (footstep) {
            let [bx, bz] = roundSqaureStep(7, bot.entity.position.y)
            const craftingTablePosition = bot.entity.position.offset(bx - 3, 0, bz - 3);
            console.log([bx, bz], craftingTablePosition)
            await placeItem(bot, "cobblestone", craftingTablePosition, false);
        }
    }
}

async function expunge() {
    var inventoryItemCount = bot.inventory.items().length;
    if (inventoryItemCount === 0) return;

    while (inventoryItemCount > 0) {
        const item = bot.inventory.items()[0];
        console.log(`Throwed ${item.name}`);
        await bot.tossStack(item);
        inventoryItemCount--;
    }
}

function roundSqaureStep(side = 7, i = 0) {
    let [x, y] = [0, 0]
    let sign = 0
    side -= 1
    sign = Math.floor(i / side) % 4
    if (sign == 0) {
        [x, y] = [0, i % side]
    } else if (sign == 1) {
        [x, y] = [i % side, side]
    } else if (sign == 2) {
        [x, y] = [side, side - i % side]
    } else if (sign == 3) {
        [x, y] = [side - i % side, 0]
    }
    return [x, y]
}

function drawSphere(center = [0, 36, 0], radius = 16) {
    // const center = [0, 36, 0]; // 假设球心位于三维坐标原点
    const sphereCoordinates = [];
    for (let y = -radius; y <= radius; y++) {
        for (let x = -radius; x <= radius; x++) {
            for (let z = -radius; z <= radius; z++) {
                const distanceToCenter = Math.sqrt(x ** 2 + y ** 2 + z ** 2);
                if (distanceToCenter <= radius && distanceToCenter >= radius - 1) {
                    sphereCoordinates.push([x + center[0], y + center[1], z + center[2]]);
                }
            }
        }
    }
    return sphereCoordinates;
}
async function buildBall(radius = 16) {
    const center = bot.entity.position
    const blocks = drawSphere(center.offset(0, radius, 0).toArray(), radius)
    for (let i = 0; i < blocks.length; i++) {
        const [x, y, z] = blocks[i];
        const position = new Vec3(x, y, z)
        console.log(position)
        try {
            await bot.pathfinder.goto(new GoalBlock(x, y, z));
            // await bot.pathfinder.goto(new GoalPlaceBlock(position, bot.world, {}));
        } catch (error) {
            console.error(error)
        }
        await placeItem(bot, 'cobblestone', position)
    }
    console.log('ball over')
}
