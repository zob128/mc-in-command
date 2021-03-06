package com.company.colordist;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Painttoblock {
    public static double Distance(double r,double g,double b,double r1,double g1,double b1){
        double rmean;
        rmean = (r+r1)/2;
        r -= r1;
        g -= g1;
        b -= b1;
        return Math.sqrt((2+rmean/256)*(r*r)+4*(g*g)+(2+(255-rmean)/256)*(b*b));
    }
    public static void main(String[] args) throws IOException {
        int[][] rgbR={
                {9,11,16},      //minecraft:black_concrete
                {38,23,16},     //minecraft:black_terracotta
                {17,18,23},     //minecraft:black_wool
                {256,256,256},  //minecraft:white_wool
                {45,47,144},    //minecraft:blue_concrete
                {75,60,91},     //minecraft:blue_terracotta
                {51,55,154},    //minecraft:blue_wool
                {96,59,31},     //minecraft:brown_concrete
                {151,114,81},   //minecraft:brown_mushroom_block
                {78,51,36},     //minecraft:brown_terracotta
                {112,70,40},    //minecraft:brown_wool
                {168,175,193},  //minecraft:clay
                {21,119,136},   //minecraft:cyan_concrete
                {21,135,143},   //minecraft:cyan_wool
                {87,91,91},     //minecraft:cyan_terracotta
                {54,57,61},     //minecraft:gray_concrete
                {58,43,36},     //minecraft:gray_terracotta
                {61,67,70},     //minecraft:gray_wool
                {73,91,36},     //minecraft:green_concrete
                {76,83,43},     //minecraft:green_terracotta
                {83,106,28},    //minecraft:green_wool
                {35,137,199},   //minecraft:light_blue_concrete
                {114,109,138},  //minecraft:light_blue_terracotta
                {55,173,215},   //minecraft:light_blue_wool
                {125,125,115},  //minecraft:light_gray_concrete
                {136,107,98},   //minecraft:light_gray_terracotta
                {140,140,140},  //minecraft:light_gray_wool
                {93,168,24},    //minecraft:lime_concrete
                {103,117,52},   //minecraft:lime_terracotta
                {109,182,24},   //minecraft:lime_wool
                {169,48,159},   //minecraft:magenta_concrete
                {151,89,109},   //minecraft:magenta_terracotta
                {189,67,178},   //minecraft:magenta_wool
                {224,91,1},     //minecraft:orange_concrete
                {162,85,38},    //minecraft:orange_terracotta
                {238,112,15},   //minecraft:orange_wool
                {213,101,143},  //minecraft:pink_concrete
                {162,79,79},    //minecraft:pink_terracotta
                {242,141,171},  //minecraft:pink_wool
                {100,32,156},   //minecraft:purple_concrete
                {120,71,87},    //minecraft:purple_terracotta
                {116,38,167},   //minecraft:purple_wool
                {143,33,33},    //minecraft:red_concrete
                {181,98,32},    //minecraft:smooth_red_sandstone
                {144,62,47},    //minecraft:red_terracotta
                {149,34,32},    //minecraft:red_wool
                {224,214,170},  //minecraft:smooth_sandstone
                {132,132,132},  //minecraft:stone
                {152,92,66},    //minecraft:terracotta
                {207,213,214},  //minecraft:white_concrete
                {210,179,162},  //minecraft:white_terracotta
                {241,175,21},   //minecraft:yellow_concrete
                {186,133,35},   //minecraft:yellow_terracotta
                {247,193,35},   //minecraft:yellow_wool
        };
        String[] rgbN={
                "minecraft:black_concrete",
                "minecraft:black_terracotta",
                "minecraft:black_wool",
                "minecraft:white_wool",
                "minecraft:blue_concrete",
                "minecraft:blue_terracotta",
                "minecraft:blue_wool",
                "minecraft:brown_concrete",
                "minecraft:brown_mushroom_block",
                "minecraft:brown_terracotta",
                "minecraft:brown_wool",
                "minecraft:clay",
                "minecraft:cyan_concrete",
                "minecraft:cyan_wool",
                "minecraft:cyan_terracotta",
                "minecraft:gray_concrete",
                "minecraft:gray_terracotta",
                "minecraft:gray_wool",
                "minecraft:green_concrete",
                "minecraft:green_terracotta",
                "minecraft:green_wool",
                "minecraft:light_blue_concrete",
                "minecraft:light_blue_terracotta",
                "minecraft:light_blue_wool",
                "minecraft:light_gray_concrete",
                "minecraft:light_gray_terracotta",
                "minecraft:light_gray_wool",
                "minecraft:lime_concrete",
                "minecraft:lime_terracotta",
                "minecraft:lime_wool",
                "minecraft:magenta_concrete",
                "minecraft:magenta_terracotta",
                "minecraft:magenta_wool",
                "minecraft:orange_concrete",
                "minecraft:orange_terracotta",
                "minecraft:orange_wool",
                "minecraft:pink_concrete",
                "minecraft:pink_terracotta",
                "minecraft:pink_wool",
                "minecraft:purple_concrete",
                "minecraft:purple_terracotta",
                "minecraft:purple_wool",
                "minecraft:red_concrete",
                "minecraft:smooth_red_sandstone",
                "minecraft:red_terracotta",
                "minecraft:red_wool",
                "minecraft:smooth_sandstone",
                "minecraft:stone",
                "minecraft:terracotta",
                "minecraft:white_concrete",
                "minecraft:white_terracotta",
                "minecraft:yellow_concrete",
                "minecraft:yellow_terracotta",
                "minecraft:yellow_wool",
        };

        int[] rgb = new int[3];
        File file = new  File("G:\\软件学习\\idea\\output\\test1\\lib\\tensei.jpg");
        BufferedImage bi=null;
        bi = ImageIO.read(file);
        int width=bi.getWidth();
        int height=bi.getHeight();
        int minx=bi.getMinX();
        int miny=bi.getMinY();
        int size = 0;
        System.out.println("width="+width+",height="+height+".");
        System.out.println("minx="+minx+",miny="+miny+".");

        double Val,minVal;
        String minName="";
        String strFont="/setblock ~ ~1 ~ minecraft:chest{Items:[{Slot:0b,id:\"minecraft:writable_book\",Count:1b,tag:{pages:[";
        String strBack="\"end\"]}}]}";
        String concat="";
        for (int j = miny ; j < height ; j++) {
            for (int i = minx ; i < width ; i++) {
                int pixel = bi.getRGB(i, j);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);

                minVal=768;
                for (int k = 0; k < rgbN.length; k++) {
                    Val = Distance(rgb[0],rgb[1],rgb[2],rgbR[k][0],rgbR[k][1],rgbR[k][2]);
                    if (minVal>Val) {
                        minVal=Val;
                        minName = String.valueOf(k);
                    }
                }
                concat += "\""+minName+"\",";
                size++;
                if (size>=6400){
                    size = 0;
                    System.out.println(strFont+concat+strBack);
                    concat = "";
                }
                if ((j==height-1)&&(i==width-1)) System.out.println(strFont+concat+strBack);
            }
        }
        int i;
        String concat1="summon falling_block ~ ~1 ~ {BlockState:{Name:redstone_block},Time:1,Passengers:" +
                "[{id:falling_block,BlockState:{Name:redstone_block},Passengers:[{id:falling_block,BlockS" +
                "tate:{Name:activator_rail},Time:1,Passengers:[" +
                "{id:command_block_minecart,Command:\"setblock ~ ~-1 ~3 minecraft:repeating_command_block[facing=up]{Command:\\\"data modify block ~ ~-1 ~-1 Items[0].tag.pages[0] set from block ~ ~-1 ~-1 Items[1].tag.pages[0]\\\"}\"}," +
                "{id:command_block_minecart,Command:\"setblock ~ ~-2 ~2 minecraft:chest{Items:[{Slot:0b,id:\\\"minecraft:writable_book\\\",Count:1b,tag:{pages:[\\\"a\\\"]}}]}\"}," +
                "{id:command_block_minecart,Command:\"scoreboard objectives add paintwrite dummy\"}," +
                "{id:command_block_minecart,Command:\"scoreboard players set widthz paintwrite "+(width-minx)+"\"}," +
                "{id:command_block_minecart,Command:\"scoreboard players set heigthx paintwrite "+(height-miny)+"\"}," +
                "{id:command_block_minecart,Command:\"scoreboard players set z paintwrite "+(width-minx)+"\"}," +
                "{id:command_block_minecart,Command:\"scoreboard players set x paintwrite "+(height-miny)+"\"},";
        /*for (int i = 0; i < rgbN.length; i++) {
            concat1 += "{id:command_block_minecart,Command:\"setblock ~ ~"+i+" ~2 "+rgbN[i]+"\"},";
        }*/
        for (i = 0; i < rgbN.length; i++) {
            concat1 += "{id:command_block_minecart,Command:\"setblock ~ ~"+i+" ~3 minecraft:chain_command_b" +
                    "lock[facing=up]{Command:\\\"execute if block ~ ~-"+(i+2)+" ~-1 minecraft:chest{Items:[{Slot:0b,id:\\\\\\\"mi" +
                    "necraft:writable_book\\\\\\\",tag:{pages:[\\\\\\\""+i+"\\\\\\\"]}}]} at @e[tag=painter,tag=start] run setblock ~ ~-1 ~ "+rgbN[i]+"\\\",auto:1b}\"},";
        }
        concat1 += "" +
                "{id:command_block_minecart,Command:\"setblock ~ ~-2 ~1 minecraft:command_block[facing=up]{Command:\\\"summon minecraft:armor_stand ~1 ~ ~ {NoGravity:1b,Tags:[\\\\\\\"painter\\\\\\\",\\\\\\\"start\\\\\\\"],CustomName:\\\\\\\"2\\\\\\\",CustomNameVisible:1b}\\\"}\"}," +
                "{id:command_block_minecart,Command:\"setblock ~ ~"+(i++)+" ~3 minecraft:chain_command_block[facing=up]{Command:\\\""+"execute if block ~ ~-"+(i+1)+" ~-1 minecraft:chest{Items:[{Slot:0b,id:\\\\\\\"minecraft:writable_book\\\\\\\",tag:{pages:[\\\\\\\"end\\\\\\\"]}}]} run data remove block ~ ~-"+(i+1)+" ~-1 Items[1]"+"\\\",auto:1b}\"}," +
                "{id:command_block_minecart,Command:\"setblock ~ ~"+(i++)+" ~3 minecraft:chain_command_block[facing=up]{Command:\\\"data remove block ~ ~-"+(i+1)+" ~-1 Items[1].tag.pages[0]\\\",auto:1b}\"}," +
                "{id:command_block_minecart,Command:\"setblock ~ ~"+(i++)+" ~3 minecraft:chain_command_block[facing=up]{Command:\\\"execute as @e[tag=painter,tag=start] run scoreboard players remove z paintwrite 1\\\",auto:1b}\"}," +
                "{id:command_block_minecart,Command:\"setblock ~ ~"+(i++)+" ~3 minecraft:chain_command_block[facing=up]{Command:\\\"execute as @e[tag=painter,tag=start] if score z paintwrite matches 0 if score x paintwrite matches 0 run kill @s\\\",auto:1b}\"},"+
                "{id:command_block_minecart,Command:\"setblock ~ ~"+(i++)+" ~3 minecraft:chain_command_block[facing=up]{Command:\\\"execute as @e[tag=painter,tag=start] at @s if score z paintwrite matches 0 run tp @s ~1 ~ ~-"+(width-minx)+"\\\",auto:1b}\"},"+
                "{id:command_block_minecart,Command:\"setblock ~ ~"+(i++)+" ~3 minecraft:chain_command_block[facing=up]{Command:\\\"execute as @e[tag=painter,tag=start] if score z paintwrite matches 0 run scoreboard players remove x paintwrite 1\\\",auto:1b}\"},"+
                "{id:command_block_minecart,Command:\"setblock ~ ~"+(i++)+" ~3 minecraft:chain_command_block[facing=up]{Command:\\\"execute as @e[tag=painter,tag=start] if score z paintwrite matches 0 run scoreboard players operation z paintwrite = widthz paintwrite\\\",auto:1b}\"},"+
                "{id:command_block_minecart,Command:\"setblock ~ ~"+(i)+" ~3 minecraft:chain_command_block[facing=up]{Command:\\\"execute as @e[tag=painter,tag=start] at @s run tp @s ~ ~ ~1\\\",auto:1b}\"},"+
                "{id:command_block_minecart,Command:\"setblock ~ ~1 ~ minecraft:command_block{Command:\\\"fill ~ ~ ~ ~ ~-3 ~ air\\\",auto:1b}\"}," +
                "{id:command_block_minecart,Command:\"/kill @e[type=minecraft:command_block_minecart,distance=..1]\"}]}]}]}";
        System.out.println(concat1);
        //System.out.println(strFont+concat+strBack);
    }
}
/*
        /scoreboard objectives add paintwrite dummy
        /scoreboard players set widthz paintwrite 146
        /scoreboard players set heigthx paintwrite 100
        /scoreboard players set z paintwrite 146
        /scoreboard players set x paintwrite 100
        ;
        /summon minecraft:armor_stand ~100 ~ ~146 {NoGravity:1b,Tags:["painter"],CustomName:"\"pw\"",CustomNameVisible:1b}
        ;
        /data modify block ~ ~ ~-1 Items[0].tag.pages[0] set from block ~ ~ ~-1 Items[1].tag.pages[0]
        ...
        /"execute if block ~ ~-"+(i+1)+" ~-1 minecraft:chest{Items:[{id:\\\\\\\"minecraft:writable_book\\\\\\\",tag:{pages:[\\\\\\\"end\\\\\\\"]}}]} run data remove block ~ ~-"+(i+1)+" ~-1 Items[1]"
        /data remove block ~ ~ ~-1 Items[1].tag.pages[0]
        /execute as @e[tag=painter,tag=start] run scoreboard players remove z paintwrite 1
        /execute as @e[tag=painter,tag=start] if score z paintwrite matches 0 if score x paintwrite matches 0 run kill @s
        /execute as @e[tag=painter,tag=start] at @s if score z paintwrite matches 0 run tp @s ~1 ~ ~-146
        /execute as @e[tag=painter,tag=start] if score z paintwrite matches 0 run scoreboard players remove x paintwrite 1
        /execute as @e[tag=painter,tag=start] if score z paintwrite matches 0 run scoreboard players operation z paintwrite = widthz paintwrite
        /execute as @e[tag=painter,tag=start] at @s run tp @s ~ ~ ~1
*/
