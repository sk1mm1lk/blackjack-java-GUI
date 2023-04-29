package yhd6147.blackjackgui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yhd6147
 */
public class FileController
{
    
    // === VARIABLES ==========================================================

    private boolean success;
    
    // === CONSTRUCTORS =======================================================

    public FileController()
    {
        // Initialise success as false
        this.success = false;
    }

    // === METHODS ============================================================
    
    private PrintWriter openWriter(String fileName, boolean toAppend)
    {
        // Creates a PrintWriter instance for a file specified
        PrintWriter pw;
        
        try
        {
            pw = new PrintWriter(new FileWriter(fileName, toAppend));
        }
        catch (FileNotFoundException e)
        {
            this.success = false;
            return null;
        }
        catch (IOException ex)
        {
            this.success = false;
            return null;
        }
        
        this.success = true;
        return pw;
    }
    
    private BufferedReader openReader(String fileName)
    {
        // Creates a BufferedReader instance for a file specified
        BufferedReader br;
        
        try
        {
            br = new BufferedReader(new FileReader(fileName));
        }
        catch (FileNotFoundException e)
        {
            this.success = false;
            return null;
        }
        
        this.success = true;
        return br;
    }
    
    public void write(String writeString, String fileName)
    {
        // Overwrites a file with a specified String
        if (writeString == null || fileName == null)
        {
            this.success = false;
            return;
        }
        
        PrintWriter pw;
        
        pw = this.openWriter(fileName, false);
        
        if (pw == null)
        {
            this.success = false;
            return;
        }
        
        pw.write(writeString + "\n");
        
        pw.close();
        this.success = true;
    }
    
    public void append(String appendString, String fileName)
    {
        // Appends a String the end of a file
        PrintWriter pw;
        
        pw = this.openWriter(fileName, true);
        
        if (pw == null)
        {
            this.success = false;
            return;
        }
        
        pw.write(appendString + "\n");
        
        pw.close();
        this.success = true;
    }
    
    public String read(String fileName)
    {
        // Returns a String read from a specified file
        String output = "";
        
        BufferedReader br;
        
        br = this.openReader(fileName);
        
        if (br == null)
        {
            this.success = false;
            return null;
        }
        
        String current;
        try
        {
            current = br.readLine();
        }
        catch (IOException e)
        {
            this.success = false;
            return null;
        }
        
        // Read every line from the file and append to an output String
        while (current != null)
        {
            output += current + "\n";
            
            try
            {
                current = br.readLine();
            }
            catch (IOException e)
            {
                this.success = false;
                return null;
            }
        }
        
        try
        {
            br.close();
        }
        catch(IOException e)
        {
            this.success = false;
            return null;
        }
        
        this.success = true;
        return output;
    }
    
    public void wipe(String fileName)
    {
        // Wipes the data from a file
        PrintWriter pw;
        
        pw = this.openWriter(fileName, false);
        
        if (pw != null)
            pw.close();
        this.success = true;
    }
}
