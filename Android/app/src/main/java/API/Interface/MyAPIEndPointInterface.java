package API.Interface;

import API.Items.Comment;
import API.Items.Entry;
import API.Items.EntryTagRelation;
import API.Items.FollowTopicRelation;
import API.Items.Predicate;
import API.Items.Result;
import API.Items.Tag;
import API.Items.Topic;
import API.Items.TopicTagRelation;
import API.Items.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyAPIEndPointInterface {

    //Get all users
    @GET("/users/")
    Call<Result> getAllUsers();
    //Get user by id
    @GET("/users/{id}/")
    Call<User> getUserById(@Path("id") int id);
    //Create new user
    @POST("/users/create/")
    Call<User> createUser(@Body User user);
    //Get all topics
    @GET("/topics/")
    Call<Result> getAllTopics();
    //Get topic by id
    @GET("/topics/{id}/")
    Call<Topic> getTopicById(@Path("id") int id);
    //Create new topic
    @POST("/topics/create/")
    Call<Topic> createTopic(@Body Topic topic);
    //Get all entries
    @GET("/entries/")
    Call<Result> getAllEntries();
    //Get entry by id
    @GET("/entries/{id}/")
    Call<Entry> getEntryById(@Path("id") int id);
    //Get entries of topic
    @GET("/topics/{id}/entries/")
    Call<Result> getEntriesByTopicId(@Path("id") int id);
    //Create entry
    @POST("/entries/create/")
    Call<Entry> createEntry(@Body Entry entry);
    //Get all comments
    @GET("/comments/")
    Call<Comment> getAllComments();
    //Get comment by id
    @GET("/comments/{id}/")
    Call<Comment> getCommentById(@Path("id") int id);
    //Get comments of an entry
    @GET("/entries/{id}/comments/")
    Call<Result> getCommentsByEntryId(@Path("id") int id);
    //Create comment
    @POST("/comments/create/")
    Call<Comment> createComment(@Body Comment comment);
    //Get all tags
    @GET("/tags/")
    Call<Result> getAllTags();
    //Get tag by id
    @GET("/tags/{id}/")
    Call<Tag> getTagById(@Path("id") int id);
    //Create tag
    @POST("/tags/create/")
    Call<Tag> createTag(@Body Tag tag);
    //Get all predicates
    @GET("/predicates/")
    Call<Result> getAllPredicates();
    //Get predicate by id
    @GET("/predicates/{id}/")
    Call<Predicate> getPredicateById(@Path("id") int id);
    //Create predicate
    @POST("/predicates/create/")
    Call<Predicate> createPredicate(@Body Predicate predicate);
    //Get all topic tag relations
    @GET("/topictagrelations/")
    Call<Result> getAllTopicTagRelations();
    //Get topic tag relation by id
    @GET("/topictagrelations/{id}/")
    Call<TopicTagRelation> getTopicTagRelationById(@Path("id") int id);
    //Create topic tag relation
    @POST("/topictagrelations/create/")
    Call<TopicTagRelation> createTopicTagRelation(@Body TopicTagRelation topicTagRelation);
    //Get all entry tag relations
    @GET("/entrytagrelations/")
    Call<EntryTagRelation> getAllEntryTagRelations();
    //Get entry tag relation by id
    @GET("/entrytagrelations/{id}")
    Call<EntryTagRelation> getEntryTagRelationById(@Path("id") int id);
    //Create entry tag relation
    @POST("/entrytagrelations/create/")
    Call<EntryTagRelation> createEntryTagRelation(@Body EntryTagRelation entryTagRelation);
    //Get all follow topic relations
    @GET("/followtopicrelations/")
    Call<FollowTopicRelation> getAllFollowTopicRelations();
    //Get follow topic relation by id
    @GET("/followtopicrelations/{id}/")
    Call<FollowTopicRelation> getFollowTopicRelationById(@Path("id") int id);
    //Create follow topic relation
    @POST("/followtopicrelations/create/")
    Call<FollowTopicRelation> createFollowTopicRealtion(@Body FollowTopicRelation followTopicRelation);
}

