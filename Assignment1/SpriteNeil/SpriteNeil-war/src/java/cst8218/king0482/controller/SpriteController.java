package cst8218.king0482.controller;

import cst8218.king0482.entity.Sprite;
import cst8218.king0482.entity.SpriteFacade;
import cst8218.king0482.util.JsfUtil;
import cst8218.king0482.util.PaginationHelper;
import java.awt.Color;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 * @author tgk
 * @version 1.0
 * @since 2022-11-10
 * 
 * Controller for the JSF pages relating to the
 * Sprite entity.
 */
@Named(value="spriteController")
@SessionScoped
public class SpriteController implements Serializable {

    private Sprite current;
    private DataModel items = null;
    @EJB
    private cst8218.king0482.entity.SpriteFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public SpriteController() {
    }

    public Sprite getSelected() {
        if (current == null) {
            current = new Sprite();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SpriteFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Sprite) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Sprite();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SpriteCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Sprite) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SpriteUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Sprite) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SpriteDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Sprite getSprite(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Sprite.class)
    public static class SpriteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SpriteController controller = (SpriteController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "spriteController");
            return controller.getSprite(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Sprite) {
                Sprite o = (Sprite) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Sprite.class.getName());
            }
        }

    }
    
    /**
     * @author Neil Kingdom
     * @version 1.0
     * @since 2022-11-10
     * 
     * Converter class for color objects. Takes a string
     * in the format #nnnnnn or [r=nnn, g=nnn, b=nnn] and
     * converts it to a java.awt.Color object.
     */
    @FacesConverter("cst8218.king0482.controller.ColorConverter")
    public static class ColorConverter implements Converter {
        
        /**
         * @since 2022-11-10
         * @param facesContext The context class for the JavaServer Faces request
         * @param component The component tree for the JavaServer Faces request
         * @param value The input string to parse
         * @return A java.awt Color object
         * 
         * Parses an input string and attempts to convert it to
         * a java.awt.Color object.
         */
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) { 
            if (value == null || value.length() == 0) {
                return null;
            }
            
            // Remove all whitespace and make letters lowercase
            value = value.replaceAll("\\s", "");
            value = value.toLowerCase();
            
            if (value.charAt(0) != '#' && value.charAt(0) != '[') {
                return null;
            }
            
            // Hexadecimal value
            if (value.charAt(0) == '#') {
                value = value.replaceAll("#", "");
                
                if (value.length() != 6) {
                    return null;
                }
                
                String[] arr = new String[3];
                arr[0] = value.substring(0, 2);
                arr[1] = value.substring(2, 4);
                arr[2] = value.substring(4, 6);
                
                int r = Integer.parseInt(arr[0], 16);
                int g = Integer.parseInt(arr[1], 16);
                int b = Integer.parseInt(arr[2], 16);
                
                return new Color(r, g, b);
            }
            
            // RGB value
            else if (value.charAt(0) == '[')
            {
                StringBuilder sb;
                int i = 1;
             
                value = value.replaceAll(",", "");
                
                System.out.println("DEBUG Value is " + value);
                
                if (value.charAt(i) != 'r'
                 || value.charAt(i+1) != '='
                   )
                {
                    return null;
                }
                
                i+=2;
                sb = new StringBuilder();
                while (Character.isDigit(value.charAt(i)))
                {
                    sb.append(value.charAt(i));
                    i++;
                }
                
                int r = Integer.valueOf(sb.toString());
                
                if (value.charAt(i) != 'g'
                 || value.charAt(i+1) != '='
                   )
                {
                    return null;
                }
                
                i+=2;
                sb = new StringBuilder();
                while (Character.isDigit(value.charAt(i)))
                {
                    sb.append(value.charAt(i));
                    i++;
                }
                                
                int g = Integer.valueOf(sb.toString());
                
                if (value.charAt(i) != 'b'
                 || value.charAt(i+1) != '='
                   )
                {
                    return null;
                }
                
                i+=2;
                sb = new StringBuilder();
                while (Character.isDigit(value.charAt(i)))
                {
                    sb.append(value.charAt(i));
                    i++;
                }
                                
                int b = Integer.valueOf(sb.toString());
                
                System.out.println("DEBUG color is " + r + " " + g + " " + b);
                
                return new Color(r, g, b);
            }
            
            return null;
        }

        /**
         * @since 2022-11-10
         * @param value The color object to string-ify
         * @return A String representation of the input Color object
         * 
         * Takes a Color object and converts it to a String.
         */
        String getColorHex(Color value) {
            return "[r=" + value.getRed() 
                    + ", g=" + value.getGreen() 
                    + ", b=" + value.getBlue() + "]";
        }

        /**
         * @since 2022-11-10
         * @param facesContext The context class for the JavaServer Faces request
         * @param component The component tree for the JavaServer Faces request
         * @param object The incoming object
         * @throws IllegalArgumentException If object is not of base type Sprite
         * @return A String representation of the input Color object
         * 
         * A wrapper function for getColorHex() that does additional
         * input validation i.e. checking if the object is null and
         * that it is of type java.awt.Color.
         */
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Color) {
                Color color = (Color)object;
                return getColorHex(color);
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Color.class.getName());
            }
        }
    }
}
