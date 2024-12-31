/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import cst8218.spritegame.service.util.JsfUtil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.awt.Color;
import java.util.ResourceBundle;
/**
 * Class to convert color object passed to JSF from string to object and object to string
 * @author J-PC
 */
@FacesConverter("convertcolor")
public class ConvertColor implements Converter {
    
    /*
    * converts jsf color input String to Object
    */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ColorConvertDefault"));
            return Color.BLUE;
        }
        Color color;
        int rgb = 3;
        int max = 255;
        int min = 0;
        int r = 0;
        int g = 1;
        int b = 2;
        int temp =0;
        String[] colorString = value.split(",");
        if(colorString.length != rgb ){ 
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ColorConvertDefault"));
            return Color.BLUE;
        }
     
        for(int i = 0; i<colorString.length; i++){
           colorString[i] = colorString[i].replaceAll("[^\\d]", "").trim();
            try{
                temp = Integer.parseInt(colorString[i]);
                if(temp > max || temp < min){
                    JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ColorConvertDefault"));
                    return Color.BLUE;
                }
            if(r == i) r = temp;
            if(g == i) g = temp;
            if(b == i) b = temp;
            }catch(NumberFormatException e){
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("ColorConvertErrorOccured"));
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ColorConvertDefault"));
                return Color.BLUE;
            }catch(Exception e){
                JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                return null;            
            }  
        }
        color = new Color(r,g,b);
        return color;
        //To change body of generated methods, choose Tools | Templates.
    }
    
    /*
    *Convert color object obtained through the controller to access sprites to string
    */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        Color col;

        try {
            if(value instanceof Color){
                col = (Color) value;
                return col.toString().replaceAll("[^\\d,]" ,"" );
            }      
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("ColorConvertErrorOccured"));
        }    
        //To change body of generated methods, choose Tools | Templates.
        return null;
    }
    
}
