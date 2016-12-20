package API.Items;

import java.util.Date;

public class TopicTagRelation {

    private int id;
    private int topic;
    private int predicate;
    private int tag;
    private Date created_at;
    private Date updated_at;

    public TopicTagRelation(int topic,int predicate,int tag){
        this.topic = topic;
        this.predicate = predicate;
        this.tag = tag;
    }
    //GETTERS

    public int getId() {
        return id;
    }

    public int getTopic() {
        return topic;
    }

    public int getPredicate() {
        return predicate;
    }

    public int getTag() {
        return tag;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }
    //SETTERS

    public void setId(int id) {
        this.id = id;
    }

    public void setTopic(int topic) {
        this.topic = topic;
    }

    public void setPredicate(int predicate) {
        this.predicate = predicate;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
