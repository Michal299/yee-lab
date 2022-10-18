package pl.edu.pg.s180564.ticket.model.converter;

import pl.edu.pg.s180564.ticket.model.ProjectModel;
import pl.edu.pg.s180564.ticket.model.UserModel;
import pl.edu.pg.s180564.user.service.UserService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(forClass = UserModel.class, managed = true)
public class UserModelConverter implements Converter<UserModel> {

    private UserService userService;

    @Inject
    public UserModelConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        final var maybeUser = userService.find(value);
        return maybeUser.map(UserModel::mapEntityToModel).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, UserModel value) {
        return value == null
                ? ""
                : value.getNickname();
    }
}
