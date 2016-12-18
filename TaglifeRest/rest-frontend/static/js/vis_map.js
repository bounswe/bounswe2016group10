var nodes_set = [];

setTimeout(function() { createMap();}, 1000)

function Node(id, label) {
    this.id = id;
    this.label = label;
}

function addTopicNodeset(topiclist) {
    $.each(topiclist, function (i, topic) {
        var node_obj = new Node(topic.id , topic.title);
        nodes_set.push(node_obj) ; 
    });
}
function createMap() {

    var nodes = new vis.DataSet(
        nodes_set
    );

    // create an array with edges
    var edges = new vis.DataSet([
        {from: 0, to: 2}
    ]);

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
    var topicID = params.nodes[0];
    var topicTitle = nodes_set.find(function(node) {
        return node.id === topicID;
    }).label;
    location.href = './topic.html?id='+ topicID + '&title='+ topicTitle ;
    });
}

