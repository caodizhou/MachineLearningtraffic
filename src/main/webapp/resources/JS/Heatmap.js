/**
 * Created by cdz on 2017/2/16.
 */
function heatmapAJAX(a) {
    // console.log(d3.select(this).attr("id"));
    var data = new Object();
    // data.fz = a.attr("fz")
    // data.cphm = a.attr("cphm");
    // data.cllx = a.attr("cllx");
    // data.oo = a.attr("oo");
    // data.dd = a.attr("dd");
    // adddate(data);
    // if(polyset!=null)
    //     data.polygon = polyset;
    // else data.polygon = "";
    console.log(data);
    $.ajax({
        url: "Heatmap",
        type: "POST",
        data: data,
        dataType: "json",
        beforeSend: function () {
            // remvoheatmap();
            // removepath();
        },
        success: function(global) {
            // getcoor(global);
            // console.log(global);
            creatheatmap(global);
        }
    });
}

function creatheatmap(data) {
    var points = [];
    for(var p in data){
        points.push([data[p].lat,data[p].lng,data[p].count*10/data[0].count]);
    }
    // for(var p=0;p<data.length;p++){
    //     points.push([data[p].lat,data[p].lng,data[p].count/data[0].count]);
    // }
heat = L.heatLayer(points,{radius: 20}).addTo(mymap);
}
function creatheatmapcase1(data) {
    var points = [];
    for(var p in data){
        points.push([data[p].lat,data[p].lng,data[p].count/(data[0].count)]);
    }
    points.push([30.1752,120.0948,10000]);
    points.push([30.3448,120.2114,10000]);
    // for(var p=0;p<data.length;p++){
    //     points.push([data[p].lat,data[p].lng,data[p].count/data[0].count]);
    // }
    heat = L.heatLayer(points,{radius: 20}).addTo(mymap);
}
function remvoheatmap() {
    heat&&mymap.removeLayer(heat);
}