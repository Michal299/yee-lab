package pl.edu.pg.s180564.ticket.model.converter;

import pl.edu.pg.s180564.ticket.model.PriorityModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = PriorityModel.class, managed = true)
public class PriorityModelConverter implements Converter<PriorityModel> {


    @Override
    public PriorityModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return PriorityModel.builder().priority(value).build();
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, PriorityModel value) {
        return value == null
                ? ""
                : value.getPriority();
    }
}
