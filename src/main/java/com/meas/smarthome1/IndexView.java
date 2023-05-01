package com.meas.smarthome1;

import com.meas.smarthome1.Models.User;
import com.meas.smarthome1.Repos.UserRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Route;

import java.awt.*;

@Route("")
public class IndexView extends VerticalLayout {

    private UserRepository _userRepo;
    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private EmailField email = new EmailField("Email");
    private Grid<User> grid = new Grid<>(User.class);
    private Binder<User> binder = new Binder<>(User.class);

    public  IndexView(UserRepository user){
        this._userRepo = user;

        //var button = new Button("Click me!");
        //var textField = new TextField();

        //add(new HorizontalLayout(textField, button));

        //button.addClickListener(e -> {
        //    add(new Paragraph("Hello, " + textField.getValue()));
        //    textField.clear();
        //});

        grid.setColumns("firstName", "lastName", "email");
        add(getForm(), grid);
        refreshGrid();
    }

    private HorizontalLayout getForm(){
        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE);

        var addButton = new Button("Add a new User");
        addButton.addClickShortcut(Key.ENTER);
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        layout.add(firstName, lastName, email, addButton);

        binder.bindInstanceFields(this);

        addButton.addClickListener(click->{
           try{
               var user = new User();
               binder.writeBean(user);
               _userRepo.save(user);
               binder.readBean(new User());
               refreshGrid();
           }catch(ValidationException e){

           }
        });
        return layout;
    }

    private void refreshGrid(){
        grid.setItems(_userRepo.findAll());
    }
}
