package com.tp.jetandpack.Settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.tp.framework.FileIO;

public class Saves {
	public static float volume = 0.5f;
	public final static boolean[] worldOneUnlocked = new boolean[] {true, false, false, false, false, false, false, false, false, false};
	public static final int[] worldOneStars = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; 
	public final static String file = ".spaceman";
	
	public static void load(FileIO files){
		BufferedReader in = null;
		try{
			in = new BufferedReader(new InputStreamReader(files.readFile(file)));
			volume = Float.parseFloat(in.readLine());
			for(int i=0; i<worldOneUnlocked.length; i++)
				worldOneUnlocked[i] = Boolean.parseBoolean(in.readLine());
			for(int i=0; i<worldOneStars.length; i++)
				worldOneStars[i] = Integer.parseInt(in.readLine());
		} catch (IOException e) {
			// We have defaults
		} catch (NumberFormatException e) {
			// we have defaults
		} finally {
			try{
				if(in != null)
					in.close();
			} catch (IOException e) {
			}
		}
	}
	
	public static void saveSettings(FileIO files){
		BufferedWriter out = null;
		try{
			out = new BufferedWriter(new OutputStreamWriter(
					files.writeFile(file)));
			out.write(Float.toString(volume));
		} catch (IOException e) {
		} finally {
			try{
				if(out != null)
					out.close();
			} catch (IOException e){
			}
		}
	}
	
	public static void saveWorldOne(FileIO files){
		BufferedWriter out = null;
		try{
			out = new BufferedWriter(new OutputStreamWriter(
					files.writeFile(file)));
			out.write("\n");
			for(int i=0; i<worldOneUnlocked.length; i++){
				out.write(Boolean.toString(worldOneUnlocked[i]));
				out.write("\n");
			}
			for(int i=0; i<worldOneStars.length; i++){
				out.write(Integer.toString(worldOneStars[i]));
				out.write("\n");
			}
		} catch (IOException e) {
		} finally {
			try{
				if(out != null)
					out.close();
			} catch (IOException e){
			}
		}
	}
}
