var predicateList = [];
var nodes_set = [];
var edge_set = [];

$.when(predPromise).then(function(predicates) {
    predicateList = predicates['results'];

});

setTimeout(function() { createMap();}, 1500)

function Node(id, label, color = null) {
    this.id = id;
    this.label = label;
    this.color = color;
}

function addTagNodeset(list) {
    $.each(list, function (i, node) {
        var node_obj = new Node("tag" + node.id , node.tagString, 'lime');
        nodes_set.push(node_obj) ; 
    });
}
function addTopicNodeset(list) {
    $.each(list, function (i, node) {
        var node_obj = new Node("topic" + node.id , node.title);
        nodes_set.push(node_obj) ; 
    });
}
function addRelationEdge(relations,predicateList) {
    $.each(relations, function(i, relation) {
       var predicate_label = predicateList.find(function(predicate) {
                            return predicate.id === relation.predicate;
                        }).predicateString;
       var edge_obj = {from: "tag" + relation.tag , to: "topic" + relation.topic , label: predicate_label , font: {background: 'yellow'}};
       edge_set.push(edge_obj); 
    });
}
function createMap() {
    var nodes = new vis.DataSet(nodes_set);

    // create an array with edges
    var edges = new vis.DataSet(edge_set);

    // create a network
    var container = document.getElementById('vismap');

    // provide the data in the vis format
    var data = {
        nodes: nodes,
        edges: edges
    };
    var options = {};

    // initialize your network!
    var network = new vis.Network(container, data, options);

    network.on('doubleClick', function(params) {
        var paramID = params.nodes[0];

        if (paramID[1] == 'o') {
            var topicID = paramID.substring(5);
            topicID = parseInt(topicID);
            // console.log(topicID);
            var topicTitle = nodes_set.find(function(node) {
                return parseInt(node.id.substring(5)) === topicID;
            }).label;
            location.href = './topic.html?id='+ topicID + '&title='+ topicTitle ;
        }
        else {
            var tagID = paramID.substring(3);
            tagID = parseInt(tagID);
            // console.log(topicID);
            var tagTitle = nodes_set.find(function(node) {
                return parseInt(node.id.substring(3)) === tagID;
            }).label;
            location.href = './tag.html?tag='+ tagID + '&title='+ tagTitle ;
        }
    
    });

}

