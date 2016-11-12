package com.sget.akshef.view;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.sget.akshef.hibernate.beans.SubcategoryBean;

 
@FacesConverter(value = "categoryConverter")
public class CategoryConverter implements Converter {
        @Override
        public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
            Object ret = null;
            if (arg1 instanceof PickList) {
                Object dualList = ((PickList) arg1).getValue();
                DualListModel dl = (DualListModel) dualList;
                for (Object o : dl.getSource()) {
                    String id = "";
                    if (o instanceof SubcategoryBean) {
                        id += ((SubcategoryBean) o).getId();
                    }
                     
                    if (arg2.equals(id)) {
                        ret = o;
                        break;
                    }
                }
                if (ret == null)
                    for (Object o : dl.getTarget()) {
                            String id = "";
                            if (o instanceof SubcategoryBean) {
                                id += ((SubcategoryBean) o).getId();
                            }
                             
                        if (arg2.equals(id)) {
                            ret = o;
                            break;
                        }
                    }
            }
            return ret;
        }

        @Override
        public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
            String str = "";
            if (arg2 instanceof SubcategoryBean) {
                str = "" + ((SubcategoryBean) arg2).getId();
            }
            
            return str;
        }
}
