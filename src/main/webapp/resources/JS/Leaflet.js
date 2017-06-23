/**
 * Created by caodizhou on 2016/7/9.
 */
var heat;
var d3Overlay;
var zoomend;
var myChart;
var zoomst;

var polygon;
var mymap = L.map('map',{
    zoomControl:false
}).setView([30.3, 120.3], 13);
//var heat = L.heatLayer(point, {radius: 25}).addTo(mymap);
//var heat = L.heatLayer([], {radius: 25}).addTo(mymap);
// function onMapClick(e) {
//     console.log(e.latlng.lat + "," + e.latlng.lng);
// }
function removepolygon() {
    mymap.removeLayer(polygon);
    polyset = null;
}

L.tileLayer('https://api.tiles.mapbox.com/v4/mapbox.streets/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
    maxZoom: 18,
    attribution: 'Map data &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors, ' +
    '<a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
    'Imagery © <a href="http://mapbox.com">Mapbox</a>',
    id: 'mapbox.streets'
}).addTo(mymap);
mymap.selectArea.enable();
// caremaAjax();
// L.tileLayer('http://10.8.248.178:8989/{z}/{x}/{y}.png', {
//     maxZoom: 17
// }).addTo(mymap);
// var a = d3.rgb(255, 255, 255);  //浅蓝色
// var b = d3.rgb(0, 0, 255);    //蓝色
//
// //颜色插值函数
// var computeColor = d3.interpolate(a, b);
