package com.company;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
public class Painttoframe{
    public static void main(String args[]) throws IOException{
        int[] rgb = new int[3];
        File file = new  File("G:\\软件学习\\idea\\output\\test1\\lib\\mf2.jpg");
        BufferedImage bi=null;
        bi = ImageIO.read(file);
        int width=bi.getWidth();
        int height=bi.getHeight();
        int minx=bi.getMinX();
        int miny=bi.getMinY();
        System.out.println("width="+width+",height="+height+".");
        System.out.println("minx="+minx+",miniy="+miny+".");
        String output1 = "/tellraw @a [";
        for(int j = miny ; j < height ; j++) {
            for(int i = minx ; i < width ; i++) {
                int pixel = bi.getRGB(i, j);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                String r = Integer.toHexString(rgb[0]);
                String g = Integer.toHexString(rgb[1]);
                String b = Integer.toHexString(rgb[2]);
                if (r.length()<2){
                    r = "0" + r;
                }
                if (g.length()<2){
                    g = "0" + g;
                }
                if (b.length()<2){
                    b = "0" + b;
                }
                if (i==minx&&j==miny) {
                    output1 = output1.concat("{\"text\":\"█\",\"color\":\"#"+ r + g + b +"\"}");
                }
                else {
                    if (i==width-1){
                        output1 = output1.concat(",{\"text\":\"█\\n\",\"color\":\"#"+ r + g + b +"\"}");
                    }
                    else{
                        output1 = output1.concat(",{\"text\":\"█\",\"color\":\"#"+ r + g + b +"\"}");
                    }
                }
            }

        }
        output1=output1.concat("]");
        System.out.println(output1);
    }
}
