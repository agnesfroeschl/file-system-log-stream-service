package ac.at.tuwien.logservice.entities;

public class Action {

    private String actionName;

    public Action(){}
    public Action(String actionName){
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    @Override
    public String toString() {
        return "Action{" +
                "actionName='" + actionName + '\'' +
                '}';
    }
}
