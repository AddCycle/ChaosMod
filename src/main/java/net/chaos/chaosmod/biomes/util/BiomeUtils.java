package net.chaos.chaosmod.biomes.util;

public class BiomeUtils {

	public static int blendColors(double t, int colorA, int colorB) {
	    int rA = (colorA >> 16) & 0xFF, rB = (colorB >> 16) & 0xFF;
	    int gA = (colorA >> 8)  & 0xFF, gB = (colorB >> 8)  & 0xFF;
	    int bA =  colorA        & 0xFF, bB =  colorB        & 0xFF;

	    int r = (int) (rA + t * (rB - rA));
	    int g = (int) (gA + t * (gB - gA));
	    int b = (int) (bA + t * (bB - bA));

	    return (r << 16) | (g << 8) | b;
	}
}