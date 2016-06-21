
package Program6;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

public class Utility {
public static List<SelectItem> genderOptions(){
        return genderoptions;
    }
    
    public static List<SelectItem> languageOptions(){
        return languageoptions;
    }
    
    public static List<SelectItem> hometownOptions(){
        return hometownoptions;
    }
    
    
    public final static List<SelectItem> genderoptions;
    static{
        genderoptions = new ArrayList<SelectItem>();
        genderoptions.add(new SelectItem("Male"));
        genderoptions.add(new SelectItem("Female"));
    }
    
    public final static List<SelectItem> languageoptions;
    static{
        languageoptions = new ArrayList<SelectItem>();
        languageoptions.add(new SelectItem("English"));
        languageoptions.add(new SelectItem("Spanish"));
        languageoptions.add(new SelectItem("Chinese"));
        languageoptions.add(new SelectItem("French"));
        languageoptions.add(new SelectItem("Portugese"));
        languageoptions.add(new SelectItem("Korean"));
    }
    
    public final static List<SelectItem> hometownoptions;
    static{
        hometownoptions = new ArrayList<SelectItem>();
        hometownoptions.add(new SelectItem("--Choose One--"));
        hometownoptions.add(new SelectItem("Edmond"));
        hometownoptions.add(new SelectItem("Luther"));
        hometownoptions.add(new SelectItem("Oklahoma City"));
        hometownoptions.add(new SelectItem("Moore"));
        hometownoptions.add(new SelectItem("Norman"));
        hometownoptions.add(new SelectItem("tulsa"));
    }
    
}
