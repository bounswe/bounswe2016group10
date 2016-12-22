package API.Items;

import java.util.Date;

public class Predicate {

    private int id;
    private String predicateString;
    private int[] relations;
    private Date created_at;
    private Date updated_at;

    public Predicate(String predicateString) {
        this.predicateString = predicateString;
    }
    //GETTERS

    public int getId() {
        return id;
    }

    public String getPredicateString() {
        return predicateString;
    }

    public int[] getRelations() {
        return relations;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
    //SETTERS
    //@TODO consider to restict to set the primary keys unless contrusting the object

    public void setId(int id) {
        this.id = id;
    }

    public void setPredicateString(String predicateString) { this.predicateString = predicateString; }

    public void setRelations(int[] relations) {
        this.relations = relations;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
