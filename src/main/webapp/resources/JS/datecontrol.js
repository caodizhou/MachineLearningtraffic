/**
 * Created by HP on 2016/12/13.
 */
$('.form_datetime').datetimepicker({
    // format: "dd MM yyyy - hh:ii"

    // language:  'fr',
    weekStart: 1,
    todayBtn:  1,
    autoclose: 1,
    todayHighlight: 1,
    startView: 2,
    forceParse: 0,
    showMeridian: 1
});
console.log("aaa")
function adddate(data) {

    data.sdate = d3.select("#dtp_input1").attr("value");
    data.edate = d3.select("#dtp_input2").attr("value");

}
