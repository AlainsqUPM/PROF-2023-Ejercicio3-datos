package mojo1;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVReader;

@Mojo(name ="tsd")
public class MyMojo extends AbstractMojo{
	//@Parameter(property = "jar")
	//String jar = null;
	//@Parameter(property = "input")
	//String input = null;
	public void execute() throws MojoExecutionException{
		String jar = "ts//ts.jar";
		String input = "ts//input.csv";
		String command = "java -jar "+jar+" "+input;
		System.out.println(command);
		try{
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		}
		catch (Exception e) {System.out.println("Error de proceso");}
		File folder = new File("./");
        File[] list = folder.listFiles();
		String archivo = "";
		for (File file : list) {
            if (file.isFile()) {
				File aux = file;
                archivo=file.getName();
				if (archivo.substring(0,3).equals("Out")){
					CSVReader reader = null;
					try {
						reader = new CSVReader(new FileReader(archivo),',','"');
         				String[] line=null; int con=0;
						String[] base=null;
						File html = new File("result.html");
						BufferedWriter w = new BufferedWriter(new FileWriter(html));
        				while ((line = reader.readNext()) != null) {
							if (con==0){
								con=1; base=line; 
								w.write("<html>");w.newLine();
            					w.write("<head>");w.newLine();
								w.write("<title>Resultado TsDetec</title>");w.newLine();
								w.write("</head>");w.newLine();
								w.write("<body>");w.newLine();
								w.write("<h1>Resultado TsDectec</h1>");w.newLine();
							}
							else{
								for (int i=0; i<=1;i=i+1){
									w.write("<h1>"+""+base[i]+": "+line[i]+"</h1>");w.newLine();
									System.out.println(base[i]+": "+line[i]);
								}
								for (int i=6; i<=27;i=i+1){
									if (i==6){w.write("<h1>"+base[i]+": "+line[i]+"</h1>");w.newLine();}
									else{w.write("<h1>"+"____"+base[i]+": "+line[i]+"</h1>");w.newLine();}
									System.out.println(base[i]+": "+line[i]);
								}
							}
							w.write("<h1>----------------------</h1>");w.newLine();
						}
						w.write("</body>");w.newLine();
            			w.write("</html>");
						w.close();
					}
					catch (Exception e) {System.out.println("Error en lectura");} 
					finally {
						if (null != reader){
							try {
								reader.close();
								aux.delete();
							}
							catch (IOException e) {}
						}
					} 
				}
			}
		}
	}
	public static void main(String [] args) {
		String jar="ts//plugin-1.1";
		String input="ts//input.csv";
		String command = "java -jar "+jar+" "+input;
		try{
			Process process = Runtime.getRuntime().exec(command);
			process.waitFor();
		}
		catch (Exception e){}
	}
}