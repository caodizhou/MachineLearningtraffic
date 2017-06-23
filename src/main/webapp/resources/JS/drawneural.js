var nodes=[
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 1},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 2},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
    {"label": "", "layer": 3},
];

var color = d3.scale.category20();

/*{
    "nodes": [
    {"label": "i0", "layer": 1},
    {"label": "i1", "layer": 1},
    {"label": "h0", "layer": 2},
    {"label": "i2", "layer": 1},
    {"label": "h1", "layer": 2},
    {"label": "h2", "layer": 2},
    {"label": "o0", "layer": 3}
]
}*/
// var nodes = [];
// for(var i=0;i<48;i++){
//     var node = new Object({"label":"","layer":1});
//     nodes.push(node);
// }
// for(var i=0;i<70;i++){
//     var node = new Object({"label":"","layer":2});
//     nodes.push(node);
// }
// for(var i=0;i<24;i++){
//     var node = new Object({"label":"","layer":3});
//     nodes.push(node);
// }
var width = $("#neuralnet").width();
var height = $("#neuralnet").height();
var svg = d3.select("div#neuralnet").append("svg")
    .attr("width", width)
    .attr("height", height);
drawneuralnet(nodes);
function drawneuralnet(graph) {
    var nodes = graph;

    // get network size
    var netsize = {};
    nodes.forEach(function (d) {
        if (d.layer in netsize) {
            netsize[d.layer] += 1;
        } else {
            netsize[d.layer] = 1;
        }
        d["lidx"] = netsize[d.layer];

    });

    // calc distances between nodes
    var largestLayerSize = Math.max.apply(
        null, Object.keys(netsize).map(function (i) {
            return netsize[i];
        }));

    var xdist = width / Object.keys(netsize).length,
        ydist = height / largestLayerSize;

    // create node locations
    nodes.map(function (d) {
        d["x"] = (d.layer - 0.5) * xdist;
        d["y"] = (d.lidx - 0.5) * (height / netsize[d.layer]);
    });

    // autogenerate links
    var links = [];
    nodes.map(function (d, i) {
        for (var n in nodes) {
            if (d.layer + 1 == nodes[n].layer) {
                links.push({"source": parseInt(i), "target": parseInt(n), "value": 1})
            }
        }
    }).filter(function (d) {
        return typeof d !== "undefined";
    });

    // draw links
    var link = svg.selectAll(".link")
        .data(links)
        .enter().append("line")
        .attr("class", "link")
        .attr("x1", function (d) {
            return nodes[d.source].x;
        })
        .attr("y1", function (d) {
            return nodes[d.source].y;
        })
        .attr("x2", function (d) {
            return nodes[d.target].x;
        })
        .attr("y2", function (d) {
            return nodes[d.target].y;
        })
        .style("stroke-width", function (d) {
            return Math.sqrt(d.value);
        });

    // draw nodes
    var node = svg.selectAll(".node")
        .data(nodes)
        .enter().append("g")
        .attr("transform", function (d) {
                return "translate(" + d.x + "," + d.y + ")";
            }
        );

    var circle = node.append("circle")
        .attr("class", "node")
        .attr("r", function (d) {
            return height / netsize[d.layer] / 4 > (height / 5 / 4) ? (height / 5 / 4) : height / netsize[d.layer] / 4
        })
        .style("fill", function (d) {
            return color(d.layer);
        });


    node.append("text")
        .attr("dx", "-.35em")
        .attr("dy", ".35em")
        .text(function (d) {
            return d.label;
        });
};


