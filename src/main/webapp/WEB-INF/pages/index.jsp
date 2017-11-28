<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<head>

    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>SpringMVC Demo 首页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-control" content="no-cache">
    <meta http-equiv="Cache" content="no-cache">
    <!-- CSS 文件 -->
    <link rel="stylesheet" href="/resources/CSS/index.css">
    <link rel="stylesheet" href="/resources/CSS/neuralnet.css">
    <link rel="stylesheet" href="/resources/CSSLIB/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/CSSLIB/bootstrap/bootstrap-datetimepicker.min.css">
    <link rel="stylesheet" href="/resources/CSSLIB/Leaflet/leaflet.css">
    <link rel="stylesheet" href="/resources/CSSLIB/bootstrap/Buttons.css">
    <link href="http://cdn.bootcss.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/resources/CSSLIB/material/material.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="/resources/CSS/button.css">



    <!--JS文件-->
    <script type="text/javascript" src="/resources/JSLIB/d3/d3.js" charset="utf-8"></script>
    <script src="/resources/JSLIB/jquery/jquery-2.1.4.min.js"></script>
    <script type="text/javascript" src="/resources/JSLIB/bootstrap/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    <script type="text/javascript" src="/resources/JSLIB/bootstrap/bootstrap-datetimepicker.fr.js" charset="UTF-8"></script>
    <script type="text/javascript" src="/resources/JSLIB/leaflet/leaflet.js"></script>
    <script type="text/javascript" src="/resources/JSLIB/material/material.min.js"></script>
    <script type="text/javascript" src="/resources/JS/Button.js"></script>
    <script type="text/javascript" src="/resources/JSLIB/echarts/echarts.min.js" charset="utf-8"></script>

    <script src="/resources/JSLIB/bootstrap/bootstrap.min.js"></script>




</head>


<body>
<div id="div1">
    <div id="timepick">
        <p style="float: left;font-size: larger;vertical-align: middle;line-height: 32px;margin: 0 6px;">开始</p>
        <div class="input-group date form_datetime col-md-3 column" data-date="2016-01-01T06:00:07Z"
             data-date-format="dd MM yyyy - HH:ii p" data-link-field="dtp_input1" id="dateinput1">
            <input class="form-control" size="10" type="text" value="" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
        </div>
        <p style="float: left;font-size: larger;vertical-align: middle;line-height: 32px;margin: 0 6px;">结束</p>
        <div class="input-group date form_datetime col-md-3 column" data-date="2016-01-01T15:25:07Z"
             data-date-format="dd MM yyyy - HH:ii p" data-link-field="dtp_input2" id="dateinput2">
            <input class="form-control" size="10" type="text" value="" readonly>
            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
            <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
        </div>
        <input type="hidden" id="dtp_input1" value="2016-01-01 00:00:07"/><br/>
        <input type="hidden" id="dtp_input2" value="2016-01-02 00:00:07"/><br/>

    </div>
    <div id="map">

    </div>

    <div id="neuralnet">
        <div id="hidenLayers">
            <button class="button button-circle " ><i class="fa fa-plus"></i></button>
            <button class="button button-circle "><i class="fa fa-minus"></i></button>
            <p style="float: left;font-size: larger;vertical-align: middle;line-height: 38px;margin: 0 6px;">1隐藏层</p>
        </div>
        <div id="hidenLayer">
            <button class="button button-circle button-tiny" ><i class="fa fa-plus"></i></button>
            <button class="button button-circle button-tiny"><i class="fa fa-minus"></i></button>
            <input type="text" class="form-control" value="70">

        </div>
        <div id="neuralnodes">
        </div>
        <div id="neuralparam">
            <div id="top-controls">
                <div class="container l--page">
                    <div class="timeline-controls">
                        <button class="mdl-button mdl-js-button mdl-button--icon ui-resetButton" id="reset-button" title="Reset the network" data-upgraded=",MaterialButton">
                            <i class="material-icons">replay</i>
                        </button>
                        <button class="mdl-button mdl-js-button mdl-button--fab mdl-button--colored ui-playButton" id="play-pause-button" title="Run/Pause" data-upgraded=",MaterialButton">
                            <i class="material-icons">play_arrow</i>
                            <i class="material-icons">pause</i>
                        </button>
                        <button class="mdl-button mdl-js-button mdl-button--icon ui-stepButton" id="next-step-button" title="Step" data-upgraded=",MaterialButton">
                            <i class="material-icons">skip_next</i>
                        </button>
                    </div>
                    <div class="control">
                        <span class="label"></span>
                        <span class="value" id="iter-number">000,000</span>
                    </div>
                    <div class="control ui-learningRate">
                        <label for="learningRate">Learning rate</label>
                        <div class="select">
                            <select id="learningRate">
                                <option value="0.00001">0.00001</option>
                                <option value="0.0001">0.0001</option>
                                <option value="0.001">0.001</option>
                                <option value="0.003">0.003</option>
                                <option value="0.01">0.01</option>
                                <option value="0.03">0.03</option>
                                <option value="0.1">0.1</option>
                                <option value="0.3">0.3</option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                    </div>
                    <div class="control ui-activation">
                        <label for="activations">Activation</label>
                        <div class="select">
                            <select id="activations">
                                <option value="relu">ReLU</option>
                                <option value="tanh">Tanh</option>
                                <option value="sigmoid">Sigmoid</option>
                                <option value="linear">Linear</option>
                            </select>
                        </div>
                    </div>
                    <div class="control ui-regularizationRate">
                        <label for="regularRate">Regularization rate</label>
                        <div class="select">
                            <select id="regularRate">
                                <option value="0">0</option>
                                <option value="0.001">0.001</option>
                                <option value="0.003">0.003</option>
                                <option value="0.01">0.01</option>
                                <option value="0.03">0.03</option>
                                <option value="0.1">0.1</option>
                                <option value="0.3">0.3</option>
                                <option value="1">1</option>
                                <option value="3">3</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="div2">
    <div id="filter"></div>
    <div id="result"></div>
</div>

<script src="/resources/JS/datecontrol.js"></script>
<script src="/resources/JS/Leaflet.js"></script>
<script src="/resources/JS/drawneural.js"></script>
<script src="/resources/JS/pie.js"></script>
</body>

</html>