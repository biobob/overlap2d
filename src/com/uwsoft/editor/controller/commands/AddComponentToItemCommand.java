package com.uwsoft.editor.controller.commands;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.uwsoft.editor.Overlap2D;
import com.uwsoft.editor.Overlap2DFacade;

/**
 * Created by CyberJoe on 7/2/2015.
 */
public class AddComponentToItemCommand extends EntityModifyRevertableCommand {

    private static final String CLASS_NAME = "com.uwsoft.editor.controller.commands.AddComponentToItemCommand";
    public static final String DONE = CLASS_NAME + "DONE";

    private Entity entity;
    private Component component;

    private void collectData() {
        Object[] payload = getNotification().getBody();
        entity = (Entity) payload[0];
        component = (Component) payload[1];
    }

    @Override
    public void doAction() {
        collectData();

        entity.add(component);

        Overlap2DFacade.getInstance().sendNotification(DONE, entity);
        Overlap2DFacade.getInstance().sendNotification(Overlap2D.ITEM_DATA_UPDATED, entity);
    }

    @Override
    public void undoAction() {
        entity.remove(component.getClass());

        Overlap2DFacade.getInstance().sendNotification(DONE, entity);
        Overlap2DFacade.getInstance().sendNotification(Overlap2D.ITEM_DATA_UPDATED, entity);
    }

    public static Object[] payload(Entity entity, Component component) {
        Object[] payload = new Object[2];
        payload[0] = entity;
        payload[1] = component;
        return payload;
    }
}
