var nodes_set = [];

function Node(id, label) {
    this.id = id;
    this.label = label;
}

$.when(topicPromise).then(function (topic) {
    createMap(topic);
});

function createMap(topics) {
    $.each(topics['results'], function (i, topic) {
        var node_obj = new Node(i, topic.title);
        nodes_set.push(node_obj) ; 
    });

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
}